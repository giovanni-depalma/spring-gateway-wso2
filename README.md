# spring-gateway-wso2

In this project there are two main folder, wso2is-5.11 and spring-gateway-wso2

## wso2is-5.11

The folder wso2is-5.11" contains the files to build and run a docker images of wso2is-5.11

There are two bash script in the root

- build.sh build a docker images with tag wso2is-5.11
- run.sh stop,remove a possibile previous container with name wso2is-5.11, and then run wso2is-5.11

Inside in "wso2is-5.11/docker" are present all the necessary stuffs to build the dockerimage

You can choose to run wso2-is by docker or or whichever method you prefer

## spring-gateway-wso2

This simple project it's a starter project to build a spring-cloud-gateway integrated with wso2is

The most important files are:

    - test-spring-gateway.xml: export of example of service provider, with checkbox saas
    - run.sh to launch gateway by maven, using client-trustore.jks as jvm arguments
    - client-trustore.jks copy of client-trustore.jks inside wso2is
    - src/main/resources/application.yml configuration of application and the client-security. If you use test-spring-gateway.xml as service provider it's contain the right values for "client-id" and "client-secret"

The application at startup try to discover "issuer-uri" of the identity server, so it's mandatory wso2is it's running.
You can choose to change the configuration from

```
        provider:
          wso2:
            issuer-uri: ${provider.host}/oauth2/token
#            token-uri: ${provider.host}/oauth2/token
#            authorization-uri: ${provider.host}/oauth2/authorize
#            user-info-uri: ${provider.host}/oauth2/userinfo
#            jwk-set-uri: ${provider.host}/oauth2/jwks
#            user-name-attribute: sub
```

to

```
#            issuer-uri: ${provider.host}/oauth2/token
            token-uri: ${provider.host}/oauth2/token
            authorization-uri: ${provider.host}/oauth2/authorize
            user-info-uri: ${provider.host}/oauth2/userinfo
            jwk-set-uri: ${provider.host}/oauth2/jwks
            user-name-attribute: sub
```

to avoid the discovery at startup time

From the browser, digit 'http://localhost:8080/' and after login the expected result it's a simple response with "Welcome" string

## Test Case

With service provider created by "test-spring-gateway.xml" is possible to login with "carbon.super" user and tenant user.

Chaning the setting in service provider, from Token Issuer Default -> JWT, is only possible to login with "carbon.super".


It's possible create directly service provider with token isser JWT by file "test-spring-gateway-issuer-jwt.xml"

### Debug Token Issuer: JWT
Using issuer-uri: ${provider.host}/oauth2/token WSO2 logs this 
```
[2020-11-15 15:52:33,326] [a000f2e2-dcf6-43f3-8ae6-3659fbd84c32] DEBUG {org.wso2.carbon.identity.oauth.endpoint.user.OpenIDConnectUserEndpoint} - Error while building user info response. org.wso2.carbon.identity.oauth.user.UserInfoEndpointException: Access token validation failed
        at org.wso2.carbon.identity.oauth.endpoint.user.impl.UserInfoISAccessTokenValidator.validateToken(UserInfoISAccessTokenValidator.java:63)
        at org.wso2.carbon.identity.oauth.endpoint.user.OpenIDConnectUserEndpoint.getUserClaims(OpenIDConnectUserEndpoint.java:76)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:566)
        at org.apache.cxf.service.invoker.AbstractInvoker.performInvocation(AbstractInvoker.java:179)
        at org.apache.cxf.service.invoker.AbstractInvoker.invoke(AbstractInvoker.java:96)
        at org.apache.cxf.jaxrs.JAXRSInvoker.invoke(JAXRSInvoker.java:201)
        at org.apache.cxf.jaxrs.JAXRSInvoker.invoke(JAXRSInvoker.java:104)
        at org.apache.cxf.interceptor.ServiceInvokerInterceptor$1.run(ServiceInvokerInterceptor.java:59)
```

Changing configuration in 

```
provider:
  wso2:
#   issuer-uri: ${provider.host}/oauth2/token
    token-uri: ${provider.host}/oauth2/token
    authorization-uri: ${provider.host}/oauth2/authorize
    user-info-uri: ${provider.host}/t/test.com/oauth2/userinfo
    jwk-set-uri: ${provider.host}/oauth2/jwks
    user-name-attribute: sub
```
(Of course test.com is the name of created tenant)

login process works.

Instead with user-info-uri and jwk-set-uri tenant based doesn't work, with another error:

```
Signed JWT rejected: Another algorithm expected, or no matching key(s) found
```

