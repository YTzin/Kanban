package src.model;

import java.util.ArrayList;
import java.util.List;

public class Coluna {
    private String nome;
    private List<Tarefa> tarefas;

    public Coluna(String nome) {
        this.nome = nome;
        this.tarefas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void adicionarTarefa(Tarefa tarefa) {
        tarefas.add(tarefa);
    }
}
