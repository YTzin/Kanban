package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tarefa {

    private String titulo;
    private String descricao;
    private String prioridade;
    private LocalDateTime dataCriacao;

    // Construtor completo
    public Tarefa(String titulo, String descricao, String prioridade) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.dataCriacao = LocalDateTime.now(); // define a data de criação como agora
    }

    // Construtor só com título (compatível com seu código antigo)
    public Tarefa(String titulo) {
        this(titulo, "essa tarefa bla bla", "Alta"); // descrição vazia e prioridade padrão
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public String getDataCriacao() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dataCriacao.format(formatter);
    }

    // Vamos permitir alteração??? se n nem precisa do setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
