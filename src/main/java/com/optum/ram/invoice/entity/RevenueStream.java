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
import javax.persistence.Table;
import javax.transaction.Transactional;

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
    @Column(name = "plan_name")
    public String planName;
    @Column(name = "company_code")
    public String companyCode;
    @Column(name = "revenue_type")
    public String revenueType;
    @Column(name = "golive_date")
    public Date goliveDate;   
    @Column(name = "term_date")
    public Date termDate;  
    @Column(name = "base_seq_revenue_id")
    public Long baseSeqRevenueId;
    @Column(name = "hold_invoice")
    public String holdInvoice;   

    public RevenueStream() {

    }

    public RevenueStream(long seqRevenueId, String planName, String companyCode, String revenueType,
            Date goliveDate, Date termDate,
            long baseSeqRevenueId, 
            String holdInvoice) {
        this.seqRevenueId = seqRevenueId;        
        this.planName = planName;
        this.companyCode = companyCode;
        this.revenueType = revenueType;
        this.goliveDate = goliveDate;        
        this.termDate = termDate;        
        this.baseSeqRevenueId = baseSeqRevenueId;        
        this.holdInvoice = holdInvoice;
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


    @Transactional
    public static List<RevenueStream> getRevenueStreams(String planName) {
        Map<String, Object> params = new HashMap<>();
        params.put("PLAN_NAME", planName);
        params.put("SYSDATE", Calendar.getInstance().getTime());
        return list("planName= :PLAN_NAME and baseSeqRevenueId is NULL and (termDate is null or termDate >= :SYSDATE ) AND goliveDate < :SYSDATE",
                params);
    }

}