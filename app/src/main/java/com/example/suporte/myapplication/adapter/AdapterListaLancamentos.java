package com.example.suporte.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.suporte.myapplication.R;
import com.example.suporte.myapplication.listaLancamentosActivity;
import com.example.suporte.myapplication.model.Lancamento;
import com.example.suporte.myapplication.model.LancamentoFirebase;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import static java.lang.System.*;

/**
 * Created by Rodrigo on 18/03/2018.
 */

public class AdapterListaLancamentos extends BaseAdapter {

    private final List<LancamentoFirebase> listaLancamentos;
    private final Context context;

    public AdapterListaLancamentos(Context context, List<LancamentoFirebase> listaLancamentos) {
        this.context = context;
        this.listaLancamentos = listaLancamentos;
        
    }

    @Override
    public int getCount() {
        return listaLancamentos.size();

    }

    @Override
    public Object getItem(int position) {
        return listaLancamentos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LancamentoFirebase lancamento = listaLancamentos.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, null);
        TextView campoTipoLancamento = (TextView)view.findViewById(R.id.tipoLancamento);
        if(lancamento.getTipoLancamento() == 1){
            campoTipoLancamento.setText("Tipo de Lançamento: Pagamento");
        }else if(lancamento.getTipoLancamento() == 2){
            campoTipoLancamento.setText("Tipo de Lançamento: Recebimento");
        }
        TextView campoDescricao = (TextView) view.findViewById(R.id.descricao);
        campoDescricao.setText("Descrição: "+ lancamento.getDescricao());
        TextView campoValor = (TextView) view.findViewById(R.id.valor);
        campoValor.setText("Valor: "+ String.valueOf(lancamento.getValor()));
        TextView campoDataLancamento = (TextView)view.findViewById(R.id.dataLancamento);
        campoDataLancamento.setText("Data Lançamento: "+lancamento.getDataLancamento());
        TextView campoDataVencimento = (TextView)view.findViewById(R.id.dataVencimento);
        campoDataVencimento.setText("Data Vencimento: "+ lancamento.getDataVencimento());
        TextView campoDataPagamento = (TextView)view.findViewById(R.id.dataPagamento);
        campoDataPagamento.setText("Data Pagamento: " + lancamento.getDataPagament());
        TextView campoStatus = (TextView)view.findViewById(R.id.status);
        if(lancamento.getStatus() == 1){
            campoStatus.setText("Status Lançamento: Pago");
        }else{
            campoStatus.setText("Status Lançamento: Não pago");
        }

        return view;
    }

    public String dataConvertida(Date data) {
        String dataConvertida;
        return dataConvertida = new SimpleDateFormat("dd/MM/yyyy").format(data);
    }
}
