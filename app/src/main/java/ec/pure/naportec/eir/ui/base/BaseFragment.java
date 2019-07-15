package ec.pure.naportec.eir.ui.base;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import ec.pure.naportec.eir.AppConstants;

public class BaseFragment extends Fragment implements AppConstants {

    protected Activity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
