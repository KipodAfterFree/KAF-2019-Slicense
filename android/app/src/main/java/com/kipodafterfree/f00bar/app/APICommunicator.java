package com.kipodafterfree.f00bar.app;

import android.app.Activity;
import android.view.View;

import com.google.common.hash.Hashing;
import com.kipodafterfree.f00bar.game.PopupUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CertificatePinner;
import okhttp3.ConnectionSpec;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APICommunicator {

//    private static final String fhivzgmbxi = "https://ctf.kipodafterfree.com:3579/scripts/backend/slicanse/slicanse.php";
    private static final String fhivzgmbxi = "https://enoz.zone:8000/scripts/backend/slicense/slicense.php";
//    private static final String liirumedav = "ctf.kipodafterfree.com";
    private static final String liirumedav = "enoz.zone";

    private Activity activity;

    public APICommunicator(Activity activity) {
        this.activity = activity;
    }

    /**
     * This function sends signed custom API requests.
     *
     * @param action
     * @param parameters
     * @param callback
     */
    public void foupmowqbo(String action, APIParameter[] parameters, APICallback callback) {
        try {
            AppIntegrityGuard appIntegrityGuard = new AppIntegrityGuard(activity);
            PreferenceManager preferenceManager = new PreferenceManager(activity);

            APIParameter[] mimicedParameters = new APIParameter[3];
            JSONObject jsonObject = new JSONObject();
            for (APIParameter parameter : parameters) {

                jsonObject.put(parameter.name, parameter.value);

            }
            mimicedParameters[0] = new APIParameter("parameters", jsonObject.toString());
            mimicedParameters[1] = new APIParameter("hash", Hashing.hmacSha256((preferenceManager.modrnzzhxp() + appIntegrityGuard.sbvoxfhuul()).getBytes()).hashString(jsonObject.toString(), StandardCharsets.UTF_8).toString());
            mimicedParameters[2] = new APIParameter("client", preferenceManager.modrnzzhxp());
            kdirlwzpou(action, mimicedParameters, callback);
        } catch (Exception ignored) {
        }
    }

    /**
     * This function loads the game from the API.
     *
     * @param callback Callback in which to return the game HTML.
     */
    public void xdksmjpssv(APICallback callback) {
        foupmowqbo("game", new APIParameter[0], callback);
    }

    /**
     * This function sends a session initialize API request.
     *
     * @param callback
     */
    public void txhcfmprvq(final APICallback callback) {
        kdirlwzpou("client", new APIParameter[0], new APICallback() {
            @Override
            public void onResult(String result) {
                new PreferenceManager(activity).zgpgifjkot(result);
                callback.onResult(result);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    /**
     * Preform a basic API call
     */
    private void kdirlwzpou(final String apiAction, APIParameter[] apiParameters, final APICallback callback) {
        try {
            CertificatePinner certificatePinner = new CertificatePinner.Builder()
                    .add(liirumedav, "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=")
                    .add(liirumedav, "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=")
                    .build();
            OkHttpClient client = new OkHttpClient.Builder().certificatePinner(certificatePinner).build();
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            JSONObject all = new JSONObject();
            all.put("action", apiAction);
            JSONObject parameters = new JSONObject();
            for (APIParameter apiParameter : apiParameters) {
                parameters.put(apiParameter.name, apiParameter.value);
            }
            all.put("parameters", parameters);
            builder.addFormDataPart("slicanse", all.toString());
            client.newCall(new Request.Builder().post(builder.build()).url(fhivzgmbxi).build()).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    callback.onError(null);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String body = response.body().string();
                    try {
                        JSONObject result = new JSONObject(body);
                        if (result.getJSONObject("slicanse").getJSONObject("state").getBoolean(apiAction)) {
                            callback.onResult(result.getJSONObject("slicanse").getJSONObject("result").getString(apiAction));
                        } else {
                            callback.onError(result.getJSONObject("slicanse").getJSONObject("result").getString(apiAction));
                        }
                    } catch (Exception ignored) {
                        callback.onError(null);
                    }

                }
            });
        } catch (Exception ignored) {
            callback.onError(null);
        }
    }

    public static class APIParameter {
        private String name;
        private String value;

        public APIParameter(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

    public interface APICallback {
        void onResult(String result);

        void onError(String error);
    }

}
