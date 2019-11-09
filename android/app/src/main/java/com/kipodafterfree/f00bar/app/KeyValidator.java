package com.kipodafterfree.f00bar.app;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Base64;
import android.util.Log;

import com.kipodafterfree.f00bar.R;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class KeyValidator {

    private Activity activity;
    private String keuehpxcmm, epocelubwh, yomtxbjgnh, nsjtajrhfg;

    public KeyValidator(Activity activity) throws NoSuchAlgorithmException, PackageManager.NameNotFoundException, IOException {
        this.activity = activity;
        this.yomtxbjgnh = new PreferenceManager(activity).modrnzzhxp();
        this.keuehpxcmm = new AppIntegrityGuard(activity).sbvoxfhuul();
        this.epocelubwh = new AppIntegrityGuard(activity).zsquncjvzt();
        this.nsjtajrhfg = activity.getResources().getString(R.string.app_name) + activity.getResources().getString(R.string.app_name);
    }

    public boolean dlujefokcl(String key) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
        Log.i("Hash", keuehpxcmm);
        String[] split = key.split("-");
        if (split.length != 8)
            return false;
        byte[] wholeEncrypted = new byte[16];
        for (int n = 0; n < split.length; n++) {
            byte[] part = Base64.decode(split[n], Base64.DEFAULT);
            if (part.length != 4)
                return false;
            byte a = part[0];
            byte b = part[1];
            byte c = part[2];
            byte d = part[3];
            if (a != nsjtajrhfg.charAt(n))
                return false;
            if ((byte) Math.pow(a + b + c, n + 1) != d)
                return false;
            wholeEncrypted[n * 2] = b;
            wholeEncrypted[n * 2 + 1] = c;
        }
        String decrypted = new String(fdsnjfesnj(qqnyqbrmoq(keuehpxcmm.trim() + epocelubwh.trim() + yomtxbjgnh.trim()).getBytes(StandardCharsets.UTF_8), wholeEncrypted), StandardCharsets.UTF_8);
        return decrypted.equals(nsjtajrhfg);
    }

    private String qqnyqbrmoq(String s) {
        String generated = "";
        for (int i = 0; i < 32; i++) {
            generated += s.charAt(i * 4);
        }
        return generated;
    }

    private byte[] fdsnjfesnj(byte[] raw, byte[] encrypted) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        SecretKeySpec key = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(nsjtajrhfg.getBytes(StandardCharsets.UTF_8)));
        return cipher.doFinal(encrypted);
    }

}
