#there is just 1 library 
create table publisher(
publisher_id int auto_increment,
name varchar(20),
address varchar(512),
website varchar(30),
primary key(publisher_id)
);


create table book (
book_id int auto_increment,
book_number int default 0,
title varchar(20),
category varchar(20),
pages int,
publisher_id int,
price numeric(8,2),
published_year int,
available bool,
primary key(book_id ),
foreign key(publisher_id) references publisher(publisher_id)
);

create table author(
author_id int auto_increment, 
book_id int,
name varchar(20),
primary key(author_id , book_id),
foreign key(book_id)references book(book_id)
);





create table account(
user_id int auto_increment,
username varchar(20) CHECK (length(username) >= 6),
password varchar(512) ,
token varchar(512),
account_balance DECIMAL(13, 4) default 0,
create_date date,
PRIMARY KEY (user_id)
);



create table loan_history(
loan_id int auto_increment,
user_id int,
loan_status varchar(20),
charged_amount DECIMAL(13, 4),
start_date date,
return_date date,
legal_loan_duration date,
primary key(loan_id),
foreign key (user_id) references account(user_id)
);


create table inventory(
inventory_id int auto_increment,
loan_id int,
book_id int,
book_issue int,
primary key(inventory_id, book_id , book_issue),
foreign key(loan_id) references loan_history(loan_id),
foreign key (book_id ) references book(book_id )
);

create table person(
national_id int,
user_id int,
name_first_name varchar(20),
name_last_name varchar(20),
primary key(national_id),
foreign key(user_id) references account(user_id)
);

create table address (
address varchar(100),
national_id int , 
primary key(address , national_id),
foreign key (national_id) references person(national_id)
);

create table phone_number(
phone_number varchar(20),
national_id int , 
primary key(phone_number , national_id),
foreign key (national_id) references person(national_id)
);

create table instructor(
national_id int,
instructor_id int,
univercity_name varchar(20),
foreign key (national_id) references person(national_id)
);

create table librarian(
national_id int,
state varchar(20),
foreign key (national_id) references person(national_id)
);

create table regular_person(
national_id int ,
job varchar(20),
foreign key (national_id) references person(national_id)
);

create table student(
national_id int,
student_id int,
univercity_name varchar(20),
foreign key (national_id) references person(national_id)
);



#*********************************************

create table inbox (
inbox_id int auto_increment ,
txt varchar(512),
primary key(inbox_id));

#add manager
insert into account(username , password , account_balance , create_date) values('manager' , sha('manager1234') , null , date(now()));

insert into person(national_id , user_id , name_first_name , name_last_name) values(1234 , 1 ,'managerfn' , 'managerln');

insert into librarian(national_id , state) values(1234 ,'manager');

insert into phone_number(phone_number , national_id) value(09181234 , 1234);

insert into address(address , national_id) values('1234' , 1234);




#add librarian
insert into account(username , password , account_balance , create_date) values('librarian1' , sha('librarian1') , null , date(now()));

insert into person(national_id , user_id , name_first_name , name_last_name) values(2 , 2 ,'librarian1' , 'librarian1');

insert into librarian(national_id , state) values(2 ,'librarian');

insert into phone_number(phone_number , national_id) value(09183701010 , 2);

insert into address(address , national_id) values('second ave' , 2);



insert into account(username , password , account_balance , create_date) values('librarian2' , sha('librarian2') , null , date(now()));

insert into person(national_id , user_id , name_first_name , name_last_name) values(3 , 3 ,'librarian2' , 'librarian2');

insert into librarian(national_id , state) values(3 ,'librarian');

insert into phone_number(phone_number , national_id) value(09183706010 , 3);

insert into address(address , national_id) values('third ave' , 3);


#insert publisher
insert into publisher values(1,'publisher1' , 'ave7' , 'www.publisher1.com');
insert into publisher values(2,'publisher2' , 'ave8' , 'www.publisher2.com');
