package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dataAccess.AuthTokenDAO;
import dataAccess.DataAccessException;
import dataAccess.Database;
import model.AuthToken;
import results.GetAllEventsResult;
import services.GetAllEvents;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class AllEventsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            System.out.println("Entered ALL EVENTS HANDLER");

            if (httpExchange.getRequestMethod().equals("GET")) {
                Headers requestHeaders = httpExchange.getRequestHeaders();

                if (requestHeaders.containsKey("Authorization")) {
                    String authTokenID = requestHeaders.getFirst("Authorization");

                    Database db  = new Database();
                    db.getConnection();
                    AuthTokenDAO tokenAccess = new AuthTokenDAO(db.getConnection());
                    AuthToken userToken = tokenAccess.find(authTokenID);
                    db.closeConnection(false);

                    GetAllEvents allEventsService = new GetAllEvents();
                    GetAllEventsResult result = allEventsService.getAllEvents(userToken);
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
                }
                else {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }


            }
        } catch (IOException | DataAccessException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
