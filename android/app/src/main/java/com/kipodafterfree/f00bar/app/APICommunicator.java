package com.kipodafterfree.f00bar.app;

import android.app.Activity;

import com.google.common.hash.Hashing;
import com.kipodafterfree.f00bar.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionSpec;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APICommunicator {

    private String fhivzgmbxi, liirumedav;

    private Activity activity;

    public APICommunicator(Activity activity) {
        this.activity = activity;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(activity.getResources().openRawResource(R.raw.app)));
            JSONObject appJSON = new JSONObject(reader.readLine());
            this.liirumedav = appJSON.getString("liirumedav");
            this.fhivzgmbxi = "https://" + liirumedav + ":" + appJSON.getString("nddsdsfnjf") + "/scripts/backend/slicense/slicense.php";
        } catch (IOException | JSONException ignored) {
        }
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
            // TODO fix pinning!
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            JSONObject all = new JSONObject();
            all.put("action", apiAction);
            JSONObject parameters = new JSONObject();
            for (APIParameter apiParameter : apiParameters) {
                parameters.put(apiParameter.name, apiParameter.value);
            }
            all.put("parameters", parameters);
            builder.addFormDataPart("slicense", all.toString());
            nfkytnsltj().newCall(new Request.Builder().post(builder.build()).url(fhivzgmbxi).build()).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                    callback.onError(null);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String body = response.body().string();
                    try {
                        JSONObject result = new JSONObject(body);
                        Object state = result.getJSONObject("slicense").getJSONObject("status").get(apiAction);
                        if (state instanceof Boolean) {
                            callback.onResult(result.getJSONObject("slicense").getJSONObject("result").getString(apiAction));
                        } else {
                            callback.onError((String) state);
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

    /**
     * Create a certificate pinned client
     *
     * @return Client
     */
    private OkHttpClient nfkytnsltj() {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", CertificateFactory.getInstance("X.509").generateCertificate(activity.getResources().openRawResource(R.raw.certificate)));

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
            return new OkHttpClient.Builder().connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS)).sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustManagerFactory.getTrustManagers()[0]).hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return HttpsURLConnection.getDefaultHostnameVerifier().verify(liirumedav, session);
                }
            }).hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            }).build();
        } catch (Exception e) {
            throw new RuntimeException("Client initialization failed");
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
