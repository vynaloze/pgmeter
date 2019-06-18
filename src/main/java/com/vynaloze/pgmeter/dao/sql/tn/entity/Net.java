package com.vynaloze.pgmeter.dao.sql.tn.entity;

import com.vynaloze.pgmeter.dao.sql.Datasource;
import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity(name = "net")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Net extends BaseStat {
    @NonNull
    private final String name;
    @NonNull
    private final Integer bytesSent;
    @NonNull
    private final Integer bytesRecv;
    @NonNull
    private final Integer packetsSent;
    @NonNull
    private final Integer packetsRecv;
    @NonNull
    private final Integer errin;
    @NonNull
    private final Integer errout;
    @NonNull
    private final Integer dropin;
    @NonNull
    private final Integer dropout;
    @NonNull
    private final Integer fifoin;
    @NonNull
    private final Integer fifoout;

    public Net(final Long id, final Long timestamp, final Datasource datasource, final String name, final Integer bytesSent, final Integer bytesRecv, final Integer packetsSent, final Integer packetsRecv, final Integer errin, final Integer errout, final Integer dropin, final Integer dropout, final Integer fifoin, final Integer fifoout) {
        super(id, timestamp, datasource);
        this.name = name;
        this.bytesSent = bytesSent;
        this.bytesRecv = bytesRecv;
        this.packetsSent = packetsSent;
        this.packetsRecv = packetsRecv;
        this.errin = errin;
        this.errout = errout;
        this.dropin = dropin;
        this.dropout = dropout;
        this.fifoin = fifoin;
        this.fifoout = fifoout;
    }

    public String getName() {
        return name;
    }

    public Integer getBytesSent() {
        return bytesSent;
    }

    public Integer getBytesRecv() {
        return bytesRecv;
    }

    public Integer getPacketsSent() {
        return packetsSent;
    }

    public Integer getPacketsRecv() {
        return packetsRecv;
    }

    public Integer getErrin() {
        return errin;
    }

    public Integer getErrout() {
        return errout;
    }

    public Integer getDropin() {
        return dropin;
    }

    public Integer getDropout() {
        return dropout;
    }

    public Integer getFifoin() {
        return fifoin;
    }

    public Integer getFifoout() {
        return fifoout;
    }
}
