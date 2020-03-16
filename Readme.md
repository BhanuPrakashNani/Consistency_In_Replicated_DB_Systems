[![ForTheBadge built-with-love](http://ForTheBadge.com/images/badges/built-with-love.svg)](https://GitHub.com/Naereen/) [![Open Source Love png2](https://badges.frapsoft.com/os/v2/open-source.png?v=103)](https://github.com/ellerbrock/open-source-badges/) [![GitHub version](https://badge.fury.io/gh/Naereen%2FStrapDown.js.svg)](https://github.com/Naereen/StrapDown.js)


# Implementation of Consistency Algorithms in Replicated Database Systems.

## HOW TO RUN

Open five terminals:


Update your $CLASSPATH env var to the path of connector-xxx.jar

** ADD current directory in your class path in every session**




# terminal1
``` cd bin/ 
    rmiregistry -J-Djava.rmi.useLocalHostName=true -J-Djava.rmi.server.hostname=127.0.0.1
```

# terminal2

```
    cd src/
    java Server -cp ./mysql-connector-java-8.0.19.jar
```

# terminal3

```
    cd src/
    java Server2 -cp ./mysql-connector-java-8.0.19.jar
```
# terminal4

```
    cd src/
    java Client -cp ./mysql-connector-java-8.0.19.jar
    
```

# terminal5

```
    cd src/
    java Client2 -cp ./mysql-connector-java-8.0.19.jar
    
```
