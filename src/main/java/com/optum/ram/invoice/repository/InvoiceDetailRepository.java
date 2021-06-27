package com.optum.ram.invoice.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.jboss.logging.Logger;

import com.optum.ram.invoice.bean.MemberData;
import com.optum.ram.invoice.entity.InvoiceDetail;
import com.optum.ram.invoice.entity.RevenueStreamHistory;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class InvoiceDetailRepository implements PanacheRepository<InvoiceDetail> {

   /*  private static final Logger LOG = Logger.getLogger(InvoiceDetailRepository.class);

    @Inject
    EntityManager em;

    public void insertInvoiceDetail(List<InvoiceDetail> invoiceDetailList) {
        Session hibernateSession = em.unwrap(Session.class);        
        StringBuilder sql = new StringBuilder().append(" insert	into ")
                .append(" invoice.ram_invoice_detail (seq_invoice_header_id, seq_invoice_detail_id,  ")
                .append(" plan_name, seq_revenue_id, base_seq_revenue_id, subscriber_id,  ")
                .append(" revenue_start_date, revenue_end_date, invoice_amount, seq_rate_id,  ")
                .append(" medicaid_id, employee_no, medicare_no, social_sec_no, family_case_id,  ")
                .append(" county_code, active_days, invoice_status, error_code, plan_code,  ")
                .append(" risk_group, first_name, last_name, middle_initial, date_of_birth,  ")
                .append(" gender, group_id, product_code, billing_required, elig_status,  ")
                .append(" insert_datetime, update_datetime, insert_process, update_process,  ")
                .append(" insert_user, update_user) ")
                .append(" values ( ?, nextval('invoice.seq_invoice_detail_id'),?,  ").append(" ?, ?, ?, ?,  ")
                .append(" ?, ?, ?, ?, ?, ").append(" ?, ?, ?, ?, ?,  ").append(" ?, ?, ?, ?, ?, ?,  ")
                .append(" ?, ?, ?, ?, ?,  ").append(" ?, ?, now(), now(), 'INVFUNC', 'INVFUNC', 'RAM', 'RAM' ) ") */
                /*
                 * .append(" values (:seq_invoice_header_id, nextval(invoice.seq_invoice_detail_id),:plan_name,  "
                 * )
                 * .append(" :seq_revenue_id, :base_seq_revenue_id, :subscriber_id, :revenue_start_date,  "
                 * )
                 * .append(" :revenue_end_date, :invoice_amount, :seq_rate_id, :medicaid_id, :employee_no, "
                 * )
                 * .append(" :medicare_no, :social_sec_no, :family_case_id, :county_code, :active_days,  "
                 * )
                 * .append(" :invoice_status, :error_code, :plan_code, :risk_group, :first_name, :last_name,  "
                 * )
                 * .append(" :middle_initial, :date_of_birth, :gender, :group_id, :product_code,  "
                 * )
                 * .append(" :billing_required, :elig_status, now(), now(), 'INVFUNC', 'INVFUNC', 'RAM', 'RAM' ) "
                 * )
                 */
        /*         .append(" on conflict do nothing ");
        hibernateSession.doWork(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {
                int i = 0;
                for (InvoiceDetail invDetail : invoiceDetailList) {
                    preparedStatement.setLong(1, invDetail.seqInvoiceHeaderId);
                    preparedStatement.setString(2, invDetail.planName);
                    preparedStatement.setLong(3, invDetail.seqRevenueId);
                    preparedStatement.setObject(4, invDetail.baseSeqRevenueId, java.sql.Types.BIGINT);
                    preparedStatement.setString(5, invDetail.subscriberId);
                    preparedStatement.setDate(6, new java.sql.Date(invDetail.revenueStartDate.getTime()));
                    preparedStatement.setDate(7, new java.sql.Date(invDetail.revenuEndDate.getTime()));
                    preparedStatement.setDouble(8, invDetail.invoiceAmount);
                    preparedStatement.setObject(9, invDetail.seqRateId, java.sql.Types.BIGINT);
                    preparedStatement.setString(10, invDetail.medicaidId);
                    preparedStatement.setString(11, invDetail.employeeNo);
                    preparedStatement.setString(12, invDetail.medicareNo);
                    preparedStatement.setString(13, invDetail.socialSecNo);
                    preparedStatement.setString(14, invDetail.familyCaseId);
                    preparedStatement.setString(15, invDetail.countyCode);
                    preparedStatement.setObject(16, invDetail.activeDays, java.sql.Types.INTEGER);
                    preparedStatement.setString(17, invDetail.invoiceStatus);
                    preparedStatement.setString(18, invDetail.errorCode);
                    preparedStatement.setString(19, invDetail.planCode);
                    preparedStatement.setString(20, invDetail.riskGroup);
                    preparedStatement.setString(21, invDetail.firstName);
                    preparedStatement.setString(22, invDetail.lastName);
                    preparedStatement.setString(23, invDetail.middleInitial);
                    preparedStatement.setDate(24,
                            invDetail.dateOfBirth == null ? null : new java.sql.Date(invDetail.dateOfBirth.getTime()));
                    preparedStatement.setString(25, invDetail.gender);
                    preparedStatement.setString(26, invDetail.groupId);
                    preparedStatement.setString(27, invDetail.productCode);
                    preparedStatement.setString(28, invDetail.billingRequired);
                    preparedStatement.setString(29, invDetail.eligStatus);

                    preparedStatement.addBatch();

                    // 20 : JDBC batch size
                    if (i % 20 == 0) {
                        preparedStatement.executeBatch();
                    }
                    i++;
                }
                preparedStatement.executeBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    } */

   /*  public List<InvoiceDetail> getRetroInvoiceDetails(MemberData memData,
            List<RevenueStreamHistory> revenueStreamHisList, boolean isCurrInvoice) {
        List<InvoiceDetail> retroInvoiceList = new ArrayList<InvoiceDetail>();
        String sqlString = fetchRetroInvoices(isCurrInvoice);
        for (RevenueStreamHistory revenueStreamHistory : revenueStreamHisList) {
            Query query = em.createNativeQuery(sqlString, InvoiceDetail.class);
            query.setParameter("PLAN_NAME", memData.getPlanName());
            query.setParameter("SEQ_REVENUE_ID", revenueStreamHistory.seqRevenueId);
            query.setParameter("SUBSCRIBER_ID", memData.getSubscriberId());
            if (isCurrInvoice) {
                query.setParameter("CURR_INV_MONTH", revenueStreamHistory.goliveDate);
            } else {
                query.setParameter("MEM_EFF_DATE", memData.getEffectiveDate());
                query.setParameter("MEM_TERM_DATE", memData.getTermDate());
            }

            retroInvoiceList.addAll(query.getResultList());
        }
        return retroInvoiceList;
    } */

    /* public static String fetchRetroInvoices(boolean isCurrInvoice) {
        StringBuffer strQuery = new StringBuffer();
        strQuery.append(
                " select seq_invoice_header_id, null as seq_invoice_detail_id, plan_name , seq_revenue_id , base_seq_revenue_id , ");
        strQuery.append(" subscriber_id ,  revenue_start_date , revenue_end_date , ");
        strQuery.append(
                " SUM(invoice_amount) as invoice_amount, seq_rate_id , medicaid_id , employee_no , medicare_no , social_sec_no , ");
        strQuery.append(" family_case_id , county_code , active_days , invoice_status , ");
        strQuery.append(" error_code , plan_code , risk_group , first_name , last_name , middle_initial ,");
        strQuery.append(" date_of_birth , gender , group_id , product_code , ");
        strQuery.append(
                " billing_required , elig_status, ");
                strQuery.append("  now() insert_datetime, now() update_datetime, 'INVFUNC' insert_process,'INVFUNC' update_process, ");
                strQuery.append("  'RAM' insert_user, 'RAM' update_user ");
        strQuery.append(" from invoice.ram_invoice_detail rid where  ");
        strQuery.append(" plan_name = :PLAN_NAME ");
        strQuery.append(" and seq_revenue_id= :SEQ_REVENUE_ID ");
        strQuery.append(" and subscriber_id = :SUBSCRIBER_ID ");
        if (isCurrInvoice) {
            strQuery.append(" and :CURR_INV_MONTH = date_trunc('month', revenue_start_date)  ");
        } else {
            strQuery.append(" and (revenue_start_date between :MEM_EFF_DATE and :MEM_TERM_DATE ");
            strQuery.append(" or revenue_end_date between :MEM_EFF_DATE and :MEM_TERM_DATE ) ");
        }
        strQuery.append(" group by rid.seq_invoice_header_id, rid.seq_revenue_id, ");
        strQuery.append(" rid.subscriber_id , rid.plan_name , rid.revenue_start_date ,  ");
        strQuery.append(" rid.revenue_end_date , rid.seq_rate_id , rid.county_code , ");
        strQuery.append(" rid.active_days , rid.invoice_status , rid.error_code , ");
        strQuery.append(" rid.plan_code , rid.risk_group , rid.first_name , rid.last_name , rid.middle_initial ,");
        strQuery.append(" rid.date_of_birth , rid.gender , rid.group_id , rid.base_seq_revenue_id ,");
        strQuery.append(" rid.medicaid_id , rid.employee_no , rid.medicare_no , rid.family_case_id, rid.social_sec_no ,");
        strQuery.append(" billing_required , elig_status , product_code ");

        return strQuery.toString();
    } */

   /*  @Transactional
    public void generateInvoices(MemberData memData, MemberData oldMemData,
            List<RevenueStreamHistory> revenueStreamHisList, boolean isCurrInvoice) {
        String sqlString = new StringBuilder().append("select invoice.generate_invoices(?, ?, ?, ?, ?, ?, ?, ?, ?) ")
                .toString();

        Session hibernateSession = em.unwrap(Session.class);
        hibernateSession.doWork(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlString.toString())) {
                for (RevenueStreamHistory revenueStreamHistory : revenueStreamHisList) {
                    preparedStatement.setString(1, memData.getSubscriberId());
                    preparedStatement.setString(2, memData.getPlanName());
                    preparedStatement.setLong(3, revenueStreamHistory.seqRevenueId);
                    preparedStatement.setString(4, memData.getPlanCode());
                    preparedStatement.setString(5, oldMemData.getPlanCode());
                    if (isCurrInvoice) {
                        preparedStatement.setDate(6, revenueStreamHistory.goliveDate);
                        preparedStatement.setDate(7, revenueStreamHistory.goliveDate);
                        preparedStatement.setDate(8, revenueStreamHistory.goliveDate);
                        preparedStatement.setDate(9, revenueStreamHistory.goliveDate);
                    } else {
                        preparedStatement.setDate(6, new java.sql.Date(memData.getEffectiveDate().getTime()));
                        preparedStatement.setDate(7, new java.sql.Date(memData.getTermDate().getTime()));
                        preparedStatement.setDate(8, new java.sql.Date(oldMemData.getEffectiveDate().getTime()));
                        preparedStatement.setDate(9, new java.sql.Date(oldMemData.getTermDate().getTime()));
                    }
                    preparedStatement.executeQuery();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    } */

   /*  @Transactional
    public void deleteEntities(List<InvoiceDetail> currInvoiceList, MemberData memData) {
        StringBuilder sqlString = new StringBuilder().append("Delete from  invoice.ram_invoice_curr_tmp ")
                .append(" where plan_name = :PLAN_NAME ").append(" and seq_invoice_detail_id  in ( ");

        Iterator<InvoiceDetail> it = currInvoiceList.iterator();
        if (currInvoiceList.size() > 0) {
            do {
                sqlString.append(it.next().seqInvoiceDetailId);
                if (it.hasNext()) {
                    sqlString.append(", ");
                }
            } while (it.hasNext());
            sqlString.append(" )");
            Query query = em.createNativeQuery(sqlString.toString());
            query.setParameter("PLAN_NAME", memData.getPlanName());
            query.executeUpdate();
        }

    } */

}
