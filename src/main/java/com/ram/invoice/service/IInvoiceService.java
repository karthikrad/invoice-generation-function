package com.ram.invoice.service;

import javax.enterprise.context.ApplicationScoped;

import com.ram.invoice.bean.MemberData;

@ApplicationScoped
public interface IInvoiceService {

	void generateInvoice(MemberData memData, boolean isCurrInvoice);	
    
}