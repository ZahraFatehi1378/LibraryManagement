#add book
DELIMITER //
create procedure add_book(book_number int,title varchar(20),
category varchar(20),pages int,publisher_id int,price numeric(8,2) , author_name varchar(20) , published_year int)
BEGIN 
declare book_id int;
declare author_id int;
insert into book values(book_id ,book_number ,title,category ,pages ,publisher_id ,price ,published_year);
set book_id = last_insert_id();
#SELECT MAX(id) FROM table1; 
set author_id = findAuthorId(book_id ,name);
if author_id> 0 then
insert into author( author_id, book_id , name) values(author_id ,book_id, author_name);
end if;
END//
DELIMITER ;



#search usrs
DELIMITER //
create procedure search_users_personal_info_username(username varchar(20))
BEGIN 
if exists( select*from account natural join person natural join student natural join phone_number natural join address where username =username)then
select*from account natural join person natural join instructor natural join phone_number natural join address where username =username;
elseif exists( select*from account natural join person natural join instructor natural join phone_number natural join address where username =username)then
select*from account natural join person natural join instructor natural join phone_number natural join address where username =username;
elseif exists( select*from account natural join person natural join regular_person natural join phone_number natural join address where username =username)then
select*from account natural join person natural join regular_person natural join phone_number natural join address where username =username;
end if;
END//
DELIMITER ;


DELIMITER //
create procedure search_users_personal_info_lastname(lastname varchar(20))
BEGIN 
if exists( select*from  person natural join student where name_last_name =lastname order by lastname)then
select*from person natural join instructor natural join phone_number natural join address where name_last_name =lastname order by name_last_name desc;
elseif exists( select*from person natural join instructor  where name_last_name =lastname)then
select*from person natural join instructor natural join phone_number natural join address where name_last_name =lastname order by name_last_name desc;
elseif exists( select*from person natural join regular_person where name_last_name =lastname)then
select*from  person natural join regular_person natural join phone_number natural join address where name_last_name =lastname order by name_last_name desc;
end if;
END//
DELIMITER ;




DELIMITER //
create procedure get_inbox_info(lastname varchar(20))
BEGIN 
if exists( select*from  person natural join student where name_last_name =lastname order by lastname)then
select*from person natural join instructor natural join phone_number natural join address where name_last_name =lastname order by name_last_name desc;
elseif exists( select*from person natural join instructor  where name_last_name =lastname)then
select*from person natural join instructor natural join phone_number natural join address where name_last_name =lastname order by name_last_name desc;
elseif exists( select*from person natural join regular_person where name_last_name =lastname)then
select*from  person natural join regular_person natural join phone_number natural join address where name_last_name =lastname order by name_last_name desc;
end if;
END//
DELIMITER ;