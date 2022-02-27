create table if not exists t_goods
(
    id bigint auto_increment comment '商品ID，主键'
        primary key,
    goods_name varchar(32) null comment '商品名称',
    goods_title varchar(64) null comment '商品标题',
    goods_img varchar(64) null comment '商品图片',
    goods_detail longtext null comment '商品详情',
    goods_price decimal(10,2) default 0.00 null comment '商品价格',
    goods_stock int default 0 null comment '商品库存，-1表示没有限制'
);

create table t_order
(
    id               bigint auto_increment comment '订单ID，主键'
        primary key,
    user_id          bigint                      null comment '用户ID',
    goods_id         bigint                      null comment '商品ID',
    delivery_addr_id bigint                      null comment '收获地址ID',
    goods_name       varchar(32)                 null comment '商品名称，冗余，方便查询',
    goods_count      int            default 0    null comment '商品数量',
    goods_price      decimal(10, 2) default 0.00 null comment '商品单价',
    order_channel    tinyint        default 0    null comment '下单渠道，1电脑，2安卓，3苹果，4其它',
    status           tinyint        default 0    null comment '订单状态，0新建未支付，1已支付，2已发货，3已收货，4已退款，5已完成',
    create_date      datetime                    null comment '订单创建的时间',
    pay_date         datetime                    null comment '支付时间'
);

create table t_seckill_goods
(
    id            bigint auto_increment comment '秒杀商品ID，主键'
        primary key,
    goods_id      bigint                      null comment '商品ID',
    seckill_price decimal(10, 2) default 0.00 null comment '商品秒杀单价',
    stock_count   int            default 0    null comment '库存数量',
    start_date    datetime                    null comment '开始时间',
    end_date      datetime                    null comment '结束时间'
);

create table t_seckill_order
(
    id       bigint auto_increment comment '秒杀订单ID，主键'
        primary key,
    user_id  bigint null comment '用户ID',
    order_id bigint null comment '订单ID',
    goods_id bigint null comment '商品ID'
);

create table t_user
(
    id              bigint        not null comment '用户ID，手机号码'
        primary key,
    nickname        varchar(255)  not null,
    password        varchar(32)   null comment '两次MD5加密,MD5(MD5(pass明文+盐)+盐)',
    salt            varchar(12)   not null,
    head            varchar(128)  null comment '头像',
    register_date   datetime      null comment '注册时间',
    last_login_date datetime      null comment '最后一次登陆时间',
    login_count     int default 0 null comment '登陆次数'
);

-- auto-generated definition
create unique index seckill_uid__gid
    on t_seckill_order (user_id, goods_id)
    comment '用户id+商品id的的唯一索引，解决同一用户秒杀多件商品';