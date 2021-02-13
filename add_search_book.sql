#add book



#search book
DELIMITER //
create procedure search_book(entered_title varchar(20), entered_author_name varchar(20) , entered_book_number int,entered_published_year int,entered_category varchar(20))
BEGIN 
IF NULLIF(entered_title, '0') IS NULL then
	IF NULLIF(entered_author_name, '0') IS NULL then 
		IF NULLIF(entered_book_number, '0') IS NULL then 
			IF NULLIF(entered_published_year, '0') IS NULL then 
				IF NULLIF(entered_category, '0') IS NOT NULL then 
                select * from book natural join author where category = entered_category;
                end if;
			else select * from book natural join author where category = entered_category and published_year = entered_published_year ;
            end if;
		else select * from book natural join author where category = entered_category and published_year = entered_published_year and book_number = entered_book_number;
        end if;
	else select * from book natural join author where category = entered_category and published_year = entered_published_year and book_number = entered_book_number and name = entered_author_name;
    end if;
else select * from book natural join author where category = entered_category and published_year = entered_published_year and book_number = entered_book_number and name = entered_author_name and title = entered_title;
end if;
END//
DELIMITER ;

#seach members


