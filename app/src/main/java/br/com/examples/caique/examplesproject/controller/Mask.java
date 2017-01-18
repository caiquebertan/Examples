package br.com.examples.caique.examplesproject.controller;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by FBisca on 24/09/2015.
 */
public class Mask implements TextWatcher {

    public static final String LEGAL_MASK = "###.###.###-##";
    public static final String DATE_MASK = "##/##/####";
    public static final String DATE_POSTAL = "#####-###";

    private String mMask;
    private EditText mField;

    public Mask(String mMask, EditText mField) {
        this.mMask = mMask;
        this.mField = mField;
        this.mField.addTextChangedListener(this);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int selection = mField.getSelectionEnd();
        mField.removeTextChangedListener(this);
        String textChanged = s.toString();

        CharSequence digit = s.subSequence(start, start + count);

        textChanged = textChanged.replaceAll("\\D","");

        char[] maskArray = mMask.toCharArray();
        char[] newString = new char[maskArray.length];

        int i = 0;
        for (char c : textChanged.toCharArray()) {
            while (i < maskArray.length
                    && maskArray[i] != '#') {
                newString[i] = maskArray[i];
                i++;
            }

            if (i < maskArray.length
                    && maskArray[i] == '#') {
                newString[i] = c;
            }
            i++;
        }

        if (i == start
                && i > 0
                && i < maskArray.length
                && digit.length() > 0
                && maskArray[i] == digit.charAt(0)) {
            newString[i] = digit.charAt(0);
        }

        String newText = String.valueOf(newString).trim();
        mField.setText(newText);
        mField.addTextChangedListener(this);

        if (selection - 1 > 0
                && selection < maskArray.length
                && maskArray[selection - 1] != '#') {
            selection += 1;
        }

        if (selection > newText.length()) {
            selection = newText.length();
        }

        mField.setSelection(selection);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void close() {
        if (this.mField != null) {
            this.mField.removeTextChangedListener(this);
            this.mField = null;
        }
    }
}
