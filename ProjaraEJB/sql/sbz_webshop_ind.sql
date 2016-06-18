/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     5/29/2016 9:53:48 PM                         */
/*==============================================================*/


drop table if exists BILL_DISCOUNT;

drop table if exists BILL_ITEM_DISCOUNT;

drop table if exists HAS_THRESHOLDS;

drop table if exists ON_DISCOUNT;

drop table if exists THRESHOLD;

drop table if exists BILL_ITEM;

drop table if exists ITEM;

drop table if exists ITEM_CATEGORY;

drop table if exists ACTION_EVENT;

drop table if exists BILL;

drop table if exists USER;

drop table if exists CUSTOMER_CATEGORY;

/*==============================================================*/
/* Table: ACTION_EVENT                                          */
/*==============================================================*/
create table ACTION_EVENT
(
   AE_ID                int not null auto_increment,
   AE_NAME              varchar(60) not null,
   AE_FROM              date not null,
   AE_UNTIL             date not null,
   AE_DISCOUNT          numeric(5,2) not null,
   primary key (AE_ID)
);

/*==============================================================*/
/* Table: BILL                                                  */
/*==============================================================*/
create table BILL
(
   BILL_ID              int not null auto_increment,
   USR_ID               int not null,
   BILL_DATE            datetime not null default CURRENT_TIMESTAMP,
   BILL_ORTOTAL         decimal(10,2) default 0,
   BILL_DISCPERC        numeric(5,2) default 0,
   BILL_TOTAL           decimal(10,2) default 0,
   BILL_SPOINTS         smallint default 0,
   BILL_APOINTS         smallint default 0,
   BILL_STATE           char(1) not null default 'O',
   primary key (BILL_ID)
);

/*==============================================================*/
/* Table: BILL_DISCOUNT                                         */
/*==============================================================*/
create table BILL_DISCOUNT
(
   BID_ID               int not null auto_increment,
   BILL_ID              int not null,
   BID_DISCOUNT         numeric(5,2),
   BID_TYPE             char(1) not null default 'R',
   primary key (BID_ID)
);

/*==============================================================*/
/* Table: BILL_ITEM                                             */
/*==============================================================*/
create table BILL_ITEM
(
   BILL_ID              int not null,
   BILLIT_NO            smallint not null auto_increment,
   IT_ID                int not null,
   BILLIT_PRICE         decimal(10,2) default 0,
   BILLIT_QUANTITY      numeric(4,0) default 0,
   BILLIT_ORTOTAL       decimal(10,2) default 0,
   BILLIT_DISCPERC      numeric(5,2) default 0,
   BILLIT_TOTAL         decimal(10,2) default 0,
   primary key (BILLIT_NO, BILL_ID)
);

/*==============================================================*/
/* Table: BILL_ITEM_DISCOUNT                                    */
/*==============================================================*/
create table BILL_ITEM_DISCOUNT
(
   BITD_ID              int not null auto_increment,
   BILL_ID              int not null,
   BILLIT_NO            smallint not null,
   BITD_DISCOUNT        numeric(5,2) default 0,
   BITD_TYPE            char(1) not null default 'R',
   primary key (BITD_ID)
);

/*==============================================================*/
/* Table: CUSTOMER_CATEGORY                                     */
/*==============================================================*/
create table CUSTOMER_CATEGORY
(
   CAT_ID               char(1) not null,
   CAT_NAME             varchar(40) not null,
   primary key (CAT_ID)
);

/*==============================================================*/
/* Table: HAS_THRESHOLDS                                        */
/*==============================================================*/
create table HAS_THRESHOLDS
(
   THRES_ID             smallint not null,
   CAT_ID               char(1) not null,
   primary key (THRES_ID, CAT_ID)
);

/*==============================================================*/
/* Table: ITEM                                                  */
/*==============================================================*/
create table ITEM
(
   IT_ID                int not null auto_increment,
   ITCAT_CODE           char(3) not null,
   IT_NAME              varchar(60) not null,
   IT_PRICE             decimal(8,2) default 0,
   IT_INSTOCK           int default 0,
   IT_CREATEDON         datetime not null default CURRENT_TIMESTAMP,
   IT_ISLOW             bool default false,
   IT_RECSTATE          bool default false,
   IT_MINQUANT          int not null default 0,
   primary key (IT_ID)
);

