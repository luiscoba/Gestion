package ec.pure.naportec.eir.ui.drawernavigator.sesion;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import ec.pure.naportec.eir.AppConstants;
import ec.pure.naportec.eir.Global;
import ec.pure.naportec.eir.data.local.entity.ReferenciaEntity;
import ec.pure.naportec.eir.data.remote.model.LoginApiResponse;
import ec.pure.naportec.eir.data.remote.api.ApiServiceDole;
import ec.pure.naportec.eir.factory.ViewModelFactory;
import ec.pure.naportec.eir.ui.base.BaseActivity;
import ec.pure.naportec.eir.utils.AddItemSpinnerDialogFragment;
import ec.pure.naportec.eir.utils.DialogoLista;
import ec.pure.naportec.eir.utils.OnSwipeTouchListener;
import ec.pure.naportec.eir.utils.SearchableAdapter;
import ec.pure.naportec.eir.ui.main.MainActivity;
import ec.pure.naportec.eir.R;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends BaseActivity {

    // ViewModelFactory se utiliza para inyectar los viewmodels en los activitis/fragments
    @Inject
    ViewModelFactory viewModelFactory;

    ReferenciaViewModel referenciaViewModel;
    ParamciaViewModel paramciaViewModel;

    private static final String TAG = "eir." + LoginActivity.class.getSimpleName();

    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView vwUsuario;
    private EditText edtClave;
    private View mProgressView;
    private View mLoginFormView;

    private static final int MY_PERMISSIONS_READ_STATE = 1;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 2;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 3;


    private SearchableAdapter mSearchableAdapter;
    private ApiServiceDole mServiceDole;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // utilizamos AndroidInjection para hacer la inyeccion en activities
        AndroidInjection.inject(this);
        referenciaViewModel = ViewModelProviders.of(this, viewModelFactory).get(ReferenciaViewModel.class);
        paramciaViewModel = ViewModelProviders.of(this, viewModelFactory).get(ParamciaViewModel.class);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //nos permite ver en el log las peticiones que se hace hacia el servidor
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
        Retrofit restAdapter = new Retrofit.Builder()
                .client(client)
                .baseUrl(AppConstants.ROOT_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mServiceDole = restAdapter.create(ApiServiceDole.class);

        setContentView(R.layout.activity_login);

        ImageView imageViewLogo = findViewById(R.id.img_naportec);
        imageViewLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vwUsuario.setText("puretech");
                edtClave.setText("pure");
            }
        });

        LinearLayout layoutPrueba = findViewById(R.id.layout_prueba);
//        layoutPrueba.setVisibility(View.INVISIBLE);
        layoutPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogoLista dialogAgregar = DialogoLista.newInstance("TAMAÑO CONTENEDOR", "Agregue un nuevo tamaño");

                dialogAgregar.show(getSupportFragmentManager(), TAG);

            }
        });
