# Getting started
## Install prerequisites

JDK 1.8
maven 3
LibAIO (optional) 
-   Fedora - 'yum install libaio'
-   Mac - ''


### Install Broker

Install A-MQ7 download zip from ? and install into ?

### Create an instance

Create an instance of A-MQ7
```code
(A_MQ_Install_Dir)/bin/artemis create myBroker
```
### Test The Broker with the CLI and Console
Start the broker
```code
(myBroker_home)/bin/artemis run
```
In a separate shell run the CLI to consume from a default address from the worksheet1 directory 
```code
(myBroker_home)/bin/artemis consumer
```
In a separate shell run the CLI to produce to the same default address from the worksheet1 directory 
```code
(A_MQ_Instance_Dir)/bin/artemis producer
```
Goto http://localhost:8161/hawtio/login and log in using the default user/pass you created when the A-MQ7 instance was created.

### Using a Queue

-   Stop the Broker
-   Add an anycast type address with a queue 
```xml 
    <addresses>
     <address name="exampleQueue" type="anycast">
        <queues>
           <queue name="exampleQueue"/>
        </queues>
     </address>
    </addresses>
```

-   Start The broker
-   from the worksheet1 directory send some messages
```code
mvn verify -PqueueSender
```
-   from the worksheet1 directory receive some messages
```code
mvn verify -PqueueReceiver
```
### Using a topic

-   Stop the Broker
-   Add a multicast type address with a queue 
```xml 
  <addresses>
     <address name="exampleTopic" type="multicast"/>
  </addresses>
```

-   Start The broker
-   from the worksheet1 directory send some messages
```code
mvn verify -PtopicSender
```
-   from the worksheet1 directory receive some messages
```code
mvn verify -PtopicReceiver
```

## Securing a queue

-   Stop the Broker
-   Add a new user to artemis-users.properties
```code
myuser=mypassword
```

-   Add a new role for this user to artemis-roles.properties
```code
mygroup=myuser
```

-   Update the QueueSender and QueueReceiver classes to use the new user and password, i.e.
```code
Connection connection = cf.createConnection("myuser", "mypassword");
```

-   Update the Security Settins to only allow send role for the new user
```xml
<security-setting match="#">
    <permission type="createNonDurableQueue" roles="admin"/>
    <permission type="deleteNonDurableQueue" roles="admin"/>
    <permission type="createDurableQueue" roles="admin"/>
    <permission type="deleteDurableQueue" roles="admin"/>
    <permission type="consume" roles="admin"/>
    <permission type="browse" roles="admin"/>
    <permission type="send" roles="admin,mygroup"/>
    <!-- we need this otherwise ./artemis data imp wouldn't work -->
    <permission type="manage" roles="admin"/>
</security-setting>
```

-  from the worksheet1 directory send some messages
```code
mvn verify -PqueueSender
```
-   from the worksheet1 directory receive some messages (this will fail)
```code
mvn verify -PqueueReceiver
``` 

Play around with security to see how to allow the example to send
