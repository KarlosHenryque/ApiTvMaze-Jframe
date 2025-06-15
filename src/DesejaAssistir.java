import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DesejaAssistir implements IGerenciadorDeSeries {

    private List<Series> lista;

    public DesejaAssistir(List<Series> listaExistente) {
        this.lista = listaExistente;
    }

    @Override
    public void adicionar(Series serie) {
        if (!lista.contains(serie)) {
            lista.add(serie);
        }
    }

    @Override
    public void remover(int idSerie) {
        lista.removeIf(serie -> serie.getId() == idSerie);
    }

    @Override
    public void listar() {
    }

    public String listarOrdenadoParaGUI(int criterio) {
        List<Series> ordenada = new ArrayList<>(lista);

        switch (criterio) {
            case 1:
                ordenada.sort(Comparator.comparing(Series::getName));
                break;
            case 2:
                ordenada.sort(Comparator.comparing((Series s) -> {
                    Series.Rating rating = s.getRating();
                    return (rating != null && rating.getAverage() != null) ? rating.getAverage() : 0.0;
                }).reversed());
                break;
            case 3:
                ordenada.sort(Comparator.comparing(Series::getStatus, Comparator.nullsLast(String::compareTo)));
                break;
            case 4:
                ordenada.sort(Comparator.comparing(Series::getPremiered, Comparator.nullsLast(String::compareTo)));
                break;
            default:
                break;
        }

        if (ordenada.isEmpty()) {
            return "Nenhuma série na lista de desejos.";
        }

        return ordenada.stream()
                .map(serie -> "Id: " + serie.getId() +
                        " | Nome: " + serie.getName() +
                        " | Idioma: " + serie.getLanguage() +
                        " | Gêneros: " + (serie.getGenres() != null && !serie.getGenres().isEmpty() ? String.join(", ", serie.getGenres()) : "N/A") +
                        " | Nota: " + (serie.getRating() != null && serie.getRating().getAverage() != null ? serie.getRating().getAverage() : "N/A") +
                        " | Status: " + (serie.getStatus() != null ? serie.getStatus() : "Desconhecido") +
                        " | Estreia: " + (serie.getPremiered() != null ? serie.getPremiered() : "Desconhecida") +
                        " | Emissora: " + (serie.getNetwork() != null ? serie.getNetwork().getName() : "Desconhecida"))
                .collect(Collectors.joining("\n"));
    }

    @Override
    public List<Series> getLista() {
        return lista;
    }
}