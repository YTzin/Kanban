package src.view;

import javax.swing.*;
import src.model.Coluna;
import src.model.Tarefa;

public class ColunaPanel extends JPanel {

    public ColunaPanel(Coluna coluna) {
        setBorder(BorderFactory.createTitledBorder(coluna.getNome()));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        for (Tarefa tarefa : coluna.getTarefas()) {
            add(new PostItPanel(tarefa));
        }
    }
}
