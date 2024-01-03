package org.ddd.fundamental.app.api.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;

import org.ddd.fundamental.app.api.user.UserRequest;

import java.io.IOException;

public class OkHttpClientUtils {

    private static OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static String get(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String post(String url,String json) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String asyncRegister(String userName,String password) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName(userName);
        userRequest.setPassword(password);
        return mapper.writeValueAsString(userRequest);
    }
}
