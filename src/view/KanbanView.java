package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Coluna;

public class KanbanView extends JFrame {

    private JPanel painelColunas;
    private JPanel painelCards;
    private CardLayout cardLayout;

    public KanbanView() {
        setTitle("Kanban");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(criarBarraNavegacao(), BorderLayout.NORTH);

        cardLayout = new CardLayout();
        painelCards = new JPanel(cardLayout);

        painelCards.add(criarPaginaKanban(), "KANBAN");
        painelCards.add(criarPaginaOutra(), "OUTRA");

        add(painelCards, BorderLayout.CENTER);

        cardLayout.show(painelCards, "KANBAN");
    }

    private JPanel criarBarraNavegacao() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        barra.setBackground(new Color(2, 106, 167));
        barra.setPreferredSize(new Dimension(900, 48));

        barra.add(criarBotaoBarra("Kanban", "KANBAN"));
        barra.add(criarBotaoBarra("Nova pagina", "OUTRA"));

        return barra;
    }
    // Ai edu, isso aqui pra mostrar com a prioridade mais alta em cima, OTIMA IDEIA
    private int prioridadePeso(String prioridade) {
        if (prioridade == null) return 0;

            switch (prioridade.toLowerCase()) {
                case "alta":
                    return 3;
                case "media":
                    return 2;
                case "baixa":
                    return 1;
                default:
                    return 0;
            }
    }


    private JButton criarBotaoBarra(String texto, String card) {
        JButton botao = new JButton(texto);

        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setContentAreaFilled(false);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        botao.addActionListener(e ->
                cardLayout.show(painelCards, card)
        );

        return botao;
    }

    private JPanel criarPaginaKanban() {
        painelColunas = new JPanel(new FlowLayout(
                FlowLayout.CENTER,
                15,
                10
        ));

        painelColunas.setBackground(new Color(230, 230, 230));
        painelColunas.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        return painelColunas;
    }

    private JPanel criarPaginaOutra() {
        JPanel painel = new JPanel();
        painel.add(new JLabel("Outra pagina (em construcao)"));
        return painel;
    }

    public void atualizarColunas(List<Coluna> colunas) {
        painelColunas.removeAll();

        for (int i = 0; i < colunas.size(); i++) {
            Coluna coluna = colunas.get(i);
            int colunaIndex = i;

            JPanel colunaPanel = new JPanel(new BorderLayout());
            colunaPanel.setPreferredSize(new Dimension(260, 400));
            colunaPanel.setBackground(Color.WHITE);

            JPanel header = new JPanel(new BorderLayout());
            header.setPreferredSize(new Dimension(260, 40));
            header.setBackground(coluna.getCor());
            header.setOpaque(true);

            JLabel titulo = new JLabel(coluna.getNome().toUpperCase());
            titulo.setForeground(Color.WHITE);
            titulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
            titulo.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

            JButton btnMenu = new JButton("...");
            btnMenu.setForeground(Color.WHITE);
            btnMenu.setFont(new Font("Segoe UI", Font.BOLD, 16));
            btnMenu.setBorderPainted(false);
            btnMenu.setContentAreaFilled(false);
            btnMenu.setFocusPainted(false);
            btnMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JPopupMenu menu = new JPopupMenu();
            JMenuItem editar = new JMenuItem("Editar");
            JMenuItem excluir = new JMenuItem("Excluir");
            menu.add(editar);
            menu.add(excluir);

            btnMenu.addActionListener(e ->
                    menu.show(btnMenu, 0, btnMenu.getHeight())
            );

            editar.addActionListener(e ->
                    firePropertyChange("editarColuna", null, colunaIndex)
            );

            excluir.addActionListener(e ->
                    firePropertyChange("excluirColuna", null, colunaIndex)
            );

            header.add(titulo, BorderLayout.WEST);
            header.add(btnMenu, BorderLayout.EAST);
            colunaPanel.add(header, BorderLayout.NORTH);

            JPanel conteudo = new JPanel();
            conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
            conteudo.setBackground(new Color(245, 245, 245));
            conteudo.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

            // Tem q mandar aqui pro controller pegar, dps dar uma olhada nisso, FAZER CONFIRMACAO VISUAL DPS, ESTA EXCLUINDO SEM PERGUNTAR
            coluna.getTarefas().stream()
                .sorted((t1, t2) ->
                    prioridadePeso(t2.getPrioridade()) - prioridadePeso(t1.getPrioridade())
                )
                .forEach(tarefa -> {
                    PostItPanel postIt = new PostItPanel(tarefa);

                    postIt.addPropertyChangeListener("excluirTarefa", evt ->
                        firePropertyChange("excluirTarefa", null, evt.getNewValue())
                    );

                    postIt.addPropertyChangeListener("moverTarefaProximo", evt ->
                        firePropertyChange("moverTarefaProximo", null, evt.getNewValue())
                    );

                    conteudo.add(postIt);
                });



            JScrollPane scroll = new JScrollPane(conteudo);
            scroll.setBorder(null);
            colunaPanel.add(scroll, BorderLayout.CENTER);

            JButton addPostItButton = new JButton("+ Adicionar Post-It");
            addPostItButton.addActionListener(e ->
                    firePropertyChange("adicionarPostIt", null, colunaIndex)
            );
            colunaPanel.add(addPostItButton, BorderLayout.SOUTH);

            painelColunas.add(colunaPanel);
        }

        painelColunas.add(new AdicionarColunaPanel(() -> {

            JTextField nomeField = new JTextField();
            JButton btnCor = new JButton("Escolher cor");

            final Color[] corSelecionada = { Color.GRAY };

            btnCor.addActionListener(e -> {
                Color cor = JColorChooser.showDialog(
                        this,
                        "Escolha a cor da coluna",
                        corSelecionada[0]
                );
                if (cor != null) {
                    corSelecionada[0] = cor;
                }
            });

            JPanel panel = new JPanel(new GridLayout(0, 1, 6, 6));
            panel.add(new JLabel("Nome da coluna:"));
            panel.add(nomeField);
            panel.add(btnCor);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    panel,
                    "Nova Coluna",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result != JOptionPane.OK_OPTION) {
                return;
            }

            firePropertyChange(
                    "adicionarColuna",
                    null,
                    new Object[]{ nomeField.getText(), corSelecionada[0] }
            );
        }));

        painelColunas.revalidate();
        painelColunas.repaint();
    }

    //Removi cores por coluna e coloquei na model, pq guardar valores padrão é tarefa da MODEL, dps pesquisar se realmente é assim q o MVC trabalha ou se volta pra VIEW,.
    // EDU E MILLER , +++++++++++++++++++++++++++++++++++++++++ CONFIRMAR ISSO ANTES DE ENVIAR +++++++++++++++++++++++++++++++++++++++++++++++++++++
}
