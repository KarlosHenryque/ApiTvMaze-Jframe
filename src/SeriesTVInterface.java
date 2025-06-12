import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Comparator;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class SeriesTVInterface extends JFrame {
    private JTextField buscarSerieField;
    private JTextField idSerieField;
    private JTextField idRemoverField;
    private JComboBox<String> listaAdicionarCombo;
    private JComboBox<String> listaRemoverCombo;
    private JComboBox<String> listaListarCombo;
    private JComboBox<String> criterioOrdenacaoCombo;
    private JTextArea resultadosArea;
    private JScrollPane scrollPaneResultados;
    private JPanel catalogPanel;
    private JScrollPane scrollPaneCatalog;
    private JButton clearResultsButton;

    private BuscarSerie buscarSerie;
    private Usuario usuario;
    private CarregarInformacao carregarInformacao;
    private GerenciadorDados gerenciadorDados;
    private DadosApp dados;

    private Favoritos favoritos;
    private Assistidas assistidas;
    private DesejaAssistir desejaAssistir;

    private List<Series> ultimaBusca;

    private String nomeUsuario;

    public SeriesTVInterface() {
        super("Sistema de Séries TV");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1000, 750);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salvarEEncerrar();
            }
        });

        if (mostrarBoasVindas()) {
            inicializaComponentes();
            inicializaSistema();
            setVisible(true);
        } else {
            System.exit(0);
        }
    }

    private boolean mostrarBoasVindas() {
        gerenciadorDados = new GerenciadorDados();
        dados = gerenciadorDados.carregarDados();

        nomeUsuario = JOptionPane.showInputDialog(this,
                "Bem-vindo ao sistema de busca de séries TV!\nPor favor, informe seu nome:",
                "Bem-vindo",
                JOptionPane.QUESTION_MESSAGE);

        if (nomeUsuario == null || nomeUsuario.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nome não fornecido. Encerrando o sistema.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        usuario = new Usuario();
        usuario.setNome(nomeUsuario);

        if (!dados.getNomesUsuarios().contains(nomeUsuario)) {
            dados.getNomesUsuarios().add(nomeUsuario);
        }

        JOptionPane.showMessageDialog(this,
                "Seja bem-vindo ao sistema, " + usuario.getNome() + "!",
                "Boas-vindas",
                JOptionPane.INFORMATION_MESSAGE);

        return true;
    }

    private void inicializaSistema() {
        buscarSerie = new BuscarSerie();
        carregarInformacao = new CarregarInformacao();

        favoritos = new Favoritos(dados.getFavoritos());
        assistidas = new Assistidas(dados.getAssistidas());
        desejaAssistir = new DesejaAssistir(dados.getDesejaAssistir());
    }

    private void inicializaComponentes() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.25);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Buscar Série"));
        GridBagConstraints gbcSearch = new GridBagConstraints();
        gbcSearch.insets = new Insets(5, 5, 5, 5);
        gbcSearch.fill = GridBagConstraints.HORIZONTAL;
        gbcSearch.gridx = 0; gbcSearch.gridy = 0; gbcSearch.weightx = 1.0;
        buscarSerieField = new JTextField("Nome da Série", 15);
        buscarSerieField.setForeground(Color.GRAY);
        buscarSerieField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (buscarSerieField.getText().equals("Nome da Série")) {
                    buscarSerieField.setText("");
                    buscarSerieField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (buscarSerieField.getText().isEmpty()) {
                    buscarSerieField.setText("Nome da Série");
                    buscarSerieField.setForeground(Color.GRAY);
                }
            }
        });
        searchPanel.add(buscarSerieField, gbcSearch);
        gbcSearch.gridx = 0; gbcSearch.gridy = 1; gbcSearch.gridwidth = 1;
        JButton buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(e -> buscarSerie());
        searchPanel.add(buscarButton, gbcSearch);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1; gbc.weightx = 1.0; leftPanel.add(searchPanel, gbc);


        // Panel for Add Series
        JPanel addPanel = new JPanel(new GridBagLayout());
        addPanel.setBorder(BorderFactory.createTitledBorder("Adicionar Série"));
        GridBagConstraints gbcAdd = new GridBagConstraints();
        gbcAdd.insets = new Insets(5, 5, 5, 5);
        gbcAdd.fill = GridBagConstraints.HORIZONTAL;

        gbcAdd.gridx = 0; gbcAdd.gridy = 0; gbcAdd.weightx = 1.0;
        idSerieField = new JTextField("ID da Série", 10);
        idSerieField.setForeground(Color.GRAY);
        idSerieField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (idSerieField.getText().equals("ID da Série")) {
                    idSerieField.setText("");
                    idSerieField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (idSerieField.getText().isEmpty()) {
                    idSerieField.setText("ID da Série");
                    idSerieField.setForeground(Color.GRAY);
                }
            }
        });
        addPanel.add(idSerieField, gbcAdd);
        gbcAdd.gridx = 0; gbcAdd.gridy = 1;
        listaAdicionarCombo = new JComboBox<>(new String[]{"Favoritos", "Assistidas", "Deseja Assistir"});
        addPanel.add(listaAdicionarCombo, gbcAdd);
        gbcAdd.gridx = 0; gbcAdd.gridy = 2;
        JButton adicionarButton = new JButton("Adicionar");
        adicionarButton.addActionListener(e -> adicionarSerie());
        addPanel.add(adicionarButton, gbcAdd);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; leftPanel.add(addPanel, gbc);


        JPanel removePanel = new JPanel(new GridBagLayout());
        removePanel.setBorder(BorderFactory.createTitledBorder("Remover Série"));
        GridBagConstraints gbcRemove = new GridBagConstraints();
        gbcRemove.insets = new Insets(5, 5, 5, 5);
        gbcRemove.fill = GridBagConstraints.HORIZONTAL;

        gbcRemove.gridx = 0; gbcRemove.gridy = 0; gbcRemove.weightx = 1.0;
        idRemoverField = new JTextField("ID da Série", 10);
        idRemoverField.setForeground(Color.GRAY);
        idRemoverField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (idRemoverField.getText().equals("ID da Série")) {
                    idRemoverField.setText("");
                    idRemoverField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (idRemoverField.getText().isEmpty()) {
                    idRemoverField.setText("ID da Série");
                    idRemoverField.setForeground(Color.GRAY);
                }
            }
        });
        removePanel.add(idRemoverField, gbcRemove);
        gbcRemove.gridx = 0; gbcRemove.gridy = 1;
        listaRemoverCombo = new JComboBox<>(new String[]{"Favoritos", "Assistidas", "Deseja Assistir"});
        removePanel.add(listaRemoverCombo, gbcRemove);
        gbcRemove.gridx = 0; gbcRemove.gridy = 2;
        JButton removerButton = new JButton("Remover");
        removerButton.addActionListener(e -> removerSerie());
        removePanel.add(removerButton, gbcRemove);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; leftPanel.add(removePanel, gbc);


        JPanel listPanel = new JPanel(new GridBagLayout());
        listPanel.setBorder(BorderFactory.createTitledBorder("Listar Séries"));
        GridBagConstraints gbcList = new GridBagConstraints();
        gbcList.insets = new Insets(5, 5, 5, 5);
        gbcList.fill = GridBagConstraints.HORIZONTAL;

        gbcList.gridx = 0; gbcList.gridy = 0; gbcList.weightx = 1.0;
        listaListarCombo = new JComboBox<>(new String[]{"Favoritos", "Assistidas", "Deseja Assistir"});
        listPanel.add(listaListarCombo, gbcList);
        gbcList.gridx = 0; gbcList.gridy = 1;
        criterioOrdenacaoCombo = new JComboBox<>(new String[]{"Sem Ordenação", "Nome (A-Z)", "Nota Geral (Maior para Menor)", "Status", "Data de Estreia"});
        listPanel.add(criterioOrdenacaoCombo, gbcList);
        gbcList.gridx = 0; gbcList.gridy = 2;
        JButton listarButton = new JButton("Listar");
        listarButton.addActionListener(e -> listarSeries());
        listPanel.add(listarButton, gbcList);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1; leftPanel.add(listPanel, gbc);


        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1; gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        JButton exitButton = new JButton("Sair do Sistema");
        exitButton.addActionListener(e -> salvarEEncerrar());
        leftPanel.add(exitButton, gbc);


        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));

        catalogPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        catalogPanel.setBackground(new Color(64, 64, 64));
        scrollPaneCatalog = new JScrollPane(catalogPanel);
        scrollPaneCatalog.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Catálogo de Séries", TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLUE));
        scrollPaneCatalog.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneCatalog.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rightPanel.add(scrollPaneCatalog, BorderLayout.CENTER);

        resultadosArea = new JTextArea(8, 50);
        resultadosArea.setEditable(false);
        resultadosArea.setLineWrap(true);
        resultadosArea.setWrapStyleWord(true);
        resultadosArea.setBackground(new Color(240, 240, 240));
        scrollPaneResultados = new JScrollPane(resultadosArea);

        JPanel resultsAndClearPanel = new JPanel(new BorderLayout());
        resultsAndClearPanel.add(scrollPaneResultados, BorderLayout.CENTER);

        clearResultsButton = new JButton("Limpar");
        try {
            Image trashIcon = ImageIO.read(getClass().getResource("/lixeira_icon.png"));
            if (trashIcon != null) {
                clearResultsButton.setIcon(new ImageIcon(trashIcon.getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
                clearResultsButton.setText("");
            }
        } catch (Exception e) {
            System.err.println("Could not load lixeira_icon.png, using text button: " + e.getMessage());
        }
        clearResultsButton.setToolTipText("Limpar campo de resultados e catálogo");
        clearResultsButton.addActionListener(e -> {
            resultadosArea.setText("");
            catalogPanel.removeAll();
            catalogPanel.revalidate();
            catalogPanel.repaint();
        });
        JPanel clearButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        clearButtonPanel.add(clearResultsButton);
        resultsAndClearPanel.add(clearButtonPanel, BorderLayout.SOUTH);

        rightPanel.add(resultsAndClearPanel, BorderLayout.SOUTH);

        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);

        add(splitPane, BorderLayout.CENTER);
    }

    private void buscarSerie() {
        String nomeSerie = buscarSerieField.getText().trim();
        if (nomeSerie.isEmpty() || nomeSerie.equals("Nome da Série")) {
            JOptionPane.showMessageDialog(this, "Por favor, digite o nome da série.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        resultadosArea.setText("Buscando séries para: " + nomeSerie + "...\n");
        catalogPanel.removeAll();
        catalogPanel.revalidate();
        catalogPanel.repaint();

        new SwingWorker<List<Series>, Void>() {
            @Override
            protected List<Series> doInBackground() {
                return buscarSerie.buscarSeries(nomeSerie);
            }

            @Override
            protected void done() {
                try {
                    ultimaBusca = get();
                    if (ultimaBusca != null && !ultimaBusca.isEmpty()) {
                        resultadosArea.append("Busca concluída. " + ultimaBusca.size() + " séries encontradas.\n");
                        displaySeriesCatalog(ultimaBusca);
                    } else {
                        resultadosArea.append("Nenhuma série encontrada para: " + nomeSerie + "\n");
                    }
                } catch (Exception e) {
                    resultadosArea.append("Erro ao buscar séries: " + e.getMessage() + "\n");
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(SeriesTVInterface.this, "Erro ao buscar séries. Verifique a conexão com a internet ou o nome da série.", "Erro de Conexão/Busca", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    private void displaySeriesCatalog(List<Series> seriesList) {
        catalogPanel.removeAll();

        if (seriesList.isEmpty()) {
            catalogPanel.add(new JLabel("Nenhuma série para exibir."));
            return;
        }

        for (Series serie : seriesList) {
            JPanel card = createSeriesCard(serie);
            catalogPanel.add(card);
        }

        catalogPanel.revalidate();
        catalogPanel.repaint();
        scrollPaneCatalog.getVerticalScrollBar().setValue(0);
    }

    private JPanel createSeriesCard(Series serie) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(5, 5));
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true)); // Rounded border
        card.setBackground(Color.LIGHT_GRAY); // Card background
        card.setPreferredSize(new Dimension(200, 350));

        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(180, 200));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);

        new SwingWorker<ImageIcon, Void>() {
            @Override
            protected ImageIcon doInBackground() throws Exception {
                String imageUrl = null;
                if (serie.getImage() != null) {
                    if (serie.getImage().getMedium() != null) {
                        imageUrl = serie.getImage().getMedium();
                    } else if (serie.getImage().getOriginal() != null) {
                        imageUrl = serie.getImage().getOriginal();
                    }
                }

                BufferedImage img = null;
                if (imageUrl != null) {
                    try {
                        img = ImageIO.read(new URL(imageUrl));
                    } catch (MalformedURLException e) {
                        System.err.println("URL malformada para a série " + serie.getName() + ": " + imageUrl);
                    } catch (IOException e) {
                        System.err.println("Erro ao carregar imagem para a série " + serie.getName() + " de " + imageUrl + ": " + e.getMessage());
                    }
                }

                if (img == null) {
                    URL placeholderUrl = getClass().getResource("/no_image_placeholder.png");
                    if (placeholderUrl != null) {
                        img = ImageIO.read(placeholderUrl);
                    } else {
                        img = new BufferedImage(imageLabel.getPreferredSize().width, imageLabel.getPreferredSize().height, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g = img.createGraphics();
                        g.setColor(Color.WHITE);
                        g.fillRect(0, 0, img.getWidth(), img.getHeight());
                        g.setColor(Color.BLACK);
                        g.drawString("No Image", 5, img.getHeight() / 2);
                        g.dispose();
                    }
                }

                if (img != null) {
                    Image scaledImg = img.getScaledInstance(imageLabel.getPreferredSize().width, imageLabel.getPreferredSize().height, Image.SCALE_SMOOTH);
                    return new ImageIcon(scaledImg);
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    ImageIcon icon = get();
                    if (icon != null) {
                        imageLabel.setIcon(icon);
                    } else {
                        imageLabel.setText("No Image");
                        imageLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
                        imageLabel.setForeground(Color.RED);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();

        card.add(imageLabel, BorderLayout.CENTER);

        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setBackground(card.getBackground());
        infoArea.setFont(new Font("SansSerif", Font.PLAIN, 9));

        String genres = (serie.getGenres() != null && !serie.getGenres().isEmpty()) ? String.join(", ", serie.getGenres()) : "N/A";
        String rating = (serie.getRating() != null && serie.getRating().getAverage() != null) ? String.format("%.1f", serie.getRating().getAverage()) : "N/A";
        String status = (serie.getStatus() != null) ? serie.getStatus() : "N/A";
        String premiered = (serie.getPremiered() != null) ? serie.getPremiered() : "Desconhecida";
        String ended = (serie.getEnded() != null) ? serie.getEnded() : "Desconhecida";
        String networkName = (serie.getNetwork() != null && serie.getNetwork().getName() != null) ? serie.getNetwork().getName() : "Desconhecida";

        String info = String.format(
                "ID: %d\n" +
                        "Nome: %s\n" +
                        "Idioma: %s\n" +
                        "Gêneros: %s\n" +
                        "Nota: %s\n" +
                        "Status: %s\n" +
                        "Estreia: %s\n" +
                        "Término: %s\n" +
                        "Emissora: %s",
                serie.getId(),
                serie.getName(),
                (serie.getLanguage() != null ? serie.getLanguage() : "N/A"),
                genres,
                rating,
                status,
                premiered,
                ended,
                networkName
        );
        infoArea.setText(info);

        card.add(infoArea, BorderLayout.SOUTH);

        return card;
    }


    private void adicionarSerie() {
        int idAdicionar;
        try {
            idAdicionar = Integer.parseInt(idSerieField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido. Por favor, insira um número.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (ultimaBusca == null || ultimaBusca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma série na busca recente para adicionar. Realize uma busca primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String tipoLista = (String) listaAdicionarCombo.getSelectedItem();
        boolean success = false;
        String serieName = "Série com ID " + idAdicionar;

        Series foundSerieInSearch = ultimaBusca.stream()
                .filter(s -> s.getId() == idAdicionar)
                .findFirst()
                .orElse(null);

        if (foundSerieInSearch == null) {
            JOptionPane.showMessageDialog(this, "Série com ID " + idAdicionar + " não encontrada na última busca.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        serieName = foundSerieInSearch.getName();

        boolean alreadyExists = false;
        switch (tipoLista) {
            case "Favoritos":
                alreadyExists = favoritos.getLista().contains(foundSerieInSearch);
                if (!alreadyExists) success = carregarInformacao.adicionarSerie(ultimaBusca, favoritos, idAdicionar);
                break;
            case "Assistidas":
                alreadyExists = assistidas.getLista().contains(foundSerieInSearch);
                if (!alreadyExists) success = carregarInformacao.adicionarSerie(ultimaBusca, assistidas, idAdicionar);
                break;
            case "Deseja Assistir":
                alreadyExists = desejaAssistir.getLista().contains(foundSerieInSearch);
                if (!alreadyExists) success = carregarInformacao.adicionarSerie(ultimaBusca, desejaAssistir, idAdicionar);
                break;
        }

        if (alreadyExists) {
            JOptionPane.showMessageDialog(this, "Série '" + serieName + "' já está nos " + tipoLista + ".", "Aviso", JOptionPane.WARNING_MESSAGE);
        } else if (success) {
            JOptionPane.showMessageDialog(this, "Série '" + serieName + "' adicionada aos " + tipoLista + " com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Erro desconhecido ao adicionar série '" + serieName + "'.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void removerSerie() {
        int idRemover;
        try {
            idRemover = Integer.parseInt(idRemoverField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido. Por favor, insira um número.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String tipoLista = (String) listaRemoverCombo.getSelectedItem();
        boolean success = false;
        String serieName = "Série não encontrada";

        List<Series> currentList = null;
        switch (tipoLista) {
            case "Favoritos":
                currentList = favoritos.getLista();
                break;
            case "Assistidas":
                currentList = assistidas.getLista();
                break;
            case "Deseja Assistir":
                currentList = desejaAssistir.getLista();
                break;
        }

        if (currentList != null) {
            Series serieToRemove = currentList.stream()
                    .filter(s -> s.getId() == idRemover)
                    .findFirst()
                    .orElse(null);
            if (serieToRemove != null) {
                serieName = serieToRemove.getName();
            }
        }

        switch (tipoLista) {
            case "Favoritos":
                success = carregarInformacao.removerSerie(favoritos, idRemover);
                break;
            case "Assistidas":
                success = carregarInformacao.removerSerie(assistidas, idRemover);
                break;
            case "Deseja Assistir":
                success = carregarInformacao.removerSerie(desejaAssistir, idRemover);
                break;
        }

        if (success) {
            JOptionPane.showMessageDialog(this, "Série '" + serieName + "' removida dos " + tipoLista + " com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            if (tipoLista.equals(listaListarCombo.getSelectedItem())) {
                listarSeries();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Série com ID " + idRemover + " não encontrada nos " + tipoLista + ".", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void listarSeries() {
        String tipoLista = (String) listaListarCombo.getSelectedItem();
        int criterioOrdenacao = criterioOrdenacaoCombo.getSelectedIndex();

        String listOutput = "";
        List<Series> seriesToList = null;

        List<Series> currentSeries = null;
        switch (tipoLista) {
            case "Favoritos":
                currentSeries = new java.util.ArrayList<>(favoritos.getLista());
                break;
            case "Assistidas":
                currentSeries = new java.util.ArrayList<>(assistidas.getLista());
                break;
            case "Deseja Assistir":
                currentSeries = new java.util.ArrayList<>(desejaAssistir.getLista());
                break;
        }

        if (currentSeries == null) {
            resultadosArea.setText("Nenhuma lista selecionada ou lista vazia.\n");
            displaySeriesCatalog(new java.util.ArrayList<>()); // Clear catalog
            return;
        }

        switch (criterioOrdenacao) {
            case 1:
                currentSeries.sort(Comparator.comparing(Series::getName, Comparator.nullsLast(String::compareToIgnoreCase)));
                break;
            case 2:
                currentSeries.sort(Comparator.comparing((Series s) -> {
                    Series.Rating rating = s.getRating();
                    return (rating != null && rating.getAverage() != null) ? rating.getAverage() : -1.0;
                }, Comparator.reverseOrder()));
                break;
            case 3:
                currentSeries.sort(Comparator.comparing(Series::getStatus, Comparator.nullsLast(String::compareToIgnoreCase)));
                break;
            case 4:
                currentSeries.sort(Comparator.comparing(Series::getPremiered, Comparator.nullsLast(String::compareTo)));
                break;
            default:
                break;
        }

        StringBuilder sb = new StringBuilder();
        if (currentSeries.isEmpty()) {
            sb.append("A lista de ").append(tipoLista).append(" está vazia.\n");
        } else {
            for (Series serie : currentSeries) {
                sb.append("ID: ").append(serie.getId())
                        .append(" | Nome: ").append(serie.getName())
                        .append(" | Nota: ").append((serie.getRating() != null && serie.getRating().getAverage() != null ? String.format("%.1f", serie.getRating().getAverage()) : "N/A"))
                        .append(" | Status: ").append((serie.getStatus() != null ? serie.getStatus() : "N/A"))
                        .append("\n");
            }
        }
        resultadosArea.setText("--- " + tipoLista + " (Ordenado por " + criterioOrdenacaoCombo.getSelectedItem() + ") ---\n" + sb.toString() + "\n");

        displaySeriesCatalog(currentSeries);
    }

    private void salvarEEncerrar() {
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja salvar os dados antes de sair?", "Confirmar Saída", JOptionPane.YES_NO_CANCEL_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean saved = gerenciadorDados.salvarDados(dados);
            if (saved) {
                JOptionPane.showMessageDialog(this, "Dados salvos com sucesso!\nObrigado por usar o Sistema de Séries TV, " + nomeUsuario + "!", "Sistema Encerrado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar dados. Saindo sem salvar.", "Erro ao Salvar", JOptionPane.ERROR_MESSAGE);
            }
            System.exit(0);
        } else if (confirm == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(this, "Saindo sem salvar.\nObrigado por usar o Sistema de Séries TV, " + nomeUsuario + "!", "Sistema Encerrado", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
}