# exercise 4: message routing vs link routing

This will use the calc_client and calc_server test clients.

## first try non-intermediated use

```code
calc_server --listen 5678
```

```code
calc_client --server 5678
```

## now lets connect *one instance* of this via a router

```code
calc_server --connect 5672 --address calc
```

```code
calc_client --server 5672
```

If we try to start multiple instances, it doesn't work as expected.

## edit router config again:

```code
listener {
    name: calc-listener
    host: 0.0.0.0
    port: 55672
    authenticatePeer: no
    saslMechanisms: ANONYMOUS
    role: route-container
}


linkRoute {
    prefix: calc
    dir: in
    connection: calc-listener
}

```

restart router service


```code
calc_server --connect 55672
```

```code
calc_client --server 5672
```


