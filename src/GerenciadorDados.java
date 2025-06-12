import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
<<<<<<< HEAD
import java.util.ArrayList;
=======

>>>>>>> 1840485 (API tv maze interface em Jframe)

public class GerenciadorDados {
    private static final String NOME_ARQUIVO = "dados.json";
    private Gson gson;

    public GerenciadorDados() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public DadosApp carregarDados() {
        try (Reader reader = new FileReader(NOME_ARQUIVO)) {
            DadosApp dados = gson.fromJson(reader, DadosApp.class);
            if (dados == null) {
<<<<<<< HEAD
                System.out.println("Arquivo " + NOME_ARQUIVO + " está vazio ou corrompido, criando novos dados.");
                return new DadosApp();
            }
            System.out.println("Dados carregados com sucesso de " + NOME_ARQUIVO);
            return dados;
        } catch (IOException e) {
            System.out.println("Arquivo " + NOME_ARQUIVO + " não encontrado, criando novo arquivo.");
=======
                return new DadosApp();
            }
            return dados;
        } catch (IOException e) {
>>>>>>> 1840485 (API tv maze interface em Jframe)
            return new DadosApp();
        }
    }

    public boolean salvarDados(DadosApp dados) {
        try (Writer writer = new FileWriter(NOME_ARQUIVO)) {
            gson.toJson(dados, writer);
<<<<<<< HEAD
            System.out.println("Dados salvos com sucesso em " + NOME_ARQUIVO);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao salvar dados em " + NOME_ARQUIVO);
=======
            return true;
        } catch (IOException e) {
            e.printStackTrace(); // Keep for debugging
>>>>>>> 1840485 (API tv maze interface em Jframe)
            return false;
        }
    }
}