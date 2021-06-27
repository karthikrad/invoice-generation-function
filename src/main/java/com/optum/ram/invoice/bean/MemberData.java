package com.optum.ram.invoice.bean;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.json.bind.annotation.JsonbProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberData implements Serializable {

	@JsonbProperty("subscriber_id")
	private String subscriberId;
	@JsonbProperty("plan_name")
	private String planName;
	@JsonbProperty("effective_date")
	private Date effectiveDate;
	@JsonbProperty("source_seq_elig_hist")
	private Long sourceSeqEligHist;
	@JsonbProperty("company_code")
	private String companyCode;
	@JsonbProperty("elig_status")
	private String eligStatus;
	@JsonbProperty("term_date")	
	private Date termDate;
	@JsonbProperty("plan_code")
	private String planCode;
	@JsonbProperty("group_id")
	private String groupId;
	@JsonbProperty("county_code")
	private String countyCode;
	@JsonbProperty("category_of_assistance")
	private String categoryOfAssistance;
	@JsonbProperty("program_status_code")
	private String programStatusCode;
	@JsonbProperty("cap_invoice_flag")
	private String capInvoiceFlag;
	@JsonbProperty("premium_invoice_flag")
	private String premiumInvoiceFlag;
	@JsonbProperty("line_of_business")
	private String lineOfBusiness;
	@JsonbProperty("insert_datetime")
	private Timestamp insertDatetime;
	@JsonbProperty("insert_process")
	private String insertProcess;
	@JsonbProperty("insert_user")
	private String insertUser;
	@JsonbProperty("update_datetime")
	private Timestamp updateDatetime;
	@JsonbProperty("update_user")
	private String updateUser;
	@JsonbProperty("update_process")
	private String updateProcess;
	@JsonbProperty("user_defined_1")
	private String userDefined1;
	@JsonbProperty("user_defined_2")
	private String userDefined2;
	@JsonbProperty("source_code")
	private String sourceCode;
	@JsonbProperty("src_group_id")
	private String srcGroupId;
	@JsonbProperty("src_plan_code")
	private String srcPlanCode;
	@JsonbProperty("con_attr_code")
	private String conAttrCode;
	@JsonbProperty("con_attr_comment")
	private String conAttrComment;
	@JsonbProperty("con_attr_desc")
	private String conAttrDesc;
	@JsonbProperty("medicaid_id")
	private String medicaidId;
	@JsonbProperty("first_name")
	private String firstName;
	@JsonbProperty("last_name")
	private String lastName;
	@JsonbProperty("date_of_birth")
	private Date dateOfBirth;
	@JsonbProperty("gender")
	private String gender;
	@JsonbProperty("social_sec_no")
	private String socialSecNo;
	@JsonbProperty("family_case_id")
	private String familyCaseId;
	@JsonbProperty("employee_no")
	private String employeeNo;
	@JsonbProperty("prev_subscriber_id")
	private String prevSubscriberId;
	@JsonbProperty("medicare_no")
	private String medicareNo;
	@JsonbProperty("prev_medicaid_id")
	private String prevMedicaidId;
	@JsonbProperty("error_code")
	private String errorCode;
	@JsonbProperty("current_invoice_trigger")
	private Timestamp currentInvoiceTrigger;
	@JsonbProperty("retro_invoice_trigger")
	private Timestamp retroInvoiceTrigger;
	@JsonbProperty("payment_match_trigger")
	private Timestamp paymentMatchTrigger;
	@JsonbProperty("file_invoice_error_trigger")
	private Timestamp fileInvoiceErrorTrigger;
	@JsonbProperty("aptc_indicator")
	private String aptcIndicator;
	@JsonbProperty("hios_qhp_id")
	private String hiosQhpId;
	@JsonbProperty("grace_period_eff_date")
	private Timestamp gracePeriodEffDate;
	@JsonbProperty("grace_period_term_date")
	private Timestamp gracePeriodTermDate;
	@JsonbProperty("claim_pay_thru_date")
	private Timestamp claimPayThruDate;
	@JsonbProperty("elig_exp_code")
	private String eligExpCode;

	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	private final static long serialVersionUID = 5751636947787915854L;

	public String getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Long getSourceSeqEligHist() {
		return sourceSeqEligHist;
	}

	public void setSourceSeqEligHist(Long sourceSeqEligHist) {
		this.sourceSeqEligHist = sourceSeqEligHist;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getEligStatus() {
		return eligStatus;
	}

	public void setEligStatus(String eligStatus) {
		this.eligStatus = eligStatus;
	}

	public Date getTermDate() {
		return termDate;
	}

	public void setTermDate(Date termDate) {
		this.termDate = termDate;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getCategoryOfAssistance() {
		return categoryOfAssistance;
	}

	public void setCategoryOfAssistance(String categoryOfAssistance) {
		this.categoryOfAssistance = categoryOfAssistance;
	}

	public String getProgramStatusCode() {
		return programStatusCode;
	}

	public void setProgramStatusCode(String programStatusCode) {
		this.programStatusCode = programStatusCode;
	}

	public String getCapInvoiceFlag() {
		return capInvoiceFlag;
	}

	public void setCapInvoiceFlag(String capInvoiceFlag) {
		this.capInvoiceFlag = capInvoiceFlag;
	}

	public String getPremiumInvoiceFlag() {
		return premiumInvoiceFlag;
	}

	public void setPremiumInvoiceFlag(String premiumInvoiceFlag) {
		this.premiumInvoiceFlag = premiumInvoiceFlag;
	}

	public String getLineOfBusiness() {
		return lineOfBusiness;
	}

	public void setLineOfBusiness(String lineOfBusiness) {
		this.lineOfBusiness = lineOfBusiness;
	}

	public Timestamp getInsertDatetime() {
		return insertDatetime;
	}

	public void setInsertDatetime(Timestamp insertDatetime) {
		this.insertDatetime = insertDatetime;
	}

	public String getInsertProcess() {
		return insertProcess;
	}

	public void setInsertProcess(String insertProcess) {
		this.insertProcess = insertProcess;
	}

	public String getInsertUser() {
		return insertUser;
	}

	public void setInsertUser(String insertUser) {
		this.insertUser = insertUser;
	}

	public Timestamp getUpdateDatetime() {
		return updateDatetime;
	}

	public void setUpdateDatetime(Timestamp updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateProcess() {
		return updateProcess;
	}

	public void setUpdateProcess(String updateProcess) {
		this.updateProcess = updateProcess;
	}

	public String getUserDefined1() {
		return userDefined1;
	}

	public void setUserDefined1(String userDefined1) {
		this.userDefined1 = userDefined1;
	}

	public String getUserDefined2() {
		return userDefined2;
	}

	public void setUserDefined2(String userDefined2) {
		this.userDefined2 = userDefined2;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getSrcGroupId() {
		return srcGroupId;
	}

	public void setSrcGroupId(String srcGroupId) {
		this.srcGroupId = srcGroupId;
	}

	public String getSrcPlanCode() {
		return srcPlanCode;
	}

	public void setSrcPlanCode(String srcPlanCode) {
		this.srcPlanCode = srcPlanCode;
	}

	public String getConAttrCode() {
		return conAttrCode;
	}

	public void setConAttrCode(String conAttrCode) {
		this.conAttrCode = conAttrCode;
	}

	public String getConAttrComment() {
		return conAttrComment;
	}

	public void setConAttrComment(String conAttrComment) {
		this.conAttrComment = conAttrComment;
	}

	public String getConAttrDesc() {
		return conAttrDesc;
	}

	public void setConAttrDesc(String conAttrDesc) {
		this.conAttrDesc = conAttrDesc;
	}

	public String getMedicaidId() {
		return medicaidId;
	}

	public void setMedicaidId(String medicaidId) {
		this.medicaidId = medicaidId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSocialSecNo() {
		return socialSecNo;
	}

	public void setSocialSecNo(String socialSecNo) {
		this.socialSecNo = socialSecNo;
	}

	public String getFamilyCaseId() {
		return familyCaseId;
	}

	public void setFamilyCaseId(String familyCaseId) {
		this.familyCaseId = familyCaseId;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getPrevSubscriberId() {
		return prevSubscriberId;
	}

	public void setPrevSubscriberId(String prevSubscriberId) {
		this.prevSubscriberId = prevSubscriberId;
	}

	public String getMedicareNo() {
		return medicareNo;
	}

	public void setMedicareNo(String medicareNo) {
		this.medicareNo = medicareNo;
	}

	public String getPrevMedicaidId() {
		return prevMedicaidId;
	}

	public void setPrevMedicaidId(String prevMedicaidId) {
		this.prevMedicaidId = prevMedicaidId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Timestamp getCurrentInvoiceTrigger() {
		return currentInvoiceTrigger;
	}

	public void setCurrentInvoiceTrigger(Timestamp currentInvoiceTrigger) {
		this.currentInvoiceTrigger = currentInvoiceTrigger;
	}

	public Timestamp getRetroInvoiceTrigger() {
		return retroInvoiceTrigger;
	}

	public void setRetroInvoiceTrigger(Timestamp retroInvoiceTrigger) {
		this.retroInvoiceTrigger = retroInvoiceTrigger;
	}

	public Timestamp getPaymentMatchTrigger() {
		return paymentMatchTrigger;
	}

	public void setPaymentMatchTrigger(Timestamp paymentMatchTrigger) {
		this.paymentMatchTrigger = paymentMatchTrigger;
	}

	public Map<String, Object> getAdditionalProperties() {
		return additionalProperties;
	}

	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

	public Timestamp getFileInvoiceErrorTrigger() {
		return fileInvoiceErrorTrigger;
	}

	public void setFileInvoiceErrorTrigger(Timestamp fileInvoiceErrorTrigger) {
		this.fileInvoiceErrorTrigger = fileInvoiceErrorTrigger;
	}

	public String getAptcIndicator() {
		return aptcIndicator;
	}

	public void setAptcIndicator(String aptcIndicator) {
		this.aptcIndicator = aptcIndicator;
	}

	public String getHiosQhpId() {
		return hiosQhpId;
	}

	public void setHiosQhpId(String hiosQhpId) {
		this.hiosQhpId = hiosQhpId;
	}

	public Timestamp getGracePeriodEffDate() {
		return gracePeriodEffDate;
	}

	public void setGracePeriodEffDate(Timestamp gracePeriodEffDate) {
		this.gracePeriodEffDate = gracePeriodEffDate;
	}

	public Timestamp getGracePeriodTermDate() {
		return gracePeriodTermDate;
	}

	public void setGracePeriodTermDate(Timestamp gracePeriodTermDate) {
		this.gracePeriodTermDate = gracePeriodTermDate;
	}

	public Timestamp getClaimPayThruDate() {
		return claimPayThruDate;
	}

	public void setClaimPayThruDate(Timestamp claimPayThruDate) {
		this.claimPayThruDate = claimPayThruDate;
	}

	public String getEligExpCode() {
		return eligExpCode;
	}

	public void setEligExpCode(String eligExpCode) {
		this.eligExpCode = eligExpCode;
	}

}