/*==============================================================*/
/* Table: ITEM_CATEGORY                                         */
/*==============================================================*/
create table ITEM_CATEGORY
(
   ITCAT_CODE           char(3) not null,
   ITE_ITCAT_CODE       char(3),
   ITCAT_NAME           varchar(60) not null,
   ITCAT_MDISCOUNT      numeric(5,2) not null,
   primary key (ITCAT_CODE)
);

/*==============================================================*/
/* Table: ON_DISCOUNT                                           */
/*==============================================================*/
create table ON_DISCOUNT
(
   AE_ID                int not null,
   ITCAT_CODE           char(3) not null,
   primary key (AE_ID, ITCAT_CODE)
);

/*==============================================================*/
/* Table: THRESHOLD                                             */
/*==============================================================*/
create table THRESHOLD
(
   THRES_ID             smallint not null auto_increment,
   THRES_FROM           decimal(10,2) not null default 0,
   THRES_TO             decimal(10,2) not null default 0,
   THRES_PERCENT        numeric(5,2) not null default 0,
   primary key (THRES_ID)
);

/*==============================================================*/
/* Table: USER                                                  */
/*==============================================================*/
create table USER
(
   USR_ID               int not null auto_increment,
   CAT_ID               char(1),
   USR_USERNAME         varchar(30) not null,
   USR_FNAME            varchar(35) not null,
   USR_LNAME            varchar(40) not null,
   USR_REGDAT           datetime not null default CURRENT_TIMESTAMP,
   USR_ROLE             char(1) not null,
   USR_CUS_ADDRESS      varchar(128),
   USR_CUS_POINT        smallint default 0,
   USR_CUS_RESERVED     smallint default 0,
   USR_PASSWORD         varchar(16) not null,
   primary key (USR_ID),
   key AK_USERNAME_UNIQUE (USR_USERNAME)
);

alter table BILL add constraint FK_BUYER foreign key (USR_ID)
      references USER (USR_ID) on delete cascade on update cascade;

alter table BILL_DISCOUNT add constraint FK_HAS_DISCOUNT foreign key (BILL_ID)
      references BILL (BILL_ID) on delete cascade on update cascade;

alter table BILL_ITEM add constraint FK_HAS_ITEMS foreign key (BILL_ID)
      references BILL (BILL_ID) on delete cascade on update cascade;

alter table BILL_ITEM add constraint FK_IS_ON_BILL foreign key (IT_ID)
      references ITEM (IT_ID) on delete cascade on update cascade;

alter table BILL_ITEM_DISCOUNT add constraint FK_ITEM_HAS_DISCOUNTS foreign key (BILLIT_NO, BILL_ID)
      references BILL_ITEM (BILLIT_NO, BILL_ID) on delete cascade on update cascade;

alter table HAS_THRESHOLDS add constraint FK_HAS_THRESHOLDS foreign key (THRES_ID)
      references THRESHOLD (THRES_ID) on delete cascade on update cascade;

alter table HAS_THRESHOLDS add constraint FK_HAS_THRESHOLDS2 foreign key (CAT_ID)
      references CUSTOMER_CATEGORY (CAT_ID) on delete cascade on update cascade;

alter table ITEM add constraint FK_ITEM_CATEGORY foreign key (ITCAT_CODE)
      references ITEM_CATEGORY (ITCAT_CODE) on delete cascade on update cascade;

alter table ITEM_CATEGORY add constraint FK_SUBCATEGORIES foreign key (ITE_ITCAT_CODE)
      references ITEM_CATEGORY (ITCAT_CODE) on delete cascade on update cascade;

alter table ON_DISCOUNT add constraint FK_ON_DISCOUNT foreign key (AE_ID)
      references ACTION_EVENT (AE_ID) on delete cascade on update cascade;

alter table ON_DISCOUNT add constraint FK_ON_DISCOUNT2 foreign key (ITCAT_CODE)
      references ITEM_CATEGORY (ITCAT_CODE) on delete cascade on update cascade;

alter table USER add constraint FK_CATEGORY_OF_CUSTOMER foreign key (CAT_ID)
      references CUSTOMER_CATEGORY (CAT_ID) on delete cascade on update cascade;

