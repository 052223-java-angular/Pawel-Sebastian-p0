create schema if not exists ecommerce_cli;

set search_path to ecommerce_cli;

drop table if exists users cascade;

drop table if exists products cascade;

drop table if exists orders cascade;

drop table if exists order_products cascade;

drop table if exists reviews cascade;

drop table if exists cart_products cascade;

create table users (
	id varchar(40) primary key,
	username varchar(24) not null unique,
	password text not null
);

create table products (
	id varchar(40) primary key,
	name varchar(64) not null,
	category varchar(52) not null,
	price integer not null,
    in_stock integer not null
);

create table orders (
	id varchar(40) primary key,
	user_id varchar(40) not null,
	amount integer not null,
	time_placed timestamp not null default now(), 
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
	foreign key (user_id) references users(id),
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

