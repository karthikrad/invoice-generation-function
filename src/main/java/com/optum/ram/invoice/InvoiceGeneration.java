package com.optum.ram.invoice;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.optum.ram.invoice.bean.MemberData;
import com.optum.ram.invoice.bean.Payload;
import com.optum.ram.invoice.service.IInvoiceService;


@Path("/invoice")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvoiceGeneration {

	@Inject
	private IInvoiceService invoiceService;

	@POST
	@Path("/generateInvoice")
	public Response generateInvoice(Payload payLoad) {
		MemberData newMemData = payLoad.getEvent().getData().getNew();
		MemberData oldMemData = payLoad.getEvent().getData().getOld();		
		invoiceService.generateInvoice(newMemData, oldMemData, false); // because retro invoice		
		return Response.ok().build();

	}

	@POST
	@Path("/generateCurrInvoice")
	public Response generateCurrInvoice(Payload payLoad) {
		MemberData newMemData = payLoad.getEvent().getData().getNew();
		MemberData oldMemData = payLoad.getEvent().getData().getOld();
		invoiceService.generateInvoice(newMemData, oldMemData, true);

		return Response.ok().build();

	}

}