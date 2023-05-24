create schema if not exists ecommerce_cli;

set search_path to ecommerce_cli;

drop table if exists users cascade;

drop table if exists products cascade;

drop table if exists orders cascade;

drop table if exists orderproducts cascade;

drop table if exists reviews cascade;

drop table if exists cartproducts cascade;

create table users (
	id varchar(255) primary key,
	username varchar(255) not null unique,
	password varchar(255) not null
);

create table products (
	id varchar(255) primary key,
	name varchar(255) not null,
	category varchar(255) not null,
	price decimal not null
);

create table orders (
	id varchar(255) primary key,
	user_id varchar(255) not null,
	amount decimal not null,
	time_placed timestamp not null default now(), 
	foreign key (user_id) references users(id)
);

create table orderproducts(
	order_id varchar(255),
	product_id varchar(255),
	quantity integer not null,
	foreign key (order_id) references orders(id),
	foreign key (product_id) references products(id),
	primary key (order_id, product_id)
);

create table reviews (
	id varchar(255) primary key,
	rating int not null,
	comment text,
	user_id varchar(255) not null,
	product_id varchar(255) not null,
	foreign key (user_id) references users (id),
	foreign key (product_id) references products (id)
);