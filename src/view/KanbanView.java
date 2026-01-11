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

    // ===== Atualiza as colunas =====
    public void atualizarColunas(List<Coluna> colunas) {
        painelColunas.removeAll();

        for (int i = 0; i < colunas.size(); i++) {
            Coluna coluna = colunas.get(i);
            int colunaIndex = i;

            JPanel colunaPanel = new JPanel(new BorderLayout());
            colunaPanel.setPreferredSize(new Dimension(260, 400));
            colunaPanel.setBackground(Color.WHITE);

            // ===== HEADER =====
            JPanel header = new JPanel(new BorderLayout());
            header.setPreferredSize(new Dimension(260, 40));
            header.setBackground(corPorColuna(coluna.getNome()));
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

            // ===== CONTEÚDO =====
            JPanel conteudo = new JPanel();
            conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
            conteudo.setBackground(new Color(245, 245, 245));
            conteudo.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

            coluna.getTarefas().forEach(tarefa ->
                conteudo.add(new PostItPanel(tarefa))
            );

            JScrollPane scroll = new JScrollPane(conteudo);
            scroll.setBorder(null);

            colunaPanel.add(scroll, BorderLayout.CENTER);

            // ===== BOTÃO ADD POST-IT =====
            JButton addPostItButton = new JButton("+ Adicionar Post-It");
            addPostItButton.addActionListener(e ->
                firePropertyChange("adicionarPostIt", null, colunaIndex)
            );

            colunaPanel.add(addPostItButton, BorderLayout.SOUTH);

            painelColunas.add(colunaPanel);
        }

        // ===== Botão adicionar coluna =====
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

    // ===== Cores por coluna =====
    private Color corPorColuna(String nome) {
        if (nome == null) return Color.DARK_GRAY;

        switch (nome.trim().toLowerCase()) {
            case "a fazer":
                return new Color(59, 130, 246);   // azul
            case "em progresso":
                return new Color(245, 158, 11);   // laranja
            case "concluído":
            case "concluido":
                return new Color(34, 197, 94);    // verde
            default:
                return new Color(107, 114, 128);  // cinza
        }
    }
}
