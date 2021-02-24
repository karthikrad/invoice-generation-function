package com.ram.invoice.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import com.ram.invoice.bean.MemberData;
import com.ram.invoice.entity.InvoiceDetail;
import com.ram.invoice.entity.InvoiceHeader;
import com.ram.invoice.entity.RateMatrix;
import com.ram.invoice.entity.RevenueStreamHistory;
import com.ram.invoice.entity.RsInvExclusion;

@ApplicationScoped
public class InvoiceService implements IInvoiceService {

    StringBuilder ratesQuery;
    Map<String, Object> rateParams;
    List<InvoiceDetail> currInvoiceList;
    List<InvoiceDetail> retroInvoiceList;
    Date runDate = new Date();
    Date runMonth; // the current month
    Date currInvDate; // after when the current invoices should be generated
    Date currInvMonth; // the invoice to generate for the month

    @Override
    public void generateInvoice(MemberData memData, boolean isCurrInvoice) {
        String planName = memData.getPlanName();
        currInvoiceList = new ArrayList<>();
        retroInvoiceList = new ArrayList<>();
        System.out.println(memData.getSubscriberId());

        // get the invoice parameters for the given planName
        List<RevenueStreamHistory> revenueStreamHisList = RevenueStreamHistory.getRevenueStreams(planName);
        // get base revenue stream
        RevenueStreamHistory baseRevStreamHis = getBaseRevenueStream(revenueStreamHisList);

        // check invoce exclusion for the plancode
        boolean isInvExclExists = RsInvExclusion.isInvRsExlExists(baseRevStreamHis.seqRevenueId, memData.getPlanCode());
        if (!isInvExclExists) {

            setRunMonth(baseRevStreamHis.fullEligibilityDate.intValue());

            if (isCurrInvoice) {
                // if Current invoice the set the golive date to the current month so that
                // invoice get generated only for the current month run
                revenueStreamHisList.stream().forEach(
                        revenueStreamHis -> revenueStreamHis.goliveDate = new java.sql.Date(currInvMonth.getTime()));
            }
            // Create a list of invoice beans named currInvoiceList for all the months the
            // member is effective
            createCurrInvoiceList(memData, revenueStreamHisList, isCurrInvoice);

            if (!isCurrInvoice) {
                // retro invoice logic
                // Fetch all the invoices from RAM_INVOICE_DETAIL table for the given memer
                // eligibility segment and create a list of invoice beans named retroInvoiceList
                fetchRetroInvoiceList(memData, revenueStreamHisList);

                // Beans in currInvoice and not in retroInvoice
                List<InvoiceDetail> newInvoices = compareInvoiceBeans(currInvoiceList, retroInvoiceList);
                InvoiceDetail.saveInvoices(newInvoices);

                // Beans in retroInvoice and not in currInvoice
                List<InvoiceDetail> retroInvoices = compareInvoiceBeans(retroInvoiceList, currInvoiceList);
                retroInvoices = updateRetroInvoiceEntities(retroInvoices);
                InvoiceDetail.saveInvoices(retroInvoices);
            } else {
                // current invoice logic
                InvoiceDetail.saveInvoices(currInvoiceList);
            }
        }
    }

    /**
     * Set the current run dates
     * 
     * @param fullInvDat
     */
    private void setRunMonth(int fullInvDat) {
        Calendar runMonthCal = Calendar.getInstance();
        runMonthCal.set(Calendar.DAY_OF_MONTH, 1);
        runMonthCal.set(Calendar.HOUR_OF_DAY, 0);
        runMonthCal.set(Calendar.MINUTE, 0);
        runMonthCal.set(Calendar.SECOND, 0);
        runMonthCal.set(Calendar.MILLISECOND, 0);

        runMonth = runMonthCal.getTime();

        runMonthCal.set(Calendar.DATE, fullInvDat);
        currInvDate = runMonthCal.getTime();

        if (fullInvDat < 0) {
            runMonthCal.add(Calendar.MONTH, 1);// generate invoices for one month in advance
            runMonthCal.set(Calendar.DATE, 1);
        }
        currInvMonth = runMonthCal.getTime();
    }

    /**
     * Fetch the base Revenue Stream entity.
     * 
     * @param revenueStreamHisList
     * @return RevenueStreamHistory
     */
    private RevenueStreamHistory getBaseRevenueStream(List<RevenueStreamHistory> revenueStreamHisList) {

        List<RevenueStreamHistory> baseRevStreamList = revenueStreamHisList.stream()
                .filter(revestreamHist -> revestreamHist.revenueStream.baseSeqRevenueId == null)
                .collect(Collectors.toList());
        return baseRevStreamList.get(0);
    }

