package com.optum.ram.invoice.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.RequestScoped;

import com.optum.ram.invoice.bean.MemberData;
import com.optum.ram.invoice.entity.InvoiceDetail;
import com.optum.ram.invoice.entity.InvoiceHeader;
import com.optum.ram.invoice.entity.RateMatrix;
import com.optum.ram.invoice.entity.RevenueStreamHistory;
import com.optum.ram.invoice.entity.RsInvExclusion;

import org.jboss.logging.Logger;

@RequestScoped
public class InvoiceService implements IInvoiceService {

    private static final Logger LOG = Logger.getLogger(InvoiceService.class);

    /*
     * @Inject private IReconService reconService;
     */

    /*
     * @Inject private InvoiceDetailRepository invDetRepo;
     */

    StringBuilder ratesQuery;

    // Map<String, Long> invoiceExlMap = new HashMap<String, Long>();
    public static final String UHGIN_SEQ_REV_ID = "110114";

    // Map<String, Object> rateParams;

    //
    Date runDate = new Date();
    Date runMonth; // the current month
    Date currInvDate; // after when the current invoices should be generated
    Date currInvMonth; // the invoice to generate for the month

    @Override
    public void generateInvoice(MemberData newMemData, MemberData oldMemData, boolean isCurrInvoice) {
        String planName = newMemData.getPlanName();
        List<InvoiceDetail> currInvoiceList;
        List<InvoiceDetail> retroInvoiceList;
        // get the invoice parameters for the given planName
        List<RevenueStreamHistory> revenueStreamHisList = RevenueStreamHistory.getRevenueStreams(planName);

        if (revenueStreamHisList.size() > 0) {

            // get base revenue stream
            // List<RevenueStreamHistory> baseRevStreamHisList =
            // getBaseRevenueStream(revenueStreamHisList);

            for (RevenueStreamHistory revenueStreamHis : revenueStreamHisList) {
                // process only for base revenue streams
                if (revenueStreamHis.revenueStream.baseSeqRevenueId == null) {
                    // create a string of seq_revenue_ids for composite plans
                    // corresponding to this base rev stream
                    StringBuilder seqReveIdsb = new StringBuilder();
                    revenueStreamHisList.forEach(rev -> {
                        if (revenueStreamHis.seqRevenueId.equals(rev.seqRevenueId)
                                || revenueStreamHis.seqRevenueId.equals(rev.revenueStream.baseSeqRevenueId)) {
                            seqReveIdsb.append(rev.seqRevenueId + ",");
                        }
                    });
                    String seqRevIds = seqReveIdsb.substring(0, seqReveIdsb.length() - 1);
                    System.out.println(seqRevIds);

                    setRunMonth(revenueStreamHis.fullEligibilityDate.intValue());
                    if (isCurrInvoice) {
                        revenueStreamHis.goliveDate = new java.sql.Date(currInvMonth.getTime());
                    }
                    // Create a list of invoice beans named currInvoiceList for all the months the
                    // member is effective
                    currInvoiceList = createCurrInvoiceList(newMemData, revenueStreamHis, seqRevIds, isCurrInvoice);

                    System.out.println(newMemData.getSubscriberId() + " currInvoiceList : " + currInvoiceList.size());

                    /*
                     * USING FUNCTION HERE InvoiceDetail.saveInvoices(currInvoiceList);
                     * invDetRepo.generateInvoices(newMemData, oldMemData, revenueStreamHisList,
                     * isCurrInvoice); invDetRepo.deleteEntities(currInvoiceList, newMemData);
                     */

                    if (!isCurrInvoice) {
                        // retro invoice logic Fetch all the invoices
                        // from RAM_INVOICE_DETAIL table for the given memer
                        // eligibility segment and create a list of invoice beans named retroInvoiceList
                        if (oldMemData != null) {
                            retroInvoiceList = fetchRetroInvoiceList(oldMemData, revenueStreamHis, seqRevIds,
                                    isCurrInvoice);
                        } else {
                            retroInvoiceList = fetchRetroInvoiceList(newMemData, revenueStreamHis, seqRevIds,
                                    isCurrInvoice);
                        }
                        System.out.println(
                                newMemData.getSubscriberId() + " retroInvoiceList : " + retroInvoiceList.size());

                        // Beans in currInvoice and not in retroInvoice List<InvoiceDetail>
                        List<InvoiceDetail> newInvoices = compareInvoiceBeans(currInvoiceList, retroInvoiceList);
                        System.out.println(newMemData.getSubscriberId() + " newInvoices : " + newInvoices.size());
                        InvoiceDetail.saveInvoices(newInvoices);
                        newInvoices = null;

                        // Beans in retroInvoice and not in currInvoice List<InvoiceDetail>
                        retroInvoiceList = compareInvoiceBeans(retroInvoiceList, currInvoiceList);
                        updateRetroInvoiceEntities(retroInvoiceList);
                        System.out
                                .println(newMemData.getSubscriberId() + " retroInvoices : " + retroInvoiceList.size());
                        InvoiceDetail.saveInvoices(retroInvoiceList);
                        currInvoiceList = null;
                        retroInvoiceList = null;

                    } else { // current invoice logic
                        InvoiceDetail.saveInvoices(currInvoiceList);
                        currInvoiceList = null;
                    }
                }
            }
        }
        revenueStreamHisList = null;

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
     * @return List<RevenueStreamHistory>
     */
    /*
     * private List<RevenueStreamHistory>
     * getBaseRevenueStream(List<RevenueStreamHistory> revenueStreamHisList) {
     * Stream<RevenueStreamHistory> revStream = revenueStreamHisList.stream();
     * List<RevenueStreamHistory> baseRevStreamList = revStream
     * .filter(revestreamHist -> revestreamHist.revenueStream.baseSeqRevenueId ==
     * null) .collect(Collectors.toList()); revStream.close(); return
     * baseRevStreamList; }
     */

    /**
     * Create the list of effective revenue months for the given member
     * 
     * @param memData
     * @param revenueStreamHisList
     */
    private List<InvoiceDetail> createCurrInvoiceList(MemberData memData, RevenueStreamHistory revenueStreamHis,
            String seqReveIds, boolean isCurrInvoice) {
        List<InvoiceDetail> currInvoiceList = new ArrayList<InvoiceDetail>();
        if (memData.getEligStatus().equalsIgnoreCase("Y")) {
            Date revenueStartDate = null;
            Calendar calRevStartDate = Calendar.getInstance();
            Calendar calRevTermDate = Calendar.getInstance();
            String[] seqRevIds = seqReveIds.split(",");
            for (String seqRevId : seqRevIds) {
                // check invoice exclusion
                if (RsInvExclusion.isInvRsExlExists(Long.valueOf(seqRevId), memData.getPlanCode()) == 0) {
                    // if (isInvRsExlExists(revenueStreamHistory.seqRevenueId,
                    // memData.getPlanCode())) {
                    revenueStartDate = memData.getEffectiveDate().before(revenueStreamHis.goliveDate)
                            ? revenueStreamHis.goliveDate
                            : memData.getEffectiveDate();

                    while (revenueStartDate.before(memData.getTermDate())
                            && revenueStartDate.compareTo(currInvMonth) <= 0) {
                        // create an invoice record with the revenue dates
                        InvoiceDetail invoiceDetail = new InvoiceDetail();
                        calRevStartDate.setTime(revenueStartDate);
                        calRevStartDate.set(Calendar.HOUR_OF_DAY, 0);
                        calRevStartDate.set(Calendar.MINUTE, 0);
                        calRevStartDate.set(Calendar.SECOND, 0);
                        calRevStartDate.set(Calendar.MILLISECOND, 0);
                        calRevTermDate.setTime(calRevStartDate.getTime());
                        calRevTermDate.set(Calendar.DAY_OF_MONTH,
                                calRevStartDate.getActualMaximum(Calendar.DAY_OF_MONTH));
                        calRevTermDate.set(Calendar.HOUR_OF_DAY, 0);
                        calRevTermDate.set(Calendar.MINUTE, 0);
                        calRevTermDate.set(Calendar.SECOND, 0);
                        calRevTermDate.set(Calendar.MILLISECOND, 0);

                        if ("Y".equalsIgnoreCase(revenueStreamHis.proration)) {// If prorated plan
                            invoiceDetail.revenueStartDate = calRevStartDate.getTime();
                            invoiceDetail.revenuEndDate = memData.getTermDate().compareTo(calRevTermDate.getTime()) >= 0
                                    ? calRevTermDate.getTime()
                                    : memData.getTermDate();
                            calRevTermDate.setTime(invoiceDetail.revenuEndDate);
                        } else {
                            calRevStartDate.set(Calendar.DAY_OF_MONTH, 1);// else set to first day of the month
                            invoiceDetail.revenueStartDate = calRevStartDate.getTime();
                            invoiceDetail.revenuEndDate = calRevTermDate.getTime();
                        }

                        // get invoice header
                        InvoiceHeader invoiceHeader = getInvoiceHeader(Long.valueOf(seqRevId), calRevStartDate,
                                isCurrInvoice);
                        invoiceDetail.seqInvoiceHeaderId = invoiceHeader.seqInvoiceHeaderId;

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
                        invoiceDetail.seqRevenueId = Long.valueOf(seqRevId);
                        invoiceDetail.planCode = memData.getPlanCode();
                        invoiceDetail.planName = memData.getPlanName();
                        invoiceDetail.dateOfBirth = memData.getDateOfBirth();
                        invoiceDetail.gender = memData.getGender();
                        invoiceDetail.groupId = memData.getGroupId();
                        invoiceDetail.eligStatus = memData.getEligStatus();
                        invoiceDetail.billingRequired = revenueStreamHis.billingRequired;
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
                            List<RateMatrix> rateMatrixList = RateMatrix.fetchRates(revenueStreamHis, seqRevId, memData,
                                    new java.sql.Date(invoiceDetail.revenueStartDate.getTime()));
                            if (rateMatrixList.size() == 1) {
                                invoiceDetail.invoiceAmount = "Y".equalsIgnoreCase(revenueStreamHis.proration)
                                        ? calculateProratedRate(rateMatrixList.get(0),
                                                revenueStreamHis.prorationFormula, invoiceDetail.activeDays,
                                                calRevStartDate.getActualMaximum(Calendar.DAY_OF_MONTH))
                                        : rateMatrixList.get(0).rate;// if non-prorated use the rate amount if not
                                                                     // compute
                                                                     // the prorated rate
                                invoiceDetail.riskGroup = rateMatrixList.get(0).riskGroup;
                                invoiceDetail.productCode = rateMatrixList.get(0).productCode;
                                invoiceDetail.seqRateId = rateMatrixList.get(0).seqRateId;
                                rateMatrixList.stream().close();
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

                        currInvoiceList.add(invoiceDetail);
                        calRevTermDate.add(Calendar.DATE, 1);
                        revenueStartDate = calRevTermDate.getTime();
                    }
                }
            }

        }
        return currInvoiceList;

    }

    /**
     * Get the invoice header details
     * 
     * @param seqRevenueId
     * @param calRevStartDate
     * @param isCurrInvoice
     * @return
     */
    private InvoiceHeader getInvoiceHeader(Long seqRevenueId, Calendar calRevStartDate, boolean isCurrInvoice) {
        Optional<InvoiceHeader> invoiceHeaderOptional = null;
        try {
            invoiceHeaderOptional = InvoiceHeader.findInvoiceHeader(seqRevenueId, calRevStartDate);
            if (!invoiceHeaderOptional.isPresent()) {
                InvoiceHeader.createInvoiceHeader(seqRevenueId, calRevStartDate);
                invoiceHeaderOptional = InvoiceHeader.findInvoiceHeader(seqRevenueId, calRevStartDate);
            }
        } catch (Exception e) {
            invoiceHeaderOptional = InvoiceHeader.findInvoiceHeader(seqRevenueId, calRevStartDate);
        }
        return invoiceHeaderOptional.get();
    }

    /**
     * Calculate the invoice amount based on the proration formula
     * 
     * @param rateMatrix
     * @param prorationFormula
     * @param activeDays
     * @return double invoiceAmt
     */
    private double calculateProratedRate(RateMatrix ratematrix, String prorationFormula, int activeDays,
            int monthDays) {
        double invoiceAmt = 0.0;
        if ("YEAR".equalsIgnoreCase(prorationFormula) || "YEAR".equalsIgnoreCase(ratematrix.prorationFormula)) {
            invoiceAmt = ratematrix.rate * (activeDays * 12.0 / 365.0);
        } else if ("MONTH".equalsIgnoreCase(prorationFormula)
                || "MONTH".equalsIgnoreCase(ratematrix.prorationFormula)) {
            invoiceAmt = ratematrix.rate * (activeDays * 1.0 / monthDays);
        } else if (("DAYS".equalsIgnoreCase(prorationFormula) || "DAYS".equalsIgnoreCase(ratematrix.prorationFormula)
                && ratematrix.seqRevenueId.toString().equalsIgnoreCase(UHGIN_SEQ_REV_ID)) && ratematrix.rate != 0) {
            invoiceAmt = activeDays < 18 ? ratematrix.rate / 2 : ratematrix.rate;
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
    private List<InvoiceDetail> fetchRetroInvoiceList(MemberData memData, RevenueStreamHistory revenueStreamHistory,
            String seqReveIds, boolean isCurrInvoice) {
        Map<String, Object> params = new HashMap<String, Object>();
        /*
         * List<InvoiceDetail> retroInvoiceList =
         * invDetRepo.getRetroInvoiceDetails(memData, revenueStreamHisList,
         * isCurrInvoice);
         */
        StringBuilder queryString = new StringBuilder();
        params.put("PLAN_NAME", revenueStreamHistory.planName);
        queryString.append(" planName= :PLAN_NAME");
        // params.put("SEQ_REVENUE_ID", revenueStreamHistory.seqRevenueId);
        queryString.append(" and seqRevenueId in (" + seqReveIds + ") ");
        params.put("SUBSCRIBER_ID", memData.getSubscriberId());
        queryString.append(" and subscriberId = :SUBSCRIBER_ID");
        if (isCurrInvoice) {
            params.put("CURR_INV_MONTH", revenueStreamHistory.goliveDate);
            queryString.append(" and :CURR_INV_MONTH = date_trunc('month', revenueStartDate) ");
        } else {
            params.put("MEM_EFF_DATE", memData.getEffectiveDate());
            params.put("MEM_TERM_DATE", memData.getTermDate());
            queryString.append(" and (revenueStartDate between :MEM_EFF_DATE and :MEM_TERM_DATE ");
            queryString.append("    or revenuEndDate between :MEM_EFF_DATE and :MEM_TERM_DATE)");
        }
        return InvoiceDetail.getInvoiceDetails(queryString.toString(), params);
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
        Stream<InvoiceDetail> invListAStream = invoiceListA.stream();
        List<InvoiceDetail> filteredList = invListAStream.filter(inv -> !invoiceListB.contains(inv))
                .collect(Collectors.toList());
        invListAStream.close();

        return filteredList;
    }

    /* private boolean notExists(InvoiceDetail invA, List<InvoiceDetail> invoiceListB) {

        Iterator<InvoiceDetail> it = invoiceListB.iterator();
        boolean doesNotExist = true;

        while (it.hasNext()) {
            InvoiceDetail invB = it.next();
            if (invA.seqInvoiceHeaderId.equals(invB.seqInvoiceHeaderId)
                    && invA.revenueStartDate.compareTo(invB.revenueStartDate) == 0
                    && invA.revenuEndDate.compareTo(invB.revenuEndDate) == 0 && invA.invoiceAmount == invB.invoiceAmount
                    && invA.subscriberId.equals(invB.subscriberId) // && invA.invoiceStatus.equals(invB.invoiceStatus)
                    && ((invA.seqRateId != null && invA.seqRateId.equals(invB.seqRateId))
                            || (invA.seqRateId == null && invB.seqRateId == null))
                    && ((invA.errorCode != null && invA.errorCode.equals(invB.errorCode))
                            || (invA.errorCode == null && invB.errorCode == null))
                    && ((invA.familyCaseId != null && invA.familyCaseId.equals(invB.familyCaseId))
                            || (invA.familyCaseId == null && invB.familyCaseId == null))) {
                doesNotExist = false;
                break;
            }

        }

        return doesNotExist;
    } */

    /**
     * Update the retro invoice for negating the invoice amount and also negating
     * the previous error codes.
     * 
     * @param retroInvoices
     * @return List<InvoiceDetail>
     */
    private void updateRetroInvoiceEntities(List<InvoiceDetail> retroInvoices) {
        retroInvoices.forEach(inv -> negateInvoice(inv));
    }

    /**
     * Negate the invoice bean
     * 
     * @param inv
     * @return InvoiceDetail
     */
    private InvoiceDetail negateInvoice(InvoiceDetail inv) {
        inv.invoiceAmount = inv.invoiceAmount * -1;
        if (inv.errorCode != null && !inv.errorCode.isEmpty()) {
            inv.errorCode = null;
            inv.invoiceStatus = "Y";
        }

        return inv;
    }

}