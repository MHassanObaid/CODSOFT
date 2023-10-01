import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
      HashMap<Integer,String> curencyCode = new HashMap<Integer,String>();
     curencyCode.put(1,"USD");
        curencyCode.put(2,"CAD");
        curencyCode.put(3,"EUR");
        curencyCode.put(4,"HKD");
        curencyCode.put(5,"PKR");

        String fromCod, toCode;
        double amount;
        Scanner sc = new Scanner(System.in);

        System.out.println("Wlcome");
        System.out.println("from");
        System.out.println("1:USD (US DOLLAR) , 2:CAD (CANADIAN DOLLAR) , 3:EUR (EURO) , 4:HKD (HONG KONG DOLLAR) , 5:CNY (CHINESE RENMINBI)");
        fromCod = curencyCode.get(sc.nextInt());

        System.out.println("to");
        System.out.println("1:USD (US DOLLAR) , 2:EUR (EURO) , 3:JPY (JAPANESE YEN) , 4:GBP (Pound Sterling) , 5:CHF (SWISS FRANC)");
        toCode = curencyCode.get(sc.nextInt());

        System.out.println("Enter amount u wanna convert");
        amount = sc.nextDouble();

        sendHttpGetRequest(fromCod,toCode,amount);

        }
        private static void sendHttpGetRequest(String fromCod , String toCod , double amnt) throws IOException, InterruptedException, MalformedURLException {

            DecimalFormat df = new DecimalFormat("00.00");
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create("https://currency-converter5.p.rapidapi.com/currency/convert?format=json&from="+fromCod+"&to="+toCod+"&amount="+amnt))
//                    .header("X-RapidAPI-Key", "0dac843051msh14dec94879e9ec6p175449jsnb747789045a5")
//                    .header("X-RapidAPI-Host", "currency-converter5.p.rapidapi.com")
//                    .method("GET", HttpRequest.BodyPublishers.noBody())
//                    .build();
//            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//            System.out.println(response.body());

            String a = "https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_X8cRqEKWonMT11PC156GL1PdqfXQ49Q8VZy3YFBU&currencies="+toCod+"&base_currency="+fromCod;
            URL url = new URL(a);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            int rsp = httpURLConnection.getResponseCode();
            if(rsp == HttpURLConnection.HTTP_OK)
            {
                BufferedReader bf = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuffer rpnsee = new StringBuffer();

                while((inputLine = bf.readLine())!=null)
                {
                    rpnsee.append(inputLine);
                }bf.close();

                JSONObject obj = new JSONObject(rpnsee.toString());
                //System.out.println(obj);
                Double exchangeRate = obj.getJSONObject("data").getDouble(toCod);
                System.out.println(exchangeRate);
                System.out.println(obj.getJSONObject("data"));
                System.out.println();
                System.out.println(df.format(amnt) + fromCod+ " = "+ df.format((amnt*exchangeRate)) + " "+toCod );
            }
            else {
                System.out.println("SYSTEM FAILED");
            }
    }

}
