math.randomseed(os.time())

request = function()
    wrk.method = "POST"
    wrk.headers["Accept"] = "application/json"
    wrk.headers["Content-Type"] = "application/json"
    wrk.path = "/stats"
    body = string.format('{"timestamp":%d,"datasource":{"ip":"192.168.10.%d","hostname":"hn","port":null,"database":null,"tags":null},"id":"virt_mem",' ..
            '"payload":[{"total":%d,"available":%d,"used":%d,"usedPercent":%d,"free":0,"active":0,"inactive":0,"wired":0,"laundry":0,"buffers":0,' ..
            '"cached":0,"writeback":0,"dirty":0,"writebacktmp":0,"shared":0,"slab":0,"sreclaimable":0,"pagetables":0,"swapcached":0,"commitlimit":0,' ..
            '"committedas":0,"hightotal":0,"highfree":0,"lowtotal":0,"lowfree":0,"swaptotal":0,"swapfree":0,"mapped":0,"vmalloctotal":0,"vmallocused":0,' ..
            '"vmallocchunk":0,"hugepagestotal":0,"hugepagesfree":0,"hugepagesize":0}]}',
            math.random(0, 4294967295), math.random(0, 40),
            math.random(0, 4294967295), math.random(0, 4294967295), math.random(0, 4294967295), math.random(0, 100))
    return wrk.format(nil, nil, nil, body)
end