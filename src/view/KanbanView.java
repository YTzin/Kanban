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
        barra.add(criarBotaoBarra("Outra Página", "OUTRA"));

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
        painel.add(new JLabel("Outra página (em construção)"));
        return painel;
    }

    // ===== ATUALIZAÇÃO DAS COLUNAS =====
    public void atualizarColunas(List<Coluna> colunas) {
        painelColunas.removeAll();

        for (Coluna coluna : colunas) {
            painelColunas.add(new ColunaPanel(coluna));
        }

        painelColunas.revalidate();
        painelColunas.repaint();
    }
}
