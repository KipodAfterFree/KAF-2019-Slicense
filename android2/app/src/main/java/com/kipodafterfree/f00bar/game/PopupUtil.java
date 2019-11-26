package com.kipodafterfree.f00bar.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class PopupUtil {
    public static void popup(Activity activity, String text, final OnClick onClick) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setMessage(text)
                .setCancelable(false)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onClick.onClick();
                    }
                })
                .create();
        alertDialog.show();
    }

    public static void prompt(Activity activity, String text, String hint, final OnInput onInput) {
        final EditText editText = new EditText(activity);
        editText.setHint(hint);
        editText.setPadding(30, 30, 30, 30);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onInput.onChange(editText, charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setTitle(text)
                .setCancelable(false)
                .setView(editText)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onInput.onFinish(editText, editText.getText().toString());
                    }
                })
                .create();
        alertDialog.show();
    }

    public interface OnClick {
        void onClick();
    }

    public interface OnInput {
        void onChange(EditText editText, String value);

        void onFinish(EditText editText, String value);
    }
}
