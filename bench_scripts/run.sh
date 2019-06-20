HOST=http://pgmeter.herokuapp.com
#HOST=http://localhost:8080
FILE=R/res_$(date '+%d%m%Y_%H%M%S').txt

echo -e "insert single\n" | tee -a $FILE
wrk -c40 -d30s -t4 -sins_s.lua --latency $HOST | tee -a $FILE

echo -e "\n\ninsert multi\n" | tee -a $FILE
wrk -c40 -d30s -t4 -sins_m.lua --latency $HOST | tee -a $FILE

echo -e "\n\n--------------\n\n" | tee -a $FILE

echo -e "\n\nread single\n" | tee -a $FILE
wrk -c10 -d30s -t4 -sread_s.lua --latency $HOST | tee -a $FILE

echo -e "\n\nread multi\n" | tee -a $FILE
wrk -c10 -d30s -t4 -sread_m.lua --latency $HOST | tee -a $FILE

echo -e "\n\n--------------\n\n" | tee -a $FILE

echo -e "\n\nread ds\n" | tee -a $FILE
wrk -c10 -d30s -t4 -sread_ds.lua --latency $HOST | tee -a $FILE

# todo aggregate functions?

echo -e "\n\n--------------\n\n" | tee -a $FILE

echo -e "\n\nSIZE\n" | tee -a $FILE
curl $HOST/api/dev/h2size | tee -a $FILE