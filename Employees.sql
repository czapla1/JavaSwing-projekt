create database Employees;
use Employees;

 create table employee(
  id_employee int not null auto_increment,
  firstname varchar(30) not null,
  lastname varchar(40) not null,
  pesel varchar(11),
  city varchar(40),
  salary int,
  seniority int,
  primary key (id_employee)
);

insert into employee (id_employee, firstname, lastname, pesel, city, salary,seniority) values
(1,'Jan','Kowalski','98501106787', 'Radom', 7000, 3),
(2,'Tomasz','Nowak','98431131142', 'Krakow', 3900, 6),
(3,'Anna','Kot','98912123450', 'Warszawa', 4600, 8),
(4,'Agata','Przybysz','9803376543', 'Wroclaw', 6700, 4),
(5,'Pawel','Wrobel','98804163456', 'Katowice', 6900,3),
(6,'Katarzyna','Mrowka','9847650719', 'Wadowice', 5600, 4),
(7,'Piotr','Czajka','9870523567', 'Torun', 7200, 5),
(8,'Michal','Tomaszewski','98301890615','Kielce',6700,3),
(9,'Cezary','Krowka','98904876512','Radom',7670,2),
(10,'Krzysztof','Pudelko','98657654321','Radom',8500,10);

create table logins (
    id_employee int not null,
    login varchar(45) not null unique,
    pass varchar(32) not null,
    priviliges varchar(15),
    primary key(id_employee),
    foreign key(id_employee) references employee (id_employee) 
    ON DELETE CASCADE
 );
 
insert into logins (id_employee, login, pass, priviliges) values  
(1, 'a', 's','admin'),
(2, 'log2', 'pass2', 'user'),
(3, 'log3', 'pass3','user'),
(4, 'log4', 'pass4', 'user'),
(5, 'log5', 'pass5', 'user'),
(6, 'log6', 'pass6', 'user'),
(7, 'log7', 'pass7', 'user'),
(8, 'log8', 'pass8', 'user'),
(9, 'log9', 'pass9', 'user'),
(10,'log10','pass10', 'user');


