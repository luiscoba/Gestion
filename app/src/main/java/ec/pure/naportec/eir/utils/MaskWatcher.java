package ec.pure.naportec.eir.utils;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import ec.pure.naportec.eir.R;

import static android.view.KeyEvent.KEYCODE_BACK;

// con esta clase podemos crear cualquier mascara para el ingreso de datos
public class MaskWatcher implements TextWatcher { // clase sacada de MEDIUM
    private boolean isRunning = false;
    //    private boolean isDeleting = false;
    private EditText mEditText;

    public MaskWatcher(EditText editText) {
        this.mEditText = editText;
    }

/*    public static MaskWatcher buildCpf() {
        return new MaskWatcher("###.###.###-##");
    }*/

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        //       isDeleting = count > after;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (isRunning) {// || isDeleting) {
            return;
        }
        isRunning = true;

        int editableLength = editable.length();
        // reset error
        mEditText.setError(null);

        boolean cancel = false;
        View focusView = null;

//        System.out.println("editableLength " + editableLength);
        if (editableLength >=3) {
            mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            mEditText.setInputType(InputType.TYPE_CLASS_TEXT);
            if (editable.toString().matches("^[0-9]*$")) {
                mEditText.setError("no ingresar numeros");
                focusView = mEditText;
                cancel = true;
            }
        }
        if (cancel) {
            focusView.requestFocus();
        }

        isRunning = false;
    }

}