package view;

import javax.swing.*;
import java.awt.*;
import model.Coluna;
import model.Tarefa;

public class ColunaPanel extends JPanel {

    public ColunaPanel(Coluna coluna) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(260, 400));
        setBackground(new Color(245, 245, 245));

        // ===== WRAPPER PARA HEADER + SCROLL =====
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BorderLayout());

        // ===== HEADER colorido =====
        JPanel header = new JPanel();
        header.setBackground(corPorColuna(coluna.getNome()));
        header.setOpaque(true);
        header.setPreferredSize(new Dimension(260, 42));
        header.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 8));

        JLabel label = new JLabel(coluna.getNome().toUpperCase());
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.add(label);

        wrapper.add(header, BorderLayout.NORTH);

        // ===== CONTEÚDO (Post-Its) =====
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        conteudo.setBackground(new Color(245, 245, 245));

        for (Tarefa tarefa : coluna.getTarefas()) {
            conteudo.add(new PostItPanel(tarefa));
            conteudo.add(Box.createVerticalStrut(8));
        }

        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        wrapper.add(scroll, BorderLayout.CENTER);

        // Adiciona o wrapper ao ColunaPanel
        add(wrapper, BorderLayout.CENTER);

        // ===== BOTÃO +ADICIONAR POST-IT =====
        JButton btnAddPostIt = new JButton("+ Adicionar Post-It");
        btnAddPostIt.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAddPostIt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        btnAddPostIt.setFocusPainted(false);
        btnAddPostIt.setBackground(Color.WHITE);
        btnAddPostIt.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        btnAddPostIt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAddPostIt.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        btnAddPostIt.addActionListener(e -> {
            String texto = JOptionPane.showInputDialog(this, "Digite o texto do Post-it:");
            if (texto != null && !texto.trim().isEmpty()) {
                Tarefa novaTarefa = new Tarefa(texto);
                coluna.adicionarTarefa(novaTarefa);

                conteudo.add(new PostItPanel(novaTarefa));
                conteudo.add(Box.createVerticalStrut(8));
                conteudo.revalidate();
                conteudo.repaint();
            }
        });

        add(btnAddPostIt, BorderLayout.SOUTH);
    }

    // ===== Cores por coluna =====
    private Color corPorColuna(String titulo) {
        switch (titulo.toLowerCase()) {
            case "a fazer":
                return new Color(59, 130, 246); // azul
            case "em progresso":
                return new Color(245, 158, 11); // laranja
            case "concluido":
                return new Color(34, 197, 94); // verde
            default:
                return Color.DARK_GRAY;
        }
    }
}
