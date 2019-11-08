package com.kipodafterfree.f00bar.game;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import com.kipodafterfree.f00bar.app.APICommunicator;

public class GameView extends WebView {
    public GameView(final Activity activity) {
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
                PopupUtil.popup(activity, text, new PopupUtil.OnClick() {
                    @Override
                    public void onClick() {

                    }
                });
            }

            @JavascriptInterface
            public void purchase() {
                PopupUtil.popup(activity, "To continue, purchase the app.", new PopupUtil.OnClick() {
                    @Override
                    public void onClick() {
                        PopupUtil.prompt(activity, "Enter your key", new PopupUtil.OnInput() {
                            @Override
                            public void onChange(EditText editText, String value) {

                            }

                            @Override
                            public void onFinish(EditText editText, String value) {
                                APICommunicator communicator = new APICommunicator(activity);
                            }
                        });
                    }
                });
            }
        }, "slicanse");
        // Setup Javascript
        getSettings().setJavaScriptEnabled(true);
    }

    public void loadGame(String html) {
        loadData(html, "text/html", "UTF-8");
    }
}
