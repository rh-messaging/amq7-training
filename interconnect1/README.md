# Getting started
## Install prerequisites

RHEL7 or Centos7
 

## Get Beta-1 RPMs

wget http://download.eng.bos.redhat.com/brewroot/packages/qpid-proton/0.14.0/1.el7/noarch/python-qpid-proton-docs-0.14.0-1.el7.noarch.rpm

wget http://download.eng.bos.redhat.com/brewroot/packages/qpid-proton/0.14.0/1.el7/x86_64/python-qpid-proton-0.14.0-1.el7.x86_64.rpm

wget http://download.eng.bos.redhat.com/brewroot/packages/qpid-proton/0.14.0/1.el7/x86_64/qpid-proton-c-0.14.0-1.el7.x86_64.rpm

wget http://download.eng.bos.redhat.com/brewroot/packages/qpid-dispatch/0.7.0/2.el7/x86_64/qpid-dispatch-router-0.7.0-2.el7.x86_64.rpm

wget http://download.eng.bos.redhat.com/brewroot/packages/qpid-dispatch/0.7.0/2.el7/x86_64/qpid-dispatch-tools-0.7.0-2.el7.x86_64.rpm


## Install RPMs

yum -y install *.rpm


## Configure the Router to be in a Network

Edit the configuration file in

```code
/etc/qpid-dispatch/qdrouterd.conf
```

The router defaults to "standalone" mode but should be changed to "interior" mode.  This will allow the router to join a
network of routers.  In a network of routers, each router must have a unique identifier.

```code
router {
    mode: interior
    id: <your email name>
}
```

Add an inter-router connector to the instructor's hub router:

```code
connector {
    host: <hub host>
    port: <hub port>
    role: inter-router
    saslMechanisms: ANONYMOUS
}
```

Add a waypoint address for queues.  We will use this later.

```code
address {
    prefix: queue
    waypoint: yes
}
```

## Start the router

```code
systemctl start qdrouterd.service
```

## Test the router's operation

The qdstat tool can be used to query management data from the router.  The -c option shows open connections.

```code
qdstat -c
```


## Using Docker

If you have Docker, you can pull the following image which contains the RPMs:

```code
docker pull tedross/centos7-dispatch
```

To run this image, use the following command:

```code
docker run -i -t --net host tedross/centos7-dispatch
```

## Broker Integration Notes

Autolinks tell the router to establish links to the the queue on the broker.

```code
qdmanage create --type=autoLink addr=queue.one connection=broker dir=in
qdmanage create --type=autoLink addr=queue.one connection=broker dir=out
```

Verify the creation using qdstat.

```code
qdstat --autolinks
```

A connector with role 'route-container' establishes a connection to a broker that can
be used with autolinks.

```code
qdmanage create --type=connector host=127.0.0.1 port=61616 name=broker role=route-container
```
