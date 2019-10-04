package com.signalfx.tracing.extensions.sleuth;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.util.UriComponentsBuilder;

public class SignalFxEndpointRewriteWrapper extends HttpRequestWrapper {

    private static Logger log = LoggerFactory.getLogger(SignalFxEndpointRewriteWrapper.class);

    public SignalFxEndpointRewriteWrapper(HttpRequest request) {
        super(request);
    }

    @Override
    public URI getURI() {
        URI unmodifiedUri = super.getURI();
        if(!isZipkinUri(unmodifiedUri))
            return unmodifiedUri;

        URI modifiedUri = replaceZipkinPathWithSignalFxPath(unmodifiedUri);
        log.debug("Overriding Zipkin endpoint {} with {}", unmodifiedUri.toString(), modifiedUri.toString());
        return modifiedUri;

    }

    protected URI replaceZipkinPathWithSignalFxPath(URI zipkinPathToBeReplaced) {
        try {
            UriComponentsBuilder modifiedUri = UriComponentsBuilder.fromUri(zipkinPathToBeReplaced);
            modifiedUri.replacePath("v1/trace");

            return new URI(modifiedUri.toUriString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    protected boolean isZipkinUri(URI uri) {
        return uri.getPath().endsWith("api/v2/spans");
    }
}