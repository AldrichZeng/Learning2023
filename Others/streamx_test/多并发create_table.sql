drop table if exists public.test_performance_2;
drop table if exists public.test_performance_3;
drop table if exists public.test_performance_4;
drop table if exists public.test_performance_5;
drop table if exists public.test_performance_6;
drop table if exists public.test_performance_7;
drop table if exists public.test_performance_8;
drop table if exists public.test_performance_9;
drop table if exists public.test_performance_10;
drop table if exists public.test_performance_1;
create table public.test_performance_1 (
    id serial primary key ,
    bigint_col bigint,
    timestamp_col timestamp,
    time_col time,
    date_col date,
    bool_col boolean,
    str1_col varchar(200),
    str2_col varchar(200),
    str3_col varchar(200),
    str4_col varchar(200),
    str5_col varchar(200),
    str6_col varchar(200),
    str7_col varchar(200),
    str8_col varchar(200),
    str9_col varchar(200),
    str10_col varchar(200),
    str11_col varchar(200),
    str12_col varchar(200),
    str13_col varchar(200)
);

CREATE TABLE public.test_performance_2 (
    LIKE public.test_performance_1 INCLUDING ALL INCLUDING INDEXES
);

CREATE TABLE public.test_performance_3 (
    LIKE public.test_performance_1 INCLUDING ALL INCLUDING INDEXES
);

CREATE TABLE public.test_performance_4 (
    LIKE public.test_performance_1 INCLUDING ALL INCLUDING INDEXES
);

CREATE TABLE public.test_performance_5 (
    LIKE public.test_performance_1 INCLUDING ALL INCLUDING INDEXES
);

CREATE TABLE public.test_performance_6 (
    LIKE public.test_performance_1 INCLUDING ALL INCLUDING INDEXES
);

CREATE TABLE public.test_performance_7 (
    LIKE public.test_performance_1 INCLUDING ALL INCLUDING INDEXES
);

CREATE TABLE public.test_performance_8 (
    LIKE public.test_performance_1 INCLUDING ALL INCLUDING INDEXES
);

CREATE TABLE public.test_performance_9 (
    LIKE public.test_performance_1 INCLUDING ALL INCLUDING INDEXES
);

CREATE TABLE public.test_performance_10 (
    LIKE public.test_performance_1 INCLUDING ALL INCLUDING INDEXES
);

-- 创建slot
select * from pg_drop_replication_slot('di_slot_performance') where exists (select * from pg_replication_slots where slot_name='di_slot_performance');

SELECT * FROM pg_create_logical_replication_slot('di_slot_performance','pgoutput');

-- 创建publication
drop publication if exists di_pub_performance;
create publication di_pub_performance for table public.test_performance_1, public.test_performance_2,
public.test_performance_3, public.test_performance_4, public.test_performance_5,
public.test_performance_6, public.test_performance_7, public.test_performance_8,
public.test_performance_9, public.test_performance_10;


ALTER TABLE public.test_performance_1 REPLICA IDENTITY FULL;
ALTER TABLE public.test_performance_2 REPLICA IDENTITY FULL;
ALTER TABLE public.test_performance_3 REPLICA IDENTITY FULL;
ALTER TABLE public.test_performance_4 REPLICA IDENTITY FULL;
ALTER TABLE public.test_performance_5 REPLICA IDENTITY FULL;
ALTER TABLE public.test_performance_6 REPLICA IDENTITY FULL;
ALTER TABLE public.test_performance_7 REPLICA IDENTITY FULL;
ALTER TABLE public.test_performance_8 REPLICA IDENTITY FULL;
ALTER TABLE public.test_performance_9 REPLICA IDENTITY FULL;
ALTER TABLE public.test_performance_10 REPLICA IDENTITY FULL;
-- 插入全量数据
insert into public.test_performance_1 (bigint_col )values (111);