package src.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import src.model.Coluna;

public class KanbanView extends JFrame {

    private JPanel painelColunas;

    public KanbanView() {
        setTitle("Kanban");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        painelColunas = new JPanel();

        painelColunas.setLayout(new FlowLayout(
                FlowLayout.CENTER,
                15,
                10
        ));

        painelColunas.setBackground(new Color(230, 230, 230));
        painelColunas.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        add(painelColunas);
    }

    public void atualizarColunas(List<Coluna> colunas) {
        painelColunas.removeAll();

        for (Coluna coluna : colunas) {
            painelColunas.add(new ColunaPanel(coluna));
        }

        painelColunas.revalidate();
        painelColunas.repaint();
    }
}
