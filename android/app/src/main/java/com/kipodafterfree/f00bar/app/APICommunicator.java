package com.kipodafterfree.f00bar.app;

import android.app.Activity;

import java.util.ArrayList;

public class APICommunicator {

    private Activity activity;

    public APICommunicator(Activity activity) {
        this.activity = activity;
    }

    public void send(String action, ArrayList<APIParameter> parameters, APICallback callback) {

    }

    public class APIParameter {
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
