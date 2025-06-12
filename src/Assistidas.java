import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
<<<<<<< HEAD
=======
import java.util.stream.Collectors;
>>>>>>> 1840485 (API tv maze interface em Jframe)

public class Assistidas implements IGerenciadorDeSeries {

    private List<Series> lista;

    public Assistidas(List<Series> listaExistente) {
        this.lista = listaExistente;
    }

    @Override
    public void adicionar(Series serie) {
        if (!lista.contains(serie)) {
            lista.add(serie);
<<<<<<< HEAD
            System.out.println("Série adicionada às assistidas: " + serie.getName());
        } else {
            System.out.println("Série já está nas assistidas.");
=======
>>>>>>> 1840485 (API tv maze interface em Jframe)
        }
    }

    @Override
    public void remover(int idSerie) {
<<<<<<< HEAD
        boolean removed = lista.removeIf(serie -> serie.getId() == idSerie);
        if (removed) {
            System.out.println("Série removida das assistidas (ID: " + idSerie + ")");
        } else {
            System.out.println("Série com ID " + idSerie + " não encontrada nas assistidas.");
        }
=======
        lista.removeIf(serie -> serie.getId() == idSerie);
>>>>>>> 1840485 (API tv maze interface em Jframe)
    }

    @Override
    public void listar() {
<<<<<<< HEAD
        listarOrdenado(0);
    }

    public void listarOrdenado(int criterio) {
=======
    }

    public String listarOrdenadoParaGUI(int criterio) {
>>>>>>> 1840485 (API tv maze interface em Jframe)
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

<<<<<<< HEAD
        System.out.println("\n--- Séries Assistidas (Ordenadas) ---");
        if (ordenada.isEmpty()) {
            System.out.println("Nenhuma série nas assistidas.");
            return;
        }
        for (Series serie : ordenada) {
            System.out.println("Id: " + serie.getId() +
                    "\n Nome: " + serie.getName() +
                    "\n Idioma: " + serie.getLanguage() +
                    "\n Gêneros: " + (serie.getGenres() != null && !serie.getGenres().isEmpty() ? String.join(", ", serie.getGenres()) : "N/A") +
                    "\n Nota geral: " + (serie.getRating() != null && serie.getRating().getAverage() != null ? serie.getRating().getAverage() : "N/A") +
                    "\n Estado: " + (serie.getStatus() != null ? serie.getStatus() : "Desconhecido") +
                    "\n Data de estreia: " + (serie.getPremiered() != null ? serie.getPremiered() : "Desconhecida") +
                    " | Data de término: " + (serie.getEnded() != null ? serie.getEnded() : "Desconhecida") +
                    "\n Nome da emissora: " + (serie.getNetwork() != null ? serie.getNetwork().getName() : "Desconhecida") +
                    "\n-------------------------------------");
        }
    }

=======
        if (ordenada.isEmpty()) {
            return "Nenhuma série nas assistidas.";
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


>>>>>>> 1840485 (API tv maze interface em Jframe)
    @Override
    public List<Series> getLista() {
        return lista;
    }
}