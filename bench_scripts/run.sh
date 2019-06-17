#HOST=http://pgmeter.herokuapp.com
HOST=http://localhost:8080
FILE=res_$(date '+%d%m%Y_%H%M%S').txt

echo -e "insert single short\n" | tee -a $FILE
wrk -c6 -d10s -t2 -sins_s_s.lua $HOST | tee -a $FILE

echo -e "\n\ninsert single long\n" | tee -a $FILE
wrk -c6 -d10s -t2 -sins_s_l.lua $HOST | tee -a $FILE

echo -e "\n\ninsert multi short\n" | tee -a $FILE
wrk -c6 -d10s -t2 -sins_m_s.lua $HOST | tee -a $FILE

echo -e "\n\ninsert multi long\n" | tee -a $FILE
wrk -c6 -d10s -t2 -sins_m_l.lua $HOST | tee -a $FILE

echo -e "\n\n--------------\n\n" | tee -a $FILE

echo -e "\n\nread single short\n" | tee -a $FILE
wrk -c6 -d10s -t2 -sread_s_s.lua $HOST | tee -a $FILE

echo -e "\n\nread single long\n" | tee -a $FILE
wrk -c6 -d10s -t2 -sread_s_l.lua $HOST | tee -a $FILE

echo -e "\n\nread multi short\n" | tee -a $FILE
wrk -c6 -d10s -t2 -sread_m_s.lua $HOST | tee -a $FILE

echo -e "\n\nread multi long\n" | tee -a $FILE
wrk -c6 -d10s -t2 -sread_m_l.lua $HOST | tee -a $FILE

echo -e "\n\n--------------\n\n" | tee -a $FILE

echo -e "\n\nread ds\n" | tee -a $FILE
wrk -c6 -d10s -t2 -sread_ds.lua $HOST | tee -a $FILE

# todo read datasources
# todo aggregate functions?