math.randomseed(os.time())

request = function()
    wrk.method = "POST"
    wrk.headers["Accept"] = "application/json"
    wrk.headers["Content-Type"] = "application/json"
    wrk.path = "/api/stats"
    body = string.format('{"timestamp":%d,"datasource":{"ip":"192.168.10.%d","hostname":"hn","port":null,"database":null,"tags":null},"id":"net",' ..
            '"payload":[{"name":"all","bytesSent":%d,"bytesRecv":%d,"packetsSent":%d,"packetsRecv":%d,"errin":0,"errout":0,"dropin":0,"dropout":0,"fifoin":0,"fifoout":0}]}',
            math.random(0, 4294967295), math.random(0, 40),
            math.random(0, 4294967295), math.random(0, 4294967295), math.random(0, 4294967295), math.random(0, 4294967295))
    return wrk.format(nil, nil, nil, body)
end