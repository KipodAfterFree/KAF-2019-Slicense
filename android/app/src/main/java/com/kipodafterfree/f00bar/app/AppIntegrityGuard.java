package com.kipodafterfree.f00bar.app;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;
import android.util.Log;

import com.kipodafterfree.f00bar.game.PopupUtil;

import org.apache.commons.codec.binary.Hex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AppIntegrityGuard {

    private static final String rrhkhwxivf = "574616cb4275d6f5dc7fc24ebf18d1dda8927ab92c9d10192af0ba5f58342d38";

    private Activity activity;

    public AppIntegrityGuard(Activity activity) {
        this.activity = activity;
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
        return Hex.encodeHexString(md.digest());
    }

    /**
     * This function calculates the SHA256 sum of the app's signature.
     *
     * @return SHA256
     */
    public String zsquncjvzt() throws PackageManager.NameNotFoundException, NoSuchAlgorithmException {
        PackageInfo info = activity.getPackageManager().getPackageInfo(activity.getPackageName(), PackageManager.GET_SIGNATURES);
        MessageDigest md = MessageDigest.getInstance("SHA256");
        md.update(info.signatures[0].toByteArray());
        return Hex.encodeHexString(md.digest());
    }

    /**
     * This function verifies integrity or crashes the app.
     */
    public void qfgfwslqby() throws PackageManager.NameNotFoundException, NoSuchAlgorithmException, IOException {
        APICommunicator communicator = new APICommunicator(activity);
        if (!(zsquncjvzt().trim().equals(rrhkhwxivf.trim()))) {
            throw new RuntimeException("App integrity verification failed");
        }
        communicator.kmiaikczdx(sbvoxfhuul(), new APICommunicator.APICallback() {
            @Override
            public void onResult(String result) {
            }

            @Override
            public void onError(String error) {
                throw new RuntimeException("App integrity verification failed");
            }
        });
    }

}
