package com.example.suporte.myapplication;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.suporte.myapplication.model.Lancamento;
import com.example.suporte.myapplication.model.LancamentoFirebase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Rodrigo on 16/03/2018.
 */

public class FormHelper {

    private RadioButton recebimento;
    private RadioButton pagamento;
    private RadioButton pago;
    private RadioButton naoPago;
    private EditText descricao;
    private EditText dataVencimento;
    private EditText dataPagamento;
    private EditText valor;
    private Lancamento lancamento;
    private LancamentoFirebase lancamentoFirebase;
    int tipoLancamento;
    int status;

    public FormHelper(EfetuarLancamentoActivity efetuarLancamentoActivity){
        recebimento = (RadioButton)efetuarLancamentoActivity.findViewById(R.id.recebido);
        pagamento = (RadioButton)efetuarLancamentoActivity.findViewById(R.id.pagou);
        pago = (RadioButton)efetuarLancamentoActivity.findViewById(R.id.pago);
        naoPago = (RadioButton)efetuarLancamentoActivity.findViewById(R.id.naoPago);
        descricao = (EditText)efetuarLancamentoActivity.findViewById(R.id.descricao);
        valor = (EditText)efetuarLancamentoActivity.findViewById(R.id.valor);
        dataVencimento = (EditText)efetuarLancamentoActivity.findViewById(R.id.dataVencimento);
        dataPagamento = (EditText)efetuarLancamentoActivity.findViewById(R.id.dataPagamento);
        lancamento = new Lancamento();
    }

    public Lancamento pegaLancamento() {

        if(pagamento.isChecked()){
            tipoLancamento = 1;
        }else{
            tipoLancamento = 2;
        }
        if(pago.isChecked()){
            status = 1;
        }else{
            status = 0;
        }
            if(!valor.getText().toString().equals("") && !descricao.getText().toString().equals("") && tipoLancamento != 0 && converteData(dataVencimento.getText().toString()) != null && converteData(dataPagamento.getText().toString()) != null){
                lancamento.setValor(Double.parseDouble(valor.getText().toString()));
                lancamento.setDescricao(descricao.getText().toString());
                lancamento.setTipoLancamento(tipoLancamento);
                lancamento.setDataLancamento(getDataAtual());
                lancamento.setDataVencimento(converteData(dataVencimento.getText().toString()));
                lancamento.setDataPagament(converteData(dataPagamento.getText().toString()));
                lancamento.setStatus(status);
                return lancamento;
            }else{
               return null;
            }

    }
    public Date getDataAtual() {
        Calendar calendar = new GregorianCalendar();
        Date date = new Date(System.currentTimeMillis());
        return date;
    }

    public Date converteData(String data){
        SimpleDateFormat sdf;
        if(data.contains("/")){
            sdf = new SimpleDateFormat("dd/MM/yyyy");
        }else{
            sdf = new SimpleDateFormat("dd-MM-yyyy");
        }

        try {
            Date date = sdf.parse(data);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void setLancamento(LancamentoFirebase lancamento){
        if (lancamento.getTipoLancamento() == 1 ){
            recebimento.setChecked(false);
            pagamento.setChecked(true);
        }else{
            pagamento.setChecked(false);
            recebimento.setChecked(true);

        }
        descricao.setText(lancamento.getDescricao());
        valor.setText(String.valueOf(lancamento.getValor()));
        dataPagamento.setText(lancamento.getDataPagament());
        dataVencimento.setText(lancamento.getDataVencimento());

        if(lancamento.getStatus() == 1){
            pago.setChecked(true);
            naoPago.setChecked(false);
        }else{
            pago.setChecked(false);
            naoPago.setChecked(true);
        }
        this.lancamentoFirebase = lancamento;

    }

}


