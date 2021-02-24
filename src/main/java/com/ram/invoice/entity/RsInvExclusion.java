package com.ram.invoice.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "ram_rs_inv_exclusion", schema = "invoice")
public class RsInvExclusion extends PanacheEntityBase implements Serializable {
    /**
     * Default serialize id
     */
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "seq_revenue_id")
    public long seqRevenueId;
    @Id
    @Column(name = "plan_code")
    public String planCode;
    @Column(name = "gender")
    public String gender;
    @Column(name = "seq_county_id")
    public Long seqCountyId;
    @Column(name = "age")
    public String age;
    @Column(name = "insert_datetime")
    public java.sql.Timestamp insertDatetime;
    @Column(name = "insert_process")
    public String insertProcess;
    @Column(name = "insert_user")
    public String insertUser;
    @Column(name = "update_datetime")
    public java.sql.Timestamp updateDatetime;
    @Column(name = "update_process")
    public String updateProcess;
    @Column(name = "update_user")
    public String updateUser;

    public RsInvExclusion() {

    }

    public RsInvExclusion(long seqRevenueId, String planCode, String gender, long seqCountyId, String age,
            java.sql.Timestamp insertDatetime, String insertProcess, String insertUser,
            java.sql.Timestamp updateDatetime, String updateProcess, String updateUser) {
        this.seqRevenueId = seqRevenueId;
        this.planCode = planCode;
        this.gender = gender;
        this.seqCountyId = seqCountyId;
        this.age = age;
        this.insertDatetime = insertDatetime;
        this.insertProcess = insertProcess;
        this.insertUser = insertUser;
        this.updateDatetime = updateDatetime;
        this.updateProcess = updateProcess;
        this.updateUser = updateUser;
    }

    public static boolean isInvRsExlExists(Long seqRevenueId, String planCode) {
        boolean isInvExlExist = false;
        Map<String, Object> params = new HashMap<>();
        params.put("PLAN_CODE", planCode);
        params.put("SEQ_REVENUE_ID", seqRevenueId);
        long count = find(" planCode= :PLAN_CODE and seqRevenueId = :SEQ_REVENUE_ID ", params).count();
        if (count > 0){
            isInvExlExist = true;
        }
        return isInvExlExist;
    }
}
