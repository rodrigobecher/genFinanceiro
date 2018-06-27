package com.example.suporte.myapplication.model;

import java.io.Serializable;
import java.util.Date;
/**
 * Created by suporte on 16/03/2018.
 */

public class Lancamento implements Serializable {


    private int id;
    private int usuario_id;
    private int tipoLancamento;
    private String descricao;
    private Date dataLancamento;
    private Date dataVencimento;
    private Date dataPagament;
    private int status;
    private double valor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
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

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Date getDataPagament() {
        return dataPagament;
    }

    public void setDataPagament(Date dataPagament) {
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

    /*@Override
    public String toString() {
        return "Lancamento: "+id  +"\n"+ "Tipo Lancamento: " + tipoLancamento +"\n"+"Descricao: " + descricao + "\n"+"Data Lancamento: " + dataLancamento +"\n"+
                "Data Vencimento:" + dataVencimento + "\n"+
                "Data Pagamento: " + dataPagament + "\n" +"Status: " + status;
    }*/
}
