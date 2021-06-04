package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import requests.LoadRequest;
import results.LoadResult;
import services.Load;

import java.io.*;
import java.net.HttpURLConnection;

public class LoadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            System.out.println("Entered LOAD HANDLER");

            if (httpExchange.getRequestMethod().equals("POST")) {
                // Headers requestHeaders = httpExchange.getRequestHeaders();
                InputStream requestBody = httpExchange.getRequestBody();
                String requestData = readString(requestBody);

                Gson gson = new Gson();

                LoadRequest request = gson.fromJson(requestData, LoadRequest.class);
                Load service = new Load();
                LoadResult result = service.load(request);

                if (!result.isSuccess()) {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                else {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }

                OutputStream responseBody = httpExchange.getResponseBody();

                writeString(gson.toJson(result), responseBody);

                responseBody.close();

            }
            else {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
            }
        } catch (IOException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
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
