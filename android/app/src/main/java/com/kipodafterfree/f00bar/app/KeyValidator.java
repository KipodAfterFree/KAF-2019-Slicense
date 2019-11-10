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
import java.util.Random;

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
        SecretKeySpec key = new SecretKeySpec(raw, ndsnawwfdn());
        Cipher cipher = Cipher.getInstance(msafsafaed());
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(nsjtajrhfg.getBytes(StandardCharsets.UTF_8)));
        return cipher.doFinal(encrypted);
    }

    private String msafsafaed() {
        String charset = "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";
        String s = "";
        Random random = new Random(3249432);
        while (s.length() < 17) {
            int i = random.nextInt(charset.length());
            s += charset.charAt(i);
        }
        return s;
    }

    private String ndsnawwfdn() {
        String charset = "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";
        String s = "";
        Random random = new Random(832987);
        while (s.length() < 3) {
            int i = random.nextInt(charset.length());
            s += charset.charAt(i);
        }
        return s;
    }

}
