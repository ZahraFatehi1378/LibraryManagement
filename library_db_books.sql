#add book
DELIMITER //
create procedure add_book(book_id int,book_number int,title varchar(20),
category varchar(20),pages int,publisher_id int,price numeric(8,2) )
BEGIN 
insert into book values(book_id ,book_number ,title,category ,pages ,publisher_id ,price , true);
#SELECT MAX(id) FROM table1; 
END//
DELIMITER ;


#insert publisher
DELIMITER //
create procedure add_publisher(publisher_name varchar(20),address varchar(512),website varchar(30))
BEGIN 
declare book_id int;
insert into  publisher(name ,address ,website ) values(publisher_name , address ,website );
END//
DELIMITER ;

	
