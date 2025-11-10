-- dự án csdl cho web ban hàng của lmao... nhằm cua gái đẹp vú to

-- 1/ câu lệnh tạo csdl 
create database camera24h
character set utf8mb4
collate utf8mb4_unicode_ci;

-- 2 usse database moi su dung dc
use camera24h;

-- 3 tien hanh tao table csdl 
-- #1 table users
create table users(
 -- unsigned la gia tri khong am, not null khong dc trong, auto_increment gia tri tu tang
 id int unsigned not null auto_increment primary key,
 username varchar(200) not null,
 password varchar(300) not null,
 lastname varchar(255) not null,
 firstname varchar(255) not null,
 gender tinyint(5),
 email varchar(200),
 birthday datetime,
 avatar mediumtext,
 code varchar(255),
 job_title varchar(250),
 department varchar(250),
 phone varchar(25),
 address varchar(500),
 city varchar(250),
 postal_code varchar(20),
 country varchar(250),
 remember_token varchar(250),
 status tinyint(4),
 create_at timestamp,
 updated_at timestamp
);


-- # create table roles
create table roles(
 id int unsigned auto_increment not null primary key,
 name varchar(250),
 display_name varchar(250),
 guard_name varchar(250),
 created_at timestamp,
 updated_at timestamp
);

-- $ create table permisions
create table permisions(
 id int unsigned auto_increment not null primary key,
 display_name varchar(200),
 guard_name varchar(250),
 created_at timestamp,
 updated_at timestamp
);

-- $ create table user_has_roles
create table user_has_roles(
	id int unsigned auto_increment not null primary key,
	user_id int unsigned not null, -- khoa ngoaij
	role_id int unsigned not null -- khoa ngoai
);

-- $ create table role_has_permission
create table role_has_permissions(
	id int unsigned auto_increment not null primary key,
	role_id int unsigned not null, -- khoa ngoai
    permission_id int unsigned not null -- khoa ngoai
);

-- 4/ crate table nhoms products
create table  Shop_categories(
	id int unsigned not null auto_increment primary key,
    category_code varchar(50) not null,
    category_name varchar(300) not null,
    description text,
    image text,
    created_at timestamp,
    updated_at timestamp
);

create table Shop_Suppliers(
	id int unsigned not null auto_increment primary key,
    supplier_code varchar(50) not null,
    supplier_name varchar(300) not null,
    description text,
    image text,
    created_at timestamp,
    updated_at timestamp
);

create table Shop_products(
  id int unsigned not null auto_increment primary key,
  product_code varchar(25) not null,
  product_name varchar(150) not null,
  image text,
  short_description varchar(200),
  description text,
  -- standard_cost la gia chuan
  standard_cost decimal(19,4),
  -- gia ban
  list_price decimal(19,4),
  -- quantity_per_unit so luong san pham co tren he thong
  quantity_per_unit varchar(50),
  -- discontinued co nghĩa là ngừng kinh doanh / ngừng bán sản phẩm đó đó nha~
  discontinued tinyint(4),
  -- is_featured la kiem tra san pham noi bat dc chon de trung bai dau tien khi show web
  is_featured bit(1),
  -- is_new la kiem tra san pham co phair la sp moi tren thi truonwg khong?
  is_new bit(1),
  category_id int unsigned not null,
  supplier_id int unsigned not null,
  created_at timestamp,
  updated_at timestamp
);

create table Shop_product_images(
	id int unsigned not null auto_increment primary key,
    product_id int unsigned not null,
    images varchar(500)
);

create table Shop_stores (
	id int unsigned auto_increment not null primary key,
    store_name varchar(500),
    description text,
    image text,
    created_at timestamp,
    updated_at timestamp
);

create table Shop_exports (
	id int unsigned auto_increment not null primary key,
    store_id int unsigned not null,
    employee_id int unsigned not null,
    export_date datetime,
    description text,
    order_id int unsigned not null,
    created_at timestamp,
    updated_at timestamp
);

create table Shop_imports (
	id int unsigned not null auto_increment primary key,
    store_id int unsigned not null,
    employee_id int unsigned not null,
    import_date datetime,
    created_at timestamp,
    updated_at timestamp
);

create table Shop_import_details (
	id int unsigned auto_increment not null primary key,
    import_id int unsigned not null,
    product_id int unsigned not null,
    quantity decimal(18,4),
    unity_price decimal(19,4)
);

create table Shop_export_details (
	id int unsigned auto_increment not null primary key,
    export_id int unsigned not null,
    product_id int unsigned not null,
    quantity decimal(18,4),
	unity_price decimal(19,4),
    import_detail_id int unsigned not null
);

-- 5/ cachs viet code tao khoa ngoai cay cau khoa ngoai phai cung kieu du lieu voi khoa chinh ko am int
 -- khoa ngoai cho bang user voi roles
 alter table user_has_roles add constraint fk_user_has_role1 foreign key(user_id) references users(id);
 alter table user_has_roles add constraint fk_user_has_role2 foreign key(role_id) references roles(id);


-- khoa ngoai cho bang role voi permission
alter table role_has_permissions add constraint fk_role_has_per1 foreign key(role_id) references roles(id);
alter table role_has_permissions add constraint fk_role_has_per2 foreign key(permission_id) references permisions(id);

-- nhom khoa ngoai cho nhom bang products
alter table Shop_products add constraint fk_shop_product1 foreign key(category_id) references Shop_categories(id);
alter table Shop_products add constraint fk_shop_product2 foreign key(supplier_id) references Shop_suppliers(id);
alter table Shop_product_images add constraint fk_shop_product_imgs foreign key(product_id) references Shop_product_images(id);

-- nhom khoa ngoai cho nhom quan ly xuat nhạp kho
alter table Shop_imports add constraint fk_shopimport1 foreign key(store_id) references Shop_stores(id);
alter table Shop_imports add constraint fk_shopimport2 foreign key(employee_id) references users(id);

alter table Shop_exports add constraint fk_shopexport1 foreign key(store_id) references Shop_stores(id);
alter table Shop_exports add constraint fk_shopexport2 foreign key(employee_id) references users(id);

alter table Shop_import_details add constraint fk_shopimportdetail1 foreign key(import_id) references Shop_imports(id);
alter table Shop_import_details add constraint fk_shopimportdetail2 foreign key(product_id) references Shop_products(id);

alter table Shop_export_details add constraint fk_shopexportdetail1 foreign key(export_id) references Shop_exports(id);
alter table Shop_export_details add constraint fk_shopexportdetail2 foreign key(product_id) references Shop_products(id);
