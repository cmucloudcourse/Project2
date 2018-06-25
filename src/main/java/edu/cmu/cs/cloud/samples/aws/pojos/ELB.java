package edu.cmu.cs.cloud.samples.aws.pojos;

import com.amazonaws.services.elasticloadbalancingv2.model.AvailabilityZone;

import java.util.ArrayList;
import java.util.List;

public class ELB {

    private String elbName;
    private String dnsName;
    private String elnARN;
    private List<AvailabilityZone> availabilityZones;

    public ELB(String elbName, String dnsName, String elnARN, List<AvailabilityZone> availabilityZones) {
        this.elbName = elbName;
        this.dnsName = dnsName;
        this.elnARN = elnARN;
        this.availabilityZones = availabilityZones;
    }

    public String getElbName() {
        return elbName;
    }

    public void setElbName(String elbName) {
        this.elbName = elbName;
    }

    public String getDnsName() {
        return dnsName;
    }

    public void setDnsName(String dnsName) {
        this.dnsName = dnsName;
    }

    public String getElnARN() {
        return elnARN;
    }

    public void setElnARN(String elnARN) {
        this.elnARN = elnARN;
    }

    public List<String> getAvailabilityZones() {
        List<String> availZones = new ArrayList<>();
        this.availabilityZones.stream().forEach(i->availZones.add(i.getZoneName()));
        return availZones;
    }

    public void setAvailabilityZones(List<AvailabilityZone> availabilityZones) {
        this.availabilityZones = availabilityZones;
    }
}
