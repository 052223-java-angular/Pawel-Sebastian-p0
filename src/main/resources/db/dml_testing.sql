set search_path to ecommerce_cli;

-- register user with id: user1 first (edit db manually)
-- 1st run ddl
-- then register a new user
-- change the new user's id to 'user1'
-- run this script

INSERT INTO ecommerce_cli.products (id,"name",category,price,in_stock,description) VALUES
	 ('jill','parasol','accessories',2,70000,'keeps you shady'),
	 ('billy','sunglasses','accessories',923,75,'keeps your eyes shady'),
	 ('june','hamburger','food',20000,2,'soo much sodium'),
	 ('bob','wings','food',50000,9,'not chicken wings'),
	 ('will','flaming greatsword','accessories',5,1,'cleave your enemies!'),
	 ('sandy','sand','food',76502,4,'for a very discerning and specialized palette'),
	 ('rob','apple','food',376,985,'gala');

--bobbbbbb's cart
INSERT INTO ecommerce_cli.cart_products (id,user_id,product_id,quantity) VALUES
	 ('larry','user1','june',2),
	 ('gertrude','user1','jill',40000),
	 ('harry','user1','will',1);

INSERT INTO ecommerce_cli.orders (id,user_id,amount,time_placed) VALUES
	 ('zimm','user1',7500,'1975-08-20 04:12:48.000'),
	 ('pam','user1',200000,'2020-01-08 01:45:22.000');

INSERT INTO ecommerce_cli.order_products (id,order_id,product_id,quantity) VALUES
	 ('barry','zimm','will',993),
	 ('simpson','zimm','bob',2),
	 ('pimm','zimm','june',1),
	 ('doug','pam','sandy',777000);

INSERT INTO ecommerce_cli.reviews (id,rating,"comment",user_id,product_id) VALUES
	 ('fran',1,'gets everywhere','user1','sandy'),
	 ('jordan',4,'verrry vorpal','user1','will'),
	 ('ted',3,'I can''t see anything!','user1','billy');
