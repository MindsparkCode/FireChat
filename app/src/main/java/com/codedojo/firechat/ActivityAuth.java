package com.codedojo.firechat;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codedojo.firechat.fragment.FragmentLogin;
import com.codedojo.firechat.fragment.FragmentRegister;
import com.codedojo.firechat.interfaces.onAuthPageListener;

public class ActivityAuth extends AppCompatActivity implements onAuthPageListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if (savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerAuth , new FragmentLogin())
                    .commit();
        }
    }

    @Override
    public void onLoginPage() {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.containerAuth , new FragmentLogin())
                .commit();
    }

    @Override
    public void onRegisterPage() {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.containerAuth , new FragmentRegister())
                .commit();
    }

    @Override
    public void onAuthenticateSuccess() {
        startActivity(new Intent(ActivityAuth.this , ActivityMain.class));
        finish();
    }
}
