create schema if not exists ecommerce_cli;

set search_path to ecommerce_cli;

drop table if exists users cascade;

drop table if exists products cascade;

drop table if exists orders cascade;

drop table if exists order_products cascade;

drop table if exists reviews cascade;

drop table if exists cart_products cascade;

DROP TABLE IF EXISTS payment_methods cascade;

create table users (
	id varchar(40) primary key,
	username varchar(24) not null unique,
	password varchar(24) not null
);

create table products (
	id varchar(40) primary key,
	name varchar(64) not null,
	category varchar(52) not null,
	price integer not null
);

create table payment_methods (
    id varchar(40) primary key,
    number varchar(16) NOT NULL,
    expiration_date timestamp NOT NULL,
    cvc varchar(3) NOT NULL,
    user_id varchar(40) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

create table orders (
	id varchar(40) primary key,
	user_id varchar(40) not null,
	amount integer not null,
	time_placed timestamp not null default now(), 
	payment_method_id varchar(40) NOT NULL,
	FOREIGN KEY (payment_method_id) REFERENCES payment_methods(id),
	foreign key (user_id) references users(id)
);

create table order_products(
	id varchar(40) PRIMARY KEY,
	order_id varchar(40),
	product_id varchar(40),
	quantity integer not null,
	foreign key (order_id) references orders(id),
	foreign key (product_id) references products(id)
);

create table cart_products(
	id varchar(40) PRIMARY KEY,
	user_id varchar(40),
	product_id varchar(40),
	quantity integer not null,
	foreign key (user_id) references orders(id),
	foreign key (product_id) references products(id)
);

create table reviews (
	id varchar(40) primary key,
	rating int not null,
	comment text,
	user_id varchar(40) not null,
	product_id varchar(40) not null,
	foreign key (user_id) references users (id),
	foreign key (product_id) references products (id)
);

