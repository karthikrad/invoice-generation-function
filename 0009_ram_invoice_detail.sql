
create table IF NOT EXISTS invoice.ram_invoice_detail
            (
              seq_invoice_header_id     BIGINT not null,
              seq_invoice_detail_id     BIGINT not null,
              seq_revenue_id            BIGINT,
              base_seq_revenue_id    	  BIGINT,                          
              subscriber_id           	varchar(15) not null,
              plan_name                 VARCHAR(5) not null,                          
              revenue_start_date        DATE not null,
              revenue_end_date          DATE not null,
              invoice_amount            DECIMAL(15,2) not null,
              seq_rate_id               BIGINT,
              medicaid_id             	VARCHAR(20),                              
						  employee_no             	VARCHAR(15),                              							                              
						  medicare_no             	VARCHAR(15),
              social_sec_no             varchar(20),                  
						  family_case_id          	VARCHAR(20), 						  
              county_code               VARCHAR(10),
              active_days               INT,
              invoice_status            VARCHAR(1),
              error_code                VARCHAR(10),                          
              plan_code              	  VARCHAR(30),					
              risk_group             	  VARCHAR(100),
              first_name				        VARCHAR(30),                                                        
						  last_name                 VARCHAR(35),                              
						  middle_initial          	VARCHAR(1),                              
						  date_of_birth             DATE,                              
						  gender                  	VARCHAR(1),
						  group_id               	  VARCHAR(10),
						  product_code           	  VARCHAR(10),
              billing_required          CHAR(1),
              elig_status               CHAR(1),
              insert_datetime           DATE not null,
              update_datetime           TIMESTAMP(0) not null,
              insert_process            VARCHAR(15) not null,
              update_process            VARCHAR(15) not null, 
              insert_user               VARCHAR(30) not null,
              update_user               VARCHAR(30) not null,              
              
              constraint invoice_detail_fk1 foreign key (seq_invoice_header_id)	references invoice.ram_invoice_header (seq_invoice_header_id),
              CONSTRAINT invoice_detail_pk PRIMARY KEY (seq_invoice_detail_id, plan_name)
            )
            partition by list (plan_name);

 CREATE TABLE IF NOT EXISTS  invoice.UHGNY_INV_DET PARTITION OF invoice.ram_invoice_detail FOR VALUES IN ('UHGNY');
 CREATE TABLE IF NOT EXISTS  invoice.ACMD_INV_DET PARTITION OF invoice.ram_invoice_detail FOR VALUES IN ('ACMD');
 CREATE TABLE IF NOT EXISTS  invoice.UHGWI_INV_DET PARTITION OF invoice.ram_invoice_detail FOR VALUES IN ('UHGWI');
 CREATE TABLE IF NOT EXISTS  invoice.ACNE_INV_DET PARTITION OF invoice.ram_invoice_detail FOR VALUES IN ('ACNE');
 

CREATE INDEX IF NOT EXISTS invoice_detail_idx1 on invoice.ram_invoice_detail (seq_invoice_header_id, seq_invoice_detail_id);

CREATE INDEX IF NOT EXISTS invoice_detail_idx2 on invoice.ram_invoice_detail (subscriber_id, revenue_start_date);

CREATE INDEX IF NOT EXISTS invoice_detail_idx3 on invoice.ram_invoice_detail (seq_revenue_id, revenue_start_date, medicaid_id);

CREATE INDEX IF NOT EXISTS invoice_detail_idx4 on invoice.ram_invoice_detail (family_case_id);

CREATE UNIQUE INDEX IF NOT EXISTS invoice_detail_idx5 on invoice.ram_invoice_detail (plan_name, seq_revenue_id, subscriber_id, revenue_start_date, revenue_end_date, plan_code, COALESCE(error_code, '000'), insert_datetime);
 