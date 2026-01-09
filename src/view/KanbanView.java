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

        // Barra superior
        add(criarBarraNavegacao(), BorderLayout.NORTH);

        cardLayout = new CardLayout();
        painelCards = new JPanel(cardLayout);

        painelCards.add(criarPaginaKanban(), "KANBAN");
        painelCards.add(criarPaginaOutra(), "OUTRA");

        add(painelCards, BorderLayout.CENTER);

        cardLayout.show(painelCards, "KANBAN");
    }

    // ===== Barra de navegação =====
    private JPanel criarBarraNavegacao() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        barra.setBackground(new Color(2, 106, 167));
        barra.setPreferredSize(new Dimension(900, 48));

        barra.add(criarBotaoBarra("Kanban", "KANBAN"));
        barra.add(criarBotaoBarra("Outra Pagina", "OUTRA"));

        return barra;
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

    // Atualiza todas as colunas e adiciona o botão "Adicionar Post-It"
    public void atualizarColunas(List<Coluna> colunas) {
        painelColunas.removeAll();

        for (int i = 0; i < colunas.size(); i++) {
            Coluna coluna = colunas.get(i);

            JPanel colunaPanel = new JPanel();
            colunaPanel.setLayout(new BoxLayout(colunaPanel, BoxLayout.Y_AXIS));
            colunaPanel.setBorder(BorderFactory.createTitledBorder(coluna.getNome()));

            // Adiciona todos os PostIts da coluna
            coluna.getTarefas().forEach(tarefa -> colunaPanel.add(new PostItPanel(tarefa)));

            // Botão "Adicionar Post-It" que dispara evento para o controller
            int colunaIndex = i; // precisa ser final ou effectively final para o listener
            JButton addPostItButton = new JButton("+ Adicionar Post-It");
            addPostItButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            addPostItButton.addActionListener(e -> {
                firePropertyChange("adicionarPostIt", null, colunaIndex);
            });

            colunaPanel.add(Box.createVerticalStrut(10));
            colunaPanel.add(addPostItButton);

            painelColunas.add(colunaPanel);
        }

        // Coluna "+ Adicionar Coluna"
        painelColunas.add(new AdicionarColunaPanel(() -> {
            String nome = JOptionPane.showInputDialog(
                this,
                "Nome da nova coluna:"
            );
            firePropertyChange("adicionarColuna", null, nome);
        }));

        painelColunas.revalidate();
        painelColunas.repaint();
    }

}
