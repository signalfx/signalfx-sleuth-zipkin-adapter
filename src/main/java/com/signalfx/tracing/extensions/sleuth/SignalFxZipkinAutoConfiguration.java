package com.signalfx.tracing.extensions.sleuth;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.sleuth.metric.SpanMetricReporter;
import org.springframework.cloud.sleuth.zipkin.ZipkinSpanReporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@ConditionalOnProperty(value = { "spring.sleuth.enabled", "spring.zipkin.enabled" },
    matchIfMissing = true)
@EnableConfigurationProperties({SignalFxZipkinProperties.class})
@Configuration
public class SignalFxZipkinAutoConfiguration {

    /**
     * This bean will override the default <code>ZipkinSpanReporter</code>, which will only send to the 
     * <code>/api/v2/spans</code> endpoint. The <code>SignalFxZipkinReporter</code> will
     * send to <code>/v1/trace</code> instead. 
     * 
     * @return ZipkinSpanReporter - Will be an instance of <code>SiganlFxZipkinReporter</code>
     * @param spanMetricReporter - Auto-injected via Spring
     * @param zipkin - @see <code>SignalFxZipkinProperties</code>
     */
    @Primary
    @Bean
    public ZipkinSpanReporter reporter(SpanMetricReporter spanMetricReporter, SignalFxZipkinProperties zipkin) {

	return new SignalFxZipkinReporter(zipkin.getBaseUrl(), zipkin.getFlushInterval(),
		zipkin.getCompression().isEnabled(), spanMetricReporter);
    }
    
}
