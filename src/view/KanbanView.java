package src.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import src.model.Coluna;
import src.model.Tarefa;

public class KanbanView extends JFrame {

    private JPanel painelColunas;

    public KanbanView() {
        setTitle("Kanban Simples");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        painelColunas = new JPanel();
        painelColunas.setLayout(new GridLayout(1, 3));

        add(painelColunas);
    }

    public void atualizarColunas(List<Coluna> colunas) {
        painelColunas.removeAll();

        for (Coluna coluna : colunas) {
            painelColunas.add(new ColunaPanel(coluna));
        }

        revalidate();
        repaint();
    }
}
