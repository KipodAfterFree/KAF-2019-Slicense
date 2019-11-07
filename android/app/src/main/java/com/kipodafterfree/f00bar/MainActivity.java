package com.kipodafterfree.f00bar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kipodafterfree.f00bar.app.AppIntegrityGuard;
import com.kipodafterfree.f00bar.app.PreferenceManager;
import com.kipodafterfree.f00bar.game.GameView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            loadGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGame() throws Exception {
        AppIntegrityGuard appIntegrityGuard = new AppIntegrityGuard(this);
        PreferenceManager preferenceManager = new PreferenceManager(this);
        GameView gameView = new GameView(this);

    }

    private void popup(String text){

    }

}
