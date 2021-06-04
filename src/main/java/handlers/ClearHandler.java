package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import requests.UserRegisterRequest;
import results.ClearResult;
import services.ClearService;
import services.UserRegister;

import java.io.*;
import java.net.HttpURLConnection;

public class ClearHandler implements HttpHandler {
    /*
        JSON -> Request
        Call service
        Result -> JSON
        Return HTTP response
     */
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            System.out.println("Entered CLEAR HANDLER");

            // we are only allowing the post method for this handler
            if (httpExchange.getRequestMethod().equals("POST")) {
                // No request required: start clearService routine
                ClearService clearService = new ClearService();
                ClearResult result = clearService.deleteAllData();

                // open up the responseBody
                OutputStream responseBody = httpExchange.getResponseBody();
                Gson gson = new Gson();

                // Send proper response headers and write our result to the response body
                if (!result.isSuccess()) {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                else {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                writeString(gson.toJson(result), responseBody);

                // close response
                responseBody.close();
            }
            // everything else is a 405 "BAD METHOD" error
            else {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);

            }
        } catch (IOException e) {
            // something went wrong in our io. send 500 "INTERNAL ERROR" error
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR,0);
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
