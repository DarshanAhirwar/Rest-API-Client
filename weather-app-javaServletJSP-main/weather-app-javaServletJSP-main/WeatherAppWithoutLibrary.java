import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class WeatherAppWithoutLibrary {
    public static void main(String[] args) {
        String city = "Delhi";
        String apiKey = "664d187e15404d6285c70709252502"; 

        try {
           
            String encodedCity = URLEncoder.encode(city, "UTF-8");
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + encodedCity + "&appid=" + apiKey + "&units=metric&lang=en";

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 401) {
                System.out.println("❌ ERROR 401: Unauthorized! API Key सही नहीं है।");
                return;
            } else if (responseCode != 200) {
                System.out.println("❌ ERROR " + responseCode + ": API Call Failed!");
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            System.out.println("🌤 Weather Data: " + response.toString());
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
