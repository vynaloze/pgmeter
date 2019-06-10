math.randomseed(os.time())

request = function()
    wrk.method = "POST"
    wrk.headers["Accept"] = "application/json"
    wrk.headers["Content-Type"] = "application/json"
    wrk.path = "/stats"
    body = string.format('{"timestamp":%d,"datasource":{"ip":"192.168.10.%d","hostname":"hn","port":5432,"database":"foo","tags":{}},"id":"pg_stat_user_indexes",' ..
            '"payload":[{"relid":17474,"indexrelid":17480,"schemaname":"public","relname":"example","indexrelname":"idx_example_text","idx_scan":%d,"idx_tup_read":%d,"idx_tup_fetch":%d},' ..
            '{"relid":17490,"indexrelid":17494,"schemaname":"public","relname":"pgbench_branches","indexrelname":"pgbench_branches_pkey","idx_scan":%d,"idx_tup_read":%d,"idx_tup_fetch":%d},' ..
            '{"relid":17484,"indexrelid":17496,"schemaname":"public","relname":"pgbench_tellers","indexrelname":"pgbench_tellers_pkey","idx_scan":%d,"idx_tup_read":%d,"idx_tup_fetch":%d},' ..
            '{"relid":17487,"indexrelid":17498,"schemaname":"public","relname":"pgbench_accounts","indexrelname":"pgbench_accounts_pkey","idx_scan":%d,"idx_tup_read":%d,"idx_tup_fetch":%d}]}',
            math.random(0, 4294967295), math.random(0, 40),
            math.random(0, 4294967295), math.random(0, 4294967295), math.random(0, 4294967295),
            math.random(0, 4294967295), math.random(0, 4294967295), math.random(0, 4294967295),
            math.random(0, 4294967295), math.random(0, 4294967295), math.random(0, 4294967295),
            math.random(0, 4294967295), math.random(0, 4294967295), math.random(0, 4294967295)
    )
    return wrk.format(nil, nil, nil, body)
end