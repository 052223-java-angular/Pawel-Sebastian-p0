set search_path to ecommerce_cli;

insert into users (id, username, password)
values --password: timmmmmm
	('1540a4f8-2659-4375-be78-73910ed86d78', 'bobbbbbb', '$2a$10$lidqtfcaQifiDu2oGEkz4ONaVczA1Ye8lDKQ3qBHcJiKhcajhdAVa'),
	--password: yahoo123
	('346d35c4-d0b9-4976-92c0-7f7368656c87', 'yahoo123', '$2a$10$M.nGTjv.6K3m7tOLaKdc5.U6jrHIz/BVdPS0UkGkTLR5aS55z5qIq');

INSERT INTO products (id,"name",category,price,in_stock) VALUES
	 ('jill','parasol','accessories',2,70000),
	 ('billy','sunglasses','accessories',923,75),
	 ('june','hamburger','food',20000,2),
	 ('bob','wings','food',50000,9),
	 ('will','flaming greatsword','accessories',5,1),
	 ('sandy','sand','food',76502,4),
	 ('rob','apple','food',376,985);

--bobbbbbb's cart
INSERT INTO cart_products (id,user_id,product_id,quantity) VALUES
	 ('larry','1540a4f8-2659-4375-be78-73910ed86d78','june',2),
	 ('gertrude','1540a4f8-2659-4375-be78-73910ed86d78','jill',40000),
	 ('harry','1540a4f8-2659-4375-be78-73910ed86d78','will',1);

INSERT INTO orders (id,user_id,amount,time_placed) VALUES
	 ('zimm','1540a4f8-2659-4375-be78-73910ed86d78',7500,'1975-08-20 04:12:48.000'),
	 ('pam','1540a4f8-2659-4375-be78-73910ed86d78',200000,'2020-01-08 01:45:22.000');

INSERT INTO order_products (id,order_id,product_id,quantity) VALUES
	 ('barry','zimm','will',993),
	 ('simpson','zimm','bob',2),
	 ('pimm','zimm','june',1),
	 ('doug','pam','sandy',777000);

INSERT INTO reviews (id,rating,"comment",user_id,product_id) VALUES
	 ('fran',1,'gets everywhere','1540a4f8-2659-4375-be78-73910ed86d78','sandy'),
	 ('jordan',4,'verrry vorpal','1540a4f8-2659-4375-be78-73910ed86d78','will'),
	 ('ted',3,'I can''t see anything!','1540a4f8-2659-4375-be78-73910ed86d78','billy');
