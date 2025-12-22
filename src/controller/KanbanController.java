package controller;

import model.Coluna;
import model.KanbanBoard;
import model.Tarefa;
import view.KanbanView;

public class KanbanController {

    private KanbanBoard board;
    private KanbanView view;

    public KanbanController(KanbanBoard board, KanbanView view) {
        this.board = board;
        this.view = view;

        // Listener para adicionar coluna
        view.addPropertyChangeListener("adicionarColuna", evt -> {
            String nome = (String) evt.getNewValue();
            board.adicionarColuna(new Coluna(nome));
            view.atualizarColunas(board.getColunas());
        });

        inicializar();
    }

    private void inicializar() {
        board.adicionarColuna(new Coluna("A Fazer"));
        board.adicionarColuna(new Coluna("Em Progresso"));
        board.adicionarColuna(new Coluna("Concluido"));

        board.getColunas().get(0).adicionarTarefa(new Tarefa("Estudar MVC"));
        board.getColunas().get(1).adicionarTarefa(new Tarefa("Criar Kanban"));
        board.getColunas().get(2).adicionarTarefa(new Tarefa("Vai Corinthians"));

        view.atualizarColunas(board.getColunas());
        view.setVisible(true);
    }
}