    /**
     * Create the list of effective revenue months for the given member
     * 
     * @param memData
     * @param revenueStreamHisList
     */
    private void createCurrInvoiceList(MemberData memData, List<RevenueStreamHistory> revenueStreamHisList,
            boolean isCurrInvoice) {
        currInvoiceList = new ArrayList<InvoiceDetail>();
        if (memData.getEligStatus().equalsIgnoreCase("Y")) {
            Date revenueStartDate = null;
            Calendar calRevStartDate = Calendar.getInstance();
            Calendar calRevTermDate = Calendar.getInstance();
            for (RevenueStreamHistory revenueStreamHistory : revenueStreamHisList) {
                revenueStartDate = memData.getEffectiveDate().before(revenueStreamHistory.goliveDate)
                        ? revenueStreamHistory.goliveDate
                        : memData.getEffectiveDate();

                while (revenueStartDate.before(memData.getTermDate())
                        && revenueStartDate.compareTo(currInvMonth) <= 0) {
                    // create an invoice record with the revenue dates
                    final InvoiceDetail invoiceDetail = new InvoiceDetail();
                    calRevStartDate.setTime(revenueStartDate);
                    calRevTermDate.setTime(calRevStartDate.getTime());
                    calRevTermDate.set(Calendar.DAY_OF_MONTH, calRevStartDate.getActualMaximum(Calendar.DAY_OF_MONTH));

                    if ("Y".equalsIgnoreCase(revenueStreamHistory.proration)) {// If prorated plan
                        invoiceDetail.revenueStartDate = calRevStartDate.getTime();
                        invoiceDetail.revenuEndDate = memData.getTermDate().compareTo(calRevTermDate.getTime()) >= 0
                                ? calRevTermDate.getTime()
                                : memData.getTermDate();
                    } else {
                        calRevStartDate.set(Calendar.DAY_OF_MONTH, 1);// else set to first day of the month
                        invoiceDetail.revenueStartDate = calRevStartDate.getTime();
                        invoiceDetail.revenuEndDate = calRevTermDate.getTime();
                    }
                    // update the invoice record with the member details
                    invoiceDetail.subscriberId = memData.getSubscriberId();
                    invoiceDetail.medicaidId = memData.getMedicaidId();
                    invoiceDetail.socialSecNo = memData.getSocialSecNo();
                    invoiceDetail.medicareNo = memData.getMedicareNo();
                    invoiceDetail.employeeNo = memData.getEmployeeNo();
                    invoiceDetail.familyCaseId = memData.getFamilyCaseId();
                    invoiceDetail.firstName = memData.getFirstName();
                    invoiceDetail.lastName = memData.getLastName();
                    invoiceDetail.countyCode = memData.getCountyCode();
                    invoiceDetail.seqRevenueId = revenueStreamHistory.seqRevenueId;
                    invoiceDetail.planCode = memData.getPlanCode();
                    invoiceDetail.planName = memData.getPlanName();
                    invoiceDetail.dateOfBirth = memData.getDateOfBirth();
                    invoiceDetail.gender = memData.getGender();
                    invoiceDetail.groupId = memData.getGroupId();
                    invoiceDetail.eligStatus = memData.getEligStatus();
                    invoiceDetail.billingRequired = revenueStreamHistory.billingRequired;
                    invoiceDetail.insertProcess = "INVFUNC";
                    invoiceDetail.updateProcess = "INVFUNC";
                    invoiceDetail.insertUser = "RAM";
                    invoiceDetail.updateUser = "RAM";
                    invoiceDetail.activeDays = calRevTermDate.get(Calendar.DAY_OF_MONTH)
                            - calRevStartDate.get(Calendar.DAY_OF_MONTH) + 1;
                    if (memData.getErrorCode() != null && !memData.getErrorCode().isEmpty()) {
                        invoiceDetail.errorCode = memData.getErrorCode();
                        invoiceDetail.invoiceStatus = "E";
                    } else {
                        invoiceDetail.invoiceStatus = "Y";
                        // Fetch the rates detail from RAM_RATE_MATRIX table for the corresponding
                        // eligibility segment as per the INVOICE_PARAMETER for the revenue stream
                        List<RateMatrix> rateMatrixList = RateMatrix.fetchRates(revenueStreamHistory, memData,
                                new java.sql.Date(invoiceDetail.revenueStartDate.getTime()));
                        if (rateMatrixList.size() == 1) {
                            invoiceDetail.invoiceAmount = "Y".equalsIgnoreCase(revenueStreamHistory.proration)
                                    ? calculateProratedRate(rateMatrixList.get(0).rate,
                                            revenueStreamHistory.prorationFormula, invoiceDetail.activeDays,
                                            calRevStartDate.getActualMaximum(Calendar.DAY_OF_MONTH))
                                    : rateMatrixList.get(0).rate;// if non-prorated use the rate amount if not compute
                                                                 // the
                                                                 // prorated rate
                            invoiceDetail.riskGroup = rateMatrixList.get(0).riskGroup;
                            invoiceDetail.productCode = rateMatrixList.get(0).productCode;
                        } else if (rateMatrixList.isEmpty()) {
                            // NRF
                            invoiceDetail.invoiceStatus = "E";
                            invoiceDetail.errorCode = "100";
                        } else {
                            // MRF
                            invoiceDetail.invoiceStatus = "E";
                            invoiceDetail.errorCode = "101";
                        }

                    }

                    // update invoice header
                    Optional<InvoiceHeader> invoiceHeaderOptional = InvoiceHeader
                            .findInvoiceHeader(revenueStreamHistory.seqRevenueId, calRevStartDate);
                    InvoiceHeader invoiceHeader = null;
                    if (invoiceHeaderOptional.isPresent()) {
                        invoiceHeader = invoiceHeaderOptional.get();
                    } else {
                        try {
                            InvoiceHeader.createInvoiceHeader(revenueStreamHistory.seqRevenueId, calRevStartDate);
                            invoiceHeaderOptional = InvoiceHeader.findInvoiceHeader(revenueStreamHistory.seqRevenueId,
                                    calRevStartDate);
                            invoiceHeader = invoiceHeaderOptional.get();
                        } catch (Exception e) {
                            invoiceHeaderOptional = InvoiceHeader.findInvoiceHeader(revenueStreamHistory.seqRevenueId,
                                    calRevStartDate);
                            invoiceHeader = invoiceHeaderOptional.get();
                        }
                    }
                    invoiceDetail.seqInvoiceHeaderId = invoiceHeader.seqInvoiceHeaderId;

                    currInvoiceList.add(invoiceDetail);
                    calRevTermDate.add(Calendar.DATE, 1);
                    revenueStartDate = calRevTermDate.getTime();
                }
            }

        }

    }

