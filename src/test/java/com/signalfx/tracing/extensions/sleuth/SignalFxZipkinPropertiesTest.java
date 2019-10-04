package com.signalfx.tracing.extensions.sleuth;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

public class SignalFxZipkinPropertiesTest {

    SignalFxZipkinProperties properties;

    @Before
    public void init() {
        this.properties = new SignalFxZipkinProperties();
    }

    @Test
    public void defaultUrlIsDefaultSignalFxAgent() {
        Assert.isTrue(this.properties.getBaseUrl().equals("http://localhost:9080"), "Default url is not the SignalFx Smart Agent default");
    }

    @Test
    public void baseUrlDefaultCanBeOverriden() {
        String newValue = "http://somewhereelse.com:80";
        this.properties.setBaseUrl(newValue);
        Assert.isTrue(this.properties.getBaseUrl().equals(newValue), "Unable to successfully set a new baseUrl value in SignalFxZipkinProperties");
    }

}
