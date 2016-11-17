# exercise 1: router networks

## Edit the configuration file in

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
    host: ec2-35-163-243-169.us-west-2.compute.amazonaws.com
    port: amqp
    role: inter-router
    saslMechanisms: ANONYMOUS
}
```

e.g.

```code
# See the qdrouterd.conf (5) manual page for information abou	# See the qdrouterd.conf (5) manual page for information abou
# file's format and options.					# file's format and options.

router {							router {
    mode: standalone					      |	    mode: interior
    id: Router.A					      |	    id: grs
}								}

listener {							listener {
    host: 0.0.0.0						    host: 0.0.0.0
    port: amqp							    port: amqp
    authenticatePeer: no					    authenticatePeer: no
    saslMechanisms: ANONYMOUS					    saslMechanisms: ANONYMOUS
}								}

							      >	connector {
							      >	    host: ec2-35-163-243-169.us-west-2.compute.amazonaws.com
							      >	    port: amqp
							      >	    saslMechanisms: ANONYMOUS
							      >	    role: inter-router
							      >	}
							      >

```

## Start or restart the router

```code
systemctl start qdrouterd.service
```

or

```code
systemctl restart qdrouterd.service
```

## run hello from test clients directory

e.g.


```code
hello --user grs
```

## run the router cli tool, qdstat to show various aspects of the network


