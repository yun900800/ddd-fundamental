package org.ddd.fundamental.example.nginx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.NginxContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Testcontainers
public class NginxTest {

    @Container
    NginxContainer<?> nginx = new NginxContainer<>("nginx");

    @Test
    void testNginx() throws IOException, InterruptedException, URISyntaxException {
        var client = HttpClient.newHttpClient();
        var url = nginx.getBaseUrl("http", 80);
        var request = HttpRequest.newBuilder(url.toURI()).GET().build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertTrue(response.body().contains("Thank you for using nginx."));
    }

    @Test
    void test() throws IOException, InterruptedException {
        try (var container = new GenericContainer<>("nginx").withExposedPorts(80)) {
            container.start();
            var client = HttpClient.newHttpClient();
            var uri = "http://" + container.getHost() + ":" + container.getFirstMappedPort();

            var request = HttpRequest.newBuilder(URI.create(uri)).GET().build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Assertions.assertTrue(response.body().contains("Thank you for using nginx."));
        }
    }
}
