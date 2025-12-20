package src.view;

import javax.swing.*;
import java.awt.*;
import src.model.Tarefa;

public class PostItPanel extends JPanel {

    private Tarefa tarefa;

    public PostItPanel(Tarefa tarefa) {
        if (tarefa == null) {
            throw new IllegalArgumentException("Tarefa não pode ser null");
        }

        this.tarefa = tarefa;

        setBackground(new Color(255, 255, 153)); // amarelo post-it
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setMaximumSize(new Dimension(200, 60));
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel(tarefa.getTitulo());
        titulo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(titulo, BorderLayout.CENTER);
    }

    public Tarefa getTarefa() {
        return tarefa;
    }
}
