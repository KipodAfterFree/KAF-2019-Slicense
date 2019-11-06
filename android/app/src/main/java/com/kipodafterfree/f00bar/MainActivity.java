package com.kipodafterfree.f00bar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kipodafterfree.f00bar.app.PreferenceManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadAccess();
    }

    private void loadAccess(){
        PreferenceManager preferenceManager = new PreferenceManager();
    }

    private void loadKey(){

    }
}
