package ec.pure.naportec.eir.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;

public class CustomProgressBar {

    private static CustomProgressBar customProgressBar;
    private Typeface typeface;
    private ProgressDialog progressDialog;

    public static CustomProgressBar getInstance(){

        if(customProgressBar == null){
            customProgressBar = new CustomProgressBar();
        }
        return customProgressBar;
    }

    public void showProgressDialog(Activity activity){

        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
            progressDialog = null;
        }

        if(progressDialog == null ) {
            progressDialog = new ProgressDialog(activity);
        }
        progressDialog.setMessage("Cargando...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        if(!activity.isFinishing())
            progressDialog.show();
    }

    public void closeProgressDialog(){
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}