package edu.cmu.cs.cloud.samples.aws.exeptions;

public class DryRunException extends Exception{

    public DryRunException(String message) {
        super(message);
    }
}
