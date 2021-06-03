package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dataAccess.AuthTokenDAO;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.UserDAO;
import model.AuthToken;
import model.User;
import results.GetTreeResult;
import services.GetTree;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class TreeHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            if (httpExchange.getRequestMethod().equals("GET")) {
                Headers requestHeaders = httpExchange.getRequestHeaders();

                if (requestHeaders.containsKey("Authorization")) {
                    String authTokenID = requestHeaders.getFirst("Authorization");

                    Database db = new Database();
                    db.getConnection();
                    AuthTokenDAO tokenAccess = new AuthTokenDAO(db.getConnection());
                    UserDAO userAccess = new UserDAO(db.getConnection());
                    AuthToken userToken = tokenAccess.find(authTokenID);
                    User userInfo = userAccess.find(userToken.getUserName());
                    db.closeConnection(false);

                    GetTree treeService = new GetTree();
                    GetTreeResult result = treeService.getTree(userInfo.getPersonID(), authTokenID);
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
            else {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
            }
        } catch (IOException | DataAccessException e) {
            e.printStackTrace();
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}

