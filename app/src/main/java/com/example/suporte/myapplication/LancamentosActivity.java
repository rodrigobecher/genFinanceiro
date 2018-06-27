package com.example.suporte.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suporte.myapplication.dao.LancamentoDao;
import com.example.suporte.myapplication.model.Lancamento;
import com.example.suporte.myapplication.model.LancamentoFirebase;
import com.example.suporte.myapplication.model.Mask;
import com.example.suporte.myapplication.model.Usuario;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class LancamentosActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Usuario usuario;
    private TextView email;
    private static final String ARQUIVO_PREFERENCIA = "ArqPreferencia";
    private String emailRecuperado;
    private Button filtraVencimento;
    private Button filtraPagamento;
    private EditText filtroDataVencimento;
    private DatabaseReference mDatabase;
    private EditText filtroDataPagamento;
    private LancamentoFirebase lancamento;
    Mask mascara;
    private LancamentoDao dao;
    private List<LancamentoFirebase> listaLancamentos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancamentos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("project/loginfirebase-5b949/database/loginfirebase-5b949/data/lancamento");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LancamentosActivity.this, EfetuarLancamentoActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        EditText etDateVencimento = (EditText) findViewById(R.id.filtroDataVencimento);
        etDateVencimento.addTextChangedListener(mascara.insert("##/##/####", etDateVencimento));
        EditText etDate = (EditText) findViewById(R.id.filtroDataPagamento);
        etDate.addTextChangedListener(mascara.insert("##/##/####", etDate));
        pegaUsuario();
        filtraVencimento = (Button)this.findViewById(R.id.botaoFiltroDataVencimento);
        filtraPagamento = (Button)this.findViewById(R.id.botaoFiltroDataPagamento);
        filtroDataVencimento = (EditText)this.findViewById(R.id.filtroDataVencimento);
        filtroDataPagamento = (EditText)this.findViewById(R.id.filtroDataPagamento);
        filtraVencimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    buscaPorDataVencimento(filtroDataVencimento.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        filtraPagamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    buscaPorDataPagamento(filtroDataPagamento.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Buscar();
    }
    public void Buscar() {
        Query query1 = mDatabase.orderByChild("email").equalTo(emailRecuperado);
        query1.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaLancamentos = new ArrayList<LancamentoFirebase>();
                if(dataSnapshot.exists()){
                    for (DataSnapshot data: dataSnapshot.getChildren()) {
                        lancamento = data.getValue(LancamentoFirebase.class);
                        Date dataAtual = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                            if(lancamento.getDataVencimento().equals(sdf.format(dataAtual))){

                                listaLancamentos.add(lancamento);
                            }
                    }
                    carregaResumo(listaLancamentos);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );
    }
    public void buscaPorDataVencimento(final String datev) throws ParseException {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        final Date dataVencimento = sdf.parse(datev);
        Query query1 = mDatabase.orderByChild("email").equalTo(emailRecuperado);
        query1.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaLancamentos = new ArrayList<LancamentoFirebase>();
                if(dataSnapshot.exists()){
                    for (DataSnapshot data: dataSnapshot.getChildren()) {
                        lancamento = data.getValue(LancamentoFirebase.class);
                        SimpleDateFormat sdfe = new SimpleDateFormat("dd-MM-yyyy");
                        if(lancamento.getDataVencimento().equals(sdfe.format(dataVencimento))){

                            listaLancamentos.add(lancamento);
                        }
                    }
                    carregaResumo(listaLancamentos);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );
    }
    public void buscaPorDataPagamento(final String datev) throws ParseException {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        final Date dataPag = sdf.parse(datev);
        Query query1 = mDatabase.orderByChild("email").equalTo(emailRecuperado);
        query1.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaLancamentos = new ArrayList<LancamentoFirebase>();
                if(dataSnapshot.exists()){
                    for (DataSnapshot data: dataSnapshot.getChildren()) {
                        lancamento = data.getValue(LancamentoFirebase.class);
                        SimpleDateFormat sdfe = new SimpleDateFormat("dd-MM-yyyy");
                        if(lancamento.getDataVencimento().equals(sdfe.format(dataPag))){
                            listaLancamentos.add(lancamento);
                        }
                    }
                    carregaResumo(listaLancamentos);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    public void carregaResumo(List<LancamentoFirebase> listaLancamento){
        ResumoMesHelper resumoMesHelper = new ResumoMesHelper(LancamentosActivity.this);
        if(listaLancamento != null) {
            resumoMesHelper.insereResumoMes(listaLancamento);
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lancamentos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.listaLancamentos) {

            Intent intent = new Intent(LancamentosActivity.this, listaLancamentosActivity.class);
            intent.putExtra("LIST", (Serializable) listaLancamentos);
            startActivity(intent);
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public Date getDataAtual() {
        Calendar calendar = new GregorianCalendar();
        Date date = new Date(System.currentTimeMillis());
        return date;
    }

    public String pegaUsuario(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        usuario = new Usuario();
        usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        if(usuario == null){
            SharedPreferences sharedPref = getSharedPreferences(ARQUIVO_PREFERENCIA,0);
            emailRecuperado = sharedPref.getString("email", "");
            email = (TextView) hView.findViewById(R.id.emailUsuario);
            email.setText(emailRecuperado);


        }else{
            email = (TextView) hView.findViewById(R.id.emailUsuario);
            email.setText(usuario.getEmail());
            emailRecuperado = usuario.getEmail();
        }
    return emailRecuperado;
    }

    public Date converteData(String data){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date dataConvertida = new Date(sdf.parse(data).getTime());
            return dataConvertida;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
