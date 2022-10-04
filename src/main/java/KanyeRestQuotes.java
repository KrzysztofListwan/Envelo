import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class KanyeRestQuotes {

    static final String instructionText = "Type \"next\" to get new quote from Kanye or \"quit\" to exit application.";
    static final String unknownCommandError = "Command unknown!";
    static final String unknownErrorMessage = "Something went wrong! Try again...";
    static final String allQuotesReceivedMessage = "All quotes received.";
    static final String goodbyeMessage = "Goodbye!";

    static ArrayList<String> quotes = new ArrayList<>();
    static final int maxQuotes = 122;

    static HttpRequest request;
    static HttpClient client = HttpClient.newHttpClient();
    static HttpResponse<String> response;
    static JSONParser jsonParser = new JSONParser();
    static final String apiUrl = "https://api.kanye.rest";

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException, ParseException {
        request = HttpRequest.newBuilder()
            .uri(new URI(apiUrl))
            .build();
        String command = "";
        System.out.println(instructionText);
        while(!Objects.equals(command, "quit")&&quotes.size()<maxQuotes) {
            command = scanner.nextLine();
            if(Objects.equals(command, "next")) {
                getQuote();
            }
            else if(Objects.equals(command, "quit")) {
                System.out.println(goodbyeMessage);
            }
            else {
                System.out.println(unknownCommandError);
                System.out.println(instructionText);
            }
        }
        scanner.close();
    }

    private static void getQuote() throws IOException, InterruptedException, ParseException {
        boolean quoteIsNew = false;
        while (!quoteIsNew) {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode()==200) {
                JSONObject quoteObject = (JSONObject) jsonParser.parse(response.body());
                String quote = (String) quoteObject.get("quote");
                if (!quotes.contains(quote)) {
                    quotes.add(quote);
                    quoteIsNew = true;
                    System.out.println(quote);
                    if(quotes.size()>=maxQuotes) {
                        System.out.println(allQuotesReceivedMessage);
                        System.out.println(goodbyeMessage);
                    }
                }
            } else {
                System.out.println(unknownErrorMessage);
            }
        }
    }
}