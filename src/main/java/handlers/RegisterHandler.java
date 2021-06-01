package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import requests.UserRegisterRequest;
import results.UserRegisterResult;
import services.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class RegisterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        boolean success = false;

        try {
            if (httpExchange.getRequestMethod().toLowerCase().equals("post")) {
                Headers requestHeaders = httpExchange.getRequestHeaders();
                InputStream requestBody = httpExchange.getRequestBody();
                Gson gson = new Gson();

                UserRegisterRequest request = gson.fromJson(requestBody.toString(), UserRegisterRequest.class);
                requestBody.close();

                UserRegister registerService = new UserRegister();
                UserRegisterResult result = registerService.registerUser(request);

                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream responseBody = httpExchange.getResponseBody();

                gson = new Gson();
                writeString(gson.toJson(result), responseBody);

                responseBody.close();
                success = true;
            }
            else {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                success = true;
                httpExchange.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
