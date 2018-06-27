package com.example.suporte.myapplication.model;

import java.io.Serializable;
import java.sql.Date;


public class LancamentoFirebase implements Serializable {


    private String id;
    private int tipoLancamento;
    private String descricao;
    private String dataLancamento;
    private String dataVencimento;
    private String dataPagament;
    private String email;
    private int status;
    private double valor;

    public LancamentoFirebase(String id, Usuario usuario, int tipoLancamento, String descricao, String dataLancamento, String dataVencimento, String dataPagament, String email, int status, double valor) {
        this.id = id;
        this.email = email;
        this.tipoLancamento = tipoLancamento;
        this.descricao = descricao;
        this.dataLancamento = dataLancamento;
        this.dataVencimento = dataVencimento;
        this.dataPagament = dataPagament;
        this.status = status;
        this.valor = valor;
    }

    public LancamentoFirebase(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(int tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getDataPagament() {
        return dataPagament;
    }

    public void setDataPagament(String dataPagament) {
        this.dataPagament = dataPagament;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
