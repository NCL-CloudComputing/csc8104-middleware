--
-- JBoss, Home of Professional Open Source
-- Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
-- contributors by the @authors tag. See the copyright.txt in the
-- distribution for a full listing of individual contributors.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- http://www.apache.org/licenses/LICENSE-2.0
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- You can use this file to load seed data into the database using SQL statements
-- Since the database doesn't know to increase the Sequence to match what is manually loaded here it starts at 1 and tries
--  to enter a record with the same PK and create an error.  If we use a high we don't interfere with the sequencing (at least until later).
-- NOTE: this file should be removed for production systems. 
insert into Contact (id, first_name, last_name, email, phone_number, birth_date) values (10001, 'John', 'Smith', 'john.smith@mailinator.com', '(212) 555-1212', '1963-06-03')
insert into Contact (id, first_name, last_name, email, phone_number, birth_date) values (10002, 'Davey', 'Jones', 'davey.jones@locker.com', '(212) 555-3333', '1996-08-07')
insert into Customer (id, name, email, phone_number) values (10003, 'Davey Jones', 'davey.jones@locker.com', '01234567891')
insert into Customer (id, name, email, phone_number) values (10004, 'tom brandy', 'tom.brandy@locker.com', '043617385921')
insert into Customer(id , name ,email ,phone_number ) values ( 73, 'Test', 'test@test.com', '01234567890');
insert into Hotel (id, name, phone_number,post_code) values (10005, 'Hilton', '33335555555','123456')
insert into Booking (id,future_date,customer_id, hotel_id) values (10009, '2021-09-07', 10003, 10005)

