#user works
# looking for books queries

call search_book(0, 'sarah',0,0);
#search book()
DELIMITER //
create procedure search_book(entered_title varchar(20), entered_author_name varchar(20) , entered_book_number int,entered_published_year int)
BEGIN 
IF (NULLIF(entered_title, '0') IS not NULL )then
	IF (NULLIF(entered_author_name, '0') IS  NULL and NULLIF(entered_published_year, '0') IS NULL and NULLIF(entered_book_number, '0') IS NULL)  then 
        select * from book natural join author where title = entered_title;
	elseIF( NULLIF(entered_published_year, '0') IS NULL and NULLIF(entered_book_number, '0') IS NULL)then
		select * from book natural join author where title = entered_title  and name = entered_author_name;
    elseIF( NULLIF(entered_published_year, '0') IS NULL and NULLIF(entered_author_name, '0') IS  NULL)then
		select * from book natural join author where title = entered_title and book_number = entered_book_number ;
    elseIF(  NULLIF(entered_book_number, '0') IS NULL and NULLIF(entered_author_name, '0') IS  NULL)then
        select * from book natural join author where title = entered_title and published_year = entered_published_year ;
	elseIF( NULLIF(entered_published_year, '0')is null)then
		select * from book natural join author where title = entered_title  and book_number = entered_book_number and name = entered_author_name;
    elseIF(  NULLIF(entered_book_number, '0') IS NULL )then
		select * from book natural join author where title = entered_title and published_year = entered_published_year and name = entered_author_name;
    elseIF(  NULLIF(entered_author_name, '0') IS  NULL)then
		select * from book natural join author where title = entered_title and published_year = entered_published_year and book_number = entered_book_number;
    else
    select * from book natural join author where title = entered_title and published_year = entered_published_year and book_number = entered_book_number and name = entered_author_name;
    end if;
end if;

IF (NULLIF(entered_title, '0') IS  NULL )then
	IF( NULLIF(entered_published_year, '0') IS NULL and NULLIF(entered_book_number, '0') IS NULL)then
		select * from book natural join author where name = entered_author_name;
    elseIF( NULLIF(entered_published_year, '0') IS NULL and NULLIF(entered_author_name, '0') IS  NULL)then
		select * from book natural join author where book_number = entered_book_number ;
    elseIF(  NULLIF(entered_book_number, '0') IS NULL and NULLIF(entered_author_name, '0') IS  NULL)then
        select * from book natural join author where published_year = entered_published_year ;
	elseIF( NULLIF(entered_published_year, '0')is null)then
		select * from book natural join author where book_number = entered_book_number and name = entered_author_name;
    elseIF(  NULLIF(entered_book_number, '0') IS NULL )then
		select * from book natural join author where published_year = entered_published_year and name = entered_author_name;
    elseIF(  NULLIF(entered_author_name, '0') IS  NULL)then
		select * from book natural join author where published_year = entered_published_year and book_number = entered_book_number;
    else
    select * from book natural join author where published_year = entered_published_year and book_number = entered_book_number and name = entered_author_name;
    end if;
end if;
END//
DELIMITER ;



#incease account_balance
DELIMITER //
create procedure incease_account_balance(entered_token varchar(512), new_account_balance decimal(13, 4))
BEGIN 
declare entered_user_id int;
declare last_account_balance decimal(13,4);
set entered_user_id =(select user_id from account where token = entered_token);
set last_account_balance = (select account_balance from account where token = entered_token);
update account 
set account_balance = case
when(new_account_balance > 0) then new_account_balance +last_account_balance
end 
where user_id = entered_user_id;
if(new_account_balance > 0)then
select 'successed' as result;
else
select 'failed' as result;
end if;
END//
DELIMITER ;
call incease_account_balance('8cfafedcedc8e745272c59b702720f1e3e593b1d' , 20000);
call getBookP('f5c833cd44f49c8dd3266957de9fbd2c8fac5239' , 4 , 1);

