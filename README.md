# accountmanager
Spring Boot Application that allows to manage accounts and collect events for an account.
The application clients are
other applications that manage accounts and events through an API.

Accounts can be listed, created and updated. Events can be listed and added, but neither updated, nor deleted.

Account statistics for a single account can be requested through the API.

Events are to be deleted after 30 days, but still present in the statistical data.

Docker Pull Command:
====================
```

docker pull lrzeszotarski/accountmanager
```

REST API:
=========
http://localhost:8080/v2/account

API Documentation:
==================
http://localhost:8080/swagger-ui.html#/account

Example Requests:
=================
Create an account: 
------------------
```
curl -X POST "http://localhost:8080/v2/account" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"name\": \"Account Name\"}"
```
Update an existing account:
---------------
```
curl -X PUT "http://localhost:8080/v2/account" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"accountId\": \"6e4e649c-9e03-4884-87ae-fbb98d96e4a5\", \"name\": \"Updated Account Name\"}"
```

Get an existing account:
------------
```
curl -X GET "http://localhost:8080/v2/account/6e4e649c-9e03-4884-87ae-fbb98d96e4a5" -H "accept: application/json"
```

Create an event:
----------------
```
curl -X POST "http://localhost:8080/v2/account/6e4e649c-9e03-4884-87ae-fbb98d96e4a5/event" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"happenedAt\": \"2020-06-27T17:40:03.129Z\", \"type\": \"Some New Event\"}"
```

Get an existing event:
----------------------
```
curl -X GET "http://localhost:8080/v2/account/6e4e649c-9e03-4884-87ae-fbb98d96e4a5/event/08e872f6-fb37-46fa-92ed-b47f0b8693b2" -H "accept: application/json"
```

Get an existing account statistics per day:
-------------------------------------------
```
curl -X GET "http://localhost:8080/v2/account/6e4e649c-9e03-4884-87ae-fbb98d96e4a5/statistics?day=2020-06-27T00%3A00%3A00.000Z" -H "accept: application/json"
```

H2 Console:
===========
http://localhost:8080/h2-console/


