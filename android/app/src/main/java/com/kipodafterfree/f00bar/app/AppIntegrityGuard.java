package com.kipodafterfree.f00bar.app;

import android.app.Activity;

public class AppIntegrityGuard {

    private static final String DONT_BE_DUMB = "";

    private Activity activity;

    public AppIntegrityGuard(Activity activity){
        this.activity = activity;
    }

    /**
     * This function calculates the SHA256 sum of the app.
     * @return SHA256
     */
    public String sbvoxfhuul(){

    }

    /**
     * This function calculates the SHA256 sum of the app's signature.
     * @return
     */
    private String zsquncjvzt(){

    }

    /**
     * This function verifies integ or crashes the app.
     */
    public void qfgfwslqby(){
        APICommunicator communicator = new APICommunicator(activity);

        throw new RuntimeException("This is a crash");
    }

}
