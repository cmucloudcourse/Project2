package edu.cmu.cs.cloud.samples.aws.launcher;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.elasticloadbalancingv2.AmazonElasticLoadBalancing;
import com.amazonaws.services.elasticloadbalancingv2.AmazonElasticLoadBalancingClientBuilder;
import com.amazonaws.services.elasticloadbalancingv2.model.*;
import edu.cmu.cs.cloud.samples.aws.pojos.TargetGroup;

public class TargetGroupLauncher {

    private static final AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();
    private static final AmazonElasticLoadBalancing  elb = AmazonElasticLoadBalancingClientBuilder
            .standard()
            .withCredentials(credentialsProvider)
            .withRegion(Regions.US_EAST_1)
            .build();

    public static TargetGroup launchTargetGroup(String tgtName){

        CreateTargetGroupRequest request = new CreateTargetGroupRequest()
                .withName(tgtName)
                .withPort(80)
                .withProtocol(ProtocolEnum.HTTP)
                .withVpcId(VPCDescriber.getDefaultVPCID());

        CreateTargetGroupResult result = elb.createTargetGroup(request);

        AddTagsRequest tagsRequest = new AddTagsRequest()
                .withResourceArns(getTargetGroupArn(result))
                .withTags(new Tag().withKey("project").withValue("2"), new Tag().withKey("type").withValue("target-group"));

        elb.addTags(tagsRequest);

        System.out.println("Target-grooup created with name "+tgtName+" VPC "+ getVpcId(result));

        TargetGroup targetGroup = new TargetGroup(tgtName,getTargetGroupArn(result), getVpcId(result), getHealthCheckProtocol(result), getHealthCheckPath(result));
        return targetGroup;
    }

    public static void deleteTargetGroup(String tgtArn){
        DeleteTargetGroupRequest request = new DeleteTargetGroupRequest().withTargetGroupArn(tgtArn);

        DeleteTargetGroupResult result = elb.deleteTargetGroup(request);

        System.out.println("Target-group with arn  "+ tgtArn+" deleted");

    }


    private static String getHealthCheckPath(CreateTargetGroupResult result) {
        return result.getTargetGroups().get(0).getHealthCheckPath();
    }

    private static String getHealthCheckProtocol(CreateTargetGroupResult result) {
        return result.getTargetGroups().get(0).getHealthCheckProtocol();
    }

    private static String getVpcId(CreateTargetGroupResult result) {
        return result.getTargetGroups().get(0).getVpcId();
    }



    private static String getTargetGroupArn(CreateTargetGroupResult result) {
        System.out.println("Target group Arn : "+result.getTargetGroups().get(0).getTargetGroupArn());
        return result.getTargetGroups().get(0).getTargetGroupArn();
    }


}
