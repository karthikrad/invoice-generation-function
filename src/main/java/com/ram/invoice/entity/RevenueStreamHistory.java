package com.ram.invoice.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "ram_revenue_stream_history", schema = "invoice")
public class RevenueStreamHistory extends PanacheEntityBase implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "seq_revenue_id")
    public Long seqRevenueId;
    @Id    
    @Column(name = "effective_date")
    public Date effectiveDate;
    @Column(name = "term_date")
    public Date termDate;
    @Column(name = "source_code")
    public String sourceCode;
    @Column(name = "plan_name")
    public String planName;
    @Column(name = "company_code")
    public String companyCode;
    @Column(name = "revenue_type")
    public String revenueType;
    @Column(name = "golive_date")
    public Date goliveDate;
    @Column(name = "proration")
    public String proration;
    @Column(name = "proration_formula")
    public String prorationFormula;
    @Column(name = "variance_threshold")
    public Double varianceThreshold;
    @Column(name = "billing_required")
    public String billingRequired;
    @Column(name = "billing_format")
    public String billingFormat;
    @Column(name = "advance_bill_months")
    public Double advanceBillMonths;
    @Column(name = "void_billing_flag")
    public String voidBillingFlag;
    @Column(name = "rebilling_flag")
    public String rebillingFlag;
    @Column(name = "invoice_parameters")
    public String invoiceParameters;
    @Column(name = "payment_parameters")
    public String paymentParameters;
    @Column(name = "recon_parameters")
    public String reconParameters;
    @Column(name = "member_key")
    public String memberKey;
    @Column(name = "gl_business_unit")
    public Double glBusinessUnit;
    @Column(name = "gl_business_account")
    public Double glBusinessAccount;
    @Column(name = "insert_datetime")
    public Timestamp insertDatetime;
    @Column(name = "insert_process")
    public String insertProcess;
    @Column(name = "insert_user")
    public String insertUser;
    @Column(name = "update_datetime")
    public Timestamp updateDatetime;
    @Column(name = "update_process")
    public String updateProcess;
    @Column(name = "update_user")
    public String updateUser;
    @Column(name = "payment_file_name")
    public String paymentFileName;
    @Column(name = "billing_file_name")
    public String billingFileName;
    @Column(name = "line_of_business")
    public String lineOfBusiness;
    @Column(name = "full_eligibility_date")
    public Double fullEligibilityDate;
    @Column(name = "payment_format")
    public String paymentFormat;
    @Column(name = "error_invalid_rate")
    public String errorInvalidRate;
    @Column(name = "last_invoice_end_date")
    public Date lastInvoiceEndDate;
    @Column(name = "pda_back_in_months")
    public Long pdaBackInMonths;
    @Column(name = "pnb_back_in_months")
    public Long pnbBackInMonths;
    @Column(name = "bnp_back_in_months")
    public Long bnpBackInMonths;
    @Column(name = "member_pick_logic")
    public String memberPickLogic;
    @Column(name = "invoice_validations")
    public String invoiceValidations;
    @Column(name = "inv_partial_month")
    public String invPartialMonth;
    @Column(name = "payment_err_flag")
    public String paymentErrFlag;
    @Column(name = "taxable_rate")
    public Double taxableRate;
    @Column(name = "match_dates")
    public String matchDates;
    @Column(name = "premium_memb_key")
    public String premiumMembKey;
    @Column(name = "data_archival_flag")
    public String dataArchivalFlag;
    @Column(name = "data_arc_back_in_mnths")
    public Long dataArcBackInMnths;
    @Column(name = "force_close_threshold_amount")
    public Double forceCloseThresholdAmount;
    @Column(name = "prorate_pick_first_elig")
    public String proratePickFirstElig;
    @Column(name = "fix_payment_flag")
    public String fixPaymentFlag;
    @OneToOne
    @JoinColumn(name = "seq_revenue_id")
    public RevenueStream revenueStream;

    public RevenueStreamHistory() {
    }

    public RevenueStreamHistory(long seqRevenueId, Date effectiveDate, Date termDate, String sourceCode,
            String planName, String companyCode, String revenueType, Date goliveDate, String proration,
            String prorationFormula, Double varianceThreshold, String billingRequired, String billingFormat,
            Double advanceBillMonths, String voidBillingFlag, String rebillingFlag, String invoiceParameters,
            String paymentParameters, String reconParameters, String memberKey, Double glBusinessUnit,
            Double glBusinessAccount, Timestamp insertDatetime, String insertProcess, String insertUser,
            Timestamp updateDatetime, String updateProcess, String updateUser, String paymentFileName,
            String billingFileName, String lineOfBusiness, double fullEligibilityDate, String paymentFormat,
            String errorInvalidRate, Date lastInvoiceEndDate, Long pdaBackInMonths, Long pnbBackInMonths,
            Long bnpBackInMonths, String memberPickLogic, String invoiceValidations, String invPartialMonth,
            String paymentErrFlag, Double taxableRate, String matchDates, String premiumMembKey,
            String dataArchivalFlag, Long dataArcBackInMnths, Double forceCloseThresholdAmount,
            String proratePickFirstElig, String fixPaymentFlag, RevenueStream revenueStream) {
        this.seqRevenueId = seqRevenueId;
        this.effectiveDate = effectiveDate;
        this.termDate = termDate;
        this.sourceCode = sourceCode;
        this.planName = planName;
        this.companyCode = companyCode;
        this.revenueType = revenueType;
        this.goliveDate = goliveDate;
        this.proration = proration;
        this.prorationFormula = prorationFormula;
        this.varianceThreshold = varianceThreshold;
        this.billingRequired = billingRequired;
        this.billingFormat = billingFormat;
        this.advanceBillMonths = advanceBillMonths;
        this.voidBillingFlag = voidBillingFlag;
        this.rebillingFlag = rebillingFlag;
        this.invoiceParameters = invoiceParameters;
        this.paymentParameters = paymentParameters;
        this.reconParameters = reconParameters;
        this.memberKey = memberKey;
        this.glBusinessUnit = glBusinessUnit;
        this.glBusinessAccount = glBusinessAccount;
        this.insertDatetime = insertDatetime;
        this.insertProcess = insertProcess;
        this.insertUser = insertUser;
        this.updateDatetime = updateDatetime;
        this.updateProcess = updateProcess;
        this.updateUser = updateUser;
        this.paymentFileName = paymentFileName;
        this.billingFileName = billingFileName;
        this.lineOfBusiness = lineOfBusiness;
        this.fullEligibilityDate = fullEligibilityDate;
        this.paymentFormat = paymentFormat;
        this.errorInvalidRate = errorInvalidRate;
        this.lastInvoiceEndDate = lastInvoiceEndDate;
        this.pdaBackInMonths = pdaBackInMonths;
        this.pnbBackInMonths = pnbBackInMonths;
        this.bnpBackInMonths = bnpBackInMonths;
        this.memberPickLogic = memberPickLogic;
        this.invoiceValidations = invoiceValidations;
        this.invPartialMonth = invPartialMonth;
        this.paymentErrFlag = paymentErrFlag;
        this.taxableRate = taxableRate;
        this.matchDates = matchDates;
        this.premiumMembKey = premiumMembKey;
        this.dataArchivalFlag = dataArchivalFlag;
        this.dataArcBackInMnths = dataArcBackInMnths;
        this.forceCloseThresholdAmount = forceCloseThresholdAmount;
        this.proratePickFirstElig = proratePickFirstElig;
        this.fixPaymentFlag = fixPaymentFlag;
        this.revenueStream = revenueStream;
    }

    public static List<RevenueStreamHistory> getRevenueStreams(String planName) {
        Map<String, Object> params = new HashMap<>();
        params.put("PLAN_NAME", planName);
        params.put("SYSDATE", Calendar.getInstance().getTime());
        return list("planName= :PLAN_NAME and (termDate is null or termDate >= :SYSDATE ) AND goliveDate < :SYSDATE", params);
    }

}
