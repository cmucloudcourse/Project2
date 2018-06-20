package edu.cmu.cs.cloud.samples.aws.pojos;

import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Tag;
import edu.cmu.cs.cloud.samples.aws.exeptions.DryRunException;
import edu.cmu.cs.cloud.samples.aws.launcher.LaunchEC2Instance;

import java.util.List;

public class LoadGenerator {

    private static LoadGenerator LGINSTANCE;
    private static String instanceID;
    private static String publicDNS;
    private static String subnet;
    private static List<Tag> tags;

//    public LoadGenerator(Instance instance) {
//        try {
//            instance = LaunchEC2Instance.populateInstancePOJO(instance.getInstanceId());
//            this.instanceID = instance.getInstanceId();
//            this.publicDNS = instance.getPublicDnsName();
//            this.subnet = instance.getSubnetId();
//            this.tags = instance.getTags();
//        } catch (DryRunException e) {
//            //If the dry run fails populate teh instance id so that we can make a call to get instance details in the future.
//            this.instanceID = instance.getInstanceId();
//        }
//    }

    private LoadGenerator(){}

    public static LoadGenerator getInstance(){
        if(LGINSTANCE == null){
            LGINSTANCE = new LoadGenerator();
        }
        return LGINSTANCE;
    }

    public static void init(Instance awsinstance){
        try {
        awsinstance = LaunchEC2Instance.populateInstancePOJO(awsinstance.getInstanceId());
        instanceID = awsinstance.getInstanceId();
        publicDNS = awsinstance.getPublicDnsName();
        subnet = awsinstance.getSubnetId();
        tags = awsinstance.getTags();
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
