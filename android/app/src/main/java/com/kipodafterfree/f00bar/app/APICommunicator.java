package com.kipodafterfree.f00bar.app;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.google.common.hash.Hashing;
import com.kipodafterfree.f00bar.game.PopupUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.util.Arrays;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
     * This function sends a hash validation request.
     *
     * @param hash
     * @param callback
     */
    public void kmiaikczdx(String hash, final APICallback callback) {
        kdirlwzpou("validate", new APICommunicator.APIParameter[]{
                new APICommunicator.APIParameter("hash", hash)
        }, new APICommunicator.APICallback() {
            @Override
            public void onResult(String result) {
                if (!result.equals("OK")) {
                    callback.onError(null);
                } else {
                    callback.onResult(null);
                }
            }

            @Override
            public void onError(String error) {
                callback.onError(null);
            }
        });
    }

    /**
     * This function sends a license request.
     *
     * @param license
     * @param callback
     */
    public void pagqjfpber(final String license, APICallback callback) {
        foupmowqbo("license", new APIParameter[]{new APIParameter("license", license)}, callback);
    }

    /**
     * Preform a basic API call
     */
    private void kdirlwzpou(final String apiAction, APIParameter[] apiParameters, final APICallback callback) {
        try {
//            CertificatePinner certificatePinner = new CertificatePinner.Builder()
//                    .add(liirumedav, "sha256/0bEHWlqhuJxLu4C4baG37jXrguiUAdS94qQgHYH8P8M")
//                    .build();
//            OkHttpClient client = new OkHttpClient.Builder().certificatePinner(certificatePinner).build();

//            OkHttpClient client = new OkHttpClient.Builder().connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS)).build();
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            JSONObject all = new JSONObject();
            all.put("action", apiAction);
            JSONObject parameters = new JSONObject();
            for (APIParameter apiParameter : apiParameters) {
                parameters.put(apiParameter.name, apiParameter.value);
            }
            all.put("parameters", parameters);
            builder.addFormDataPart("slicense", all.toString());
            getClient().newCall(new Request.Builder().post(builder.build()).url(fhivzgmbxi).build()).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    callback.onError(null);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String body = response.body().string();
//                    Log.i("OKHTTP", body);
                    try {
                        JSONObject result = new JSONObject(body);
                        if (result.getJSONObject("slicense").getJSONObject("status").getBoolean(apiAction)) {
                            callback.onResult(result.getJSONObject("slicense").getJSONObject("result").getString(apiAction));
                        } else {
                            callback.onError(result.getJSONObject("slicense").getJSONObject("result").getString(apiAction));
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

    private static OkHttpClient getClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
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
