package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import requests.UserLoginRequest;
import results.UserLoginResult;
import services.UserLogin;

import java.io.*;
import java.net.HttpURLConnection;

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        boolean success = false;

        try  {
            if (httpExchange.getRequestMethod().equals("POST")) {
                InputStream requestBody = httpExchange.getRequestBody();
                Gson gson = new Gson();

                UserLoginRequest request = gson.fromJson(readString(requestBody), UserLoginRequest.class);
                requestBody.close();

                UserLogin service = new UserLogin();
                UserLoginResult result = service.login(request);

                if (!result.isSuccess()) {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                else {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                OutputStream responseBody = httpExchange.getResponseBody();
                gson = new Gson();
                writeString(gson.toJson(result), responseBody);

                responseBody.close();
                httpExchange.close();
                success = true;
            }
            else {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                httpExchange.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            httpExchange.close();
        }
    }

    private String readString(InputStream in) throws IOException {
        StringBuilder build = new StringBuilder();
        InputStreamReader read = new InputStreamReader(in);
        char[] buf = new char[1024];
        int len;
        while ((len = read.read(buf)) > 0) {
            build.append(buf, 0, len);
        }
        return build.toString();
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
