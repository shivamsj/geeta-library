package com.geetalibrary.backend.lambda;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.geetalibrary.backend.GeetaLibraryBackendApplication;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamLambdaHandler implements RequestStreamHandler {
    private static final SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> HANDLER;

    static {
        try {
            HANDLER = SpringBootLambdaContainerHandler.getAwsProxyHandler(GeetaLibraryBackendApplication.class);
            HANDLER.stripBasePath("/" + System.getenv().getOrDefault("STAGE_NAME", "prod"));
        } catch (ContainerInitializationException exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        HANDLER.proxyStream(inputStream, outputStream, context);
    }
}
