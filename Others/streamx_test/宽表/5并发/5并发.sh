#!/bin/bash
# streamx PostgreSQL->Holo 性能测试
home_dir=`echo $0 |/usr/bin/xargs /usr/bin/dirname`
[ ${home_dir} == "." ] && home_dir=`/bin/pwd`
source ../global_env.sh

jobid=1000
COUNT=4000000

rm -rf /home/admin/streamx/log/$jobid

# 创建表
cat ${home_dir}/create_table.sql | psql -h ${pgHost} -p${pgPort} "dbname=${pgDb} user=${pgUser} password=${pgPwd}"
sleep 3s

# 启动位点
startTimestamp=$(($(date +%s%N)/1000000))
startPosition=$(date +'%Y-%m-%d %H:%M:%S')
echo "startDateTime: "$startTimestamp", startPosition: "$startPosition
sleep 2s

# 构造源端数据
/usr/local/bin/python ${datax_run_scrpit} ${home_dir}/datax.json -p"-DhomeDir=$home_dir -DpgHost=$pgHost -DpgPort=$pgPort -DpgUser=$pgUser -DpgPwd=$pgPwd -DpgDb=$pgDb -Dcount=$COUNT -Dtable=public.test_performance_1" > pre_process1.log 2>&1 &
PID1=$!
# 等待2秒，等待Java进程启动
sleep 2s
JAVAPID1=`ps -ef|grep $PID1 |awk -F" " -v pid="$PID1" '{ if ($3 == pid) print $2}'`
echo "using (/opt/taobao/java/bin/jstat -gc "$JAVAPID1" 1000 100) to view java stack"

/usr/local/bin/python ${datax_run_scrpit} ${home_dir}/datax.json -p"-DhomeDir=$home_dir -DpgHost=$pgHost -DpgPort=$pgPort -DpgUser=$pgUser -DpgPwd=$pgPwd -DpgDb=$pgDb -Dcount=$COUNT -Dtable=public.test_performance_2" > pre_process2.log 2>&1 &
PID2=$!
# 等待2秒，等待Java进程启动
sleep 2s
JAVAPID2=`ps -ef|grep $PID2 |awk -F" " -v pid="$PID2" '{ if ($3 == pid) print $2}'`
echo "using (/opt/taobao/java/bin/jstat -gc "$JAVAPID2" 1000 100) to view java stack"

/usr/local/bin/python ${datax_run_scrpit} ${home_dir}/datax.json -p"-DhomeDir=$home_dir -DpgHost=$pgHost -DpgPort=$pgPort -DpgUser=$pgUser -DpgPwd=$pgPwd -DpgDb=$pgDb -Dcount=$COUNT -Dtable=public.test_performance_3" > pre_process3.log 2>&1 &
PID3=$!
# 等待2秒，等待Java进程启动
sleep 2s
JAVAPID3=`ps -ef|grep $PID3 |awk -F" " -v pid="$PID3" '{ if ($3 == pid) print $2}'`
echo "using (/opt/taobao/java/bin/jstat -gc "$JAVAPID3" 1000 100) to view java stack"

/usr/local/bin/python ${datax_run_scrpit} ${home_dir}/datax.json -p"-DhomeDir=$home_dir -DpgHost=$pgHost -DpgPort=$pgPort -DpgUser=$pgUser -DpgPwd=$pgPwd -DpgDb=$pgDb -Dcount=$COUNT -Dtable=public.test_performance_4" > pre_process4.log 2>&1 &
PID4=$!
# 等待2秒，等待Java进程启动
sleep 2s
JAVAPID4=`ps -ef|grep $PID4 |awk -F" " -v pid="$PID4" '{ if ($3 == pid) print $2}'`
echo "using (/opt/taobao/java/bin/jstat -gc "$JAVAPID4" 1000 100) to view java stack"

/usr/local/bin/python ${datax_run_scrpit} ${home_dir}/datax.json -p"-DhomeDir=$home_dir -DpgHost=$pgHost -DpgPort=$pgPort -DpgUser=$pgUser -DpgPwd=$pgPwd -DpgDb=$pgDb -Dcount=$COUNT -Dtable=public.test_performance_5" > pre_process5.log 2>&1 &
PID5=$!
# 等待2秒，等待Java进程启动
sleep 2s
JAVAPID5=`ps -ef|grep $PID5 |awk -F" " -v pid="$PID5" '{ if ($3 == pid) print $2}'`
echo "using (/opt/taobao/java/bin/jstat -gc "$JAVAPID5" 1000 100) to view java stack"

echo "wait datax running"
wait $PID1
wait $PID2
wait $PID3
wait $PID4
wait $PID5

sleep 2s
echo "run streamx..."
/usr/local/bin/python ${streamx_run_script}  ${home_dir}/job.json -p"-DpgSlot=$pgSlot -DpgPublication=$pgPublication -DhomeDir=$home_dir -DstartTimestamp=$startTimestamp -DstartPosition='${startPosition}' -DpgHost=$pgHost -DpgPort=$pgPort -DpgUser=$pgUser -DpgPwd=$pgPwd -DpgDb=$pgDb" --jobid ${jobid} --jobmark addf --mode standalone --limit ${COUNT} > process.log 2>&1 &
PID=$!

# 等待2秒，等待Java进程启动
sleep 2s
JAVAPID=`ps -ef|grep $PID |awk -F" " -v pid="$PID" '{ if ($3 == pid) print $2}'`

echo "wait process: "$JAVAPID
echo "using (/opt/taobao/java/bin/jstat -gc "$JAVAPID" 1000 100) to view java stack"

sleep 5s

# 插入数据
# cat ${home_dir}/insert_pg.sql | psql -h ${pgHost} -p${pgPort} "dbname=${pgDb} user=${pgUser} password=${pgPwd}"

wait $PID

# 从日志中获取启动时间
startTime=`cat process.log |grep "StreamX job start"|head -n 1 |awk -F" " '{print $1" "$2}'`
# 从日志中获取结束时间
endTime=`cat process.log |grep canExit:true|head -n 1 |awk -F" " '{print $1" "$2}'`
echo "开始时间: "$startTime
echo "结束时间: "$endTime

start_seconds=$(date -d "$startTime" +%s%N)
end_seconds=$(date -d "$endTime" +%s%N)
nano_diff=$((end_seconds - start_seconds))
s_diff=$((nano_diff / 1000000000))
echo "using time : $s_diff 秒"
speed=$(echo "scale=2; ($COUNT) / ($s_diff)" | bc)
echo "The speed is: $speed record/s"


# 判断实时同步条数是否满足预期
streamx_log_path=`cat ${home_dir}/process.log |grep "Current Working dir" |awk -F ": " '{print $2}'`

echo "read total in lines:"
cat $streamx_log_path/stepMetric.log |awk -F"|" '{if ($3 == "reader") print $5}'|tail -n 1

echo "read total in bytes:"
bytes_in_total=`cat $streamx_log_path/stepMetric.log |awk -F"|" '{if ($3 == "reader") print $6}'|tail -n 1`
speed_in_bytes=$(echo "scale=2; ($bytes_in_total) / ($s_diff) / 1024 / 1024" | bc)
echo "speed_in_bytes $speed_in_bytes MB/s"

echo "streamx_log_path: "$streamx_log_path
wc result/output -l|awk -F " " '{print $1}'|grep $COUNT
if [ $? -eq 0 ];then
    echo "PASS"
    exit 0
else
    echo "Failed"
    exit -1
fi
