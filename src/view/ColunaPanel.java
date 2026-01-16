package view;

import javax.swing.*;
import java.awt.*;
import model.Coluna;
import model.Tarefa;

public class ColunaPanel extends JPanel {

    private JPanel conteudo;

    public ColunaPanel(Coluna coluna) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(260, 400));
        setMaximumSize(new Dimension(260, Integer.MAX_VALUE));
        setBackground(Color.WHITE);
        setOpaque(true);

        JPanel header = criarHeader(coluna.getNome());
        add(header, BorderLayout.NORTH);

        conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(new Color(245, 245, 245));
        conteudo.setOpaque(true);
        conteudo.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        for (Tarefa tarefa : coluna.getTarefas()) {
            conteudo.add(new PostItPanel(tarefa));
            conteudo.add(Box.createVerticalStrut(8));
        }

        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(true);
        add(scroll, BorderLayout.CENTER);

        JButton btnAddPostIt = new JButton("+ Adicionar Post-it");
        btnAddPostIt.setFocusPainted(false);
        btnAddPostIt.setBackground(Color.WHITE);
        btnAddPostIt.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        btnAddPostIt.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnAddPostIt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAddPostIt.setPreferredSize(new Dimension(260, 32));

        btnAddPostIt.addActionListener(e -> {
            String texto = JOptionPane.showInputDialog(this, "Digite o texto do Post-it:");
            if (texto == null || texto.trim().isEmpty()) {
                return;
            }
            firePropertyChange("adicionarTarefa", null, new Object[]{ coluna, texto });
        });

        add(btnAddPostIt, BorderLayout.SOUTH);
    }

    private JPanel criarHeader(String titulo) {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(true);
        header.setBackground(corPorColuna(titulo));
        header.setMinimumSize(new Dimension(260, 42));
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        header.setPreferredSize(new Dimension(260, 42));

        JLabel label = new JLabel(titulo.toUpperCase());
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));

        header.add(label, BorderLayout.WEST);
        return header;
    }

    private Color corPorColuna(String titulo) {
        if (titulo == null) return Color.DARK_GRAY;

        String t = titulo.toLowerCase().trim()
                .replace("í", "i")
                .replace("ú", "u");

        switch (t) {
            case "a fazer":
                return new Color(59, 130, 246);
            case "em progresso":
                return new Color(245, 158, 11);
            case "concluido":
                return new Color(34, 197, 94);
            default:
                return Color.DARK_GRAY;
        }
    }
}
