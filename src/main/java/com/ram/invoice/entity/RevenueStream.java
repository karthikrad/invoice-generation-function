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
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "ram_revenue_stream", schema = "invoice")
public class RevenueStream extends PanacheEntityBase implements Serializable {

    /**
     * Default serialization id
     */
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "seq_revenue_id")
    public Long seqRevenueId;
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
    @Column(name = "rs_description")
    public String rsDescription;
    @Column(name = "term_date")
    public Date termDate;
    @Column(name = "taxable_flag")
    public String taxableFlag;
    @Column(name = "base_seq_revenue_id")
    public Long baseSeqRevenueId;
    @Column(name = "priority")
    public Short priority;
    @Column(name = "dist_status_code")
    public String distStatuscode;
    @Column(name = "implementation_date")
    public Date implementationDate;
    @Column(name = "hold_reconciliation")
    public String holdReconciliation;
    @Column(name = "hold_invoice")
    public String holdInvoice;
    @Column(name = "user_comments")
    public String userComments;
    @Column(name = "hold_finance_reports")
    public String holdFinanceReports;

    public RevenueStream() {

    }

    public RevenueStream(long seqRevenueId, String sourceCode, String planName, String companyCode, String revenueType,
            Date goliveDate, Timestamp insertDatetime, String insertProcess, String insertUser,
            Timestamp updateDatetime, String updateProcess, String updateUser, String rsDescription, Date termDate,
            String taxableFlag, long baseSeqRevenueId, short priority, String distStatuscode, Date implementationDate,
            String holdReconciliation, String holdInvoice, String userComments, String holdFinanceReports) {
        this.seqRevenueId = seqRevenueId;
        this.sourceCode = sourceCode;
        this.planName = planName;
        this.companyCode = companyCode;
        this.revenueType = revenueType;
        this.goliveDate = goliveDate;
        this.insertDatetime = insertDatetime;
        this.insertProcess = insertProcess;
        this.insertUser = insertUser;
        this.updateDatetime = updateDatetime;
        this.updateProcess = updateProcess;
        this.updateUser = updateUser;
        this.rsDescription = rsDescription;
        this.termDate = termDate;
        this.taxableFlag = taxableFlag;
        this.baseSeqRevenueId = baseSeqRevenueId;
        this.priority = priority;
        this.distStatuscode = distStatuscode;
        this.implementationDate = implementationDate;
        this.holdReconciliation = holdReconciliation;
        this.holdInvoice = holdInvoice;
        this.userComments = userComments;
        this.holdFinanceReports = holdFinanceReports;
    }

    /**
     * Fetch the composite revenue stream details.
     * 
     * @param planName
     * @return List<RevenueStream>
     */
    public static List<RevenueStream> findCompositePlans(String planName) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("PLAN_NAME", planName);
        params.put("SYSDATE", Calendar.getInstance().getTime());
        return list("planName= :PLAN_NAME and goliveDate < :SYSDATE ", params);
    }

}