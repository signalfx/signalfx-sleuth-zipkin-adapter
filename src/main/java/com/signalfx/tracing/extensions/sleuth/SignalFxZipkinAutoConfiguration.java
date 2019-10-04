package com.signalfx.tracing.extensions.sleuth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.sleuth.zipkin2.ZipkinAutoConfiguration;
import org.springframework.cloud.sleuth.zipkin2.ZipkinRestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * This configuration creates an interceptor for the <code>RestTemplate</code> repsonsible for 
 * sending Spans to the <code>spring.zipkin.baseUrl</code>. It will replace any URI path 
 * matching <code>/api/v2/spans</code> with <code>/v1/trace</code>.
 * 
 * This allows for the standard Zipkin configuration to be used.
 * 
 * @see <a href="https://cloud.spring.io/spring-cloud-sleuth/2.1.x/single/spring-cloud-sleuth.html#_sending_spans_to_zipkin">Sending spans to Zipkin</a>
 * 
 */
@Configuration
@AutoConfigureBefore(ZipkinAutoConfiguration.class)
public class SignalFxZipkinAutoConfiguration {

    Logger log = LoggerFactory.getLogger(SignalFxZipkinAutoConfiguration.class);
    

    @Bean ZipkinRestTemplateCustomizer overrideZipkinUriCustomizer() {
	ZipkinRestTemplateCustomizer customizer = (RestTemplate restTemplate) -> {
	    log.warn("Adding Zipkin endpoint override. All spans sent to endpoint '/api/v2/spans' will be sent to '/v1/trace' instead.");
	    restTemplate.getInterceptors().add(new ZipkinToSignalFxUriInterceptor());
	};
	return customizer;
    }

}
