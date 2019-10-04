package com.signalfx.tracing.extensions.sleuth;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpRequest;

import com.signalfx.tracing.extensions.sleuth.SignalFxEndpointRewriteWrapper;

@RunWith(MockitoJUnitRunner.class)
public class SignalFxEndpointRewriteWrapperTest {

    @Mock 
    HttpRequest httpRequest;

    @Test
    public void doesntAffectNonZipkinEndpoint() throws Exception {
        validateEndpoints("/other/path","/other/path");
    }

    @Test
    public void rewritesZipkinEndpoint() throws Exception {
        validateEndpoints("/api/v2/spans","/v1/trace");
    }

    protected void validateEndpoints(String inputPath, String expectedOutputPath) throws Exception {

        URI uri = new URI("http://localhost:8090" + inputPath);
        when(httpRequest.getURI()).thenReturn(uri);

        SignalFxEndpointRewriteWrapper wrapper = new SignalFxEndpointRewriteWrapper(httpRequest);

        String path = wrapper.getURI().getPath();

        //The endpoint shouldn't be an affected
        assertEquals(expectedOutputPath,path);
    }


}
