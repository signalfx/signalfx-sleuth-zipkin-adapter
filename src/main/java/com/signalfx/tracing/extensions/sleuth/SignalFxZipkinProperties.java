package com.signalfx.tracing.extensions.sleuth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.sleuth.zipkin2.ZipkinProperties;

/**
 * <p>
 * This overrides the default behavior of <code>ZipkinProperties</code>
 * to specify a default <code>baseUrl</code> matching the default 
 * location of the SignalFx Smart Agent: <code>http://localhost:9080</code>.
 * </p><p>
 * Note: The <code>SignalFxZipkinReporter</code> is responsible for appending
 * the endpoint <code>/v1/trace</code>.
 * </p>
 * @see <a href="https://docs.signalfx.com/en/latest/integrations/agent/monitors/trace-forwarder.html">SignalFx Smart Agent Monitor: Trace Forwarder</a>
 * @author brandon
 */
@ConfigurationProperties("spring.zipkin")
public class SignalFxZipkinProperties extends ZipkinProperties {

    /** 
     *  Set the default baseUrl to match the path of the SignalFx Smart Agent defaults
     */
    private String baseUrl = "http://localhost:9080";

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }


}
