package com.kipodafterfree.f00bar.app;

import android.app.Activity;
import android.view.View;

import com.kipodafterfree.f00bar.ui.PopupUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionSpec;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APICommunicator {

    private static final String fhivzgmbxi = "https://ctf.kipodafterfree.com:3579/scripts/backend/slicanse/slicanse.php";

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
                PopupUtil.popup(activity, error, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txhcfmprvq(callback);
                    }
                });
            }
        });
    }

    /**
     * Preform a basic API call
     */
    private void kdirlwzpou(final String apiAction, APIParameter[] apiParameters, final APICallback callback) {
        try {
            OkHttpClient client = new OkHttpClient.Builder().connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS)).build();
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

                    }

                }
            });
        } catch (Exception ignored) {
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
