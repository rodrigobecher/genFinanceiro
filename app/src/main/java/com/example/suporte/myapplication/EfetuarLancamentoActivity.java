package com.example.suporte.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.MaskFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.suporte.myapplication.dao.LancamentoDao;
import com.example.suporte.myapplication.model.Lancamento;
import com.example.suporte.myapplication.model.LancamentoFirebase;
import com.example.suporte.myapplication.model.Mask;
import com.example.suporte.myapplication.model.Usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EfetuarLancamentoActivity extends AppCompatActivity {

    LancamentoFirebase lancamentoFirebase;
    Lancamento lancamento;
    EditText etDate;
    Mask mascara;
    private View efetuarLancamentoView;
    private Usuario usuario;
    int status;
    int tipoLancamento;
    FormHelper formHelper;
    RadioButton pago;
    private DatabaseReference mDatabase;
    private static final String ARQUIVO_PREFERENCIA = "ArqPreferencia";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_efetuar_lancamento);
        formHelper = new FormHelper(EfetuarLancamentoActivity.this);

        if(getIntent().getSerializableExtra("lancamento") != null ){
            lancamentoFirebase = (LancamentoFirebase) getIntent().getSerializableExtra("lancamento");
        }
        if(lancamentoFirebase != null){
            formHelper.setLancamento(lancamentoFirebase);
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        efetuarLancamentoView = this.findViewById(R.id.efetuar_lancamento);


        EditText etDateVencimento = (EditText) findViewById(R.id.dataVencimento);
        etDateVencimento.addTextChangedListener(mascara.insert("##/##/####", etDateVencimento));
        EditText etDate = (EditText) findViewById(R.id.dataPagamento);
        etDate.addTextChangedListener(mascara.insert("##/##/####", etDate));

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:


                    lancamento = formHelper.pegaLancamento();
                if (lancamento == null) {
                    Toast.makeText(EfetuarLancamentoActivity.this, "Dados informados invalidos", Toast.LENGTH_SHORT).show();
                }else {
                    pago = (RadioButton)this.findViewById(R.id.pago);
                    if(pago.isChecked()){
                        status =1;
                    }else{
                        status = 0;
                    }
                    lancamento.setStatus(status);
                    if (lancamentoFirebase == null) {
                            Calendar calPag = Calendar.getInstance();
                            calPag.setTime(lancamento.getDataPagament());
                            Calendar calVenc = Calendar.getInstance();
                            calVenc.setTime(lancamento.getDataVencimento());
                            int diaVenc = calVenc.get(Calendar.DAY_OF_MONTH);
                            int mesVenc = calVenc.get(Calendar.MONTH) + 1;
                            int anoVenc = calVenc.get(Calendar.YEAR);
                            int diaPag = calPag.get(Calendar.DAY_OF_MONTH);
                            int mesPag = calPag.get(Calendar.MONTH) + 1;
                            int anoPag = calPag.get(Calendar.YEAR);

                            if (diaVenc > 31 || diaVenc <= 0 || mesVenc > 12 || mesVenc <= 0 || anoVenc <= 1900 || anoVenc >= 3000) {
                                Toast.makeText(EfetuarLancamentoActivity.this, "Data de Vencimento inválida", Toast.LENGTH_SHORT).show();
                            } else if (diaPag > 31 || diaPag <= 0 || mesPag > 12 || mesPag <= 0 || anoPag <= 1900 || anoVenc >= 3000) {
                                Toast.makeText(EfetuarLancamentoActivity.this, "Data de Pagamento inválida", Toast.LENGTH_SHORT).show();
                            } else if (anoPag < anoVenc || mesPag < mesVenc || diaPag < diaVenc ) {
                                Toast.makeText(EfetuarLancamentoActivity.this, "Data de Pagamento Menor que a data vencimento!", Toast.LENGTH_LONG).show();

                            } else {

                                SharedPreferences sharedPref = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
                                String emailRecuperado = sharedPref.getString("email", "");
                                LancamentoFirebase lancamentoFirebase = new LancamentoFirebase();
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                String id = mDatabase.push().getKey();
                                lancamentoFirebase.setId(id);
                                lancamentoFirebase.setStatus(lancamento.getStatus());
                                lancamentoFirebase.setDescricao(lancamento.getDescricao());
                                lancamentoFirebase.setTipoLancamento(lancamento.getTipoLancamento());
                                lancamentoFirebase.setValor(lancamento.getValor());
                                lancamentoFirebase.setDataVencimento(sdf.format(lancamento.getDataVencimento()));
                                lancamentoFirebase.setDataLancamento(sdf.format(lancamento.getDataLancamento()));
                                lancamentoFirebase.setDataPagament(sdf.format(lancamento.getDataPagament()));
                                lancamentoFirebase.setEmail(emailRecuperado);
                                mDatabase.child(id).setValue(lancamentoFirebase);

                                Intent intent = new Intent(EfetuarLancamentoActivity.this, LancamentosActivity.class);
                                intent.putExtra("usuario", usuario);

                                startActivity(intent);
                                finish();
                            }
                        }else {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                            Calendar calPag = Calendar.getInstance();
                            calPag.setTime(lancamento.getDataPagament());
                            Calendar calVenc = Calendar.getInstance();
                            calVenc.setTime(lancamento.getDataVencimento());
                            int diaVenc = calVenc.get(Calendar.DAY_OF_MONTH);
                            int mesVenc = calVenc.get(Calendar.MONTH) + 1;
                            int anoVenc = calVenc.get(Calendar.YEAR);
                            int diaPag = calPag.get(Calendar.DAY_OF_MONTH);
                            int mesPag = calPag.get(Calendar.MONTH) + 1;
                            int anoPag = calPag.get(Calendar.YEAR);

                            if (diaVenc >= 31 || diaVenc <= 0 || mesVenc > 12 || mesVenc <= 0 || anoVenc <= 1900 || anoVenc >= 3000) {
                                Toast.makeText(EfetuarLancamentoActivity.this, "Data de Vencimento inválida", Toast.LENGTH_SHORT).show();
                            } else if (diaPag > 31 || diaPag <= 0 || mesPag > 12 || mesPag <= 0 || anoPag <= 1900 || anoVenc >= 3000) {
                                Toast.makeText(EfetuarLancamentoActivity.this, "Data de Pagamento inválida", Toast.LENGTH_SHORT).show();
                            } else if (anoPag < anoVenc) {
                                Toast.makeText(EfetuarLancamentoActivity.this, "Data de Pagamento Menor que a data vencimento!", Toast.LENGTH_LONG).show();
                            } else if (mesPag < mesVenc) {
                                Toast.makeText(EfetuarLancamentoActivity.this, "Data de Pagamento Menor que a data vencimento!", Toast.LENGTH_LONG).show();
                            } else {

                                SharedPreferences sharedPref = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
                                String emailRecuperado = sharedPref.getString("email", "");
                                LancamentoDao dao = new LancamentoDao(EfetuarLancamentoActivity.this);
                                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference ref = database.getReference();
                                lancamentoFirebase.setStatus(lancamento.getStatus());
                                lancamentoFirebase.setDescricao(lancamento.getDescricao());
                                lancamentoFirebase.setTipoLancamento(lancamento.getTipoLancamento());
                                lancamentoFirebase.setValor(lancamento.getValor());
                                lancamentoFirebase.setDataVencimento(sdf.format(lancamento.getDataVencimento()));
                                lancamentoFirebase.setDataLancamento(sdf.format(lancamento.getDataLancamento()));
                                lancamentoFirebase.setDataPagament(sdf.format(lancamento.getDataPagament()));
                                lancamentoFirebase.setEmail(emailRecuperado);
                                ref.child(lancamentoFirebase.getId()).setValue(lancamentoFirebase);
                                usuario = new Usuario();
                                usuario.setEmail(emailRecuperado);
                                Intent intent = new Intent(EfetuarLancamentoActivity.this, LancamentosActivity.class);
                                intent.putExtra("usuario", usuario);
                                startActivity(intent);
                                finish();
                            }
                            break;
                    }
                }
        }
        /*itemRef=mDatabase.getReference().child("items").child(itemId).child("name");
        mItemsRef.setValue("Novo Nome do Item");
*/
        return super.onOptionsItemSelected(item);
    }
    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.pago:
                if(checked)
                    status = 1;
                else
                    status = 0;
                break;
            case R.id.naoPago:
                if(checked)
                    status = 0;
                else
                    status = 1;
                break;
            case R.id.pagou:
                if(checked)
                    tipoLancamento = 1;
                else
                    tipoLancamento = 2;
                break;
        }
    }

}
