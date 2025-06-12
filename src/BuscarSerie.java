import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;

public class BuscarSerie {

    private ConexaoApi conexaoApi;
    private Gson gson;

    public BuscarSerie() {
        this.conexaoApi = new ConexaoApi();
        this.gson = new Gson();
    }

    public List<Series> buscarSeries(String nomeSerie) {
        String jsonResposta = conexaoApi.buscarSeries(nomeSerie);
        List<Series> seriesEncontradas = new ArrayList<>();

        if (jsonResposta == null) {
<<<<<<< HEAD
            System.out.println("Nenhuma resposta da API.");
=======
>>>>>>> 1840485 (API tv maze interface em Jframe)
            return seriesEncontradas;
        }

        try {
            JsonArray jsonArray = JsonParser.parseString(jsonResposta).getAsJsonArray();

            if (jsonArray.size() > 0) {
<<<<<<< HEAD
                System.out.println("Séries encontradas com o nome \"" + nomeSerie + "\":\n");

                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject showObject = jsonArray.get(i).getAsJsonObject().getAsJsonObject("show");
                    Series serie = gson.fromJson(showObject, Series.class);

                    System.out.println("Id: " + serie.getId());
                    System.out.println("Nome: " + serie.getName());
                    System.out.println("Idioma: " + serie.getLanguage());
                    System.out.println("Gêneros: " + (serie.getGenres() != null && !serie.getGenres().isEmpty() ? String.join(", ", serie.getGenres()) : "N/A"));
                    System.out.println("Status: " + (serie.getStatus() != null ? serie.getStatus() : "Desconhecido"));
                    System.out.println("Estreia: " + (serie.getPremiered() != null ? serie.getPremiered() : "Desconhecida"));
                    System.out.println("Final: " + (serie.getEnded() != null ? serie.getEnded() : "Desconhecida"));
                    System.out.println("Nota média: " + (serie.getRating() != null && serie.getRating().getAverage() != null ? serie.getRating().getAverage() : "N/A"));
                    System.out.println("Rede: " + (serie.getNetwork() != null ? serie.getNetwork().getName() : "N/A"));
                    System.out.println("--------------------------------------");

                    seriesEncontradas.add(serie);
                }
            } else {
                System.out.println("Nenhuma série encontrada para: " + nomeSerie);
            }
        } catch (Exception e) {
            System.out.println("Erro ao processar JSON da API.");
=======
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject showObject = jsonArray.get(i).getAsJsonObject().getAsJsonObject("show");
                    Series serie = gson.fromJson(showObject, Series.class);
                    seriesEncontradas.add(serie);
                }
            }
        } catch (Exception e) {
>>>>>>> 1840485 (API tv maze interface em Jframe)
            e.printStackTrace();
        }

        return seriesEncontradas;
    }
}