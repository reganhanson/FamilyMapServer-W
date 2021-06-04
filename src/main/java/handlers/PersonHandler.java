package handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import results.GetPersonResult;
import services.GetPerson;
import com.google.gson.*;
import java.io.*;
import java.net.HttpURLConnection;

public class PersonHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        boolean success = false;

        try {
            System.out.println("Entered PERSON HANDLER");

            if (httpExchange.getRequestMethod().toLowerCase().equals("get")) {
                Headers requestHeaders = httpExchange.getRequestHeaders();

                if (requestHeaders.containsKey("Authorization")) {
                    String authToken = requestHeaders.getFirst("Authorization");

                    StringBuilder personID = new StringBuilder();
                    String path = httpExchange.getRequestURI().toString();
                    for (int i = 8; i < path.length(); i++) {
                        personID.append(path.charAt(i));
                    }
                    GetPerson servicePerson = new GetPerson();
                    GetPersonResult result;
                    result = servicePerson.getPerson(personID.toString(), authToken);

                    if (!result.isSuccess()) {
                        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                    else {
                        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }

                    // parse resp data and put it into json
                    OutputStream responseBody = httpExchange.getResponseBody();

                    // write the String with the data we put into JSON
                    Gson gson = new Gson();
                    writeString(gson.toJson(result), responseBody);

                    responseBody.close();
                    success = true;
                }
            }
            else {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                success = true;
                httpExchange.close();
            }
            if (!success && httpExchange != null) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                httpExchange.close();
            }
        } catch (IOException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            httpExchange.close();
            e.printStackTrace();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
