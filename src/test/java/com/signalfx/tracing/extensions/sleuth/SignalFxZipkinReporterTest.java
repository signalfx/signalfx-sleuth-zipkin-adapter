package com.signalfx.tracing.extensions.sleuth;

import java.lang.reflect.Field;

import org.junit.Test;
import org.springframework.util.Assert;

public class SignalFxZipkinReporterTest {

    @Test
    public void signalFxTraceEndpointIsAppended() throws Exception {
	String expectedEndpoint = "/v1/trace";
	
	SignalFxZipkinReporter reporter = new SignalFxZipkinReporter("http://baseurl.com", 5, false, null);
	
	Field privateStringField = SignalFxZipkinReporter.class.
	            getDeclaredField("url");
	privateStringField.setAccessible(true);
	String privateUrlFieldValue = getPrivateField(reporter, "url");
	
	Assert.isTrue(privateUrlFieldValue.endsWith(expectedEndpoint));
    }
    
    private String getPrivateField(Object instance, String fieldName) throws Exception {
	Field privateStringField = instance.getClass().getDeclaredField(fieldName);
	privateStringField.setAccessible(true);
	return (String) privateStringField.get(instance);
    }
}
