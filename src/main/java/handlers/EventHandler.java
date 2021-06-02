package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import results.GetEventResult;
import services.GetEvent;

import java.io.IOException;
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
            if (httpExchange.getRequestMethod().equals("GET")) {
                // Change the json to a request
                Gson gson = new Gson();
                // gson.fromJson()

                GetEvent service = new GetEvent();
                // GetEventResult result = service.getEvent();
            }
            else {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
            }
        } catch (IOException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            e.printStackTrace();
        }
    }
}
