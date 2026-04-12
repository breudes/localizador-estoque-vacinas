package com.hackathon.estoque.model;

import java.util.List;

public class Vacina {
    private String nome;
    private List<Doenca> doencasPrevenidas;
    private List<FaixaEtaria> faixasEtarias;
    private Dose dose;

    public Vacina(String nome, List<Doenca> doencasPrevenidas, List<FaixaEtaria> faixasEtarias, Dose dose) {
        this.nome = nome;
        this.doencasPrevenidas = doencasPrevenidas;
        this.faixasEtarias = faixasEtarias;
        this.dose = dose;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Doenca> getDoencasPrevenidas() {
        return doencasPrevenidas;
    }

    public void setDoencasPrevenidas(List<Doenca> doencasPrevenidas) {
        this.doencasPrevenidas = doencasPrevenidas;
    }

    public List<FaixaEtaria> getFaixasEtarias() {
        return faixasEtarias;
    }

    public void setFaixasEtarias(List<FaixaEtaria> faixasEtarias) {
        this.faixasEtarias = faixasEtarias;
    }

    public Dose getDose() {
        return dose;
    }

    public void setDose(Dose dose) {
        this.dose = dose;
    }
}
