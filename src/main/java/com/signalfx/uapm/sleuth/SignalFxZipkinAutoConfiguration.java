package com.signalfx.uapm.sleuth;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.sleuth.metric.SpanMetricReporter;
import org.springframework.cloud.sleuth.zipkin.ZipkinProperties;
import org.springframework.cloud.sleuth.zipkin.ZipkinSpanReporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

//@ConditionalOnProperty(value = { "spring.sleuth.enabled", "spring.zipkin.enabled" },
//    matchIfMissing = true)
@Configuration
public class SignalFxZipkinAutoConfiguration {

    /**
     * This bean will override the default ZipkinSpanReporter that will only send to the 
     * <code>/api/v2/spans</code> endpoint.  
     * 
     */
    @Primary
    @Bean
    public ZipkinSpanReporter reporter(SpanMetricReporter spanMetricReporter, ZipkinProperties zipkin) {

	return new SignalFxZipkinReporter(zipkin.getBaseUrl(), zipkin.getFlushInterval(),
		zipkin.getCompression().isEnabled(), spanMetricReporter);
    }
}
