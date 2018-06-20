package edu.cmu.cs.cloud.samples.aws.launcher;

import com.amazonaws.services.directconnect.model.Loa;
import com.amazonaws.services.dynamodbv2.xspec.L;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Tag;
import edu.cmu.cs.cloud.samples.aws.pojos.LoadGenerator;

import java.util.ArrayList;
import java.util.List;

public class LoadGeneratorLauncher {
    private static final String AMI_ID         = "ami-2575315a";
    private static final String INSTANCE_TYPE  = "t2.micro";
    private static final String KEY_NAME       = "15319-demo";
    private static final String SECURITY_GROUP = "sg-0915e3b86fdf658ec";
    private static final List<Tag> tags = new ArrayList<>();
    public static Instance lginstance;

    public static String launchLoadGenerator(){
        tags.add(new Tag("project","2"));
        tags.add(new Tag("type","LoadGenerator"));
        lginstance = LaunchEC2Instance.launchInstance(AMI_ID,INSTANCE_TYPE,KEY_NAME,SECURITY_GROUP,tags);
//        LaunchEC2Instance.describeInstance(lginstance);
        LoadGenerator.getInstance();
        LoadGenerator.init(lginstance);
        return lginstance.getInstanceId();
    }


    public static void authenticate(String TPZUserName, String TPZPassword){

    }




}




