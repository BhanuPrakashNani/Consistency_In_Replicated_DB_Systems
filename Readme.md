##HOW TO RUN

Open three terminals:


Update your $CLASSPATH env var to the path of connector-xxx.jar






#terminal1
``` cd bin/ 
    rmiregistry -J-Djava.rmi.useLocalHostName=true -J-Djava.rmi.server.hostname=127.0.0.1
```

#terminal2

```
    cd src/
    java Server -cp ./mysql-connector-java-8.0.19.jar
```

#terminal3

```
    cd src/
    java Client -cp ./mysql-connector-java-8.0.19.jar
    
```