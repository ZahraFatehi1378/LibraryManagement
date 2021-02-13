#add book
DELIMITER //
create procedure add_book(book_number int,title varchar(20),
category varchar(20),pages int,publisher_id int,price numeric(8,2) , author_name varchar(20) , published_year int)
BEGIN 
declare book_id int;
declare author_id int;
insert into book values(book_id ,book_number ,title,category ,pages ,publisher_id ,price ,published_year,true);
set book_id = last_insert_id();
#SELECT MAX(id) FROM table1; 
set author_id = findAuthorId(book_id ,author_name);
select 'book added' as result;
END//
DELIMITER ;



DELIMITER $$
CREATE FUNCTION findAuthorId( entered_book_id int ,entered_name varchar(20))
RETURNS INT
BEGIN
    declare authorId int;
    if exists(select author_id from author where book_id = entered_book_id and name = entered_name )then
    set authorId=(select author_id from author where book_id = entered_book_id and name = entered_name);
    else 
    insert into author(  book_id , name) values(entered_book_id, entered_name);
    set authorId=(select author_id from author where book_id = entered_book_id and name = entered_name);
    END IF;
    RETURN authorId;
END$$
DELIMITER ;

call add_book(1 , 'book1' , 'reference',20 , 1 , 300,'sarah26',1000);


select author_id from author where book_id = 20 and name = 'sarah26';
#search usrs
DELIMITER //
create procedure search_users_personal_info_username(entered_username varchar(20))
BEGIN 
if exists( select*from account natural join person natural join student natural join phone_number natural join address where username =entered_username)then
select national_id , username , password , account_balance ,create_date,national_id,name_first_name ,name_last_name ,student_id , univercity_name,phone_number,address
from account natural join person natural join instructor natural join phone_number natural join address where username =entered_username;
elseif exists( select*from account natural join person natural join instructor natural join phone_number natural join address where username =entered_username)then
select national_id , username , password , account_balance ,create_date,national_id,name_first_name ,name_last_name ,instructor_id , univercity_name,phone_number,address
from account natural join person natural join instructor natural join phone_number natural join address where username =entered_username;
elseif exists( select*from account natural join person natural join regular_person natural join phone_number natural join address where username =entered_username)then
select national_id , username , password , account_balance ,create_date,national_id,name_first_name ,name_last_name , job,phone_number,address
from account natural join person natural join regular_person natural join phone_number natural join address where username =entered_username;
elseif exists( select*from account natural join person natural join librarian natural join phone_number natural join address where username =entered_username)then
select national_id , username , password , account_balance ,create_date,national_id,name_first_name ,name_last_name , state,phone_number,address
from account natural join person natural join librarian natural join phone_number natural join address where username =entered_username;
end if;
END//
DELIMITER ;

call search_users_personal_info_username('12345678');
call search_users_personal_info_lastname('12345678');


DELIMITER //
create procedure search_users_personal_info_lastname(lastname varchar(20))
BEGIN 
if exists( select*from  person natural join student where name_last_name =lastname order by lastname)then
select national_id , username , password , account_balance ,create_date,national_id,name_first_name ,name_last_name ,student_id , univercity_name,phone_number,address
from account natural join  person natural join instructor natural join phone_number natural join address where name_last_name =lastname order by name_last_name desc;
elseif exists( select*from person natural join instructor  where name_last_name =lastname)then
select national_id , username , password , account_balance ,create_date,national_id,name_first_name ,name_last_name ,instructor_id , univercity_name,phone_number,address
from account natural join  person natural join instructor natural join phone_number natural join address where name_last_name =lastname order by name_last_name desc;
elseif exists( select*from person natural join regular_person where name_last_name =lastname)then
select national_id , username , password , account_balance ,create_date,national_id,name_first_name ,name_last_name , job,phone_number,address
from account natural join  person natural join regular_person natural join phone_number natural join address where name_last_name =lastname order by name_last_name desc;
elseif exists( select*from  person natural join librarian natural join phone_number natural join address where name_last_name =lastname)then
select national_id , username , password , account_balance ,create_date,national_id,name_first_name ,name_last_name , state,phone_number,address
from account natural join person natural join librarian natural join phone_number natural join address  address where name_last_name =lastname order by name_last_name desc;
end if;
END//
DELIMITER ;




DELIMITER //
create procedure get_inbox_info()
BEGIN 
select * from inbox;
END//
DELIMITER ;



DELIMITER //
create procedure delete_user(entered_username varchar(20))
BEGIN 
declare entered_national_id int;
declare massage varchar(20);

set massage = 'not found';
set entered_national_id = (select national_id from person where user_id = (select user_id from account where username = entered_username));

delete  from address where national_id= entered_national_id;
delete  from phone_number where national_id= entered_national_id;


if exists( select*from  person natural join student where national_id = entered_national_id)then
delete from student where national_id= entered_national_id;
set massage = 'deleted';
elseif exists( select*from person natural join instructor  where national_id = entered_national_id)then
delete from instructor where national_id= entered_national_id;
set massage = 'deleted';
elseif exists( select*from person natural join regular_person where national_id = entered_national_id)then
delete from regular_person where national_id= entered_national_id;
set massage = 'deleted';
elseif exists( select*from person natural join librarian where national_id = entered_national_id)then
delete from librarian where national_id= entered_national_id;
set massage = 'deleted';
end if;

delete from person where national_id= entered_national_id;

delete from account where username= entered_username;

select massage as result;
END//
DELIMITER ;
