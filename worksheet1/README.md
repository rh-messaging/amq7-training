# Getting started
## Install prerequisites

### Fedora

### Mac

### Install Broker
Install A-MQ7 download zip from ? and install into ?

### Create an instance

Create an instance of A-MQ7
```code
(A_MQ_Install_Dir)/bin/artemis create myBroker
```
### Test The Broker with the CLI and Console
In a separate shell run the CLI to consume from a default address
```code
(A_MQ_Install_Dir)/bin/artemis consumer
```
In a separate shell run the CLI to produce to the same default address
```code
(A_MQ_Install_Dir)/bin/artemis producer
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
-   Send some messages
```code
mvn verify -PqueueSender
```
-   Receive some messages
-   Send some messages
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
-   Send some messages
```code
mvn verify -PtopicSender
```
-   Receive some messages
-   Send some messages
```code
mvn verify -PtopicReceiver
```
