package model;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class Coluna {

    private String nome;
    private ArrayList<Tarefa> tarefas;
    private Color cor;

    public Coluna(String nome) {
        this.nome = nome;
        this.tarefas = new ArrayList<>();
        this.cor = corPadrao(nome);
    }
    public Coluna(String nome, Color cor) {
        this.nome = nome;
        this.tarefas = new ArrayList<>();
        this.cor = cor;
    }

    private Color corPadrao(String nome) {
        if (nome == null) return Color.GRAY;

        switch (nome.trim().toLowerCase()) {
            case "a fazer":
                return new Color(0, 0, 240);
            case "em progresso":
                return new Color(245, 158, 11);
            case "concluído":
                return new Color(34, 197, 94);
            default:
                return new Color(107, 114, 128);
        }
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
