package com.optum.ram.invoice.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "ram_invoice_detail", schema = "invoice")
public class InvoiceDetail extends PanacheEntityBase implements Serializable {
	/**
	 * Default serialize id
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "seq_invoice_header_id")
	public Long seqInvoiceHeaderId;
	@Id
	@Column(name = "seq_invoice_detail_id")
	@SequenceGenerator(name = "seq_invoice_curr_id", sequenceName = "seq_invoice_curr_id", allocationSize = 1, schema = "invoice")
	@GeneratedValue(generator = "seq_invoice_curr_id", strategy = GenerationType.SEQUENCE)
	public Long seqInvoiceDetailId;
	@Id
	@Column(name = "plan_name")
	public String planName;
	@Id
	@Column(name = "seq_revenue_id")
	public Long seqRevenueId;
	@Column(name = "base_seq_revenue_id")
	public Long baseSeqRevenueId;
	@Column(name = "subscriber_id")
	public String subscriberId;
	@Column(name = "revenue_start_date")
	@Temporal(TemporalType.DATE)
	public Date revenueStartDate;
	@Column(name = "revenue_end_date")
	@Temporal(TemporalType.DATE)
	public Date revenuEndDate;
	@Column(name = "invoice_amount")
	@ColumnDefault(value = "0.00")
	public double invoiceAmount;
	@Column(name = "seq_rate_id")
	public Long seqRateId;
	@Column(name = "medicaid_id")
	public String medicaidId;
	@Column(name = "employee_no")
	public String employeeNo;
	@Column(name = "medicare_no")
	public String medicareNo;
	@Column(name = "social_sec_no")
	public String socialSecNo;
	@Column(name = "family_case_id")
	public String familyCaseId;
	@Column(name = "county_code")
	public String countyCode;
	@Column(name = "active_days")
	public int activeDays;
	@Column(name = "invoice_status")
	@ColumnDefault(value = "Y")
	public String invoiceStatus;
	@Column(name = "error_code")
	public String errorCode;
	@Column(name = "plan_code")
	public String planCode;
	@Column(name = "risk_group")
	@ColumnDefault(value = "UNKNOWN")
	public String riskGroup;
	@Column(name = "first_name")
	public String firstName;
	@Column(name = "last_name")
	public String lastName;
	@Column(name = "middle_initial")
	public String middleInitial;
	@Column(name = "date_of_birth")
	@Temporal(TemporalType.DATE)
	public Date dateOfBirth;
	@Column(name = "gender")
	public String gender;
	@Column(name = "group_id")
	public String groupId;
	@Column(name = "product_code")
	@ColumnDefault(value = "UNKNOWN")
	public String productCode;
	@Column(name = "billing_required")
	public String billingRequired;
	@Column(name = "elig_status")
	public String eligStatus;
	@Column(name = "insert_datetime")
	@Temporal(TemporalType.DATE)
	@CreationTimestamp
	public Date insertDatetime;
	@Column(name = "update_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	public Date updateDatetime;
	@Column(name = "insert_process")
	@ColumnDefault(value = "INVFUNC")
	public String insertProcess;
	@Column(name = "update_process")
	@ColumnDefault(value = "INVFUNC")
	public String updateProcess;
	@Column(name = "insert_user")
	@ColumnDefault(value = "RAM")
	public String insertUser;
	@Column(name = "update_user")
	@ColumnDefault(value = "RAM")
	public String updateUser;

	public InvoiceDetail() {

	}

	public InvoiceDetail(Long seqInvoiceHeaderId, Long seqInvoiceDetailId, Long seqRevenueId, Long baseSeqRevenueId,
			String subscriberId, String planName, Date revenueStartDate, Date revenuEndDate, double invoiceAmount,
			Long seqRateId, String medicaidId, String employeeNo, String medicareNo, String socialSecNo,
			String familyCaseId, String countyCode, int activeDays, String invoiceStatus, String errorCode,
			String planCode, String riskGroup, String firstName, String lastName, String middleInitial,
			Date dateOfBirth, String gender, String groupId, String productCode, String billingRequired,
			String eligStatus, Date insertDatetime, Date updateDatetime, String insertProcess, String updateProcess,
			String insertUser, String updateUser) {
		this.seqInvoiceHeaderId = seqInvoiceHeaderId;
		this.seqInvoiceDetailId = seqInvoiceDetailId;
		this.seqRevenueId = seqRevenueId;
		this.baseSeqRevenueId = baseSeqRevenueId;
		this.subscriberId = subscriberId;
		this.planName = planName;
		this.revenueStartDate = revenueStartDate;
		this.revenuEndDate = revenuEndDate;
		this.invoiceAmount = invoiceAmount;
		this.seqRateId = seqRateId;
		this.medicaidId = medicaidId;
		this.employeeNo = employeeNo;
		this.medicareNo = medicareNo;
		this.socialSecNo = socialSecNo;
		this.familyCaseId = familyCaseId;
		this.countyCode = countyCode;
		this.activeDays = activeDays;
		this.invoiceStatus = invoiceStatus;
		this.errorCode = errorCode;
		this.planCode = planCode;
		this.riskGroup = riskGroup;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleInitial = middleInitial;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.groupId = groupId;
		this.productCode = productCode;
		this.billingRequired = billingRequired;
		this.eligStatus = eligStatus;
		this.insertDatetime = insertDatetime;
		this.updateDatetime = updateDatetime;
		this.insertProcess = insertProcess;
		this.updateProcess = updateProcess;
		this.insertUser = insertUser;
		this.updateUser = updateUser;
	}

	public InvoiceDetail(Long seqInvoiceHeaderId, Long seqRevenueId, Long baseSeqRevenueId, String subscriberId,
			String planName, Date revenueStartDate, Date revenuEndDate, Long seqRateId, String medicaidId,
			String employeeNo, String medicareNo, String socialSecNo, String familyCaseId, String countyCode,
			int activeDays, String invoiceStatus, String errorCode, String planCode, String riskGroup, String firstName,
			String lastName, String middleInitial, Date dateOfBirth, String gender, String groupId, String productCode,
			String billingRequired, String eligStatus) {
		this.seqInvoiceHeaderId = seqInvoiceHeaderId;
		this.seqRevenueId = seqRevenueId;
		this.baseSeqRevenueId = baseSeqRevenueId;
		this.subscriberId = subscriberId;
		this.planName = planName;
		this.revenueStartDate = revenueStartDate;
		this.revenuEndDate = revenuEndDate;
		this.seqRateId = seqRateId;
		this.medicaidId = medicaidId;
		this.employeeNo = employeeNo;
		this.medicareNo = medicareNo;
		this.socialSecNo = socialSecNo;
		this.familyCaseId = familyCaseId;
		this.countyCode = countyCode;
		this.activeDays = activeDays;
		this.invoiceStatus = invoiceStatus;
		this.errorCode = errorCode;
		this.planCode = planCode;
		this.riskGroup = riskGroup;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleInitial = middleInitial;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.groupId = groupId;
		this.productCode = productCode;
		this.billingRequired = billingRequired;
		this.eligStatus = eligStatus;
		this.insertProcess = "INVFUNC";
		this.updateProcess = "INVFUNC";
		this.insertUser = "RAM";
		this.updateUser = "RAM";
	}

	@Override
	public int hashCode() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		final int prime = 31;
		int result = 1;
		result = prime * result + ((familyCaseId == null) ? 0 : familyCaseId.hashCode());
		// long temp;
		// temp = Double.doubleToLongBits(invoiceAmount);
		// result = prime * result + (int) (temp ^ (temp >>> 32));
		// result = prime * result + ((invoiceStatus == null) ? 0 :
		// invoiceStatus.hashCode());
		// result = prime * result + ((medicaidId == null) ? 0 : medicaidId.hashCode());
		result = prime * result + ((revenuEndDate == null) ? 0 : sdf.format(revenuEndDate).hashCode());
		result = prime * result + ((revenueStartDate == null) ? 0 : sdf.format(revenueStartDate).hashCode());
		result = prime * result + (int) (seqInvoiceHeaderId ^ (seqInvoiceHeaderId >>> 32));
		result = prime * result + ((seqRateId == null) ? 0 : (int) (seqRateId ^ (seqRateId >>> 32)));
		result = prime * result + (int) (seqRevenueId ^ (seqRevenueId >>> 32));
		result = prime * result + ((subscriberId == null) ? 0 : subscriberId.hashCode());
		result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
		result = prime * result + (int) (activeDays ^ (activeDays >>> 32));

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		InvoiceDetail other = (InvoiceDetail) obj;
		if (familyCaseId == null) {
			if (other.familyCaseId != null) {
				return false;
			}
		} else if (!familyCaseId.equals(other.familyCaseId)) {
			return false;
		}
		/*
		 * if (invoiceAmount != other.invoiceAmount) return false; if (invoiceStatus ==
		 * null) { if (other.invoiceStatus != null) return false; } else if
		 * (!invoiceStatus.equals(other.invoiceStatus)) return false;
		 */
		/*
		 * if (medicaidId == null) { if (other.medicaidId != null) return false; } else
		 * if (!medicaidId.equals(other.medicaidId)) return false;
		 */
		if (revenuEndDate == null) {
			if (other.revenuEndDate != null) {
				return false;
			}
		} else if (!sdf.format(revenuEndDate).equals(sdf.format(other.revenuEndDate))) {
			return false;
		}
		if (revenueStartDate == null) {
			if (other.revenueStartDate != null) {
				return false;
			}
		} else if (!sdf.format(revenueStartDate).equals(sdf.format(other.revenueStartDate))) {
			return false;
		}
		if (!seqInvoiceHeaderId.equals(other.seqInvoiceHeaderId)) {
			return false;
		}
		if (seqRateId == null) {
			if (other.seqRateId != null) {
				return false;
			}
		} else if (!seqRateId.equals(other.seqRateId)) {
			return false;
		}
		if (!seqRevenueId.equals(other.seqRevenueId)) {
			return false;
		}
		if (subscriberId == null) {
			if (other.subscriberId != null) {
				return false;
			}
		} else if (!subscriberId.equals(other.subscriberId)) {
			return false;
		}

