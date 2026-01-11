package controller;

import model.Coluna;
import model.KanbanBoard;
import model.Tarefa;
import view.KanbanView;

import javax.swing.*;
import java.awt.*;

public class KanbanController {

    private KanbanBoard board;
    private KanbanView view;

    public KanbanController(KanbanBoard board, KanbanView view) {
        this.board = board;
        this.view = view;

        // Listener para adicionar nova coluna
        view.addPropertyChangeListener("adicionarColuna", evt -> {
            String nome = (String) evt.getNewValue();

            // CANCELAR ou X, acho q resolvi não estava verificando NULL
            if (nome == null) {
                return;
            }

            nome = nome.trim();

            // Nome vazio
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(
                        view,
                        "O nome da coluna eh obrigatorio!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            board.adicionarColuna(new Coluna(nome));
            view.atualizarColunas(board.getColunas());
        });


        // Listener para adicionar post-it (nova tarefa)
        view.addPropertyChangeListener("adicionarPostIt", evt -> {
            int colunaIndex = (int) evt.getNewValue(); // qual coluna clicou
            criarNovaTarefa(colunaIndex);
        });

        inicializar();
    }

    private void inicializar() {
        board.adicionarColuna(new Coluna("A Fazer"));
        board.adicionarColuna(new Coluna("Em Progresso"));
        board.adicionarColuna(new Coluna("Concluido"));

        view.atualizarColunas(board.getColunas());
        view.setVisible(true);
    }

private void criarNovaTarefa(int colunaIndex) {

    JPanel panel = new JPanel(new GridLayout(0, 1, 6, 6));

    JTextField tituloField = new JTextField();
    JTextField descricaoField = new JTextField();

    String[] prioridades = {"Alta", "Media", "Baixa"};
    JComboBox<String> prioridadeBox = new JComboBox<>(prioridades);

    panel.add(new JLabel("Titulo:"));
    panel.add(tituloField);

    panel.add(new JLabel("Descricao:"));
    panel.add(descricaoField);

    panel.add(new JLabel("Prioridade:"));
    panel.add(prioridadeBox);

    int resultado = JOptionPane.showConfirmDialog(
            view,
            panel,
            "Nova Tarefa",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
    );

    // Se clicar em Cancelar ou no X, não faz nada
    if (resultado != JOptionPane.OK_OPTION) {
        return;
    }

    String titulo = tituloField.getText().trim();
    String descricao = descricaoField.getText().trim();
    String prioridade = (String) prioridadeBox.getSelectedItem();

    // Validação
    if (titulo.isEmpty()) {
        JOptionPane.showMessageDialog(
                view,
                "O título eh obrigatorio!",
                "Erro",
                JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    // Cria a tarefa e adiciona no model
    Tarefa novaTarefa = new Tarefa(titulo, descricao, prioridade);
    board.getColunas().get(colunaIndex).adicionarTarefa(novaTarefa);

    // Atualiza a view
    view.atualizarColunas(board.getColunas());
}

}
