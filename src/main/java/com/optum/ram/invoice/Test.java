package com.optum.ram.invoice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.optum.ram.invoice.entity.InvoiceDetail;

public class Test {

    public static void main(String args[]) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date revStartDate = sdf.parse("2020-09-01");
            Date revendDate = sdf.parse("2020-09-30");
            Date dobDate = sdf.parse("1965-08-14");

            List<InvoiceDetail> retroInvocList = new ArrayList<InvoiceDetail>();
            List<InvoiceDetail> groupedInvRtrList = new ArrayList<InvoiceDetail>();

            InvoiceDetail invDet1 = new InvoiceDetail(Long.valueOf(100405), Long.valueOf(111113), Long.valueOf(100021),
                    Long.valueOf(100021), "108596423", "UHGMI", revStartDate, revendDate, 1223.27,
                    Long.valueOf(3059611), "0009985563", "", "", "378787090", "123276005", "MACOMB", 30, "Y", "",
                    "MI14MI53", "MI CSHCS TANF MALE 45-64 REGION 4", "TIMOTHY", "DAVIS", "", dobDate, "M", "MIPHCP",
                    "250312", "N", "Y", new Date(), new Date(), "INVFUNC", "INVFUNC", "RAM", "RAM");
            InvoiceDetail invDet2 = new InvoiceDetail(Long.valueOf(100405), Long.valueOf(111114), Long.valueOf(100021),
                    Long.valueOf(100021), "108596423", "UHGMI", revStartDate, revendDate, -1223.27,
                    Long.valueOf(3059611), "0009985563", "", "", "378787090", "123276005", "MACOMB", 30, "Y", "",
                    "MI14MI53", "MI CSHCS TANF MALE 45-64 REGION 4", "TIMOTHY", "DAVIS", "", dobDate, "M", "MIPHCP",
                    "250312", "N", "Y", new Date(), new Date(), "INVFUNC", "INVFUNC", "RAM", "RAM");

                    retroInvocList.add(invDet1);
                    retroInvocList.add(invDet2);
                    retroInvocList.stream().collect(Collectors.groupingBy(
                        invDet -> new InvoiceDetail(invDet.seqInvoiceHeaderId, invDet.seqRevenueId, invDet.baseSeqRevenueId,
                                invDet.subscriberId, invDet.planName, invDet.revenueStartDate, invDet.revenuEndDate,
                                invDet.seqRateId, invDet.medicaidId, invDet.employeeNo, invDet.medicareNo,
                                invDet.socialSecNo, invDet.familyCaseId, invDet.countyCode, invDet.activeDays,
                                invDet.invoiceStatus, invDet.errorCode, invDet.planCode, invDet.riskGroup, invDet.firstName,
                                invDet.lastName, invDet.middleInitial, invDet.dateOfBirth, invDet.gender, invDet.groupId,
                                invDet.productCode, invDet.billingRequired, invDet.eligStatus),
                        Collectors.summarizingDouble(invDet -> invDet.invoiceAmount))).forEach((k, v) -> {
                            k.invoiceAmount = v.getSum();
                            groupedInvRtrList.add(k);
                        });
                    
                    System.out.println(invDet1.equals(invDet2));

                    System.out.println(groupedInvRtrList.size());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