#get book
DELIMITER //
create procedure getBookP(IN entered_token varchar(512) , IN  requested_book_id int, IN book_issue int )
BEGIN
DECLARE personState VARCHAR(20);
DECLARE entered_user_id  varchar(512);
declare ask_book varchar(20);
declare charged_amount DECIMAL(13, 4);

set personState =  getPersonState(entered_token);
set entered_user_id =(select user_id 
			  from account 
			  where token = entered_token);
set charged_amount =(select account_balance from account where user_id =entered_user_id );
set ask_book = checkUserCanGetBook(entered_user_id ,requested_book_id , charged_amount);

if(ask_book = 'successful') then
    IF personState = 'student' THEN
		if exists ( select book_id from book where book_id = requested_book_id and category = 'reference' )then
		select "You do not have permission to access this book";
		else
		call getBook(entered_user_id,requested_book_id , ask_book , book_issue);
		end if;
    ELSEIF personState = 'instructor' then
		call getBook(entered_user_id ,requested_book_id , ask_book , book_issue);
	ELSEIF personState = 'regular' then
		if exists ( select book_id from book where book_id = requested_book_id and (category = 'reference' or category= 'academic'))then
        select "You do not have permission to access this book";
		else
		call getBook(entered_user_id ,requested_book_id ,ask_book, book_issue);
		end if;
    END IF; 
else 
select ask_book as result;
end if;
END//
DELIMITER ;

    
  select  (select price from book where book_id = 1 ) *0.05;
    
        insert into loan_history(user_id , loan_status , charged_amount, start_date , return_date , legal_loan_duration)
    values(2 , 2 , 2 , date(now()) , null ,str_to_date("0000-00-14","%Y%m%d"));
    
    select str_to_date("0000-00-14","%Y-%m-%d");

DELIMITER $$
CREATE procedure getBook(entered_user_id int , requested_book_id int , ask_book varchar(20) , book_issue int )
BEGIN
declare result varchar(50);
declare needed_charged_amount decimal(13 , 4);
declare current_loan_id int;
declare firsname varchar(20);
declare lastname varchar(20);
declare bookname varchar(20);
set needed_charged_amount = ((select price from book where book_id = requested_book_id ) *0.05);

    insert into loan_history(user_id , loan_status , charged_amount, start_date , return_date , legal_loan_duration)
    values(entered_user_id , ask_book , needed_charged_amount , date(now()) , null , '0000-00-14');
    
    set current_loan_id = (select max(loan_id) from loan_history);
    
    insert into inventory ( loan_id , book_id ,book_issue )
    values(current_loan_id, requested_book_id ,book_issue );

if(ask_book = 'successful') then
	set firsname = (select name_first_name from person where user_id = entered_user_id );
    set lastname = (select name_last_name from person where user_id = entered_user_id );
    set bookname = (select price from book where book_id = requested_book_id ); 
	insert into inbox (txt)
    values(concat(firsname ,' ' , lastname,' in date : ',date(now()) ,' borrowed ', bookname ));
	update book set available = false where book_id = requested_book_id;
    set result = 'borrowed successfully';
else
	set result = concat('you can not get book because of ', ask_book);
end if;
select result;
END$$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION checkUserCanGetBook(entered_user_id int , requested_book_id int , charged_amount DECIMAL(13, 4) )
RETURNS varchar(50)
BEGIN
declare result varchar(50);
declare needed_charged_amount int;
declare current_charged_amount int;

set needed_charged_amount = ((select price from book where book_id = requested_book_id ) *0.05);
set current_charged_amount = (select account_balance from account where user_id = entered_user_id);
if (needed_charged_amount < current_charged_amount) then
	if(exists(select requested_book_id from book where book_id = requested_book_id) ) then
		if(checkHistory())then 
			set result = 'successful';
            update account set account_balance = current_charged_amount -needed_charged_amount where user_id =entered_user_id;
		else
			set result = 'Deprived';
		end if;
	else 
		set result = 'book is not available';
	end if;
