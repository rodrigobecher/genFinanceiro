package com.example.suporte.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.suporte.myapplication.model.Lancamento;
import com.example.suporte.myapplication.model.Usuario;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by suporte on 16/03/2018.
 */

public class LancamentoDao  extends SQLiteOpenHelper{
    public LancamentoDao(Context context) {
        super(context, "financeiro", null, 6);
    }
    // Context é o contexto no caso a activite que vai instanciar a classe.
    //Name é o nome do banco, version serve para que a cada mudança no banco ele execute o onUpdrade depois o onCreate.
    //É bem facil.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "Create table usuario (id_usuario INTEGER PRIMARY KEY, email TEXT, senha TEXT)";
         String sql2 = "Create table lancamento (id_lancamento INTEGER PRIMARY KEY, usuario_id Integer, valor double, tipo_lancamento Integer, descricao text, data_lancamento date, data_vencimento date, data_pagamento date, status INTEGER)";

        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql2);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVerson, int newVersion) {
        String sql = "DROP TABLE IF EXISTS usuario";
        String sql2 = "DROP TABLE IF EXISTS lancamento";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql2);
        onCreate(sqLiteDatabase);
    }
    public String adiconaUsuario(Usuario usuario) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();
        dados.put("email", usuario.getEmail());
        dados.put("senha", usuario.getSenha());
        try{
           db.insert("usuario", null, dados);

        }catch (SQLException e){
            return e.toString();
        }
        return "Deu certo";
    }
    public Boolean buscaUsuario(Usuario usuario) {
        SQLiteDatabase db = getReadableDatabase();
        String sql ="select id_usuario, email, senha from usuario where email = ? and senha = ?";

        Cursor result = db.rawQuery(sql, new String [] {usuario.getEmail(), usuario.getSenha() });
        if(result.getCount() > 0 ){
            return true;
        }
        result.close();
        db.close();
        return false;
    }

    public Boolean validaUsuarioRepedito(Usuario usuario){
        SQLiteDatabase db = getReadableDatabase();
        String sql ="select id_usuario, email, senha from usuario where email = ?";
        Cursor result = db.rawQuery(sql, new String [] {usuario.getEmail()});
        if(result.getCount() > 0 ){
            return false;
        }
        result.close();
        db.close();
        return true;
    }

    public String adiconaLancamento(Lancamento lancamento, String email) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();
        dados.put("usuario_id", buscaUsuarioId(email));
        dados.put("valor", lancamento.getValor());
        dados.put("tipo_lancamento", lancamento.getTipoLancamento());
        dados.put("descricao", lancamento.getDescricao());
        dados.put("data_lancamento", String.valueOf(lancamento.getDataLancamento()));
        dados.put("data_vencimento", String.valueOf(lancamento.getDataVencimento()));
        dados.put("data_pagamento", String.valueOf(lancamento.getDataPagament()));
        dados.put("status", lancamento.getStatus());
        try{
            db.insert("lancamento", null, dados);

        }catch (SQLException e){
            return e.toString();
        }
        return "";
    }
    public int buscaUsuarioId(String email){
        int id = 0 ;
        SQLiteDatabase db = getReadableDatabase();
        String sql ="select id_usuario, email, senha from usuario where email = ?";
        Cursor result = db.rawQuery(sql, new String [] {email});
        if(result.moveToFirst()){
            do{
               id = result.getInt(result.getColumnIndex("id_usuario"));
            } while (result.moveToNext());
        }
        return id;
    }

    public List<Lancamento> buscaLancamento(String logado){
        SQLiteDatabase db = getReadableDatabase();
        String sql ="select id_lancamento, usuario_id, valor, tipo_lancamento, descricao, data_lancamento, data_vencimento, data_pagamento, status from lancamento where usuario_id = ?";
        Cursor result = db.rawQuery(sql, new String[] {String.valueOf(buscaUsuarioId(logado))});
        List<Lancamento> lista = new ArrayList<>();
        if(result.moveToFirst()){
            do{
                Lancamento lancamento = new Lancamento();
                lancamento.setId(result.getInt(result.getColumnIndex("id_lancamento")));
                lancamento.setUsuario_id(result.getInt(result.getColumnIndex("usuario_id")));
                lancamento.setValor(result.getDouble(result.getColumnIndex("valor")));
                lancamento.setTipoLancamento(result.getInt(result.getColumnIndex("tipo_lancamento")));
                lancamento.setDescricao(result.getString(result.getColumnIndex("descricao")));
                lancamento.setDataLancamento(Date.valueOf(result.getString(result.getColumnIndex("data_lancamento"))));
                lancamento.setDataVencimento(Date.valueOf(result.getString(result.getColumnIndex("data_vencimento"))));
                lancamento.setDataPagament(Date.valueOf(result.getString(result.getColumnIndex("data_pagamento"))));
                lancamento.setStatus(result.getInt(result.getColumnIndex("status")));
                lista.add(lancamento);

            } while (result.moveToNext());
        }

        return lista;

    }

    public List<Lancamento> buscaLancamentoDataVencimento(String logado, Date dataVencimento){
        SQLiteDatabase db = getReadableDatabase();
        String sql ="select id_lancamento, usuario_id, valor, tipo_lancamento, descricao, data_lancamento, data_vencimento, data_pagamento, status from lancamento where usuario_id = ? and data_vencimento = ?";
        Cursor result = db.rawQuery(sql, new String[] {String.valueOf(buscaUsuarioId(logado)), String.valueOf(dataVencimento)});
        List<Lancamento> lista = new ArrayList<>();
        if(result.moveToFirst()){
            do{
                Lancamento lancamento = new Lancamento();
                lancamento.setId(result.getInt(result.getColumnIndex("id_lancamento")));
                lancamento.setUsuario_id(result.getInt(result.getColumnIndex("usuario_id")));
                lancamento.setValor(result.getDouble(result.getColumnIndex("valor")));
                lancamento.setTipoLancamento(result.getInt(result.getColumnIndex("tipo_lancamento")));
                lancamento.setDescricao(result.getString(result.getColumnIndex("descricao")));
                lancamento.setDataLancamento(Date.valueOf(result.getString(result.getColumnIndex("data_lancamento"))));
                lancamento.setDataVencimento(Date.valueOf(result.getString(result.getColumnIndex("data_vencimento"))));
                lancamento.setDataPagament(Date.valueOf(result.getString(result.getColumnIndex("data_pagamento"))));
                lancamento.setStatus(result.getInt(result.getColumnIndex("status")));
                lista.add(lancamento);

            } while (result.moveToNext());
        }
        return lista;
    }
    public List<Lancamento> buscaLancamentoDataPagamento(String logado, Date dataPagamento){
        SQLiteDatabase db = getReadableDatabase();
        String sql ="select id_lancamento, usuario_id, valor, tipo_lancamento, descricao, data_lancamento, data_vencimento, data_pagamento, status from lancamento where usuario_id = ? and data_pagamento = ?";
        Cursor result = db.rawQuery(sql, new String[] {String.valueOf(buscaUsuarioId(logado)), String.valueOf(dataPagamento)});
        List<Lancamento> lista = new ArrayList<>();
        if(result.moveToFirst()){
            do{
                Lancamento lancamento = new Lancamento();
                lancamento.setId(result.getInt(result.getColumnIndex("id_lancamento")));
                lancamento.setUsuario_id(result.getInt(result.getColumnIndex("usuario_id")));
                lancamento.setValor(result.getDouble(result.getColumnIndex("valor")));
                lancamento.setTipoLancamento(result.getInt(result.getColumnIndex("tipo_lancamento")));
                lancamento.setDescricao(result.getString(result.getColumnIndex("descricao")));
                lancamento.setDataLancamento(Date.valueOf(result.getString(result.getColumnIndex("data_lancamento"))));
                lancamento.setDataVencimento(Date.valueOf(result.getString(result.getColumnIndex("data_vencimento"))));
                lancamento.setDataPagament(Date.valueOf(result.getString(result.getColumnIndex("data_pagamento"))));
                lancamento.setStatus(result.getInt(result.getColumnIndex("status")));
                lista.add(lancamento);

            } while (result.moveToNext());
        }
        return lista;
    }

    public void alteraLancamento(Lancamento lancamento) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tipo_lancamento", lancamento.getTipoLancamento());
        cv.put("valor", lancamento.getValor());
        cv.put("descricao", lancamento.getDescricao());
        cv.put("data_vencimento", String.valueOf(lancamento.getDataVencimento()));
        cv.put("data_pagamento", String.valueOf(lancamento.getDataPagament()));
        cv.put("status", lancamento.getStatus());
        db.update("lancamento",cv,"id_lancamento= ?", new String[]{String.valueOf(lancamento.getId())});
    }

    public void remove(int idLancamento) {
            SQLiteDatabase db = getWritableDatabase();
            db.delete("lancamento", "id_lancamento= ?", new String[]{String.valueOf(idLancamento)});

    }
}
