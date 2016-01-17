
cas-overlay
================

Just another test, following leleuj [great example](https://github.com/leleuj/cas-overlay-demo/blob/master/README.md)  


### Conf

- run `ant build` to copy `etc/**` content under `/etc/cas` as configured in `propertyFileConfigurer.xml`



### Build 

Inside `cas-server-overlay`  and `cas-management-overlay` run:

```
mvn clean package
```

to build war


### cert

I had some troubles but this stackoverflow [page](http://stackoverflow.com/questions/13123083/cas-sslhandshakeexception-validatorexception-pkix-path-building-failed-u) helped me a lot.

- create a `cert` direcorty somewhere

- find your `hostname` and your `java path` 

```
hostname && echo $JAVA_HOME
```

```
- keytool -genkey -alias HOSTNAME -keyalg RSA -keystore .HOSTNAME -storepass changeit
```

```
keytool -exportcert -alias HOSTNAME -file HOSTNAME.crt -keystore .HOSTNAME
```

```
 keytool -import -alias cas -file cas.crt -keystore /JAVA_PATH/jre/lib/security/cacerts
```


### tomcat

 Open `/your_tomcat/conf/server.xml` and modify the `Connector`


```
    <Connector port="8443" protocol="HTTP/1.1" SSLEnabled="true"
                maxThreads="150" scheme="https" secure="true"
                clientAuth="false" sslProtocol="TLS"
                keystoreFile="/CERT_DIR/.HOSTNAME"
                keystorePass="changeit"
                allowUnsafeLegacyRenegotiation="true"
    />
```