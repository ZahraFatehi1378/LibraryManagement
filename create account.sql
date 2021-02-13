#Signin and signup queries


call create_account('1111dddd' , '1fgd1111z' , 11111 , '11111111' , '11111111' ,11111 , '11111111' , 'student','11111111',null,'1111',null);
#signup a student
DELIMITER //
create procedure create_account(IN entered_username varchar(20) ,IN entered_password  varchar(20),
IN entered_national_id int,IN name_first_name varchar(20),IN name_last_name varchar(20) ,
IN student_instructor_id int , IN university_job varchar(20) ,IN user_state varchar(20),
IN phone_number1 varchar(20),IN phone_number2 varchar(20),IN address1 varchar(100) , IN address2 varchar(100) )
BEGIN 
declare is_pass_acceptable varchar(50);

set is_pass_acceptable = checkPasswordIsAcceptable(entered_username ,entered_password);

if is_pass_acceptable = 'true' then
START TRANSACTION;
    call insert_person(entered_username , entered_password ,entered_national_id  ,name_first_name ,name_last_name );
    call insert_person_state(entered_national_id ,student_instructor_id , university_job , user_state);
    	insert into address values(address1 , entered_national_id);
        if not(address2 ='')then
        insert into address values(address2 , entered_national_id);
        end if;
    	insert into phone_number values(phone_number1 , entered_national_id );
		if not(phone_number2 ='')then
        insert into phone_number values(phone_number2 , entered_national_id);
        end if;
        set is_pass_acceptable = 'inserted successfully';
COMMIT;
else select is_pass_acceptable as your_error;
end if;
END//
DELIMITER ;

#insert a person informations
DELIMITER //
create procedure insert_person(entered_username varchar(20) , entered_password varchar(20),entered_national_id int, name_first_name varchar(20), name_last_name varchar(20) )
DETERMINISTIC
BEGIN 
declare new_user_id int;

	insert into account ( username , password , token, account_balance , create_date)
	values(entered_username , sha(entered_password) , null ,0 , date(now()));
	set new_user_id = (select user_id from account where username = entered_username );
    
	insert into person (national_id ,user_id ,name_first_name ,name_last_name)
	values(entered_national_id ,new_user_id ,name_first_name,name_last_name);
    
END//
DELIMITER ;

#insert a person informations accourding to state
DELIMITER //
create procedure insert_person_state(IN entered_national_id int ,IN student_instructor_id int , IN university_job varchar(20) , user_state varchar(20))
DETERMINISTIC
BEGIN 

if(user_state = 'student')then
	insert into student values(entered_national_id , student_instructor_id , university_job );
elseif (user_state = 'instructor')then
	insert into instructor values(entered_national_id , student_instructor_id , university_job );
elseif (user_state = 'regular')then
	insert into regular_person values(entered_national_id , university_job );
#elseif (user_state = 'manager')then
#	insert into librarian values(entered_national_id , user_state );
elseif (user_state = 'librarian')then
	insert into librarian values(entered_national_id , user_state );
end if;
END//
DELIMITER;




call createAccount('zahrafatehi','sddsfffff4', 87345,123432,'jsdhk','hdfksjh');
call isAccountValid('zahrafatehi','sddsfffff4');
call getSystemInfo('zahrafatehi');
call getPersonalInfo('zahrafatehi');

#login
DELIMITER $$
create procedure isAccountValid(entered_username varchar(20) ,entered_password varchar(20))
DETERMINISTIC
BEGIN 
declare token varchar(60);
declare chosen_user_id int;
    if EXISTS (SELECT username FROM account WHERE username = entered_username AND password = sha(entered_password))
    then
    set token = sha1(concat(entered_username ,entered_password ,now()));
    set chosen_user_id = (select user_id from account where username = entered_username);
		UPDATE account set token = token WHERE user_id = chosen_user_id;
    else 
		set token= 'you need to sign up';
    end if;
select token;
END$$
DELIMITER;

#logout
DELIMITER $$
create procedure signout(entered_token varchar(512))
DETERMINISTIC
BEGIN 
declare entered_id int;
set entered_id =(select user_id from account where token = entered_token);
    UPDATE account set token = null WHERE user_id = entered_id;
    select 'bye' as state;
END$$
DELIMITER;



        
#get all info
DELIMITER //
create procedure getAllInfo(IN entered_token varchar(512))
BEGIN
DECLARE personState VARCHAR(20); 
set personState =  getPersonState(entered_token);
    IF personState = 'student' THEN
		select national_id , username , password , account_balance ,create_date,national_id,name_first_name ,name_last_name ,student_id , univercity_name,phone_number,address
        from account natural join person natural join student natural join phone_number natural join address where account.token = entered_token;
    ELSEIF personState = 'instructor' then
		select national_id , username , password , account_balance ,create_date,national_id,name_first_name ,name_last_name ,instructor_id , univercity_name,phone_number,address
		from account natural join person natural join instructor natural join phone_number natural join address where account.token = entered_token;
	ELSEIF personState = 'regular' then
		select national_id , username , password , account_balance ,create_date,national_id,name_first_name ,name_last_name , job,phone_number,address
        from account natural join person natural join regular_person natural join phone_number natural join address where account.token = entered_token;
	ELSEIF personState = 'librarian' or personState = 'manager' then
		select national_id , username , password , account_balance ,create_date,national_id,name_first_name ,name_last_name , state,phone_number,address
        from account natural join person natural join librarian natural join phone_number natural join address where account.token = entered_token;
    END IF; 
