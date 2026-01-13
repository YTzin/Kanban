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
        setBackground(new Color(240, 0, 0));
        setOpaque(true);
        

        // ===== HEADER =====
        JPanel header = criarHeader(coluna.getNome());
        add(header, BorderLayout.NORTH);

        // ===== CONTEÚDO =====
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

        // ===== BOTÃO =====
        JButton btnAddPostIt = new JButton("+ Adicionar Post-it");
        btnAddPostIt.setFocusPainted(false);
        btnAddPostIt.setBackground(Color.WHITE);
        btnAddPostIt.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        btnAddPostIt.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnAddPostIt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAddPostIt.setPreferredSize(new Dimension(260, 32));

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

    // ===== HEADER =====
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
        String t = titulo.toLowerCase().trim()
                .replace("í", "i")
                .replace("ú", "u");

        switch (t) {
            case "a fazer":
                return new Color(59, 130, 246);   // azul
            case "em progresso":
                return new Color(245, 158, 11);   // laranja
            case "concluido":
                return new Color(34, 197, 94);    // verde
            default:
                return Color.DARK_GRAY;
        }
    }
}
