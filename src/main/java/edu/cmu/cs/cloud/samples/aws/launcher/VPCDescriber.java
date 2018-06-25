package edu.cmu.cs.cloud.samples.aws.launcher;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeVpcsResult;
import com.amazonaws.services.ec2.model.Subnet;

import java.util.ArrayList;
import java.util.List;

public class VPCDescriber {

    private static AmazonEC2 ec2Client = AmazonEC2ClientBuilder.defaultClient();
    private static DescribeVpcsResult result = ec2Client.describeVpcs();

    public static void describeVPC(){



        System.out.println("VPC id" + result.getVpcs().get(0).getVpcId());
        System.out.println("VPC default" + result.getVpcs().get(0).getIsDefault());
        System.out.println("VPC id" + result.getVpcs().get(0).getCidrBlock());
        result.getVpcs().get(0).getIpv6CidrBlockAssociationSet();

    }

    public static String getDefaultVPCID(){
        return result.getVpcs().get(0).getVpcId();
    }

    public static List<Subnet> getDefaultSubnets(){

        return ec2Client.describeSubnets().getSubnets();

    }

    public static List<String> getDefaultSubnetIds(){
        List<String> subnetIDs = new ArrayList<>();
        ec2Client.describeSubnets().getSubnets().stream().forEach(i -> {
            System.out.println("Subnet "+i.getSubnetId()+"\t"+i.getCidrBlock());
            subnetIDs.add(i.getSubnetId());
        });

        return subnetIDs;
    }


//    public static List<String> getSubnetIds(String vpcid){
//        ec2Client.
//    }





}
