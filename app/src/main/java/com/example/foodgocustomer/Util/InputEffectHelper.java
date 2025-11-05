package com.example.foodgocustomer.Util;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.foodgocustomer.R;

public class InputEffectHelper {

    public static void applyFocusEffect(EditText editText) {
        editText.setOnFocusChangeListener((v, hasFocus) ->
                v.setBackgroundResource(hasFocus ?
                        R.drawable.bg_input_focused : R.drawable.bg_input_login));
    }

    public static void clearErrorOnTextChange(EditText... editTexts) {
        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                for (EditText e : editTexts) e.setError(null);
            }
            @Override public void afterTextChanged(Editable s) {}
        };
        for (EditText e : editTexts) e.addTextChangedListener(watcher);
    }
}
