# Getting started
## Install prerequisites

JDK 1.8
 
OpenJDK on Fedora
    
    yum install java-1.8.0-openjdk-devel
    yun install java-1.8.0-openjdk
    
Oracle JDK with  Mac

    http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
    
Maven 3

Download 3.3.9 from https://maven.apache.org/download.cgi ans unzip somewhere.
make sure it is available on your path

Git client

Fedora
    
    yum install git on Fedora

Mac 
    follow instructions at https://git-scm.com/book/en/v2/Getting-Started-Installing-Git
    

LibAIO (optional Fedora only) - Fedora - 'yum install libaio'


Also checkout the worksheets from git

    git clone https://github.com/rh-messaging/amq7-training.git
    
Or if you haven't installed git you can download it from https://github.com/rh-messaging/amq7-training
by clicking 'clone or download'.



### Install Broker

Download the latest version of the Broker from https://developers.redhat.com/products/amq/download/.

Follow the install instructions at https://developers.redhat.com/products/amq/hello-world and create and run an AMQ 7 Broker
instance.

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
-   Add a multicast type address with a topic 
```xml 
  <addresses>
     <address name="exampleTopic" type="multicast"/>
  </addresses>
```

-   Start The broker

-   from the worksheet1 directory receive some messages

```code
mvn verify -PtopicReceiver
```         

-   from the worksheet1 directory send some messages

```code
mvn verify -PtopicSender
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

-   Update the Security Settings to only allow send role for the new user
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
