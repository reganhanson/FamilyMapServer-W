package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import results.GetEventResult;
import services.GetEvent;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class EventHandler implements HttpHandler {
    /*
    JSON -> Request
    Call service
    Result -> JSON
    Return HTTP response
 */
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        try {
            System.out.println("Entered EVENT HANDLER");

            if (httpExchange.getRequestMethod().equals("GET")) {
                Headers requestHeaders = httpExchange.getRequestHeaders();

                if (requestHeaders.containsKey("Authorization")) {
                    String authtoken = requestHeaders.getFirst("Authorization");

                    StringBuilder eventID = new StringBuilder();
                    String path = httpExchange.getRequestURI().toString();
                    for (int i = 7; i < path.length(); i++) {
                        eventID.append(path.charAt(i));
                    }
                    GetEvent service = new GetEvent();
                    GetEventResult result = service.getEvent(eventID.toString(), authtoken);

                    if (!result.isSuccess()) {
                        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                    else {
                        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                    OutputStream responseBody = httpExchange.getResponseBody();

                    Gson gson = new Gson();
                    writeString(gson.toJson(result), responseBody);
                    responseBody.close();

                } else {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
            }
            else {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
            }
        } catch (IOException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            e.printStackTrace();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
