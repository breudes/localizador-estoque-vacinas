package com.hackathon.estoque.model;

public class Dose {
    private int qtdDoses;
    private boolean doseUnica;
    private boolean doseReforco;
    private int intervaloDiasEntreDoses;

    public Dose(int qtdDoses, boolean doseUnica, boolean doseReforco, int intervaloDiasEntreDoses) {
        this.qtdDoses = qtdDoses;
        this.doseUnica = doseUnica;
        this.doseReforco = doseReforco;
        this.intervaloDiasEntreDoses = intervaloDiasEntreDoses;
    }

    public int getQtdDoses() {
        return qtdDoses;
    }

    public void setQtdDoses(int qtdDoses) {
        this.qtdDoses = qtdDoses;
    }

    public boolean isDoseUnica() {
        return doseUnica;
    }

    public void setDoseUnica(boolean doseUnica) {
        this.doseUnica = doseUnica;
    }

    public boolean isDoseReforco() {
        return doseReforco;
    }

    public void setDoseReforco(boolean doseReforco) {
        this.doseReforco = doseReforco;
    }

    public int getIntervaloDiasEntreDoses() {
        return intervaloDiasEntreDoses;
    }

    public void setIntervaloDiasEntreDoses(int intervaloDiasEntreDoses) {
        this.intervaloDiasEntreDoses = intervaloDiasEntreDoses;
    }
}
