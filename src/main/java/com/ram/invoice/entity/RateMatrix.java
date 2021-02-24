package com.ram.invoice.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ram.invoice.bean.MemberData;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "ram_rate_matrix", schema = "invoice")
public class RateMatrix extends PanacheEntityBase implements Serializable {
    /**
     * Default serialize id
     */
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "seq_rate_id")
    public Long seqRateId;
    @Column(name = "old_seq_rate_id")
    public Long oldSeqRateId;
    @Column(name = "seq_revenue_id")
    public Long seqRevenueId;
    @Column(name = "plan_code")
    public String planCode;
    @Column(name = "risk_group")
    public String riskGroup;
    @Column(name = "company_code")
    public String companyCode;
    @Column(name = "effective_date")
    public Date effectiveDate;
    @Column(name = "term_date")
    public Date termDate;
    @Column(name = "status")
    public String status;
    @Column(name = "rate")
    public Double rate;
    @Column(name = "gender")
    public String gender;
    @Column(name = "county")
    public String county;
    @Column(name = "product_code")
    public String productCode;
    @Column(name = "gl_product")
    public Double glProduct;
    @Column(name = "rate_desc")
    public String rateDesc;
    @Column(name = "age_from")
    public Double ageFrom;
    @Column(name = "age_to")
    public Double ageTo;
    @Column(name = "category_of_assistance")
    public String categoryOfAssistance;
    @Column(name = "program_status_code")
    public String programStatusCode;
    @Column(name = "user_defined_1")
    public String userDefined1;
    @Column(name = "user_defined_2")
    public String userDefined2;
    @Column(name = "insert_datetime")
    public Timestamp insertDatetime;
    @Column(name = "insert_user")
    public String insertUser;
    @Column(name = "update_datetime")
    public Timestamp updateDatetime;
    @Column(name = "update_user")
    public String updateUser;
    @Column(name = "base_seq_revenue_id")
    public Long baseSeqRevenueId;
    @Column(name = "revenue_type")
    public String revenueType;
    @Column(name = "proration_formula")
    public String prorationFormula;
    @Column(name = "retro_rate_trigger")
    public Timestamp retroRateTrigger;
    @Column(name = "seq_rate_file_id")
    public Long seqRateFileId;

    public RateMatrix() {

    }

    public RateMatrix(Long seqRateId, Long oldSeqRateId, Long seqRevenueId, String planCode, String riskGroup,
            String companyCode, Date effectiveDate, Date termDate, String status, Double rate, String gender,
            String county, String productCode, Double glProduct, String rateDesc, Double ageFrom, Double ageTo,
            String categoryOfAssistance, String programStatusCode, String userDefined1, String userDefined2,
            Timestamp insertDatetime, String insertUser, Timestamp updateDatetime, String updateUser,
            Long baseSeqRevenueId, String revenueType, String prorationFormula, Timestamp retroRateTrigger,
            Long seqRateFileId) {
        this.seqRateId = seqRateId;
        this.oldSeqRateId = oldSeqRateId;
        this.seqRevenueId = seqRevenueId;
        this.planCode = planCode;
        this.riskGroup = riskGroup;
        this.companyCode = companyCode;
        this.effectiveDate = effectiveDate;
        this.termDate = termDate;
        this.status = status;
        this.rate = rate;
        this.gender = gender;
        this.county = county;
        this.productCode = productCode;
        this.glProduct = glProduct;
        this.rateDesc = rateDesc;
        this.ageFrom = ageFrom;
        this.ageTo = ageTo;
        this.categoryOfAssistance = categoryOfAssistance;
        this.programStatusCode = programStatusCode;
        this.userDefined1 = userDefined1;
        this.userDefined2 = userDefined2;
        this.insertDatetime = insertDatetime;
        this.insertUser = insertUser;
        this.updateDatetime = updateDatetime;
        this.updateUser = updateUser;
        this.baseSeqRevenueId = baseSeqRevenueId;
        this.revenueType = revenueType;
        this.prorationFormula = prorationFormula;
        this.retroRateTrigger = retroRateTrigger;
        this.seqRateFileId = seqRateFileId;
    }

    /**
     * Fetch the rates detail from RAM_RATE_MATRIX table for the corresponding
     * eligibility segment as per the INVOICE_PARAMETER for the revenue stream
     * 
     * @param revenueStreamHistory
     * @param memData
     * @param revenueStartDate
     * @return List<RateMatrix>
     */
    public static List<RateMatrix> fetchRates(RevenueStreamHistory revenueStreamHistory, MemberData memData,
            Date revenueStartDate) {

        StringBuilder ratesQuery = new StringBuilder();
        Map<String, Object> rateParams = new HashMap<>();
        double computedAge = 0.0;

        String[] invParams = revenueStreamHistory.invoiceParameters.split(",");

        // compute age
        LocalDate dobLocalDate = memData.getDateOfBirth().toLocalDate();
        LocalDate reveStartLocDate = revenueStartDate.toLocalDate();
        if ("UHGMI".equalsIgnoreCase(memData.getPlanName())) {
            reveStartLocDate = reveStartLocDate.plusDays(14);
        }
        Period agePeriod = Period.between(dobLocalDate, reveStartLocDate);
        if ("IACAP".equalsIgnoreCase(memData.getPlanName())) {
            computedAge = agePeriod.getDays() / 365.25;
        } else {
            computedAge = agePeriod.toTotalMonths() / 12.0;
        }

        rateParams.put("SEQ_REVENUE_ID", revenueStreamHistory.seqRevenueId);
        ratesQuery.append("seqRevenueId = :SEQ_REVENUE_ID ");
        for (String invParam : invParams) {
            if ("PLAN_CODE".equalsIgnoreCase(invParam)) {
                rateParams.put(invParam, memData.getPlanCode());
                ratesQuery.append("and planCode = :PLAN_CODE ");
            } else if ("GENDER".equalsIgnoreCase(invParam)) {
                rateParams.put(invParam, memData.getGender());
                ratesQuery.append("and (gender = :GENDER or gender = 'B' )");
            } else if ("COUNTY".equalsIgnoreCase(invParam)) {
                rateParams.put(invParam, memData.getCountyCode());
                ratesQuery.append("and county = :COUNTY ");
            } else if ("AGE".equalsIgnoreCase(invParam)) {
                rateParams.put(invParam, computedAge);
                ratesQuery.append("and :AGE between ageFrom and ageTo ");
            } else if ("AGE_MONTHS".equalsIgnoreCase(invParam)) {
                rateParams.put(invParam, computedAge);
                ratesQuery.append("and :AGE_MONTHS between ageFrom and ageTo ");
            }
        }
        rateParams.put("REV_START_DATE", revenueStartDate);// updating the date parameter
        ratesQuery.append("and :REV_START_DATE between effectiveDate and termDate ");

        return list(ratesQuery.toString(), rateParams);
    }

}