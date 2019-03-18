# pcc-client
A Client for Pivotal Cloud Cache

Exposes APIs to add and remove data to a PCC service instance.
You can run this app against a GemFire cluster running on localhost or against a PCC SI.

## Working with a localhost GemFire cluster

1. After installing GemFire on your machine, run the below which will create 1 locator and 3 servers and couple of regions. 
```
./src/server-bootstrap/scripts/startServer.sh
```
2. Launch gfsh to verify
```
± |master ✓| → gfsh
    _________________________     __
   / _____/ ______/ ______/ /____/ /
  / /  __/ /___  /_____  / _____  /
 / /__/ / ____/  _____/ / /    / /
/______/_/      /______/_/    /_/    9.6.0

Monitor and Manage Pivotal GemFire
gfsh>connect
Connecting to Locator at [host=localhost, port=10334] ..
Connecting to Manager at [host=10.37.18.15, port=1099] ..
Successfully connected to: [host=10.37.18.15, port=1099]

gfsh>list members
     Name       | Id
--------------- | --------------------------------------------------------------
locator1        | 10.37.18.15(locator1:97640:locator)<ec><v0>:1024 [Coordinator]
gemfire-server1 | 10.37.18.15(gemfire-server1:97651)<v1>:1025
gemfire-server2 | 10.37.18.15(gemfire-server2:97659)<v2>:1026
gemfire-server3 | 10.37.18.15(gemfire-server3:97662)<v3>:1027

gfsh>list regions
List of regions
---------------
Customer
CustomerCounter

```

#### Hit the available APIs after starting pcc-client.

- Loading data
http://localhost:8080/customer/loadEntries/1000000

- Removing data
http://localhost:8080/customer/remove/50

## Working with a PCC Service Instance

- Modify the manifest.yml and add the service instance name. You should have created the SI and service key before doing this.
- Do a `cf push`
- Hit the below endpoints, change the hostname part of the url based on your app route
  
  http://pcc-client.apps.etna.cf-app.com/customer/loadEntries/300000 --> To load n number of entries each of size 100KB.
  
  http://pcc-client.apps.etna.cf-app.com/customer/loadBytes/100mb --> To load specific bytes (ex 500kb, 500mb, 1gb).
  
  http://pcc-client.apps.etna.cf-app.com/customer/remove/1000000 --> remove n number of entries from customer region.

