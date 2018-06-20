package edu.cmu.cs.cloud.samples.aws.pojos;

import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Tag;
import edu.cmu.cs.cloud.samples.aws.exeptions.DryRunException;
import edu.cmu.cs.cloud.samples.aws.launcher.LaunchEC2Instance;

import java.util.List;

public class WebService {
    private static int count;
    private String instanceID;
    private String publicDNS;
    private String subnet;
    private List<Tag> tags;

    public WebService(Instance awsinstance) {
        try{
        awsinstance = LaunchEC2Instance.populateInstancePOJO(awsinstance.getInstanceId());
        this.instanceID = awsinstance.getInstanceId();
        this.publicDNS = awsinstance.getPublicDnsName();
        this.subnet = awsinstance.getSubnetId();
        this.tags = awsinstance.getTags();
    } catch (DryRunException e) {
        System.out.println("Exception while populating Load generator instance details"+e.getMessage());
        instanceID = awsinstance.getInstanceId();
    }
    }

    public String getInstanceID() {
        return instanceID;
    }

    public String getPublicDNS() {
        return publicDNS;
    }

    public String getSubnet() {
        return subnet;
    }

    public List<Tag> getTags() {
        return tags;
    }



}
