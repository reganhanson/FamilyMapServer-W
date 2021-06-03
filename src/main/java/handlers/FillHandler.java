package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dataAccess.DataAccessException;
import results.FillResult;
import services.FillService;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            if (httpExchange.getRequestMethod().equals("POST")) {
                String path = httpExchange.getRequestURI().toString();

                String userID = new String();
                String gen = new String();
                int generations = 0;
                StringBuilder builder = new StringBuilder();
                for (int i= 6; i < path.length(); i++) {
                    if (path.charAt(i) == '/') {
                        userID = builder.toString();
                        builder = new StringBuilder();
                    }
                    else {
                        builder.append(path.charAt(i));
                    }
                }
                gen = builder.toString();
                for (int i = 0; i < gen.length(); i++) {
                    if (!Character.isDigit(gen.charAt(i))) {
                        // ERROR
                        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        return;
                    }
                }

                FillService service = new FillService();
                FillResult result = service.fill(userID, generations);

                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream responseBody = httpExchange.getResponseBody();

                Gson gson = new Gson();
                writeString(gson.toJson(result), responseBody);

                responseBody.close();
            }
            else {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
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
