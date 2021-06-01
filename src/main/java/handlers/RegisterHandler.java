package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import requests.UserRegisterRequest;
import results.UserRegisterResult;
import services.*;

import java.io.*;
import java.net.HttpURLConnection;

public class RegisterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        boolean success = false;

        try {
            if (httpExchange.getRequestMethod().toLowerCase().equals("post")) {
                // Headers requestHeaders = httpExchange.getRequestHeaders();
                InputStream requestBody = httpExchange.getRequestBody();
                Gson gson = new Gson();

                UserRegisterRequest request = gson.fromJson(readString(requestBody), UserRegisterRequest.class);
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
            success = false;
            e.printStackTrace();
        }

    }

    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
