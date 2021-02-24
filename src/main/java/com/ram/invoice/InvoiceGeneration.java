package com.ram.invoice;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ram.invoice.bean.MemberData;
import com.ram.invoice.bean.Payload;
import com.ram.invoice.service.IInvoiceService;

@ApplicationScoped
@Path("/invoice")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvoiceGeneration {

	@Inject
	private IInvoiceService invoiceService;

	@POST
	@Path("/generateInvoice")
	public Response generateInvoice(Payload payLoad) {
		MemberData memData = payLoad.getEvent().getData().getNew();		
		invoiceService.generateInvoice(memData, false); // because retro invoice
		return Response.ok().build();

	}

	@POST
	@Path("/generateCurrInvoice")
	public Response generateCurrInvoice(Payload payLoad) {
		MemberData memData = payLoad.getEvent().getData().getNew();
		invoiceService.generateInvoice(memData, true);

		return Response.ok().build();

	}

}