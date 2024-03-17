-- 创建slot
select * from pg_drop_replication_slot('di_slot_performance') where exists (select * from pg_replication_slots where slot_name='di_slot_performance');

SELECT * FROM pg_create_logical_replication_slot('di_slot_performance','pgoutput');

-- 创建publication
drop publication if exists di_pub_performance;