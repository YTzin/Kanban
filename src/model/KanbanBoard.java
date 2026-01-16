package model;

import java.util.ArrayList;

public class KanbanBoard {
    private ArrayList<Coluna> colunas;

    public KanbanBoard() {
        colunas = new ArrayList<>();
    }

    public void adicionarColuna(Coluna coluna) {
        colunas.add(coluna);
    }

    public ArrayList<Coluna> getColunas() {
        return colunas;
    }

    public void removerColuna(int index) {
        if (index >= 0 && index < colunas.size()) {
            colunas.remove(index);
        }
    }

    public void criarNovaTarefa(int coluna, String titulo, String descricao, String prioridade) {
        Tarefa tarefa = new Tarefa(titulo, descricao, prioridade);

        if (coluna >= 0 && coluna < colunas.size()) {
            colunas.get(coluna).adicionarTarefa(tarefa);
        }
    }

    public void moverParaProximaColuna(Tarefa tarefa) {
        for (int i = 0; i < colunas.size(); i++) {
            Coluna colunaAtual = colunas.get(i);

            if (colunaAtual.getTarefas().contains(tarefa)) {
                // N ir se for a ultima coluna
                if (i == colunas.size() - 1) {
                    return;
                }

                colunaAtual.removerTarefa(tarefa); 
                colunas.get(i + 1).adicionarTarefa(tarefa);
                break; 
            }
        }
        
    }

    public void moverParaColunaAnterior(Tarefa tarefa) {
        for (int i = 0; i < colunas.size(); i++) {
            Coluna colunaAtual = colunas.get(i);

            if (colunaAtual.getTarefas().contains(tarefa)) {
                // N ir se n tiver coluna atras
                if (i == 0) {
                    return;
                }

                colunaAtual.removerTarefa(tarefa);
                colunas.get(i - 1).adicionarTarefa(tarefa);
                return; 
            }
        }
    }
}