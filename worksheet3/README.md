# High Availability

## Prerequisites
 
-   worksheet1 and worksheet2
-   A loopback address configured  
    
    route add -net 224.0.0.0 netmask 240.0.0.0 dev lo

## Shared Store

### Creating a Shared Store Pair

-   create live broker

```code
(A_MQ_Install_Dir)/bin/artemis create --shared-store --failover-on-shutdown  --user admin --password password --role admin --allow-anonymous y --clustered --host 127.0.0.1 --cluster-user clusterUser --cluster-password clusterPassword --max-hops 1 liveBroker
```
-   create a backup broker

```code
 (A_MQ_Install_Dir)/bin/artemis create --shared-store --failover-on-shutdown --slave --data ../liveBroker/data --user admin --password password --role admin --allow-anonymous y --clustered --host 127.0.0.1 --cluster-user clusterUser --cluster-password clusterPassword --max-hops 1 --port-offset 100 backupBroker
```

-   add a queue to each broker 
```xml
  <addresses>
     <address name="exampleQueue" type="anycast">
        <queues>
           <queue name="exampleQueue"/>
        </queues>
     </address>
  </addresses>
```

-   start both brokers
```code
(liveBrokerHome)/bin/artemis run
(backupBrokerHome)/bin/artemis run
```
You should see the backup broker announce itself as a backup in the logs.

-   Now kill the live broker


The backup should start as a live

-   Now restart the live broker

Since <failover-on-shutdown> is set to true you should see the backup automatically shut down and the live restart

- Now configure the clients to use ha, change the connection factory in the jndi.properties to be
```code
connectionFactory.ConnectionFactory=(tcp://localhost:61616?ha=true&retryInterval=1000&retryIntervalMultiplier=1.0&reconnectAttempts=-1,tcp://localhost:61716?ha=true&retryInterval=1000&retryIntervalMultiplier=1.0&reconnectAttempts=-1)
```

Notice we have configured both brokers in the connection factory but could have used udp
 
-   from the worksheet3 directory start a queue receiver

```code
mvn verify -PqueueReceiver
```

-   from the worksheet3 directory start the sender

```code
mvn verify -PqueueSender
```

you will see messages produced and consumed

-   now kill the live broker

You will see the backup broker take over and then clients continue to send and receive messages with maybe a warning.

-   now kill the backup and restart it

Once the backup is restarted the clients will continue

-   now restart the live broker 

The live will resume its responsibilities and clients will carry on

### Creating a Shared Nothing Pair (replicated)

-   create a live broker

```code
$ARTEMIS_HOME/bin/artemis create --replicated --failover-on-shutdown  --user admin --password password --role admin --allow-anonymous y --clustered --host 127.0.0.1 --cluster-user clusterUser --cluster-password clusterPassword  --max-hops 1 repLiveBroker
```
   
-   create a backup broker    

```code
$ARTEMIS_HOME/bin/artemis create --replicated --failover-on-shutdown --slave --user admin --password password --role admin --allow-anonymous y --clustered --host 127.0.0.1 --cluster-user clusterUser --cluster-password clusterPassword  --max-hops 1 --port-offset 100 repBackupBroker
```
-   add a queue to each broker 
```xml
  <addresses>
     <address name="exampleQueue" type="anycast">
        <queues>
           <queue name="exampleQueue"/>
        </queues>
     </address>
  </addresses>
```
-   start both brokers
 
 ```code
 (repLiveBrokerHome)/bin/artemis run
 (repBackupBrokerHome)/bin/artemis run
 ```
 You should see the backup broker announce itself as a backup in the logs and the journal being replicated.
 
 -   Now kill the live broker
 
 
 The backup should start as a live
 
 -  Before we restart the live broker we need to add some configuration to the live broker, so update the broker.xml and update
 
 ```xml
 <ha-policy>
  <replication>
     <master>
        <check-for-live-server>true</check-for-live-server>
     </master>
  </replication>
</ha-policy>
 ```
 
 Without this the live broker would just start without checking for a backup broker that has failed over so always configure this
 
 -   Now restart the live broker
 
you will see the live broker start and replicate back from the backup but notice it doesnt start

-   So manually kill the backup broker

You will see the live take over

-   now to automate failback update the backups broker.xml with

```xml
<ha-policy>
 <replication>
    <slave>
       <allow-failback>true</allow-failback>
    </slave>
 </replication>
</ha-policy>
```

-   now restart the backup and wait for replication to start
-   Kill the live broker
-   restart the live

you will now see the live broker replicate back then take over as live
 
 
-   from the worksheet3 directory start a queue receiver

```code
mvn verify -PqueueReceiver
```

-   from the worksheet3 directory start the sender

```code
mvn verify -PqueueSender
```

- play around killing the brokers


    
 


