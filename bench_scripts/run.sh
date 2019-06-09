mv res.txt res.txt.old

echo -e "insert single short\n" | tee -a res.txt
wrk -c6 -d10s -t2 -sins_s_s.lua https://pgmeter.herokuapp.com | tee -a res.txt

echo -e "\n\ninsert single long\n" | tee -a res.txt