# Getting started

## Prerequisites
 
-   worksheet1
-   A loopback address configured

## Creating 2 Clustered Brokers

-   create broker 1

```code
$ARTEMIS_HOME/bin/artemis create  --user admin --password password --role admin --allow-anonymous y --clustered --host 127.0.0.1 --cluster-user clusterUser --cluster-password clusterPassword broker1
```
-   create broker 2

```code
 $ARTEMIS_HOME/bin/artemis create  --user admin --password password --role admin --allow-anonymous y --clustered --host 127.0.0.1 --cluster-user clusterUser --cluster-password clusterPassword --port-offset 100 broker2
```

-   start both brokers
```code
(A_MQ_Instance_Dir)/bin/artemis create broker1
(A_MQ_Instance_Dir)/bin/artemis create broker2
```

After some initial negotiation you should see each broker log that a bridge has been created.

## clustering a queue

Both brokers need to be configured with the same anycast queue definition

-   Add an anycast queue configuration for both brokers
```xml
  <addresses>
     <address name="exampleQueue" type="anycast">
        <queues>
           <queue name="exampleQueue"/>
        </queues>
     </address>
  </addresses>
```

-   run 2 consumers 1 connected to each broker
```code
mvn verify -PqueueReceiver1
mvn verify -PqueueReceiver2                                      
```

-   run a producer to send 10 messages
```code
mvn verify -PqueueSender
```

NB you will see each consumer receives 5 messages in a round robin fashion

-   kill the consumers and run the producer again

```code
mvn verify -PqueueSender
```
-   now run the consumers again
```code
mvn verify -PqueueReceiver1
mvn verify -PqueueReceiver2                                      
```

NB you will see that only 1 consumer received the messages as they were load balanced on demand.

-   now stop the brokers and update the load balancing to strict in the broker.xml file
```xml
<message-load-balancing>STRICT</message-load-balancing>
```

-   now re run the exercise

## clustering a topic

Both brokers need to be configured with the same anycast queue definition

-   Add an multicast queue configuration for both brokers
```xml
  <addresses>
     <address name="exampleTopic" type="multicast"/>
   </addresses>
```

-   run 2 consumers 1 connected to each broker
```code
mvn verify -PtopicReceiver1
mvn verify -PtopicReceiver2                                      
```

-   run a producer to send 10 messages
```code
mvn verify -PtopicSender
```

NB you will see each consumer receives all 10 messages



