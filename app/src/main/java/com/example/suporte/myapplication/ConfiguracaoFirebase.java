package com.example.suporte.myapplication;

import com.google.firebase.auth.FirebaseAuth;

public class ConfiguracaoFirebase {

    private static FirebaseAuth firebaseAuth;

    public static FirebaseAuth getFirebaseAutenticacao(){
        if(firebaseAuth == null) {
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;

    }
}
