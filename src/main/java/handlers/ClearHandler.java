package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;

public class ClearHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            // we are only allowing the post method for this handler
            if (httpExchange.getRequestMethod().equals("POST")) {
                //
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
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
}
