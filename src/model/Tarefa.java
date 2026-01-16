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

    // Construtor só com título 
    public Tarefa(String titulo) {
        this(titulo, "essa tarefa", "Alta"); 
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

    public int getPesoPrioridade(){
        if(prioridade == null){
            return 0;
        }
        switch(prioridade.toLowerCase()){
            case "alta": return 3;
            case "media": return 2;
            case "baixa": return 1;
            default: return 0;
        }
    }
}
