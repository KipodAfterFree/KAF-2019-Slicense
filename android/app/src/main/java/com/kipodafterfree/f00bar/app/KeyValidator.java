package com.kipodafterfree.f00bar.app;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Base64;

import com.kipodafterfree.f00bar.R;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class KeyValidator {

    private Activity activity;

    public KeyValidator(Activity activity) {
        this.activity = activity;
    }

    public boolean dlujefokcl(String key) throws NoSuchAlgorithmException, PackageManager.NameNotFoundException, IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {
        AppIntegrityGuard appIntegrityGuard = new AppIntegrityGuard(activity);
        String client = new PreferenceManager(activity).modrnzzhxp();
        String clientKey = client + appIntegrityGuard.sbvoxfhuul();
        String appSigKey = client + appIntegrityGuard.zsquncjvzt();
        String name = activity.getResources().getString(R.string.app_name);
        String[] split = key.split("-");
        if (split.length != 6)
            return false;
        for (int part = 0; part < split.length; part++) {
            byte[] kpart = Base64.decode(split[part], Base64.DEFAULT);
            if (kpart.length != 5)
                return false;
            if (kpart[0] != name.charAt(part))
                return false;
            byte[] AESA = new byte[]{kpart[1], kpart[2]};
            if (Arrays.equals(AESA, decrypt(clientKey.getBytes(), name.substring(part, part + 2).getBytes())))
                return false;
            if (kpart[3] != decrypt(appSigKey.getBytes(), String.valueOf(part).getBytes())[0])
                return false;
        }
        return true;
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        return cipher.doFinal(clear);
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        return cipher.doFinal(encrypted);
    }

}