END//
   DELIMITER;
   
   
#get personal info
DELIMITER //
create procedure getPersonalInfo(IN entered_token varchar(512))
BEGIN
DECLARE personState VARCHAR(20); 
set personState =  getPersonState(entered_token);
    IF personState = 'student' THEN
		select national_id , username , password , account_balance ,create_date,national_id,name_first_name ,name_last_name ,student_id , univercity_name,phone_number,address
        from person natural join student natural join phone_number natural join address where account.token = entered_token;
    ELSEIF personState = 'instructor' then
		select national_id , username , password , account_balance ,create_date,national_id,name_first_name ,name_last_name ,instructor_id , univercity_name,phone_number,address
        from  person natural join instructor natural join phone_number natural join address where account.token = entered_token;
	ELSEIF personState = 'regular' then
		select national_id , username , password , account_balance ,create_date,national_id,name_first_name ,name_last_name , job,phone_number,address
        from person natural join regular_person natural join phone_number natural join address where account.token = entered_token;
	ELSEIF personState = 'librarian' or personState = 'manager' then
		select *from person natural join librarian;
    END IF; 
END//
   DELIMITER;
   

 
#get system informaion
DELIMITER //
create procedure getSystemInfo(IN entered_token varchar(512))
begin
SELECT *
FROM account
where account.token = entered_token;
END//
   DELIMITER;


#get system informaion
DELIMITER //
create function get_nationalId_from_token(entered_token varchar(512))
returns int
begin
declare chosen_national_id int;
set chosen_national_id = (	select national_id 
					from person 
					where person.user_id =(select user_id 
										   from account 
											where token = entered_token));
return chosen_national_id;
END//
   DELIMITER ;
   
#get person state
DELIMITER //
create procedure get_person_state(entered_token varchar(512))
begin
select  getPersonState(entered_token)as person_state;
END//
   DELIMITER ;

#get person state
DELIMITER $$
create function getPersonState(entered_token varchar(512))
RETURNS VARCHAR(20)
DETERMINISTIC
BEGIN
    declare personState VARCHAR(20);
    declare entered_national_id int;
    set entered_national_id = get_nationalId_from_token(entered_token);
    IF entered_national_id in 
    (select national_id 
    from student) THEN
		SET personState = 'student';
    ELSEIF entered_national_id in 
    (select national_id 
    from instructor) THEN
        SET personState = 'instructor';
	ELSEIF entered_national_id in 
    (select national_id 
    from regular_person) THEN
        SET personState = 'regular';
	ELSEIF entered_national_id in 
    (select national_id 
    from librarian) THEN
		if ('manager' in (select state from librarian where national_id = entered_national_id) ) then
		SET personState = 'manager';
        else
        SET personState = 'librarian';
        end if;
    END IF;
	RETURN (personState);
END$$
DELIMITER ;


DELIMITER //
create procedure get_person_state(entered_token varchar(512))
begin
select  getPersonState(entered_token)as person_state;
END//
   DELIMITER ;

#get person state
DELIMITER $$
create procedure getPersonStateViaNationalId(entered_national_id varchar(20))
DETERMINISTIC
BEGIN
    declare personState VARCHAR(20);

    IF entered_national_id in 
    (select national_id 
    from student) THEN
		SET personState = 'student';
    ELSEIF entered_national_id in 
    (select national_id 
    from instructor) THEN
        SET personState = 'instructor';
	ELSEIF entered_national_id in 
    (select national_id 
    from regular_person) THEN
        SET personState = 'regular';
	ELSEIF entered_national_id in 
    (select national_id 
    from librarian) THEN
		if ('manager' in (select state from librarian where national_id = entered_national_id) ) then
		SET personState = 'manager';
        else
        SET personState = 'librarian';
        end if;
    END IF;
	select personState as person_state;
END$$
DELIMITER ;

call getPersonStateViaNationalId(1234);
#check hashed pass
DELIMITER $$
create function checkPasswordIsAcceptable(entered_username varchar(20) , entered_password  varchar(20))
returns varchar(50)
BEGIN 
DECLARE acceptable varchar(50);
set acceptable = 'true';
if length(entered_username) < 6
	then set acceptable = 'your username is less than 6';
elseif length(entered_password) < 8
	then set acceptable = 'your pass is less than 8';
elseif( entered_password  like '%[0-9]%' and entered_password like '%[A-Z]%')
	then set acceptable = 'you need to use letter and number for password';
end if;
return (acceptable);
END$$
DELIMITER ;





