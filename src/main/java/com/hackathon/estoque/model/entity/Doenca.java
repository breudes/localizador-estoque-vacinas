package com.hackathon.estoque.model.entity;

public class Doenca {
    private String nome;
    private String descricao;
    private String cid10;

    public Doenca(String nome, String descricao, String cid10) {
        this.nome = nome;
        this.descricao = descricao;
        this.cid10 = cid10;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCid10() {
        return cid10;
    }

    public void setCid10(String cid10) {
        this.cid10 = cid10;
    }
}
