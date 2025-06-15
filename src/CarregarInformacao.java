import java.util.List;

public class CarregarInformacao {

    public boolean adicionarSerie(List<Series> busca, IGerenciadorDeSeries lista, int idAdicionar) {
        if (busca != null) {
            for (Series serie : busca) {
                if (serie.getId() == idAdicionar) {
                    if (!lista.getLista().contains(serie)) {
                        lista.adicionar(serie);
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public boolean removerSerie(IGerenciadorDeSeries lista, int idRemover) {
        boolean removed = lista.getLista().removeIf(serie -> serie.getId() == idRemover);
        return removed;
    }
}