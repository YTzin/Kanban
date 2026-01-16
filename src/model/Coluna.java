package model;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class Coluna {

    private String nome;
    private ArrayList<Tarefa> tarefas;
    private Color cor;

    public Coluna(String nome, Color cor) {
        this.nome = nome;
        this.tarefas = new ArrayList<>();
        this.cor = cor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void adicionarTarefa(Tarefa tarefa) {
        tarefas.add(tarefa);
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }
}
