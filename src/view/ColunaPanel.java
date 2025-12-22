package view;

import javax.swing.*;
import java.awt.*;
import model.Coluna;
import model.Tarefa;

public class ColunaPanel extends JPanel {

    public ColunaPanel(Coluna coluna) {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // HEADER (Título + ...)
        JPanel header = criarHeader(coluna.getNome());
        add(header, BorderLayout.NORTH);

        // CONTEÚDO (Post-its + botão)
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(new Color(245, 245, 245));
        conteudo.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // POST-ITS
        for (Tarefa tarefa : coluna.getTarefas()) {
            conteudo.add(new PostItPanel(tarefa));
            conteudo.add(Box.createVerticalStrut(8));
        }

        // BOTÃO DE ADICIONAR POST-IT
        JButton btnAddPostIt = new JButton("+ Adicionar Post-it");
        btnAddPostIt.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAddPostIt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        btnAddPostIt.setFocusPainted(false);
        btnAddPostIt.setBackground(Color.WHITE);
        btnAddPostIt.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        btnAddPostIt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAddPostIt.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        btnAddPostIt.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                this,
                "Adicionar Post-it em: " + coluna.getNome()
            );
        });

        conteudo.add(Box.createVerticalStrut(6));
        conteudo.add(btnAddPostIt);

        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(scroll, BorderLayout.CENTER);
    }

    private JPanel criarHeader(String titulo) {
        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(260, 42));
        header.setBackground(corPorColuna(titulo));
        header.setOpaque(true);

        JLabel label = new JLabel(titulo.toUpperCase());
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));

        JButton btnMenu = new JButton("⋮");
        btnMenu.setFocusPainted(false);
        btnMenu.setBorderPainted(false);
        btnMenu.setContentAreaFilled(false);
        btnMenu.setForeground(Color.WHITE);
        btnMenu.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnMenu.setPreferredSize(new Dimension(32, 32));

        btnMenu.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                this,
                "Menu da coluna: " + titulo
            );
        });

        header.add(label, BorderLayout.WEST);
        header.add(btnMenu, BorderLayout.EAST);

        return header;
    }

    private Color corPorColuna(String titulo) {
        switch (titulo.toLowerCase()) {
            case "a fazer":
                return new Color(59, 130, 246);
            case "em progresso":
                return new Color(245, 158, 11);
            case "concluido":
                return new Color(34, 197, 94);
            default:
                return Color.BLACK;
        }
    }
}
