package com.optum.ram.invoice.service;

import javax.enterprise.context.RequestScoped;

import com.optum.ram.invoice.bean.MemberData;

@RequestScoped
public interface IInvoiceService {

	void generateInvoice(MemberData newMemData, MemberData oldMemData, boolean isCurrInvoice);

}