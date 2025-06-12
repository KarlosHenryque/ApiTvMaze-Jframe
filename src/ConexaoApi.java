import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConexaoApi {

    private HttpClient client;

    public ConexaoApi() {
        this.client = HttpClient.newHttpClient();
    }

    public String buscarSeries(String nomeSerie) {
        String urlCompleta = "https://api.tvmaze.com/search/shows?q=" + nomeSerie.replace(" ", "%20");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlCompleta))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
<<<<<<< HEAD
                System.out.println("Erro na requisição. Código: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
=======
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
>>>>>>> 1840485 (API tv maze interface em Jframe)
    }
}