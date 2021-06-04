package handlers;

import java.net.*;
import java.io.*;
import java.nio.file.*;
import com.sun.net.httpserver.*;

public class FileHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            System.out.println("Entered FILE HANDLER");

            String urlPath = httpExchange.getRequestURI().toString();
            if (urlPath.equals("/") || urlPath == null) {
                urlPath = "/index.html";
            }
            String filePath = "web" + urlPath;
            File file = new File(filePath);
            if (file.exists()) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }
            else {
                filePath = "web/HTML/404.html";
                file = new File(filePath);
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            }
            // read the file and write it to the HTTPExchange output stream
            OutputStream responseBody = httpExchange.getResponseBody();
            Files.copy(file.toPath(), responseBody);
            responseBody.close();

        } catch (IOException e) {
            // return a server error
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR,0);
            httpExchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
