package com.kipodafterfree.f00bar;

import android.app.Activity;
import android.os.Bundle;

import com.kipodafterfree.f00bar.app.APICommunicator;
import com.kipodafterfree.f00bar.app.AppIntegrityGuard;
import com.kipodafterfree.f00bar.app.PreferenceManager;
import com.kipodafterfree.f00bar.game.GameView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        start();
    }

    private void start(){
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
            communicator.txhcfmprvq(new APICommunicator.APICallback() {
                @Override
                public void onResult(String result) {
                    start();
                }

                @Override
                public void onError(String error) {

                }
            });
        } else {
            GameView gameView = new GameView(this);
            communicator.xdksmjpssv(new APICommunicator.APICallback() {
                @Override
                public void onResult(String result) {

                }

                @Override
                public void onError(String error) {

                }
            });
        }
    }

}
