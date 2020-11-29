package io.alcide.handson.model;

public class Relation {
    public final String srcIp;
    public final String destIp;

    public Relation(String srcIp, String destIp) {
        this.srcIp = srcIp;
        this.destIp = destIp;
    }
}