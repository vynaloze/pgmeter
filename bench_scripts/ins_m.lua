math.randomseed(os.time())

request = function()
    wrk.method = "POST"
    wrk.headers["Accept"] = "application/json"
    wrk.headers["Content-Type"] = "application/json"
    wrk.path = "/api/stats"
    body = string.format('{"timestamp":%d,"datasource":{"ip":"192.168.10.%d","hostname":"hn","port":5432,"database":"foo","tags":{}},"id":"pg_stat_activity",' ..
            '"payload":[{"datid":null,"datname":null,"pid":%d,"usesysid":10,"usename":"postgres","application_name":"","client_addr":null,"client_hostname":null,"client_port":null,"backend_start":"2019-05-29T20:37:16.317851+02:00","xact_start":null,"query_start":null,"state_change":null,"wait_event_type":"Activity","wait_event":"LogicalLauncherMain","waiting":null,"state":null,"backend_xid":null,"backend_xmin":null,"query":"","backend_type":"background worker"},' ..
            '{"datid":null,"datname":null,"pid":%d,"usesysid":null,"usename":null,"application_name":"","client_addr":null,"client_hostname":null,"client_port":null,"backend_start":"2019-05-29T20:37:16.36418+02:00","xact_start":null,"query_start":null,"state_change":null,"wait_event_type":"Activity","wait_event":"AutoVacuumMain","waiting":null,"state":null,"backend_xid":null,"backend_xmin":null,"query":"","backend_type":"autovacuum launcher"},' ..
            '{"datid":17469,"datname":"foo","pid":%d,"usesysid":10,"usename":"postgres","application_name":"","client_addr":"::1","client_hostname":null,"client_port":24667,"backend_start":"2019-06-10T11:31:53.194954+02:00","xact_start":"2019-06-10T11:31:55.068121+02:00","query_start":"2019-06-10T11:31:55.068121+02:00","state_change":"2019-06-10T11:31:55.068124+02:00","wait_event_type":null,"wait_event":null,"waiting":null,"state":"active","backend_xid":null,"backend_xmin":120656,"query":"select * from pg_stat_activity","backend_type":"client backend"},' ..
            '{"datid":null,"datname":null,"pid":%d,"usesysid":null,"usename":null,"application_name":"","client_addr":null,"client_hostname":null,"client_port":null,"backend_start":"2019-05-29T20:37:16.286685+02:00","xact_start":null,"query_start":null,"state_change":null,"wait_event_type":"Activity","wait_event":"BgWriterMain","waiting":null,"state":null,"backend_xid":null,"backend_xmin":null,"query":"","backend_type":"background writer"},' ..
            '{"datid":null,"datname":null,"pid":%d,"usesysid":null,"usename":null,"application_name":"","client_addr":null,"client_hostname":null,"client_port":null,"backend_start":"2019-05-29T20:37:16.313979+02:00","xact_start":null,"query_start":null,"state_change":null,"wait_event_type":"Activity","wait_event":"CheckpointerMain","waiting":null,"state":null,"backend_xid":null,"backend_xmin":null,"query":"","backend_type":"checkpointer"},' ..
            '{"datid":null,"datname":null,"pid":%d,"usesysid":null,"usename":null,"application_name":"","client_addr":null,"client_hostname":null,"client_port":null,"backend_start":"2019-05-29T20:37:16.310345+02:00","xact_start":null,"query_start":null,"state_change":null,"wait_event_type":"Activity","wait_event":"WalWriterMain","waiting":null,"state":null,"backend_xid":null,"backend_xmin":null,"query":"","backend_type":"walwriter"}]}',
            math.random(0, 4294967295), math.random(0, 40),
            math.random(0, 20000), math.random(0, 20000), math.random(0, 20000),
            math.random(0, 20000), math.random(0, 20000), math.random(0, 20000)
    )
    return wrk.format(nil, nil, nil, body)
end