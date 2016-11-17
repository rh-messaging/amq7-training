# exercise 3: store-and-forward capability through waypoints

## edit the broker.xml

Change the AMQP port to something other than 5672 (e.g. 5673).

Add two queues:

(i) queue.<username> e.g. queue.grs
(ii) either queue.foo or queue.bar

## edit the router config /etc/qpid-dispatch/qdrouterd.conf

```code
address {
    prefix: queue
    waypoint: yes
}

connector {
    name: broker
    host: localhost
    port: 5673
    role: route-container
}

autoLink {
   addr: queue.grs
   dir: out
   connection: broker
}

autoLink {
   addr: queue.grs
   dir: in
   connection: broker
}

autoLink {
   addr: queue.foo
   dir: out
   connection: broker
}

autoLink {
   addr: queue.foo
   dir: in
   connection: broker
}
```

## using send client send to queues

Try your own named queue, someone elses queue, foo and bar.

Using the broker console, look at the message count.

## using recv client, consume the messages