else 
	set result = 'not enough balance';
end if;
return result;
END$$
DELIMITER ;


DELIMITER $$
CREATE FUNCTION checkHistory(user_id int , requested_book_id int )
RETURNS bool
BEGIN
declare result bool;
if( select count(start_date) as numOfDelayed
	from loan_history 
    where (DATEDIFF(return_date , start_date) > '0000-00-14'))then
    set result = true;
else 
    set result = false;
end if;
END$$
DELIMITER ;

#*************

call returnBookP ('067eaaf2844e4c8cdc0aecc87e25f4f460a8c989' , 3);
select max(loan_id) 
				   from loan_history natural join inventory 
                   where user_id = 19 and book_id = 3;
select start_date from loan_history where user_id=19;
#return book
DELIMITER //
create procedure returnBookP(IN entered_token varchar(512) , IN  returned_book_id int)
BEGIN
DECLARE entered_user_id  varchar(512);
declare entered_loan_id int;
declare firstname varchar(20);
declare lastname varchar(20);
declare bookname varchar(20);
declare return_state varchar(10);
declare account_start_date date;

set entered_user_id =(select user_id 
					  from account 
					  where token = entered_token);
start transaction;#use max because someone borrow a book twice
    set entered_loan_id = (select max(loan_id) 
				   from loan_history natural join inventory 
                   where user_id = user_id and book_id = returned_book_id );
                   
	set account_start_date= (select start_date from loan_history where loan_id=entered_user_id);
                   
    update  loan_history
    set return_date = date(now())
    where(loan_id = entered_loan_id);

	set firstname = (select name_first_name from person where user_id = entered_user_id );
    set lastname = (select name_last_name from person where user_id = entered_user_id );
    set bookname = (select price from book where book_id = returned_book_id ); 
    
	if (DATEDIFF(date(now()) , account_start_date) > '0000-00-14')then
		set return_state = 'delayed';
	else
		set return_state = 'in time';
	end if;
	insert into inbox (txt)
    values(concat(firstname ,' ' , lastname,' in date : ',date(now()) ,' returned ', bookname , ' and status is :  ',return_state ));
	update book set available = true where book_id = returned_book_id;
    select concat('returned with : ',return_state) as result;
commit;

END//
DELIMITER ;


DELIMITER $$
CREATE FUNCTION checkHistory( )
RETURNS bool
BEGIN
declare numOfDelays bool;
declare result bool;
set numOfDelays=( select count(start_date) as numOfDelayed
	from loan_history 
    where ((DATEDIFF(return_date , start_date) > '0000-00-14')
    and (date(now())- start_date < '0000-00-60')));
if( numOfDelays>4)then
    set result = false;
else 
    set result = true;
end if;
return result;
END$$
DELIMITER ;

#emtiazi
DELIMITER $$


#emtiazi
DELIMITER $$
CREATE procedure delayed_books( )
BEGIN
SELECT datediff(now() , loan_history.legal_loan_duration) as difff
 , book.*  from loan_history inner join inventory inner join book on 
 inventory.loan_id = loan_history.loan_id and book.book_id = inventory.book_id 
 WHERE (datediff(now() , loan_history.legal_loan_duration)  > 0 and loan_history.return_date is null) order by difff DESC;
END$$
DELIMITER ;

#emtiazi
DELIMITER $$
CREATE procedure delayed_books( )
BEGIN
select * 
from book natural join loan_history
where((DATEDIFF(date(now()) , start_date) > 0000-00-14)and return_date = null);
END$$
DELIMITER ;

call delayed_books();
DELIMITER $$
CREATE procedure history(In entered_book_id int)
BEGIN
select * 
from book natural join loan_history natural join inventory where book_id = entered_book_id
order by return_date;
END$$
DELIMITER ;


call history(4);