    /**
     * Calculate the invoice amount based on the proration formula
     * 
     * @param rateMatrix
     * @param prorationFormula
     * @param activeDays
     * @return double invoiceAmt
     */
    private double calculateProratedRate(double rateAmount, String prorationFormula, int activeDays, int monthDays) {
        double invoiceAmt = 0.0;
        if ("YEAR".equalsIgnoreCase(prorationFormula)) {
            invoiceAmt = rateAmount * (activeDays * 12.0 / 365.0);
        } else if ("MONTH".equalsIgnoreCase(prorationFormula)) {
            invoiceAmt = rateAmount * (activeDays * 1.0 / monthDays);
        }

        return invoiceAmt;
    }

    /**
     * Fetch all the invoices from RAM_INVOICE_DETAIL table for the given memer
     * eligibility segment
     * 
     * @param memData
     * @param revenueStreamHisList
     */
    private void fetchRetroInvoiceList(MemberData memData, List<RevenueStreamHistory> revenueStreamHisList) {
        Map<String, Object> params = new HashMap<String, Object>();
        retroInvoiceList = new ArrayList<InvoiceDetail>();
        for (RevenueStreamHistory revenueStreamHistory : revenueStreamHisList) {
            StringBuilder queryString = new StringBuilder();
            params.put("PLAN_NAME", revenueStreamHistory.planName);
            queryString.append(" planName= :PLAN_NAME");
            params.put("SEQ_REVENUE_ID", revenueStreamHistory.seqRevenueId);
            queryString.append(" and seqRevenueId= :SEQ_REVENUE_ID");
            params.put("MEM_EFF_DATE", memData.getEffectiveDate());
            params.put("MEM_TERM_DATE", memData.getTermDate());
            queryString.append(" and subscriberId = :SUBSCRIBER_ID");
            params.put("SUBSCRIBER_ID", memData.getSubscriberId());
            queryString.append(" and revenueStartDate between :MEM_EFF_DATE and :MEM_TERM_DATE");
            retroInvoiceList.addAll(InvoiceDetail.getInvoiceDetails(queryString.toString(), params));
        }

    }

    /**
     * Compare the two invoice bean lists and give the difference A-B
     * 
     * @param invoiceListA
     * @param invoiceListB
     * @return List<InvoiceDetail>
     */
    private List<InvoiceDetail> compareInvoiceBeans(List<InvoiceDetail> invoiceListA,
            List<InvoiceDetail> invoiceListB) {
        return invoiceListA.stream().filter(inv -> !invoiceListB.contains(inv)).collect(Collectors.toList());
    }

    /**
     * Update the retro invoice for negating the invoice amount and also negating
     * the previous error codes.
     * 
     * @param retroInvoices
     * @return List<InvoiceDetail>
     */
    private List<InvoiceDetail> updateRetroInvoiceEntities(List<InvoiceDetail> retroInvoices) {
        return retroInvoices.stream().map(inv -> negateInvoice(inv)).collect(Collectors.toList());
    }

    /**
     * Negate the invoice bean
     * 
     * @param inv
     * @return InvoiceDetail
     */
    private InvoiceDetail negateInvoice(InvoiceDetail inv) {
        inv.invoiceAmount = inv.invoiceAmount * -1;
        if (!inv.errorCode.isEmpty()) {
            inv.errorCode = "";
            inv.invoiceStatus = "Y";
        }
        return inv;
    }

}