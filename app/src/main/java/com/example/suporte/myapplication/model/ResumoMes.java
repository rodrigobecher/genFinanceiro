package com.example.suporte.myapplication.model;

import java.util.List;

/**
 * Created by Rodrigo on 18/03/2018.
 */

public class ResumoMes {

    private double totalRecebido;
    private double totalPagamento;
    private double totalMes;
    private double faltaReceber;
    private double faltaPagar;
    private double saldoDoMes;

    public double getTotalRecebido() {
        return totalRecebido;
    }

    public void setTotalRecebido(List<LancamentoFirebase> listaLancamentos) {

        for (LancamentoFirebase lancamento: listaLancamentos
                ) {
            if(lancamento.getTipoLancamento() == 2) {
                this.totalRecebido += lancamento.getValor();
            }
        }

    }

    public double getTotalPagamento() {
        return totalPagamento;
    }

    public void setTotalPagamento(List<LancamentoFirebase> listaLancamentos) {

        for (LancamentoFirebase lancamento: listaLancamentos
             ) {
            if(lancamento.getTipoLancamento() == 1) {
                this.totalPagamento += lancamento.getValor();
            }
        }
    }

    public double getTotalMes() {
        return totalMes;
    }

    public void setTotalMes() {
        this.totalMes += this.totalRecebido - this.totalPagamento;
    }

    public double getFaltaReceber() {
        return faltaReceber;
    }

    public void setFaltaReceber(List<LancamentoFirebase> listaLancamentos) {

        for (LancamentoFirebase lancamento: listaLancamentos
             ) {
            if(lancamento.getTipoLancamento() == 2){
                if(lancamento.getStatus() == 0){
                    this.faltaReceber += lancamento.getValor();
                }
            }
        }

        this.faltaReceber = faltaReceber;
    }

    public double getFaltaPagar() {
        return faltaPagar;
    }

    public void setFaltaPagar(List<LancamentoFirebase> listaLancamentos) {
        for (LancamentoFirebase lancamento: listaLancamentos
                ) {
            if(lancamento.getTipoLancamento() == 1){
                if(lancamento.getStatus() == 0){
                    this.faltaPagar += lancamento.getValor();
                }
            }
        }
    }

    public double getSaldoDoMes() {
        return saldoDoMes;
    }

    public void setSaldoDoMes(List<LancamentoFirebase> listaLancamentos) {
        double totalPagamentoConfirmado = 0;
        double totalRecebimentoConfirmado = 0;

        for (LancamentoFirebase lancamento: listaLancamentos
             ) {
            if (lancamento.getTipoLancamento() == 1){
                if(lancamento.getStatus() == 1){
                    totalPagamentoConfirmado += lancamento.getValor();
                }
            }
            if(lancamento.getTipoLancamento() == 2){
                if(lancamento.getStatus() == 1){
                    totalRecebimentoConfirmado += lancamento.getValor();
                }
            }
        }
        this.saldoDoMes = totalRecebimentoConfirmado - totalPagamentoConfirmado;
    }
}