/*
        layoutPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String carArr[] = getResources().getStringArray(R.array.lista_de_prueba);
                ArrayList<String> myList = new ArrayList(Arrays.asList(carArr));

                LayoutInflater factory = LayoutInflater.from(LoginActivity.this);
                final View textEntryView = factory.inflate(R.layout.table_layout2, null);
                final ListView lv_validate = (ListView) textEntryView.findViewById(R.id.lv_validate);
                final EditText et_buscar = (EditText) textEntryView.findViewById(R.id.et_buscar_table);

                Comparator<String> ALPHABETICAL_ORDER = new Comparator<String>() {
                    public int compare(String str1, String str2) {
                        int res = String.CASE_INSENSITIVE_ORDER.compare(str1, str2);
                        if (res == 0) {
                            res = str1.compareTo(str2);
                        }
                        return res;
                    }
                };
                Collections.sort(myList, ALPHABETICAL_ORDER);
                //inicializamos el arraydapter
                mSearchableAdapter = new SearchableAdapter(LoginActivity.this, myList);
                lv_validate.setTextFilterEnabled(true);
                lv_validate.setAdapter(mSearchableAdapter);
                final TextView[] txtview = new TextView[1];

                lv_validate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        System.out.println("valor seleccionado position " + position);
                        txtview[0] = view.findViewById(R.id.listTextView);
                        System.out.println("valor " + txtview[0].getText());

                        lv_validate.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
                            @Override
                            public void onSwipeLeft() {
                                // Whatever
                                System.out.println("se desliza a la izquierda ");
                                System.out.println("se desliza el dato "+txtview[0].getText().toString());
                                // eliminamos el dato del arreglo
                                myList.remove(txtview[0].getText().toString());
                                mSearchableAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
                et_buscar.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        System.out.println("Text [" + s + "]");
                        mSearchableAdapter.getFilter().filter(s.toString());
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                final AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                alert.setMessage("mensaje");
                alert.setTitle("titulo ")
                        .setView(textEntryView)
                        .setPositiveButton(getResources().getString(R.string.text_aceptar),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        System.out.println("aqui va click!");
                                    }
                                }).setNegativeButton(getResources().getString(R.string.text_cancelar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
                alert.show();
                alert.setCancelable(false);

            }
        });
*/

        //Inicio de permiso
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
        //Fin de permiso

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // Set up the login form.
        vwUsuario = findViewById(R.id.usuario);
        edtClave = findViewById(R.id.clave);
        edtClave.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin(textView);
                    return true;
                }
                return false;
            }
        });

        Button inicia_sesion = findViewById(R.id.inicia_sesion);
        inicia_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isAirplaneModeOn(getApplicationContext())) {// verificamos que no tenga activado el modo avión
                    Snackbar.make(view, "Quite el modo avión", Snackbar.LENGTH_LONG)
                            .setAction("VER CONFIGURACIÓN", view2 -> {
                                startActivity(new Intent("android.settings.WIRELESS_SETTINGS")
                                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                            }).show();
                } else if (!isOnline()) { // verificamos que tenga wifi
                    Snackbar.make(view, "Revise su conexión de internet", Snackbar.LENGTH_LONG)
                            .setAction("VER CONFIGURACIÓN", view2 -> {
                                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                            }).show();
                } else {
                    attemptLogin(view);
                }
            }
        });

 /*       Button btnPrueba = findViewById(R.id.button3);
          btnPrueba.setOnClickListener(view -> {
          referenciaViewModel.insertarReferencia("","","");
            referenciaViewModel.obtenerReferenciaInsertadaLiveData().observe(this, resource -> {

                System.out.println("entro al observe login ");
                if (resource.isLoading()) {
                    System.out.println("login is loading");
                } else if (resource.data != null) {
                    System.out.println("resource.data " + resource.data);
                    imprimeDatoInsertado(resource.data);
                }
            });
        });
*/
        mLoginFormView = findViewById(R.id.sellos_form);
        mProgressView = findViewById(R.id.progress_bar);
    }

    private void imprimeDatoInsertado(ReferenciaEntity referenciaEntity) {
        System.out.println("aqui va ReferenciaEntity.getGenrciDescripcion " + referenciaEntity.getGenrciDescripcion());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void attemptLogin(View rootView) {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        vwUsuario.setError(null);
        edtClave.setError(null);

        // Store values at the time of the login attempt.
        String usuario = vwUsuario.getText().toString();
        String clave = edtClave.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(clave)) {
            // si usuario es vacio
            edtClave.setError(getString(R.string.error_clave_vacio));
            focusView = edtClave;
            cancel = true;
        } else if (longitudValida(clave)) {
            // si la clave no es valida
            edtClave.setError(getString(R.string.error_clave_corta));
            focusView = edtClave;
            cancel = true;
        } else if (TextUtils.isEmpty(usuario)) {
            vwUsuario.setError(getString(R.string.error_usuario_vacio));
            focusView = vwUsuario;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the ic_user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(usuario, clave, rootView);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean longitudValida(String clave) {
        return clave.length() < 4; //numero de digitos de la clave
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        // The ViewPropertyAnimator APIs are not available, so simply show
        // and hide the relevant UI components.
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    private void recuperaDelServidorYalmacenaLocalmente() {
        referenciaViewModel.cargarTablaReferencia();
        paramciaViewModel.cargarTablaParamcia();
    }

    /**
     * Gets the state of Airplane Mode.
     *
     * @param context
     * @return true if enabled.
     */
    private static boolean isAirplaneModeOn(Context context) {

        return Settings.System.getInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;

    }

    //verificamos si el movil tiene conexion de internet
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    /**
     * lanzamos MainActivity
     */
    public void launch_MainActivity() {
        Intent mIntent = new Intent(this, MainActivity.class);
        startActivity(mIntent);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the ic_user.
     */
    int estado_login_fracaso = -1;
    int estado_login_exitoso = 1;

    public class UserLoginTask extends AsyncTask<Void, Void, LoginApiResponse> {

        private final String mUsuario;
        private final String mClave;
        private View rootView;

        UserLoginTask(String usuario, String clave, View rootView) {
            this.mUsuario = usuario;
            this.mClave = clave;
            this.rootView = rootView;
        }

        @Override
        protected LoginApiResponse doInBackground(Void... params) {
            LoginApiResponse loginApiResponse = new LoginApiResponse();
            Call<LoginApiResponse> result = mServiceDole.login(mUsuario, mClave);
            try {
                //se procesa el resul para obtener el login
                loginApiResponse = result.execute().body();

            } catch (Exception e) {
                loginApiResponse.setEstado(estado_login_fracaso);
                loginApiResponse.setMensaje(e.toString());
                Log.e(TAG, "Error de conexión " + e);
            }
            return loginApiResponse;
        }

        @Override
        protected void onPostExecute(final LoginApiResponse loginApiResponse) {
            mAuthTask = null;

            try {
                if (loginApiResponse.getEstado() == estado_login_exitoso) {
                    showProgress(true);
                    recuperaDelServidorYalmacenaLocalmente();
                    Global.nombre_de_usuario = loginApiResponse.getMensaje();

                    launch_MainActivity();

                    Toast.makeText(getApplicationContext(), loginApiResponse.getMensaje(), Toast.LENGTH_LONG).show();
                    finish();

                } else if (loginApiResponse.getEstado() == estado_login_fracaso) {
                    showProgress(false);
                    Snackbar.make(rootView, "Error de conexión ", Snackbar.LENGTH_INDEFINITE)
                            .setAction("VER ERROR ", view -> {
                                Toast.makeText(getApplicationContext(), loginApiResponse.getMensaje(), Toast.LENGTH_LONG).show();
                            }).show();
                }
            } catch (Exception e) {
                showProgress(false);
                Snackbar.make(rootView, "Error de conexión ", Snackbar.LENGTH_INDEFINITE)
                        .setAction("VER ERROR ", view -> {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_READ_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted!
                    // you may now do the action that requires this permission
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        } else {
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    }
                } else {
                    // permission denied
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted!
                    // you may now do the action that requires this permission
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                        } else {
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                } else {
                    // permission denied
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted!
                    // you may now do the action that requires this permission
                } else {
                    // permission denied
                }
                return;
            }
        }
    }


}

