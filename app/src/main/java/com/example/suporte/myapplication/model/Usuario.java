package com.example.suporte.myapplication.model;

import java.io.Serializable;

/**
 * Created by suporte on 16/03/2018.
 */

public class Usuario implements Serializable{

    private String nome;
    private String email;
    private String senha;

    public Usuario(){

    }

    public Usuario(String nome, String email, String senha){
        this.nome = nome;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}