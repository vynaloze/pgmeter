package com.vynaloze.pgmeter.dao.sql.tn.entity;

import com.vynaloze.pgmeter.dao.sql.Datasource;
import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.sql.Time;

@Entity(name = "pg_stat_activity")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class PgStatActivity extends BaseStat {
    private final Integer datid;
    private final String datname;
    @NonNull
    private final Integer pid;
    private final Integer usesysid;
    private final String usename;
    private final String client_addr;
    private final String client_hostname;
    private final Integer client_port;
    private final Time backend_start;
    private final Time xact_start;
    private final Time query_start;
    private final Time state_change;
    private final String wait_event_type;
    private final String wait_event;
    private final Boolean waiting;
    private final String state;
    private final Integer backend_xid;
    private final Integer backend_xmin;
    private final String query;
    private final String backend_type;

    public PgStatActivity(final Long id, final Long timestamp, final Datasource datasource, final Integer datid, final String datname, final Integer pid, final Integer usesysid, final String usename, final String client_addr, final String client_hostname, final Integer client_port, final Time backend_start, final Time xact_start, final Time query_start, final Time state_change, final String wait_event_type, final String wait_event, final Boolean waiting, final String state, final Integer backend_xid, final Integer backend_xmin, final String query, final String backend_type) {
        super(id, timestamp, datasource);
        this.datid = datid;
        this.datname = datname;
        this.pid = pid;
        this.usesysid = usesysid;
        this.usename = usename;
        this.client_addr = client_addr;
        this.client_hostname = client_hostname;
        this.client_port = client_port;
        this.backend_start = backend_start;
        this.xact_start = xact_start;
        this.query_start = query_start;
        this.state_change = state_change;
        this.wait_event_type = wait_event_type;
        this.wait_event = wait_event;
        this.waiting = waiting;
        this.state = state;
        this.backend_xid = backend_xid;
        this.backend_xmin = backend_xmin;
        this.query = query;
        this.backend_type = backend_type;
    }

    public Integer getDatid() {
        return datid;
    }

    public String getDatname() {
        return datname;
    }

    public Integer getPid() {
        return pid;
    }

    public Integer getUsesysid() {
        return usesysid;
    }

    public String getUsename() {
        return usename;
    }

    public String getClient_addr() {
        return client_addr;
    }

    public String getClient_hostname() {
        return client_hostname;
    }

    public Integer getClient_port() {
        return client_port;
    }

    public Time getBackend_start() {
        return backend_start;
    }

    public Time getXact_start() {
        return xact_start;
    }

    public Time getQuery_start() {
        return query_start;
    }

    public Time getState_change() {
        return state_change;
    }

    public String getWait_event_type() {
        return wait_event_type;
    }

    public String getWait_event() {
        return wait_event;
    }

    public Boolean getWaiting() {
        return waiting;
    }

    public String getState() {
        return state;
    }

    public Integer getBackend_xid() {
        return backend_xid;
    }

    public Integer getBackend_xmin() {
        return backend_xmin;
    }

    public String getQuery() {
        return query;
    }

    public String getBackend_type() {
        return backend_type;
    }
}