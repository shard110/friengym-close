create table user_table (
    id VARCHAR2(50) primary key,
    pwd VARCHAR2(100) not null,
    name VARCHAR2(10) not null,
    phone VARCHAR2(30) not null,
    sex VARCHAR2(10) not null,
    height NUMBER,
    weight NUMBER,
    birth date,
    firstday NUMBER,
    restday NUMBER,
    photo BLOB
);

create table master (
    mid VARCHAR2(50) primary key,
    mpwd VARCHAR2(100) not null
);

create table post (
ponum NUMBER PRIMARY KEY,
poTitle VARCHAR2(100) not null,
poContents VARCHAR2(4000) not null,
poDate TIMESTAMP,
poFile BLOB,
poWarning NUMBER(1),
id VARCHAR2(50), 
    CONSTRAINT fk_user
        FOREIGN KEY (id)
        REFERENCES user_table(id)
);

create table reply(
rpnum NUMBER  primary key,
rpDate TIMESTAMP,
rpContents VARCHAR2(300) not null,
rpWarning NUMBER(1),
id VARCHAR2(50),
    ponum NUMBER,
    CONSTRAINT fk_reply_user
        FOREIGN KEY (id)
        REFERENCES user_table(id),
    CONSTRAINT fk_reply_post
        FOREIGN KEY (ponum)
        REFERENCES post(ponum)
);

create table ask (
anum NUMBER primary key,
aTitle VARCHAR2(100) not null,
aContents VARCHAR2(4000) not null,
aDate TIMESTAMP,
aFile BLOB,
id VARCHAR2(50),
    CONSTRAINT fk_ask_user
        FOREIGN KEY (id)
        REFERENCES user_table(id)
);

create table Manswer (
mContents VARCHAR2(4000) not null,
mDate TIMESTAMP,
mid VARCHAR2(50),
anum NUMBER,
    CONSTRAINT fk_Manswer_master
        FOREIGN KEY (mid)
        REFERENCES master(mid),
         CONSTRAINT fk_Manswer_ask
        FOREIGN KEY (anum)
        REFERENCES ask(anum)
        );
        
create table product(
pnum number primary key,
pname varchar2(20) not null,
pPrice number not null,
pCount number  not null,
pImg blob
);        

create table cart (
cnum number PRIMARY key,
cCount number,
id VARCHAR2(50),
pnum number,
 CONSTRAINT fk_cart_user
        FOREIGN KEY (id)
        REFERENCES user_table(id),
         CONSTRAINT fk_cart_product
        FOREIGN KEY (pnum)
        REFERENCES product(pnum)
);

create table order_table(
onum number PRIMARY key,
oDate TIMESTAMP,
id VARCHAR2(50),
 CONSTRAINT fk_order_user
        FOREIGN KEY (id)
        REFERENCES user_table(id)
);

create table Dorder(
DOnum number PRIMARY key,
DOcount number not null,
DOprice number not null,
onum number,
pnum number,
CONSTRAINT fk_Dorder_order
        FOREIGN KEY (onum)
        REFERENCES order_table(onum),
         CONSTRAINT fk_Dorder_product
        FOREIGN KEY (pnum)
        REFERENCES product(pnum)
);

create table review(
rvnum number primary key,
star number,
rvDate TIMESTAMP not null,
rvContent VARCHAR2(1000),
rvWarning number(1),
id VARCHAR2(50),
pnum number,
CONSTRAINT fk_review_user
        FOREIGN KEY (id)
        REFERENCES user_table(id),
         CONSTRAINT fk_review_product
        FOREIGN KEY (pnum)
        REFERENCES product(pnum)
);

create table warning_tbl(
wnum number PRIMARY key,
ponum number,
rpnum number,
rvnum number,
CONSTRAINT fk_warning_tbl_post
        FOREIGN KEY (ponum)
        REFERENCES post(ponum),
         CONSTRAINT fk_warning_tbl_reply
         FOREIGN KEY (rpnum)
         REFERENCES reply(rpnum),
        CONSTRAINT fk_warning_tbl_review
         FOREIGN KEY (rvnum)
         REFERENCES review(rvnum)
);

ALTER TABLE user_table RENAME TO usertbl;
ALTER TABLE order_table RENAME TO ordertbl;
ALTER TABLE warning_tbl RENAME TO warningtbl;
