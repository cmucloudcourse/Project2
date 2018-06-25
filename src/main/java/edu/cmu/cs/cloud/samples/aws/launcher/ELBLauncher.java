package edu.cmu.cs.cloud.samples.aws.launcher;


import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.elasticloadbalancingv2.AmazonElasticLoadBalancing;
import com.amazonaws.services.elasticloadbalancingv2.AmazonElasticLoadBalancingClientBuilder;
import com.amazonaws.services.elasticloadbalancingv2.model.*;
import com.amazonaws.waiters.Waiter;
import com.amazonaws.waiters.WaiterParameters;
import edu.cmu.cs.cloud.samples.aws.pojos.ELB;
import edu.cmu.cs.cloud.samples.aws.pojos.TargetGroup;

public class ELBLauncher {

    private static final AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();
    private static final AmazonElasticLoadBalancing elb = AmazonElasticLoadBalancingClientBuilder
            .standard()
            .withCredentials(credentialsProvider)
            .withRegion(Regions.US_EAST_1)
            .build();

    public static ELB launchELB(String elbName, String securityGroupID, TargetGroup targetGroup){



        CreateLoadBalancerRequest request = new CreateLoadBalancerRequest()
                .withName(elbName)
                .withSecurityGroups(securityGroupID)
                .withSubnets(VPCDescriber.getDefaultSubnetIds())
                .withTags(new Tag().withKey("project").withValue("2"),new Tag().withKey("type").withValue("elb"));


        CreateLoadBalancerResult result = elb.createLoadBalancer(request);



        String lbArn = result.getLoadBalancers().get(0).getLoadBalancerArn();
         result.getLoadBalancers().get(0).getDNSName();


        CreateListenerRequest listenerRequest = new CreateListenerRequest()
                .withLoadBalancerArn(lbArn)
                .withPort(80)
                .withProtocol(ProtocolEnum.HTTP)
                .withDefaultActions(new Action()
                        .withType(ActionTypeEnum.Forward)
                        .withTargetGroupArn(targetGroup.getArn()));

        CreateListenerResult listenerResult = elb.createListener(listenerRequest);

        waitUntilELBAvailable(elbName);
        ELB elb = new ELB(
                elbName, result.getLoadBalancers().get(0).getDNSName(), result.getLoadBalancers().get(0).getLoadBalancerArn(),
                result.getLoadBalancers().get(0).getAvailabilityZones());
        return elb;
    }


    private static void waitUntilELBAvailable(String elbName){

        Waiter waiter =  elb.waiters().loadBalancerAvailable();
        System.out.println("Waiting for load balancer to be available");
        waiter.run(new WaiterParameters<>(
                new DescribeLoadBalancersRequest().withNames(elbName)));
        System.out.println("load balancer is available");

    }

    public static void deleteLoadBalancer(String lbARN){
        DeleteLoadBalancerRequest request = new DeleteLoadBalancerRequest()
                .withLoadBalancerArn(lbARN);

        DeleteLoadBalancerResult result = elb.deleteLoadBalancer(request);

    }

}
