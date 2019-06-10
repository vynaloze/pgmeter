mv res.txt res.txt.old

echo -e "insert single short\n" | tee -a res.txt
wrk -c6 -d10s -t2 -sins_s_s.lua http://pgmeter.herokuapp.com | tee -a res.txt

echo -e "\n\ninsert single long\n" | tee -a res.txt
wrk -c6 -d10s -t2 -sins_s_l.lua http://pgmeter.herokuapp.com | tee -a res.txt

echo -e "\n\ninsert multi short\n" | tee -a res.txt
wrk -c6 -d10s -t2 -sins_m_s.lua http://pgmeter.herokuapp.com | tee -a res.txt

echo -e "\n\ninsert multi long\n" | tee -a res.txt
wrk -c6 -d10s -t2 -sins_m_l.lua http://pgmeter.herokuapp.com | tee -a res.txt

echo -e "\n\n--------------\n\n" | tee -a res.txt

echo -e "\n\nread single short\n" | tee -a res.txt
wrk -c6 -d10s -t2 -sread_s_s.lua http://pgmeter.herokuapp.com | tee -a res.txt

echo -e "\n\nread single long\n" | tee -a res.txt
wrk -c6 -d10s -t2 -sread_s_l.lua http://pgmeter.herokuapp.com | tee -a res.txt

echo -e "\n\nread multi short\n" | tee -a res.txt
wrk -c6 -d10s -t2 -sread_m_s.lua http://pgmeter.herokuapp.com | tee -a res.txt

echo -e "\n\nread multi long\n" | tee -a res.txt
wrk -c6 -d10s -t2 -sread_m_l.lua http://pgmeter.herokuapp.com | tee -a res.txt

# todo aggregate functions