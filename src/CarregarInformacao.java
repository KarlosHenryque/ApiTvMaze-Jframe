<<<<<<< HEAD
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CarregarInformacao {

    public void adicionarSerie(Scanner scanner, List<Series> busca, IGerenciadorDeSeries lista, String tipo) {
        if (busca != null && !busca.isEmpty()) {
            System.out.print("Digite o ID da série que deseja adicionar aos " + tipo + ": ");
            try {
                int idAdicionar = scanner.nextInt();
                scanner.nextLine();

                boolean adicionada = false;
                for (Series serie : busca) {
                    if (serie.getId() == idAdicionar) {
                        lista.adicionar(serie);
                        adicionada = true;
                        break;
                    }
                }

                if (!adicionada) {
                    System.out.println("Série com ID " + idAdicionar + " não encontrada na última busca.");
                }
            } catch (InputMismatchException e) {
                System.out.println("ID inválido. Por favor, insira um número.");
                scanner.nextLine();
            }
        } else {
            System.out.println("Nenhuma série disponível para adicionar. Realize uma busca primeiro.");
        }
    }

    public void removerSerie(Scanner scanner, IGerenciadorDeSeries lista, String tipo) {
        System.out.print("Digite o ID da série para remover dos " + tipo + ": ");
        try {
            int idRemover = scanner.nextInt();
            scanner.nextLine();
            lista.remover(idRemover);
        } catch (InputMismatchException e) {
            System.out.println("ID inválido. Por favor, insira um número.");
            scanner.nextLine();
        }
=======
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
>>>>>>> 1840485 (API tv maze interface em Jframe)
    }
}