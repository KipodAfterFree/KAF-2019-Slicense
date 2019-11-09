package com.kipodafterfree.f00bar.app;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Base64;
import android.util.Log;

import com.kipodafterfree.f00bar.R;
import com.kipodafterfree.f00bar.game.PopupUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
    private String keuehpxcmm, epocelubwh, yomtxbjgnh;

    public KeyValidator(Activity activity) throws NoSuchAlgorithmException, PackageManager.NameNotFoundException, IOException {
        this.activity = activity;
        this.yomtxbjgnh = new PreferenceManager(activity).modrnzzhxp();
        this.keuehpxcmm = new AppIntegrityGuard(activity).sbvoxfhuul();
        this.epocelubwh = new AppIntegrityGuard(activity).zsquncjvzt();
    }

    public boolean dlujefokcl(String key) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        String clientKey = keuehpxcmm + yomtxbjgnh;
        String appSigKey = epocelubwh + yomtxbjgnh;
        String name = activity.getResources().getString(R.string.app_name);
        Log.i("Sig", epocelubwh);
        Log.i("Has", keuehpxcmm);
        Log.i("Cli", yomtxbjgnh);
        String[] split = key.split("-");
        Log.i("len", split.length+"");
        if (split.length != 6)
            return false;
        for (int part = 0; part < split.length; part++) {
            Log.i("Hi", "HIII");
            byte[] kpart = Base64.decode(split[part], Base64.DEFAULT);
//            if (kpart.length != 33)
//                return false;
//            if (kpart[0] != name.charAt(part))
//                return false;
            byte[] foobarEncrypted = new byte[16];
            System.arraycopy(kpart, 1, foobarEncrypted, 0, foobarEncrypted.length);
            Log.i("foobar", "foo "+new String(decrypt(qqnyqbrmoq(clientKey).getBytes(), foobarEncrypted)));
            if (!Arrays.equals(decrypt(qqnyqbrmoq(clientKey).getBytes(), foobarEncrypted), name.substring(part, part + 2).getBytes()))
                return false;
            byte[] partEncrypted = new byte[16];
            System.arraycopy(kpart, 1 + foobarEncrypted.length, partEncrypted, 0, partEncrypted.length);
            if (!Arrays.equals(decrypt(qqnyqbrmoq(appSigKey).getBytes(), partEncrypted), String.valueOf(part).getBytes()))
                return false;

        }
        return true;
    }

    private String qqnyqbrmoq(String s) {
        String generated = "";
        for (int i = 0; i < 32; i++) {
            generated += s.charAt(i * 4);
        }
        return generated;
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
