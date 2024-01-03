package org.ddd.fundamental.ribbon.intercepter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;

@Component
public class SecurityInterceptor implements ClientHttpRequestInterceptor {


    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes,
                                        ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        this.addRequestHeader(httpRequest);
        ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest,bytes);
        this.addResponseHeader(response);
        return response;
    }

    private void addResponseHeader(ClientHttpResponse response) {
        HttpHeaders httpHeaders = response.getHeaders();
        httpHeaders.add("Access-Control-Expose-Headers","key");
        httpHeaders.add("key","token");
    }

    private void addRequestHeader(HttpRequest httpRequest) {
        HttpHeaders httpHeaders = httpRequest.getHeaders();
        int ret = new Random().nextInt();
        if (ret % 3 != 0) {
            httpHeaders.add("security","token");
        }
    }
}
