#!/bin/bash
mvn spring-boot:run \
    -Dspring-boot.run.jvmArguments="-Djavax.net.ssl.trustStore=client-truststore.jks -Djavax.net.ssl.trustStorePassword=wso2carbon"