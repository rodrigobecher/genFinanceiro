package com.example.suporte.myapplication;

import android.widget.EditText;
import android.widget.TextView;

import com.example.suporte.myapplication.model.Lancamento;
import com.example.suporte.myapplication.model.LancamentoFirebase;
import com.example.suporte.myapplication.model.ResumoMes;

import java.util.List;

/**
 * Created by Rodrigo on 18/03/2018.
 */

public class ResumoMesHelper {

    private TextView totalRecebido;
    private TextView totalPagamento;
    private TextView totalMes;
    private TextView faltaReceber;
    private TextView faltaPagar;
    private TextView saldoDoMes;

    public ResumoMesHelper(LancamentosActivity lancamentosActivity){

        totalRecebido = (TextView) lancamentosActivity.findViewById(R.id.totalRecebido);
        totalPagamento = (TextView) lancamentosActivity.findViewById(R.id.totalPagamento);
        totalMes = (TextView) lancamentosActivity.findViewById(R.id.totalMes);
        faltaReceber = (TextView) lancamentosActivity.findViewById(R.id.faltaReceber);
        faltaPagar = (TextView) lancamentosActivity.findViewById(R.id.faltaPagar);
        saldoDoMes = (TextView) lancamentosActivity.findViewById(R.id.saldoMes);
    }

    public void insereResumoMes(List<LancamentoFirebase> listaLancamento){
        ResumoMes resumoMes = new ResumoMes();
        resumoMes.setTotalRecebido(listaLancamento);
        resumoMes.setTotalPagamento(listaLancamento);
        resumoMes.setTotalMes();
        resumoMes.setFaltaPagar(listaLancamento);
        resumoMes.setFaltaReceber(listaLancamento);
        resumoMes.setSaldoDoMes(listaLancamento);

        totalRecebido.setText(String.valueOf(resumoMes.getTotalRecebido())+"R$");
        totalPagamento.setText(String.valueOf(resumoMes.getTotalPagamento())+"R$");
        totalMes.setText(String.valueOf(resumoMes.getTotalMes()) +"R$");
        faltaReceber.setText(String.valueOf(resumoMes.getFaltaReceber())+"R$");
        faltaPagar.setText(String.valueOf(resumoMes.getFaltaPagar())+"R$");
        saldoDoMes.setText(String.valueOf(resumoMes.getSaldoDoMes())+"R$");
    }

}
