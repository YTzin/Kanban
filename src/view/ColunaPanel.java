package src.view;

import javax.swing.*;
import java.awt.*;
import src.model.Coluna;
import src.model.Tarefa;

public class ColunaPanel extends JPanel {

    public ColunaPanel(Coluna coluna) {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        JPanel header = criarHeader(coluna.getNome());
        add(header, BorderLayout.NORTH);

        // POST ITS GLR
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(new Color(245, 245, 245));

        for (Tarefa tarefa : coluna.getTarefas()) {
            conteudo.add(new PostItPanel(tarefa));
            conteudo.add(Box.createVerticalStrut(8)); 
        }

        add(conteudo, BorderLayout.CENTER);
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

        JButton btnAdd = new JButton("+");
        btnAdd.setFocusPainted(false);
        btnAdd.setBorderPainted(false);
        btnAdd.setContentAreaFilled(false);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnAdd.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                this,
                "FUNCIONALIDADE A FAZER: "
            );
        });

        header.add(label, BorderLayout.WEST);
        header.add(btnAdd, BorderLayout.EAST);

        return header;
}


    private Color corPorColuna(String titulo) {
        switch (titulo.toLowerCase()) {
            case "a fazer":
                return new Color(59, 130, 246);   // Azul 
            case "em progresso":
                return new Color(245, 158, 11);   // Laranja
            case "concluído":
                return new Color(34, 197, 94);    // Verde
            default:
                return Color.GRAY;
        }
    }
}
