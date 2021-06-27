package com.optum.ram.invoice.entity;

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

    public RsInvExclusion() {
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((planCode == null) ? 0 : planCode.hashCode());
        result = prime * result + (int) (seqRevenueId ^ (seqRevenueId >>> 32));
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
        RsInvExclusion other = (RsInvExclusion) obj;
        if (planCode == null) {
            if (other.planCode != null)
                return false;
        } else if (!planCode.equals(other.planCode))
            return false;
        if (seqRevenueId != other.seqRevenueId)
            return false;
        return true;
    }

    public RsInvExclusion(long seqRevenueId, String planCode) {
        this.seqRevenueId = seqRevenueId;
        this.planCode = planCode;
    }

    public static long isInvRsExlExists(Long seqRevenueId, String planCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("PLAN_CODE", planCode);
        params.put("SEQ_REVENUE_ID", seqRevenueId);
        return count(" planCode= :PLAN_CODE and seqRevenueId = :SEQ_REVENUE_ID ", params);
    }
}
