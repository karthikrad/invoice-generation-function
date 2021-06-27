package com.optum.ram.invoice.entity;

import java.io.Serializable;
import java.sql.Date;
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
import javax.transaction.Transactional;

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
    @Column(name = "billing_required")
    public String billingRequired;
    @Column(name = "invoice_parameters")
    public String invoiceParameters;
    @Column(name = "full_eligibility_date")
    public Double fullEligibilityDate;
    @Column(name = "inv_partial_month")
    public String invPartialMonth;
    @OneToOne
    @JoinColumn(name = "seq_revenue_id")
    public RevenueStream revenueStream;

    public RevenueStreamHistory() {
    }

    public RevenueStreamHistory(long seqRevenueId, Date effectiveDate, Date termDate, String planName,
            String companyCode, String revenueType, Date goliveDate, String proration, String prorationFormula,
            String billingRequired, String invoiceParameters, double fullEligibilityDate, String invPartialMonth,
            RevenueStream revenueStream) {
        this.seqRevenueId = seqRevenueId;
        this.effectiveDate = effectiveDate;
        this.termDate = termDate;
        this.planName = planName;
        this.companyCode = companyCode;
        this.revenueType = revenueType;
        this.goliveDate = goliveDate;
        this.proration = proration;
        this.prorationFormula = prorationFormula;
        this.billingRequired = billingRequired;
        this.invoiceParameters = invoiceParameters;
        this.fullEligibilityDate = fullEligibilityDate;
        this.invPartialMonth = invPartialMonth;
        this.revenueStream = revenueStream;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((effectiveDate == null) ? 0 : effectiveDate.hashCode());
        result = prime * result + ((planName == null) ? 0 : planName.hashCode());
        result = prime * result + ((seqRevenueId == null) ? 0 : seqRevenueId.hashCode());
        result = prime * result + ((termDate == null) ? 0 : termDate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RevenueStreamHistory other = (RevenueStreamHistory) obj;
        if (effectiveDate == null) {
            if (other.effectiveDate != null)
                return false;
        } else if (!effectiveDate.equals(other.effectiveDate))
            return false;
        if (planName == null) {
            if (other.planName != null)
                return false;
        } else if (!planName.equals(other.planName))
            return false;
        if (seqRevenueId == null) {
            if (other.seqRevenueId != null)
                return false;
        } else if (!seqRevenueId.equals(other.seqRevenueId))
            return false;
        if (termDate == null) {
            if (other.termDate != null)
                return false;
        } else if (!termDate.equals(other.termDate))
            return false;
        return true;
    }

    @Transactional
    public static List<RevenueStreamHistory> getRevenueStreams(String planName) {
        Map<String, Object> params = new HashMap<>();
        params.put("PLAN_NAME", planName);
        params.put("SYSDATE", Calendar.getInstance().getTime());
        return list("planName= :PLAN_NAME and (termDate is null or termDate >= :SYSDATE ) AND goliveDate < :SYSDATE",
                params);
    }

}
