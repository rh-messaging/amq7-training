# exercise 2: basic addressing

## start a receiving client using the recv test client

```code
recv -a <username> -a closest -a multicast -a amq7
```

## send some messages using the send test client

First broadcast the name you used

```code
send -a multicast <username>
```

Then pick someone from the messages you have received and send them a message

```code
send -a <some user> <message text>
```

Now send messages to the 'closest' address

```code
send -a closest <message text>
```

Only you will get these, as your receiver is the closest on your router.