		if (errorCode == null) {
			if (other.errorCode != null) {
				return false;
			}
		} else if (!errorCode.equals(other.errorCode)) {
			return false;
		}

		if (activeDays != other.activeDays) {
			return false;
		}

		return true;
	}

	@Transactional
	public static void saveInvoices(List<InvoiceDetail> invoiceList) {
		InvoiceDetail.persist(invoiceList);
	}

	/*
	 * @Transactional(value = TxType.REQUIRES_NEW)
	 * 
	 * @Lock(Lock.Type.WRITE) public static void saveInvoice(InvoiceDetail inv) {
	 * try { inv.persist(); } catch (Exception ex) { // skipping exception
	 * System.out.println(inv.subscriberId + " : " + ex.getMessage()); } }
	 */

	/**
	 * Get the list of invoice details.
	 * 
	 * @param params
	 * @param string
	 * 
	 */
	@Transactional
	public static List<InvoiceDetail> getInvoiceDetails(String queryString, Map<String, Object> params) {
		List<InvoiceDetail> retroInvoiceList = new ArrayList<InvoiceDetail>();
		Stream<InvoiceDetail> invRtrSteam = stream(queryString, params);

		invRtrSteam.collect(Collectors.groupingBy(
				invDet -> new InvoiceDetail(invDet.seqInvoiceHeaderId, invDet.seqRevenueId, invDet.baseSeqRevenueId,
						invDet.subscriberId, invDet.planName, invDet.revenueStartDate, invDet.revenuEndDate,
						invDet.seqRateId, invDet.medicaidId, invDet.employeeNo, invDet.medicareNo, invDet.socialSecNo,
						invDet.familyCaseId, invDet.countyCode, invDet.activeDays, invDet.invoiceStatus,
						invDet.errorCode, invDet.planCode, invDet.riskGroup, invDet.firstName, invDet.lastName,
						invDet.middleInitial, invDet.dateOfBirth, invDet.gender, invDet.groupId, invDet.productCode,
						invDet.billingRequired, invDet.eligStatus),
				Collectors.summarizingDouble(invDet -> invDet.invoiceAmount))).forEach((k, v) -> {
					k.invoiceAmount = v.getSum();
					retroInvoiceList.add(k);
				});
		invRtrSteam.close();
		return retroInvoiceList;
	}

	public static void deleteEntities(List<InvoiceDetail> currInvoiceList) {
	}

	/*
	 * @Override public String toString() { return "InvoiceDetail [errorCode=" +
	 * errorCode + ", invoiceAmount=" + invoiceAmount + ", invoiceStatus=" +
	 * invoiceStatus + ", planCode=" + planCode + ", revenuEndDate=" + revenuEndDate
	 * + ", revenueStartDate=" + revenueStartDate + ", seqInvoiceDetailId=" +
	 * seqInvoiceDetailId + ", seqInvoiceHeaderId=" + seqInvoiceHeaderId +
	 * ", seqRateId=" + seqRateId + ", subscriberId=" + subscriberId +
	 * ", eligStatus=" + eligStatus + "]"; }
	 */

}