package com.kipodafterfree.f00bar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kipodafterfree.f00bar.app.APICommunicator;
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
        APICommunicator communicator = new APICommunicator(this);
        if (preferenceManager.modrnzzhxp() == null) {

        } else {
            GameView gameView = new GameView(this);
            communicator.foupmowqbo("game", new APICommunicator.APIParameter[]{
                    new APICommunicator.APIParameter("client")
            }, new APICommunicator.APICallback() {
                @Override
                public void onResult(String result) {

                }

                @Override
                public void onError(String error) {

                }
            });
        }
    }

    private void popup(String text) {

    }

}
