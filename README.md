; MSB AutoScaling Test
; Test launched. Please check every minute for update.
[Test Start]
time=2018-06-26 05:02:49
type=autoscaling
testId=1529989368973
testFile=test.1529989368973.log

[Minute 1]
rps=4.00

[Minute 2]
rps=3.98

[Minute 3]
rps=4.00

[Minute 4]
rps=4.00

[Minute 5]
rps=9.68

[Minute 6]
rps=9.68

[Minute 7]
rps=11.93

[Minute 8]
rps=19.80

[Minute 9]
rps=19.80

[Minute 10]
rps=19.83

[Minute 11]
rps=11.93

[Minute 12]
rps=11.93

[Minute 13]
rps=11.91

[Minute 14]
rps=19.68

[Minute 15]
rps=19.81

[Minute 16]
rps=19.78

[Minute 17]
rps=11.95

[Minute 18]
rps=11.93

[Minute 19]
rps=10.83

[Minute 20]
rps=4.00

[Minute 21]
rps=4.00

[Minute 22]
rps=4.00

[Minute 23]
rps=4.00

[Minute 24]
rps=4.00

[Minute 25]
rps=0

[Minute 26]
rps=0

[Minute 27]
rps=0

[Minute 28]
rps=.25

[Minute 29]
rps=3.66

[Minute 30]
rps=19.20

[Minute 31]
rps=13.70

[Minute 32]
rps=8.21

[Minute 33]
rps=29.56

[Minute 34]
rps=44.38

[Minute 35]
rps=44.55

[Minute 36]
rps=19.83

[Minute 37]
rps=44.56

[Minute 38]
rps=44.56

[Minute 39]
rps=41.55

[Minute 40]
rps=38.45

[Minute 41]
rps=19.31

[Minute 42]
rps=28.41

[Minute 43]
rps=4.00

[Minute 44]
rps=4.00

[Minute 45]
rps=4.00

[Minute 46]
rps=4.00

[Minute 47]
rps=4.00

[Minute 48]
rps=4.00

[Load Generator]
username=XXXXXXXXXXXXXXX
platform=AWS
instanceId=i-02723a750c8f71307
instanceType=m3.medium
hostname=ec2-54-227-211-59.compute-1.amazonaws.com

[Elastic Load Balancer]
dns=project2-elb-826668520.us-east-1.elb.amazonaws.com

[Test End]
time=2018-06-26 05:57:30
averageRps=14.17
maxRps=44.56
pattern=557
ih=136.48
; Instance-Hour Usage
; i-009c08051cf6b787d	m3.medium	0.62	2018-06-26 05:50:00	2018-06-26 05:50:37
; i-02740da344151735c	m3.medium	11.97	2018-06-26 05:09:34	2018-06-26 05:21:32
; i-02b444ddda8721c9c	m3.medium	5.20	2018-06-26 05:40:24	2018-06-26 05:45:36
; i-03588310228190df2	m3.medium	9.38	2018-06-26 05:17:32	2018-06-26 05:26:55
; i-039c332336881b4bb	m3.medium	7.27	2018-06-26 05:09:35	2018-06-26 05:16:51
; i-05c4637fc7ec803ef	m3.medium	5.43	2018-06-26 05:38:36	2018-06-26 05:44:02
; i-0639b12676bf4c511	m3.medium	13.85	2018-06-26 05:02:49	2018-06-26 05:16:40
; i-066e0f07fe27beb75	m3.medium	6.85	2018-06-26 05:40:24	2018-06-26 05:47:15
; i-070d3d8141f8e4591	m3.medium	5.28	2018-06-26 05:41:58	2018-06-26 05:47:15
; i-07c97534e26f669fa	m3.medium	5.43	2018-06-26 05:38:36	2018-06-26 05:44:02
; i-085ce5c8553ee9bd9	m3.medium	10.42	2018-06-26 05:31:32	2018-06-26 05:41:57
; i-09422d365dacfc486	m3.medium	8.95	2018-06-26 05:41:58	2018-06-26 05:50:55
; i-095f3d0b9b3496031	m3.medium	0.53	2018-06-26 05:26:01	2018-06-26 05:26:33
; i-0a1b45e458e060f64	m3.medium	5.72	2018-06-26 05:16:02	2018-06-26 05:21:45
; i-0b06efab46325a5c9	m3.medium	4.60	2018-06-26 05:50:00	2018-06-26 05:54:36
; i-0be76a3178f09188a	m3.medium	5.43	2018-06-26 05:38:36	2018-06-26 05:44:02
; i-0d6a3a54d2fab5d12	m3.medium	1.07	2018-06-26 05:26:01	2018-06-26 05:27:05
; i-0d82f84d4303ce91b	m3.medium	10.65	2018-06-26 05:31:19	2018-06-26 05:41:58
; i-0d90d48598e8acc5c	m3.medium	0.53	2018-06-26 05:54:02	2018-06-26 05:54:34
; i-0e5bba8f9f4cc311f	m3.medium	8.95	2018-06-26 05:41:58	2018-06-26 05:50:55
; i-0f126a1f54ba736e5	m3.medium	3.17	2018-06-26 05:54:00	2018-06-26 05:57:10
; i-0f1ee33c058f5d57c	m3.medium	5.18	2018-06-26 05:40:24	2018-06-26 05:45:35

; MSB is validating...
{"status":1209,"success":true,"message":"Your submission is in processing, submission token is:XXXXXXXXXXXXXXX_vm-scaling_p2-task2-autoscaling_1130977804 Wait and check your code, score and feedback on TPZ."}; Test finished