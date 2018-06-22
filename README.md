# Project2
This project uses AWS JAVA APIs to launch a load generator and web service instances. Webservices are scaled automatically to cater the growing requests.

Below is the result from load generator run : 

`; 2018-06-21T23:57:41+00:00
; Horizontal Scaling Test
; isTestingThroughCode=true
; Test launched. Please check every minute for update.
; Your goal is too achieve rps=60 in 30 min
; Minimal interval of adding instances is 100 sec
[Test]
type=horizontal
testId=1529625461627
testFile=test.1529625461627.log
startTime=2018-06-21T23:57:41+00:00

[Minute 1]
ec2-18-233-157-96.compute-1.amazonaws.com=9.49
[Current rps=9.49]

[Minute 2]
ec2-18-233-157-96.compute-1.amazonaws.com=7.30
[Current rps=7.30]

[Minute 3]
ec2-18-233-157-96.compute-1.amazonaws.com=7.32
[Current rps=7.32]

[Minute 4]
ec2-18-233-157-96.compute-1.amazonaws.com=7.36
ec2-54-89-141-248.compute-1.amazonaws.com=9.45
[Current rps=16.81]

[Minute 5]
ec2-18-233-157-96.compute-1.amazonaws.com=8.58
ec2-54-89-141-248.compute-1.amazonaws.com=8.50
[Current rps=17.08]

[Minute 6]
ec2-18-233-157-96.compute-1.amazonaws.com=8.59
ec2-54-89-141-248.compute-1.amazonaws.com=8.52
[Current rps=17.11]

[Minute 7]
ec2-18-233-157-96.compute-1.amazonaws.com=8.59
ec2-54-89-141-248.compute-1.amazonaws.com=8.54
ec2-52-90-31-145.compute-1.amazonaws.com=9.54
[Current rps=26.67]

[Minute 8]
ec2-18-233-157-96.compute-1.amazonaws.com=9.77
ec2-54-89-141-248.compute-1.amazonaws.com=8.94
ec2-52-90-31-145.compute-1.amazonaws.com=8.92
[Current rps=27.63]

[Minute 9]
ec2-18-233-157-96.compute-1.amazonaws.com=9.74
ec2-54-89-141-248.compute-1.amazonaws.com=8.96
ec2-52-90-31-145.compute-1.amazonaws.com=8.96
ec2-18-207-226-142.compute-1.amazonaws.com=9.50
[Current rps=37.16]

[Minute 10]
ec2-18-233-157-96.compute-1.amazonaws.com=9.20
ec2-54-89-141-248.compute-1.amazonaws.com=9.74
ec2-52-90-31-145.compute-1.amazonaws.com=9.14
ec2-18-207-226-142.compute-1.amazonaws.com=9.17
[Current rps=37.25]

[Minute 11]
ec2-18-233-157-96.compute-1.amazonaws.com=9.22
ec2-54-89-141-248.compute-1.amazonaws.com=9.74
ec2-52-90-31-145.compute-1.amazonaws.com=9.17
ec2-18-207-226-142.compute-1.amazonaws.com=9.21
[Current rps=37.34]

[Minute 12]
ec2-18-233-157-96.compute-1.amazonaws.com=9.20
ec2-54-89-141-248.compute-1.amazonaws.com=9.75
ec2-52-90-31-145.compute-1.amazonaws.com=9.08
ec2-18-207-226-142.compute-1.amazonaws.com=9.25
ec2-54-175-240-122.compute-1.amazonaws.com=9.50
[Current rps=46.78]

[Minute 13]
ec2-18-233-157-96.compute-1.amazonaws.com=9.35
ec2-54-89-141-248.compute-1.amazonaws.com=9.31
ec2-52-90-31-145.compute-1.amazonaws.com=9.73
ec2-18-207-226-142.compute-1.amazonaws.com=9.32
ec2-54-175-240-122.compute-1.amazonaws.com=9.27
[Current rps=46.98]

[Minute 14]
ec2-18-233-157-96.compute-1.amazonaws.com=9.35
ec2-54-89-141-248.compute-1.amazonaws.com=9.30
ec2-52-90-31-145.compute-1.amazonaws.com=9.74
ec2-18-207-226-142.compute-1.amazonaws.com=9.34
ec2-54-175-240-122.compute-1.amazonaws.com=9.29
[Current rps=47.02]

[Minute 15]
ec2-18-233-157-96.compute-1.amazonaws.com=9.33
ec2-54-89-141-248.compute-1.amazonaws.com=9.33
ec2-52-90-31-145.compute-1.amazonaws.com=9.72
ec2-18-207-226-142.compute-1.amazonaws.com=9.38
ec2-54-175-240-122.compute-1.amazonaws.com=9.31
ec2-54-161-215-38.compute-1.amazonaws.com=9.58
[Current rps=56.65]

[Minute 16]
ec2-18-233-157-96.compute-1.amazonaws.com=9.45
ec2-54-89-141-248.compute-1.amazonaws.com=9.42
ec2-52-90-31-145.compute-1.amazonaws.com=9.35
ec2-18-207-226-142.compute-1.amazonaws.com=9.80
ec2-54-175-240-122.compute-1.amazonaws.com=9.37
ec2-54-161-215-38.compute-1.amazonaws.com=9.33
[Current rps=56.72]

[Minute 17]
ec2-18-233-157-96.compute-1.amazonaws.com=9.43
ec2-54-89-141-248.compute-1.amazonaws.com=9.41
ec2-52-90-31-145.compute-1.amazonaws.com=9.39
ec2-18-207-226-142.compute-1.amazonaws.com=9.78
ec2-54-175-240-122.compute-1.amazonaws.com=9.41
ec2-54-161-215-38.compute-1.amazonaws.com=9.36
ec2-18-206-160-186.compute-1.amazonaws.com=9.53
[Current rps=66.31]

[Load Generator]
username=manasshukla.official@gmail.com
platform=AWS
instanceId=i-06a769df5380db5fb
instanceType=m3.medium
hostname=ec2-18-207-202-113.compute-1.amazonaws.com
passwd=EI5bw1SiJhNFxU4gF7P1gQ

[Web Service 0]
username=manasshukla.official@gmail.com
platform=AWS
instanceId=i-0d74097773c23eef8
instanceType=m3.medium
hostname=ec2-18-233-157-96.compute-1.amazonaws.com

[Web Service 1]
username=manasshukla.official@gmail.com
platform=AWS
instanceId=i-03f8925896d9bd927
instanceType=m3.medium
hostname=ec2-54-89-141-248.compute-1.amazonaws.com

[Web Service 2]
username=manasshukla.official@gmail.com
platform=AWS
instanceId=i-00f82d435101af334
instanceType=m3.medium
hostname=ec2-52-90-31-145.compute-1.amazonaws.com

[Web Service 3]
username=manasshukla.official@gmail.com
platform=AWS
instanceId=i-009f8f268fb567c20
instanceType=m3.medium
hostname=ec2-18-207-226-142.compute-1.amazonaws.com

[Web Service 4]
username=manasshukla.official@gmail.com
platform=AWS
instanceId=i-003acfa844e7009f2
instanceType=m3.medium
hostname=ec2-54-175-240-122.compute-1.amazonaws.com

[Web Service 5]
username=manasshukla.official@gmail.com
platform=AWS
instanceId=i-01cae2ada667513c6
instanceType=m3.medium
hostname=ec2-54-161-215-38.compute-1.amazonaws.com

[Web Service 6]
username=manasshukla.official@gmail.com
platform=AWS
instanceId=i-02b2aaa1c9c24fbac
instanceType=m3.medium
hostname=ec2-18-206-160-186.compute-1.amazonaws.com

; MSB is validating...
[Test End]
rps=66.31
pass=true
endTime=2018-06-22T00:14:47+00:00
{"status":1209,"success":true,"message":"Your submission is in processing, submission token is:manasshukla.official@gmail.com_vm-scaling_p2-task1-aws_390764938 Wait and check your code, score and feedback on TPZ."}`
