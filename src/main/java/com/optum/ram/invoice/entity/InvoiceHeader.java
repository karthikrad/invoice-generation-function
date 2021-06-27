package com.optum.ram.invoice.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.transaction.Transactional;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "ram_invoice_header", schema = "invoice")
public class InvoiceHeader extends PanacheEntityBase {
	@Id
	@Column(name = "seq_invoice_header_id")
	@SequenceGenerator(name = "seq_invoice_header_id", sequenceName = "seq_invoice_header_id", allocationSize = 5, schema = "invoice")
	@GeneratedValue(generator = "seq_invoice_header_id", strategy = GenerationType.SEQUENCE)
	public Long seqInvoiceHeaderId;

	@Column(name = "invoice_date")
	public Date invoiceDate;

	@Column(name = "total_invoice_amount")
	public Double totalInvoiceAmount;

	@Column(name = "seq_revenue_id")
	public Long seqRevenueId;

	@Column(name = "seq_file_id")
	public Long seqFileId;

	@Column(name = "insert_datetime")
	@CreationTimestamp
	public Timestamp insertDatetime;

	@Column(name = "insert_process")
	public String insertProcess;

	@Column(name = "insert_user")
	public String insertUser;

	@Column(name = "update_datetime")
	@UpdateTimestamp
	@Version
	public Timestamp updateDatetime;

	@Column(name = "update_user")
	public String updateUser;

	@Column(name = "update_process")
	public String updateProcess;

	public InvoiceHeader() {

	}

	public InvoiceHeader(Long seqInvoiceHeaderId, Date invoiceDate, Double totalInvoiceAmount, Long seqRevenueId,
			Long seqFileId, Timestamp insertDatetime, String insertProcess, String insertUser, Timestamp updateDatetime,
			String updateUser, String updateProcess) {
		this.seqInvoiceHeaderId = seqInvoiceHeaderId;
		this.invoiceDate = invoiceDate;
		this.totalInvoiceAmount = totalInvoiceAmount;
		this.seqRevenueId = seqRevenueId;
		this.seqFileId = seqFileId;
		this.insertDatetime = insertDatetime;
		this.insertProcess = insertProcess;
		this.insertUser = insertUser;
		this.updateDatetime = updateDatetime;
		this.updateUser = updateUser;
		this.updateProcess = updateProcess;
	}

	public static Optional<InvoiceHeader> findInvoiceHeader(Long seqRevenueId, Calendar calRevStartDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("SEQ_REVENUE_ID", seqRevenueId);
		Calendar invoiceDate = Calendar.getInstance();
		invoiceDate.setTime(calRevStartDate.getTime());
		invoiceDate.set(Calendar.DAY_OF_MONTH, 1);
		params.put("REV_START_DATE", invoiceDate.getTime());

		Optional<InvoiceHeader> invoiceHeaderOptional = find(
				"seqRevenueId= :SEQ_REVENUE_ID and invoiceDate = :REV_START_DATE ", params).singleResultOptional();

		return invoiceHeaderOptional;
	}

	@Transactional
	public static void createInvoiceHeader(Long seqRevenueId, Calendar calRevStartDate) {
		InvoiceHeader invoiceHeader = new InvoiceHeader();
		calRevStartDate.set(Calendar.DAY_OF_MONTH, 1);
		invoiceHeader.invoiceDate = new java.sql.Date(calRevStartDate.getTime().getTime());
		invoiceHeader.seqRevenueId = seqRevenueId;
		invoiceHeader.totalInvoiceAmount = 0.0;
		invoiceHeader.insertProcess = "INVFUNC";
		invoiceHeader.updateProcess = "INVFUNC";
		invoiceHeader.insertUser = "RAM";
		invoiceHeader.updateUser = "RAM";
		invoiceHeader.persist();
	}

}