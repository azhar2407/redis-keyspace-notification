# redis-keyspace-notification
This produces an example on how to make use of redis keyspace notification.

To start the app:
mvn spring-boot:run

There 2 APIs I have exposed:

add : localhost:8080/add?a=1&b=2

evict: localhost:8080/evict?a=1

Run them to see the output of the listener in the stand console
