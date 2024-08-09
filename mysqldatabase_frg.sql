create database frg;
use frg;

show tables;

create table usertbl (
    id VARCHAR(50) primary key,
    pwd VARCHAR(50) not null,
    name VARCHAR(10) not null,
    phone VARCHAR(30) not null,
    sex VARCHAR(10) not null,
    height int,
    weight int,
    birth date,
    firstday int,
    restday int,
    photo BLOB,
    sessionkey varchar(50),
    sessionlimit timestamp
);

create table master (
    mid VARCHAR(50) primary key,
    mpwd VARCHAR(50) not null
);


create table post (
ponum int PRIMARY KEY,
poTitle VARCHAR(100) not null,
poContents VARCHAR(4000) not null,
poDate TIMESTAMP default now(),
poFile BLOB,
poWarning boolean,
viewcnt int,
replycnt int,
id  VARCHAR(50),
FOREIGN KEY (id) REFERENCES usertbl(id)
);

ALTER TABLE post MODIFY viewcnt INT DEFAULT 0;
ALTER TABLE post MODIFY replycnt INT DEFAULT 0;


create table reply(
rpnum int primary key,
rpDate TIMESTAMP default now(),
rpContents VARCHAR(300) not null,
rpWarning boolean,
id VARCHAR(50),
    ponum int,
  FOREIGN KEY (id) REFERENCES usertbl(id),
  FOREIGN KEY (ponum) REFERENCES post(ponum)
);

create table ask (
anum int primary key,
aTitle VARCHAR(100) not null,
aContents VARCHAR(4000) not null,
aDate TIMESTAMP default now(),
aFile BLOB,
id VARCHAR(50),
FOREIGN KEY (id) REFERENCES usertbl(id)
);

create table Manswer (
mContents VARCHAR(4000) not null,
mDate TIMESTAMP default now(),
mid VARCHAR(50),
	FOREIGN KEY (mid) REFERENCES master(mid),
anum int,
   FOREIGN KEY (anum) REFERENCES ask(anum)
        );
        
create table product(
pnum int primary key,
pname varchar(30) not null,
pPrice int not null,
pCount int  not null,
pImg blob,
pDate timestamp default now()
);

create table cart (
cnum int PRIMARY key,
cCount int,
id VARCHAR(50),
pnum int,
 FOREIGN KEY (id) REFERENCES usertbl(id),
 FOREIGN KEY (pnum) REFERENCES product(pnum)
);        


create table ordertbl(
onum int PRIMARY key,
oDate TIMESTAMP,
id VARCHAR(50),
 FOREIGN KEY (id) REFERENCES usertbl(id)
);

create table Dorder(
DOnum int PRIMARY key,
DOcount int not null,
DOprice int not null,
onum int,
FOREIGN KEY (onum) REFERENCES ordertbl(onum),
pnum int,
FOREIGN KEY (pnum) REFERENCES product(pnum)
);

ALTER TABLE Dorder MODIFY DOcount int DEFAULT 0;

create table review(
rvnum int primary key,
star int,
rvDate TIMESTAMP default now(),
rvContent VARCHAR(1000),
rvWarning boolean,
id VARCHAR(50),
FOREIGN KEY (id) REFERENCES usertbl(id),
pnum int,
FOREIGN KEY (pnum) REFERENCES product(pnum)
);

create table warningtbl(
wnum int PRIMARY key,
ponum int,
FOREIGN KEY (ponum) REFERENCES post(ponum),
rpnum int,
FOREIGN KEY (rpnum) REFERENCES reply(rpnum),
rvnum int,
FOREIGN KEY (rvnum) REFERENCES review(rvnum)
);


-- 트리거 설정 (경고 여부가 true일 때 신고테이블에 wnum이 생성되도록)

DELIMITER //
CREATE TRIGGER after_post_warning
AFTER UPDATE ON post
FOR EACH ROW
BEGIN
    IF NEW.poWarning = TRUE AND OLD.poWarning = FALSE THEN
        INSERT INTO warningtbl (ponum) VALUES (NEW.ponum);
    END IF;
END //
DELIMITER ;

DELIMITER //
CREATE TRIGGER after_reply_warning
AFTER UPDATE ON reply
FOR EACH ROW
BEGIN
    IF NEW.rpWarning = TRUE AND OLD.rpWarning = FALSE THEN
        INSERT INTO warningtbl (rpnum) VALUES (NEW.rpnum);
    END IF;
END //
DELIMITER ;

DELIMITER //
CREATE TRIGGER after_review_warning
AFTER UPDATE ON review
FOR EACH ROW
BEGIN
    IF NEW.rvWarning = TRUE AND OLD.rvWarning = FALSE THEN
        INSERT INTO warningtbl (rvnum) VALUES (NEW.rvnum);
    END IF;
END //
DELIMITER ;


