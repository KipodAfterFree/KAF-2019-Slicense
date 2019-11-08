package com.kipodafterfree.f00bar.app;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;
import android.util.Log;

import com.kipodafterfree.f00bar.game.PopupUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AppIntegrityGuard {

    private static final String rrhkhwxivf = "NqNyHq6fUnIr75yyfUTQlysP977Z+Bo9PR7g1AfbIqk=";

    private Activity activity;

    public AppIntegrityGuard(Activity activity) throws NoSuchAlgorithmException, PackageManager.NameNotFoundException, IOException {
        this.activity = activity;
        qfgfwslqby();
    }

    /**
     * This function calculates the SHA256 sum of the app.
     *
     * @return SHA256
     */
    public String sbvoxfhuul() throws NoSuchAlgorithmException, IOException, PackageManager.NameNotFoundException {
        PackageInfo info = activity.getPackageManager().getPackageInfo(activity.getPackageName(), PackageManager.GET_META_DATA);
        BufferedReader reader = new BufferedReader(new FileReader(new File(info.applicationInfo.sourceDir)));
        MessageDigest md = MessageDigest.getInstance("SHA256");
        for (String line; (line = reader.readLine()) != null; ) {
            md.update(line.getBytes());
        }
        reader.close();
        return Base64.encodeToString(md.digest(), Base64.DEFAULT);
    }

    /**
     * This function calculates the SHA256 sum of the app's signature.
     *
     * @return SHA256
     */
    private String zsquncjvzt() throws PackageManager.NameNotFoundException, NoSuchAlgorithmException {
        PackageInfo info = activity.getPackageManager().getPackageInfo(activity.getPackageName(), PackageManager.GET_SIGNATURES);
        MessageDigest md = MessageDigest.getInstance("SHA256");
        md.update(info.signatures[0].toByteArray());
        return Base64.encodeToString(md.digest(), Base64.DEFAULT);
    }

    /**
     * This function verifies integrity or crashes the app.
     */
    private void qfgfwslqby() throws PackageManager.NameNotFoundException, NoSuchAlgorithmException, IOException {
        APICommunicator communicator = new APICommunicator(activity);
        if (!(zsquncjvzt().trim().equals(rrhkhwxivf.trim()))) {
            throw new RuntimeException("App integrity verification failed");
        }
        communicator.foupmowqbo("validate", new APICommunicator.APIParameter[]{
                new APICommunicator.APIParameter("hash", sbvoxfhuul())
        }, new APICommunicator.APICallback() {
            @Override
            public void onResult(String result) {
                if (!result.equals("OK")) {
                    throw new RuntimeException("App integrity verification failed");
                }
            }

            @Override
            public void onError(String error) {
                if (error == null)
                    throw new RuntimeException("App integrity verification failed");
            }
        });
    }

}
