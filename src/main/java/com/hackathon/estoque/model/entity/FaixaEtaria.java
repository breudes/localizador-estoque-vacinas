package com.hackathon.estoque.model.entity;

public class FaixaEtaria {
    private int idadeMinimaEmMeses;
    private int idadeMaximaEmMeses;
    private int idadeMinimaEmAnos;
    private int idadeMaximaEmAnos;

    public FaixaEtaria(int idadeMinimaEmMeses, int idadeMaximaEmMeses, int idadeMinimaEmAnos, int idadeMaximaEmAnos) {
        this.idadeMinimaEmMeses = idadeMinimaEmMeses;
        this.idadeMaximaEmMeses = idadeMaximaEmMeses;
        this.idadeMinimaEmAnos = idadeMinimaEmAnos;
        this.idadeMaximaEmAnos = idadeMaximaEmAnos;
    }

    public int getIdadeMinimaEmMeses() {
        return idadeMinimaEmMeses;
    }

    public void setIdadeMinimaEmMeses(int idadeMinimaEmMeses) {
        this.idadeMinimaEmMeses = idadeMinimaEmMeses;
    }

    public int getIdadeMaximaEmMeses() {
        return idadeMaximaEmMeses;
    }

    public void setIdadeMaximaEmMeses(int idadeMaximaEmMeses) {
        this.idadeMaximaEmMeses = idadeMaximaEmMeses;
    }

    public int getIdadeMinimaEmAnos() {
        return idadeMinimaEmAnos;
    }

    public void setIdadeMinimaEmAnos(int idadeMinimaEmAnos) {
        this.idadeMinimaEmAnos = idadeMinimaEmAnos;
    }

    public int getIdadeMaximaEmAnos() {
        return idadeMaximaEmAnos;
    }

    public void setIdadeMaximaEmAnos(int idadeMaximaEmAnos) {
        this.idadeMaximaEmAnos = idadeMaximaEmAnos;
    }
}
