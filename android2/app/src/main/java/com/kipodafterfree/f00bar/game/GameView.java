package com.kipodafterfree.f00bar.game;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import com.kipodafterfree.f00bar.app.APICommunicator;
import com.kipodafterfree.f00bar.app.KeyValidator;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

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
                try {
                    final KeyValidator keyValidator = new KeyValidator(activity);
                    PopupUtil.prompt(activity, "To continue, purchase the app.", "License Key", new PopupUtil.OnInput() {
                        @Override
                        public void onChange(EditText editText, String value) {
                            try {
                                if (keyValidator.dlujefokcl(value)) {
                                    editText.setTextColor(Color.GREEN);
                                } else {
                                    editText.setTextColor(Color.RED);
                                }
                            } catch (Exception ignored) {

                            }
                        }

                        @Override
                        public void onFinish(EditText editText, String value) {
                            APICommunicator communicator = new APICommunicator(activity);
                            communicator.pagqjfpber(value, new APICommunicator.APICallback() {
                                @Override
                                public void onResult(String result) {
                                    if (result.equals("OK")) {
                                        activity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                PopupUtil.popup(activity, "License activated!, please reopen the app.", new PopupUtil.OnClick() {
                                                    @Override
                                                    public void onClick() {
                                                        activity.finish();
                                                    }
                                                });
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onError(String error) {
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            PopupUtil.popup(activity, "License not activated!, please try again!", new PopupUtil.OnClick() {
                                                @Override
                                                public void onClick() {
                                                    activity.runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            purchase();
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
                } catch (Exception ignored) {

                }
            }
        }, "slicense");
        // Setup Javascript
        getSettings().setJavaScriptEnabled(true);
    }

    public void loadGame(String html) {
        loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
    }
}
