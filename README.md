## Test Task for gbsfo

### Prerequisites

In order to run this application you need to install two tools: **Docker** & **Docker Compose**.

Instructions how to install **Docker**
on [Ubuntu](https://docs.docker.com/install/linux/docker-ce/ubuntu/), [Windows](https://docs.docker.com/docker-for-windows/install/) , [Mac](https://docs.docker.com/docker-for-mac/install/).

**Dosker Compose** is already included in installation packs for *Windows* and *Mac*, so only Ubuntu users needs to
follow [this instructions](https://docs.docker.com/compose/install/).

### How to run it?

You can run entire application using a single command in a terminal:

```
docker-compose up
```

When the application is started you must create and populate database using next commands:

```
docker exec postgres /bin/sh -c 'psql -U postgres -d gbsfo -f /var/lib/postgresql/sql/create-tables.sql'
```

```
docker exec postgres /bin/sh -c 'psql -U postgres -d gbsfo -f /var/lib/postgresql/sql/populate-tables.sql'
```

If you want to stop this app you can use this command:

```
docker-compose down
```

### How to use?

In order to be able to make requests to rest api you have to sign in account or create your own one.
After tables populating there's two users:

```
User Account

Username: User
Password: Pass

Manager account

Username: Manager
Password: Pass
```

If you send requests to rest api via postman, you must add a header with the "Authorization" key and the value "Bearer [your JWT token]". You will get a JWT token when you sign in or sign up.



Swagger documentation with all endpoints is accessible by link http://localhost:8080/swagger-ui/index.html







