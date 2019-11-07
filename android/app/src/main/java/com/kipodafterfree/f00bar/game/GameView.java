package com.kipodafterfree.f00bar.game;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class GameView extends WebView {
    public GameView(Activity activity) {
        super(activity);
        // Setup client
        setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
        // Setup Javascript theme interface
        addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void popup(String text) {

            }
        }, "slicanse");
        // Setup Javascript
        getSettings().setJavaScriptEnabled(true);
    }

    public void loadGame(String html) {
        loadData(html, "text/html", "UTF-8");
    }
}
