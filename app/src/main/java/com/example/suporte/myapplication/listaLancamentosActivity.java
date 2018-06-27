package com.example.suporte.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.suporte.myapplication.adapter.AdapterListaLancamentos;
import com.example.suporte.myapplication.model.Lancamento;
import com.example.suporte.myapplication.model.LancamentoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class listaLancamentosActivity extends AppCompatActivity {
    private List<LancamentoFirebase> listaLancamentos;
    private ListView lista;
    private Button formulario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_lancamentos);
        final Intent i = getIntent();
        lista = (ListView)this.findViewById(R.id.listaLancamentos);
        listaLancamentos = (List<LancamentoFirebase>) i.getSerializableExtra("LIST");
        AdapterListaLancamentos adapterLancamentos = new AdapterListaLancamentos(this,listaLancamentos);
        lista.setAdapter(adapterLancamentos);
        registerForContextMenu(lista);
        formulario = (Button)this.findViewById(R.id.formulario);
        formulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(listaLancamentosActivity.this, EfetuarLancamentoActivity.class);
                startActivity(intent);
                finish();
            }
        });
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LancamentoFirebase lancamento = (LancamentoFirebase) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(listaLancamentosActivity.this, EfetuarLancamentoActivity.class);
                intent.putExtra("lancamento", lancamento);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo)  {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final LancamentoFirebase lancamento = (LancamentoFirebase) lista.getItemAtPosition(info.position);
        MenuItem deletar = menu.add("Deletar");

        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                excluiLancamento(lancamento.getId());
                Intent i = new Intent(listaLancamentosActivity.this, LancamentosActivity.class);
                startActivity(i);
                finish();
                return false;
            }
        });
    }
    private void excluiLancamento(String idLancamento) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        ref.child(idLancamento).setValue(null);
    }
}
