# signalfx-sleuth-zipkin-adapter
An extension to Spring Cloud's spring-cloud-sleuth-zipkin library used to send 
Zipkin formatted traces to the SignalFx Smart Agent or Smart Gateway.

## About

Spring Cloud's `spring-cloud-sleuth-zipkin` is an extension used to send Zipkin 
formatted spans/traces to a Zipkin server. SignalFx Smart Agent and SignalFx 
Smart Gateway can both accept Zipkin formatted spans. 

However, the `spring-cloud-sleuth-zipkin` library is only able to configure a 
base url and has the Zipkin server endpoint, `/api/v2/spans`, hardcoded.  The
SignalFx Smart Gatweay and SignalFx Smart Agent both listen the `/v1/trace` 
endpoint.

This library is an extension to `spring-cloud-sleuth-zipkin` and will only 
modify the behavior of that library by changing the URI endpoint. 

## Configuration

When added as a dependency, the default behavior of this library is to send the traces to the SignalFx Smart
Agent, expected to be reachable via `http://localhost:8090/v1/trace`.  

Other configuration is determined by the `spring-cloud-sleuth-zipkin` and 
`spring-cloud-sleuth` properties.  See [SignalFx Tracing Examples: Sleuth 1.x]()
for recommended configuration. 

### Adding this library as a dependency

#### Maven

```xml
<dependency>
    <groupId>com.signalfx.public</groupId>
    <artifactId>signalfx-sleuth-zipkin-adapter</artifactId>
    <version>1.0.1-RELEASE</version>
</dependency>
```

#### Gradle

```gradle
classpath 'com.signalfx.public:signalfx-sleuth-zipkin-adapter'
```

## Debugging

This library will log an `INFO` level statement during instantiation, and output
as part of Spring Boot Web startup, that reflects the http endpoint. An example: 
```
INFO [Coin Flip - Sleuth,,,] 90272 --- [ost-startStop-1] c.s.uapm.sleuth.SignalFxZipkinReporter   : Registering Zipkin reporter for SignalFx endpoint: http://localhost:9080/v1/trace
```

This library will respect any `spring.zipkin.baseUrl` value configured in your
application properties and will _always_ append `/v1/trace` as the endpoint. 