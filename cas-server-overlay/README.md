cas-server-overlay
======


A cas-server configured to authenticate users through a custom database.

The database is the [app-one](https://github.com/stefanopuddu/app-one) database.

Passwords are encoded with the `BCryptPasswordEncoder` provided by spring.

Configurations are:

- a  `datasource` bean
- a `PasswordEncoder` implementation, called `customPasswordEncoder` that encode password by using an injected spring passwordEncoder
- a `AbstractJdbcUsernamePasswordAuthenticationHandler` implementation called `CustomQueryDatabaseAuthenticationHandler` that matches passwords throught the `customPasswordEncoder`


### build and deploy

The `sync_ect_cas` ant target copy under `/etc/cas/` the following files (look at the xml overlays under `/WEB-INF` for more details):

```
├── cas-log4j2.xml
├── cas.properties
└── database.properties
```




The `tomcat_deploy` ant target performs the following operations:

- stop and clean tomcat
- build cas.war
- copy cas.war under tomcat

Just edit the tomcat path inside `tomcat.sh`

