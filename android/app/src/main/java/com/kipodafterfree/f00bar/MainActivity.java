package com.kipodafterfree.f00bar;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.kipodafterfree.f00bar.app.APICommunicator;
import com.kipodafterfree.f00bar.app.AppIntegrityGuard;
import com.kipodafterfree.f00bar.app.PreferenceManager;
import com.kipodafterfree.f00bar.game.GameView;
import com.kipodafterfree.f00bar.game.PopupUtil;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        start();
    }

    private void start() {
        try {
            loadGame();
        } catch (IOException | NoSuchAlgorithmException | PackageManager.NameNotFoundException ignored) {
        }
    }

    private void loadGame() throws NoSuchAlgorithmException, PackageManager.NameNotFoundException, IOException {
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
            final GameView gameView = new GameView(this);
            communicator.xdksmjpssv(new APICommunicator.APICallback() {
                @Override
                public void onResult(String result) {
                    gameView.loadGame(result);
                }

                @Override
                public void onError(String error) {
                    PopupUtil.popup(MainActivity.this, "Unable to load game", new PopupUtil.OnClick() {
                        @Override
                        public void onClick() {
                            finish();
                        }
                    });
                }
            });
        }
    }

}
