package edu.cmu.cs.cloud.samples.aws.pojos;

public class TargetGroup {

    private String name;
    private String arn;
    private String vpcId;
    private String healthCheckProtocol;
    private String healthCheckPath;


    public TargetGroup(String name, String arn, String vpcId, String healthCheckProtocol, String healthCheckPath) {
        this.name = name;
        this.arn = arn;
        this.vpcId = vpcId;
        this.healthCheckProtocol = healthCheckProtocol;
        this.healthCheckPath = healthCheckPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArn() {
        return arn;
    }

    public void setArn(String arn) {
        this.arn = arn;
    }

    public String getVpcId() {
        return vpcId;
    }

    public void setVpcId(String vpcId) {
        this.vpcId = vpcId;
    }

    public String getHealthCheckProtocol() {
        return healthCheckProtocol;
    }

    public void setHealthCheckProtocol(String healthCheckProtocol) {
        this.healthCheckProtocol = healthCheckProtocol;
    }

    public String getHealthCheckPath() {
        return healthCheckPath;
    }

    public void setHealthCheckPath(String healthCheckPath) {
        this.healthCheckPath = healthCheckPath;
    }
}
