package ec.pure.naportec.eir.utils;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;

import ec.pure.naportec.eir.R;

/*  LLAMADA DE ACTIVITY
        AppUtils.verificaSD_Directory("/eir_imagenes/");
        if (!AppUtils.bool_sdAccesoEscritura || !AppUtils.bool_sdDisponible)
        toast("Problemas con la Memoria Externa");
        if (!AppUtils.bool_dirExist)
        toast("No se puede crear el Directorio de imagenes");

        Intent intent = new Intent(this, VariasImageCameraActivity.class);
        intent.putExtra(VariasImageCameraActivity.ARG_PANTALLA_ORIGEN, VariasImageCameraActivity.PANTALLA_UNO);
        intent.putExtra(VariasImageCameraActivity.ARG_PHOTO_NAME_BASE, codActividad+"_");
        intent.putExtra(VariasImageCameraActivity.EXTRA_CLIENTE,S113ActividadActivity._variable.toString());
        intent.putExtra(VariasImageCameraActivity.EXTRA_VENDEDOR,S113ActividadActivity._codVendedor);
        intent.putExtra(VariasImageCameraActivity.EXTRA_SECVISITA,S113ActividadActivity._secVisita);
        intent.putExtra(VariasImageCameraActivity.EXTRA_MOBILID,S113ActividadActivity._mobilID);
        intent.putExtra(VariasImageCameraActivity.EXTRA_ENTIDAD_CODE, codActividad);
        intent.putExtra(VariasImageCameraActivity.EXTRA_TITULO, nomDefAct);
        intent.putExtra(VariasImageCameraActivity.EXTRA_CONSULTA, true);
        intent.putExtra(VariasImageCameraActivity.EXTRA_VAR_ADIC1, idDefAct);
        intent.putExtra(VariasImageCameraActivity.EXTRA_VAR_ADIC2, codLocal);
        intent.putExtra(VariasImageCameraActivity.EXTRA_VAR_ADIC3, codLocal);
        startActivityForResult(intent, VariasImageCameraActivity.PANTALLA_UNO);
*/

public class VariasImageCameraActivity extends PersonalizadoToolBar {
    private static final String TAG = "PURETECH." + VariasImageCameraActivity.class.getSimpleName();
    public static final int PANTALLA_UNO = 1;   //Invocada desde Panatalla 1 que es ...
    public static final int PANTALLA_DOS = 2;   //Invocada desde Panatalla 2 que es ...
    public static final int PANTALLA_TRES = 3;   //Invocada desde Panatalla 2 que es ...
    public static final int PANTALLA_CUATRO = 4;   //Invocada desde Panatalla 2 que es ...
    public static final int PANTALLA_CINCO = 5;   //Invocada desde Panatalla 2 que es ...
    public static final int PANTALLA_SEIS = 6;   //Invocada desde Panatalla 2 que es ...
    public static final int PANTALLA_SIETE = 7;   //Invocada desde Panatalla 2 que es ...
    public static final int PANTALLA_OCHO = 8;   //Invocada desde Panatalla 2 que es ...
    public static final int PANTALLA_NUEVE = 9;   //Invocada desde Panatalla 2 que es ...
    public static final int PANTALLA_NNN = 99;  //Invocada desde Panatalla NNN que es ...

    public static final int CANT_IMAGENES = 5;
    public static final int REQUEST_CAMERA_CINCO_1 = 9011;
    public static final int REQUEST_CAMERA_CINCO_2 = 9012;
    public static final int REQUEST_CAMERA_CINCO_3 = 9013;
    public static final int REQUEST_CAMERA_CINCO_4 = 9014;
    public static final int REQUEST_CAMERA_CINCO_5 = 9015;
    public static final int REQUEST_GALLERY_CINCO_1 = 9016;
    public static final int REQUEST_GALLERY_CINCO_2 = 9017;
    public static final int REQUEST_GALLERY_CINCO_3 = 9018;
    public static final int REQUEST_GALLERY_CINCO_4 = 9019;
    public static final int REQUEST_GALLERY_CINCO_5 = 9010;
    public static final String ARG_PHOTO_NAME_BASE = "arg_photo_name_base";
    public static final String ARG_PANTALLA_ORIGEN = "arg_pantalla_origen";
    public static final String EXTRA_ENTIDAD_CODE = "extra_entidad_code";
    public static final String EXTRA_TITULO = "extra_titulo";
    public static final String EXTRA_CONSULTA = "extra_es_consulta";
    public static final String EXTRA_VAR_ADIC1 = "extra_var_adic1";
    public static final String EXTRA_VAR_ADIC2 = "extra_var_adic2";
    public static final String EXTRA_VAR_ADIC3 = "extra_var_adic3";
    public static final String EXTRA_CLIENTE = "extra_cliente";
    public static final String EXTRA_VENDEDOR = "extra_vendedor";
    public static final String EXTRA_MOBILID = "extra_mobil_ID";
    public static final String EXTRA_SECVISITA = "extra_secvisita";

    public static String _cliente, _vendedor, _secVisita, _mobilID;
    public static boolean _consulta;    //si debe ser modo consulta o modifica/inserta/delete
    String[] nombreFotoBD = new String[CANT_IMAGENES];  //Simplemente nombre de la imagen actual sin extensión
    String nombreFotoPivote = "";  //Nombre base para generar los nuevos nombres
    int pantallaOrigen = 0; //De que pantalla proviene
    int ultFotoCapturada = 0;  //La ultima ejecucion de la camara de que imagen fue
    String code_entidad = "";  //codigo de la entidad o registro
    String _titulo = null;  //titulo que se colocará en la pantalla
    String _varAdic1 = null, _varAdic2 = null, _varAdic3 = null;    //Variables de uso general
    //OJO    public static OpenHelper OpHelper;
    public static SQLiteDatabase db;

    private TextView[] tv_nombre = new TextView[CANT_IMAGENES];
    private ImageView[] im_Photo = new ImageView[CANT_IMAGENES];
    private Button[] bt_cambiar = new Button[CANT_IMAGENES];
    private Button[] bt_nota = new Button[CANT_IMAGENES];
    Uri photoUriTemp = null;
    String nombreFotoBDModif = "";  //El nuevo archivo que se genera
    private boolean doubleBackToExitPressedOnce = false;
    private boolean doubleBackToChangePressedOnce = false;


    private final String extra_placa = "extra_placa";
    private final String extra_evento = "extra_evento";
    private String mPlacaIngresada, mEventoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varias_image_camera);

        // Obtenemos el Intent que inició el activity y extraemos la placa y el evento
        Intent mIntent = getIntent();
        mPlacaIngresada = mIntent.getStringExtra(extra_placa);
        mEventoSeleccionado = mIntent.getStringExtra(extra_evento);
        // con este metodo heredado colocamos los datos en el toolbar
        poner_placa_y_evento(mPlacaIngresada, mEventoSeleccionado);



        tv_nombre[0] = (TextView) findViewById(R.id.tv_nombre1);
        bt_cambiar[0] = (Button) findViewById(R.id.button_cambiar1);
        bt_nota[0] = (Button) findViewById(R.id.button_nota1);
        im_Photo[0] = (ImageView) findViewById(R.id.img_foto1);
        tv_nombre[1] = (TextView) findViewById(R.id.tv_nombre2);
        bt_cambiar[1] = (Button) findViewById(R.id.button_cambiar2);
        bt_nota[1] = (Button) findViewById(R.id.button_nota2);
        im_Photo[1] = (ImageView) findViewById(R.id.img_foto2);
        tv_nombre[2] = (TextView) findViewById(R.id.tv_nombre3);
        bt_cambiar[2] = (Button) findViewById(R.id.button_cambiar3);
        bt_nota[2] = (Button) findViewById(R.id.button_nota3);
        im_Photo[2] = (ImageView) findViewById(R.id.img_foto3);
        tv_nombre[3] = (TextView) findViewById(R.id.tv_nombre4);
        bt_cambiar[3] = (Button) findViewById(R.id.button_cambiar4);
        bt_nota[3] = (Button) findViewById(R.id.button_nota4);
        im_Photo[3] = (ImageView) findViewById(R.id.img_foto4);
        tv_nombre[4] = (TextView) findViewById(R.id.tv_nombre5);
        bt_cambiar[4] = (Button) findViewById(R.id.button_cambiar5);
        bt_nota[4] = (Button) findViewById(R.id.button_nota5);
        im_Photo[4] = (ImageView) findViewById(R.id.img_foto5);

        if (getIntent().getExtras() != null) {
            pantallaOrigen = getIntent().getExtras().getInt(ARG_PANTALLA_ORIGEN);
            nombreFotoPivote = getIntent().getExtras().getString(ARG_PHOTO_NAME_BASE);
            _cliente = getIntent().getExtras().getString(EXTRA_VENDEDOR);
            _vendedor = getIntent().getExtras().getString(EXTRA_VENDEDOR);
            _secVisita = getIntent().getExtras().getString(EXTRA_SECVISITA);
            _mobilID = getIntent().getExtras().getString(EXTRA_MOBILID);
            code_entidad = getIntent().getExtras().getString(EXTRA_ENTIDAD_CODE);
            _titulo = getIntent().getExtras().getString(EXTRA_TITULO);
            _consulta = getIntent().getExtras().getBoolean(EXTRA_CONSULTA, false);
            _varAdic1 = getIntent().getExtras().getString(EXTRA_VAR_ADIC1, "");  //Act:id de def.activ.
            _varAdic2 = getIntent().getExtras().getString(EXTRA_VAR_ADIC2, "");  //Act:id de local
            _varAdic3 = getIntent().getExtras().getString(EXTRA_VAR_ADIC3, "");
        }

        // Instancia de helper
//OJO        OpHelper = OpenHelper.getInstance(this);
//OJO        db = OpHelper.getWritableDatabase();

        for (int idx = 0; idx < CANT_IMAGENES; idx++) registerForContextMenu(im_Photo[idx]);

        if (_titulo != null) {
            setTitle(_titulo);
        }

        for (int idx = 0; idx < CANT_IMAGENES; idx++) nombreFotoBD[idx] = "";

        // esto debe ir con Room
// comentar desde aqui
        Cursor cur = null;
/*                db.rawQuery("SELECT * FROM GENR_IMAGEN where genimg_tipo = " + pantallaOrigen + " AND genimg_tipo_codigo = '" + code_entidad + "' AND genimg_secuencia <> 0 AND genimg_deleted_at IS NULL ", null);
        if (cur.moveToFirst()) {
            do {// obtenemos el nombre de la foto
                nombreFotoBD[Integer.parseInt(cur.getString(cur.getColumnIndex("genimg_secuencia"))) - 1]
                        = cur.getString(cur.getColumnIndex("genimg_nombre"));//obtiene un string nombre de la imagen
            }
            while (cur.moveToNext());
        }
*/
// comentar hasta aqui

        for (int idx = 0; idx < CANT_IMAGENES; idx++) {
            Glide
                    .with(this)
                    .load(Uri.parse("file://" + (AppUtils.dirImagenProcesa.substring(0, 1).equals("/") ? "" : "/") + AppUtils.dirImagenProcesa + nombreFotoBD[idx]))
                    .asBitmap()
                    .error(R.drawable.sin_imagen)
                    .centerCrop()
                    .into(im_Photo[idx]);

            //im_Photo[idx].setTag(idx);  //lo setea al inicio cuando hacia el findbyview y dio error, debo hacerlo luego de glide
            tv_nombre[idx].setText(nombreFotoBD[idx]);
            bt_cambiar[idx].setVisibility(View.INVISIBLE);

            if (pantallaOrigen == PANTALLA_UNO || pantallaOrigen == PANTALLA_DOS)
                bt_nota[idx].setVisibility(View.INVISIBLE);
            else {  //USANDO DETALLE POR IMAGEN
                if (!nombreFotoBD[idx].toLowerCase().contains(".jpg"))
                    bt_nota[idx].setVisibility(View.INVISIBLE);
            }
        }

    }
/*
    @Override
    public boolean onSupportNavigateUp() {
        boolean mflag = false;
        for (int idx = 0; idx < CANT_IMAGENES; idx++)   //Existe por lo menos 1 boton visible
            if (bt_cambiar[idx].getVisibility() == View.VISIBLE) mflag = true;

        if (mflag) {
            if (doubleBackToExitPressedOnce) {
                onBackPressed();
                return true;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "NO se ha aceptado el cambio de una foto\nPresione BACK otra vez para salir", Toast.LENGTH_LONG);
            return false;
        } else {
            onBackPressed();
            return true;
        }
    }
*/
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            boolean mflag = false;
            for (int idx = 0; idx < CANT_IMAGENES; idx++)   //Existe por lo menos 1 boton visible
                if (bt_cambiar[idx].getVisibility() == View.VISIBLE) mflag = true;

            if (mflag) {
                if (!doubleBackToExitPressedOnce) {
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(this, "NO se ha aceptado el cambio de una foto\nPresione BACK otra vez para salir", Toast.LENGTH_LONG);
                    return true;
                }
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        int posImagen = 0;
        if (v.getId() == R.id.img_foto1) posImagen = 1;
        else if (v.getId() == R.id.img_foto2) posImagen = 2;
        else if (v.getId() == R.id.img_foto3) posImagen = 3;
        else if (v.getId() == R.id.img_foto4) posImagen = 4;
        else if (v.getId() == R.id.img_foto5) posImagen = 5;

        if (bt_cambiar[posImagen - 1].getVisibility() != View.VISIBLE && nombreFotoBD[posImagen - 1].toLowerCase().contains(".jpg") && !_consulta) {
            super.onCreateContextMenu(menu, v, menuInfo);
            MenuInflater inflater = getMenuInflater();
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            inflater.inflate(R.menu.menu_image_camera, menu);
            if (posImagen == 1) {
                menu.removeItem(R.id.mn_eliminar2);
                menu.removeItem(R.id.mn_eliminar3);
                menu.removeItem(R.id.mn_eliminar4);
                menu.removeItem(R.id.mn_eliminar5);
                menu.removeItem(R.id.mn_galeria1);
                menu.removeItem(R.id.mn_galeria2);
                menu.removeItem(R.id.mn_galeria3);
                menu.removeItem(R.id.mn_galeria4);
                menu.removeItem(R.id.mn_galeria5);
            } else if (posImagen == 2) {
                menu.removeItem(R.id.mn_eliminar1);
                menu.removeItem(R.id.mn_eliminar3);
                menu.removeItem(R.id.mn_eliminar4);
                menu.removeItem(R.id.mn_eliminar5);
                menu.removeItem(R.id.mn_galeria1);
                menu.removeItem(R.id.mn_galeria2);
                menu.removeItem(R.id.mn_galeria3);
                menu.removeItem(R.id.mn_galeria4);
                menu.removeItem(R.id.mn_galeria5);
            } else if (posImagen == 3) {
                menu.removeItem(R.id.mn_eliminar1);
                menu.removeItem(R.id.mn_eliminar2);
                menu.removeItem(R.id.mn_eliminar4);
                menu.removeItem(R.id.mn_eliminar5);
                menu.removeItem(R.id.mn_galeria1);
                menu.removeItem(R.id.mn_galeria2);
                menu.removeItem(R.id.mn_galeria3);
                menu.removeItem(R.id.mn_galeria4);
                menu.removeItem(R.id.mn_galeria5);
            } else if (posImagen == 4) {
                menu.removeItem(R.id.mn_eliminar1);
                menu.removeItem(R.id.mn_eliminar2);
                menu.removeItem(R.id.mn_eliminar3);
                menu.removeItem(R.id.mn_eliminar5);
                menu.removeItem(R.id.mn_galeria1);
                menu.removeItem(R.id.mn_galeria2);
                menu.removeItem(R.id.mn_galeria3);
                menu.removeItem(R.id.mn_galeria4);
                menu.removeItem(R.id.mn_galeria5);
            } else if (posImagen == 5) {
                menu.removeItem(R.id.mn_eliminar1);
                menu.removeItem(R.id.mn_eliminar2);
                menu.removeItem(R.id.mn_eliminar3);
                menu.removeItem(R.id.mn_eliminar4);
                menu.removeItem(R.id.mn_galeria1);
                menu.removeItem(R.id.mn_galeria2);
                menu.removeItem(R.id.mn_galeria3);
                menu.removeItem(R.id.mn_galeria4);
                menu.removeItem(R.id.mn_galeria5);
            }
        } else if (bt_cambiar[posImagen - 1].getVisibility() != View.VISIBLE && !nombreFotoBD[posImagen - 1].toLowerCase().contains(".jpg") && !_consulta) {
            super.onCreateContextMenu(menu, v, menuInfo);
            MenuInflater inflater = getMenuInflater();
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            inflater.inflate(R.menu.menu_image_camera, menu);
            if (posImagen == 1) {
                menu.removeItem(R.id.mn_eliminar1);
                menu.removeItem(R.id.mn_eliminar2);
                menu.removeItem(R.id.mn_eliminar3);
                menu.removeItem(R.id.mn_eliminar4);
                menu.removeItem(R.id.mn_eliminar5);
                menu.removeItem(R.id.mn_galeria2);
                menu.removeItem(R.id.mn_galeria3);
                menu.removeItem(R.id.mn_galeria4);
                menu.removeItem(R.id.mn_galeria5);
            } else if (posImagen == 2) {
                menu.removeItem(R.id.mn_eliminar1);
                menu.removeItem(R.id.mn_eliminar2);
                menu.removeItem(R.id.mn_eliminar3);
                menu.removeItem(R.id.mn_eliminar4);
                menu.removeItem(R.id.mn_eliminar5);
                menu.removeItem(R.id.mn_galeria1);
                menu.removeItem(R.id.mn_galeria3);
                menu.removeItem(R.id.mn_galeria4);
                menu.removeItem(R.id.mn_galeria5);
            } else if (posImagen == 3) {
                menu.removeItem(R.id.mn_eliminar1);
                menu.removeItem(R.id.mn_eliminar2);
                menu.removeItem(R.id.mn_eliminar3);
                menu.removeItem(R.id.mn_eliminar4);
                menu.removeItem(R.id.mn_eliminar5);
                menu.removeItem(R.id.mn_galeria1);
                menu.removeItem(R.id.mn_galeria2);
                menu.removeItem(R.id.mn_galeria4);
                menu.removeItem(R.id.mn_galeria5);
            } else if (posImagen == 4) {
                menu.removeItem(R.id.mn_eliminar1);
                menu.removeItem(R.id.mn_eliminar2);
                menu.removeItem(R.id.mn_eliminar3);
                menu.removeItem(R.id.mn_eliminar4);
                menu.removeItem(R.id.mn_eliminar5);
                menu.removeItem(R.id.mn_galeria1);
                menu.removeItem(R.id.mn_galeria2);
                menu.removeItem(R.id.mn_galeria3);
                menu.removeItem(R.id.mn_galeria5);
            } else if (posImagen == 5) {
                menu.removeItem(R.id.mn_eliminar1);
                menu.removeItem(R.id.mn_eliminar2);
                menu.removeItem(R.id.mn_eliminar3);
                menu.removeItem(R.id.mn_eliminar4);
                menu.removeItem(R.id.mn_eliminar5);
                menu.removeItem(R.id.mn_galeria1);
                menu.removeItem(R.id.mn_galeria2);
                menu.removeItem(R.id.mn_galeria3);
                menu.removeItem(R.id.mn_galeria4);
            }

        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String query = "";
        File fichero;
        boolean ok = false;
        int tipo = 0;

        int posImagen = 0;
        if (item.getItemId() == R.id.mn_eliminar1) posImagen = 1;
        else if (item.getItemId() == R.id.mn_eliminar2) posImagen = 2;
        else if (item.getItemId() == R.id.mn_eliminar3) posImagen = 3;
        else if (item.getItemId() == R.id.mn_eliminar4) posImagen = 4;
        else if (item.getItemId() == R.id.mn_eliminar5) posImagen = 5;
        else if (item.getItemId() == R.id.mn_galeria1) posImagen = 11;
        else if (item.getItemId() == R.id.mn_galeria2) posImagen = 12;
        else if (item.getItemId() == R.id.mn_galeria3) posImagen = 13;
        else if (item.getItemId() == R.id.mn_galeria4) posImagen = 14;
        else if (item.getItemId() == R.id.mn_galeria5) posImagen = 15;

        if (posImagen > 10) {
            tipo = 2;
            posImagen = posImagen - 10;
        } else if (posImagen > 0) {
            tipo = 1;
        }

        if (tipo == 1) {    //Eliminar
            if (pantallaOrigen == PANTALLA_UNO ||
                    pantallaOrigen == PANTALLA_DOS ||
                    pantallaOrigen == PANTALLA_TRES ||
                    pantallaOrigen == PANTALLA_CUATRO ||
                    pantallaOrigen == PANTALLA_CINCO ||
                    pantallaOrigen == PANTALLA_SEIS ||
                    pantallaOrigen == PANTALLA_SIETE ||
                    pantallaOrigen == PANTALLA_OCHO ||
                    pantallaOrigen == PANTALLA_NUEVE) {    //Diferente de detalle de imagen
                try {
                    query = "UPDATE GENR_IMAGEN SET enviadofile='S', Transmitido='P', genimg_deleted_at= datetime('now', 'localtime') ";
                    query = query + " WHERE genimg_tipo = " + pantallaOrigen + " AND genimg_tipo_codigo = '" + code_entidad + "' AND genimg_secuencia = " + posImagen + " ";
                    db.execSQL(query);
                    ok = true;
                } catch (SQLException e) {
                    Log.e(TAG, "Error updating " + query, e);
                    Toast.makeText(this, "Error al grabar en BD Imagen " + posImagen + " ", Toast.LENGTH_LONG);
                }
            } else if (pantallaOrigen == PANTALLA_NNN) {    //manejo detalle en imagen
                try {
                    db.beginTransaction();
                    query = "DELETE FROM mcht_actividad_det WHERE genimg_codigo = '" + pantallaOrigen + "-" + code_entidad + "-" + posImagen + "' ";
                    db.execSQL(query);

                    query = "DELETE FROM genr_imagen WHERE genimg_tipo= " + pantallaOrigen + " AND genimg_tipo_codigo = '" + code_entidad + "' AND genimg_secuencia = " + posImagen + " ";
                    db.execSQL(query);

                    String cuentaReg = "SELECT count(*) from genr_imagen where genimg_tipo = " + pantallaOrigen + " AND genimg_tipo_codigo = '" + code_entidad + "' ";
                    Cursor cursor = db.rawQuery(cuentaReg, null);
                    int contReg = 0;
                    if (cursor.moveToFirst()) {
                        contReg = cursor.getInt(0);
                    }
                    cursor.close();

                    if (contReg == 0) {
                        query = "DELETE FROM mcht_actividad_cab WHERE mchcca_codigo= '" + code_entidad + "'  ";
                        db.execSQL(query);
                    }

                    db.setTransactionSuccessful();
                    db.endTransaction();
                    ok = true;
                } catch (SQLException e) {
                    db.endTransaction();
                    Log.e(TAG, "Error updating " + query, e);
                    Toast.makeText(this, "Error al grabar en BD Imagen " + posImagen + " ", Toast.LENGTH_LONG).show();

                }
            }
            //IF

            if (ok) {   //Grabo correctamente
                fichero = new File(Uri.parse(AppUtils.dirImagenProcesa.substring(0, 1).equals("/") ? "" : "/") + AppUtils.dirImagenProcesa + nombreFotoBD[posImagen - 1]);
                //fichero.delete();  de la foto por si acaso
                nombreFotoBD[posImagen - 1] = "";   //Como se realizó el cambio se procede a colocar el nuevo nombre de archivo
                photoUriTemp = null;
                nombreFotoBDModif = "";

                Glide
                        .with(this)
                        .load(R.drawable.sin_imagen)
                        .asBitmap()
                        .centerCrop()
                        .into(im_Photo[posImagen - 1]);

                tv_nombre[posImagen - 1].setText(nombreFotoBD[posImagen - 1]);
                bt_nota[posImagen - 1].setVisibility(View.INVISIBLE);
                Toast.makeText(this, "Eliminado correctamente Imagen " + posImagen + " ", Toast.LENGTH_LONG).show();
            }
            return true;
        } else if (tipo == 2) {  //Galeria
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            //intent.setType("image/*");

            ultFotoCapturada = posImagen;
            switch (ultFotoCapturada) {
                case 1:
                    startActivityForResult(Intent.createChooser(intent, "Seleccione la Aplicación"), REQUEST_GALLERY_CINCO_1);
                    //startActivityForResult(intent, REQUEST_GALLERY_CINCO_1);
                    break;
                case 2:
                    startActivityForResult(Intent.createChooser(intent, "Seleccione la Aplicación"), REQUEST_GALLERY_CINCO_2);
                    break;
                case 3:
                    startActivityForResult(Intent.createChooser(intent, "Seleccione la Aplicación"), REQUEST_GALLERY_CINCO_3);
                    break;
                case 4:
                    startActivityForResult(Intent.createChooser(intent, "Seleccione la Aplicación"), REQUEST_GALLERY_CINCO_4);
                    break;
                case 5:
                    startActivityForResult(Intent.createChooser(intent, "Seleccione la Aplicación"), REQUEST_GALLERY_CINCO_5);
                    break;
            }

            return true;
        } else
            return super.onContextItemSelected(item);

    }

    public void click_Image(View view) {
        int posImagen = 0;
        boolean verifica = false;

        if (view.getId() == R.id.img_foto1) {
            posImagen = 1;
            if ((bt_cambiar[0].getVisibility() == View.VISIBLE) || (bt_cambiar[0].getVisibility() != View.VISIBLE && !nombreFotoBD[0].toLowerCase().contains(".jpg")))
                verifica = true;
        } else if (view.getId() == R.id.img_foto2) {
            posImagen = 2;
            if ((bt_cambiar[1].getVisibility() == View.VISIBLE) || (bt_cambiar[1].getVisibility() != View.VISIBLE && !nombreFotoBD[1].toLowerCase().contains(".jpg")))
                verifica = true;
        } else if (view.getId() == R.id.img_foto3) {
            posImagen = 3;
            if ((bt_cambiar[2].getVisibility() == View.VISIBLE) || (bt_cambiar[2].getVisibility() != View.VISIBLE && !nombreFotoBD[2].toLowerCase().contains(".jpg")))
                verifica = true;
        } else if (view.getId() == R.id.img_foto4) {
            posImagen = 4;
            if ((bt_cambiar[3].getVisibility() == View.VISIBLE) || (bt_cambiar[3].getVisibility() != View.VISIBLE && !nombreFotoBD[3].toLowerCase().contains(".jpg")))
                verifica = true;
        } else if (view.getId() == R.id.img_foto5) {
            posImagen = 5;
            if ((bt_cambiar[4].getVisibility() == View.VISIBLE) || (bt_cambiar[4].getVisibility() != View.VISIBLE && !nombreFotoBD[4].toLowerCase().contains(".jpg")))
                verifica = true;
        }

        if (!_consulta && verifica) {   //No es consulta
            //Para proteger las variables globales del boton presionado , porque cada boton reeemplza valores de variables
            boolean mflag = false;
            for (int idx = 0; idx < CANT_IMAGENES; idx++)   //Existe por lo menos 1 boton visible
                if (bt_cambiar[idx].getVisibility() == View.VISIBLE) mflag = true;

            if (mflag) {
                if (!doubleBackToChangePressedOnce) {
                    this.doubleBackToChangePressedOnce = true;
                    Toast.makeText(this, "NO se ha aceptado el cambio de una foto\nPresione CLICK otra vez para descartarla.", Toast.LENGTH_LONG).show();

                    return;
                }
            }

            doubleBackToChangePressedOnce = false;

            for (int idx = 0; idx < CANT_IMAGENES; idx++)   //Existe por lo menos 1 boton visible
                bt_cambiar[idx].setVisibility(View.INVISIBLE);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(this.getPackageManager()) != null) {    //Verificación que el celular tenga camara
                File photoFile = null;
                try {
                    photoFile = createImageFile(posImagen);
                    photoFile.delete();
                } catch (Exception e) {
                    Log.e(TAG, "Error createImageFile()", e);
                    e.printStackTrace();
                }

                if (photoFile != null) {
                    /* codigo que se bloqueó por error de android N luis coba
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //Versión 24
                        Uri photoUri = FileProvider.getUriForFile(this, this.getPackageName(), photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    } else {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUriTemp);
                    }
                    */
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile);
                    } else {
                        File file = new File(photoFile.getPath());
                        Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName(), file);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    }
/* esto va con el codigo que arregla                     intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    if (intent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
                        startActivityForResult(intent, REQUEST_CAMERA);
                    }
                    */
                    ultFotoCapturada = posImagen;
                    switch (ultFotoCapturada) {
                        case 1:
                            startActivityForResult(intent, REQUEST_CAMERA_CINCO_1);
                            break;
                        case 2:
                            startActivityForResult(intent, REQUEST_CAMERA_CINCO_2);
                            break;
                        case 3:
                            startActivityForResult(intent, REQUEST_CAMERA_CINCO_3);
                            break;
                        case 4:
                            startActivityForResult(intent, REQUEST_CAMERA_CINCO_4);
                            break;
                        case 5:
                            startActivityForResult(intent, REQUEST_CAMERA_CINCO_5);
                            break;
                    }
                }
            } else {
                Toast.makeText(this, "Camara NO disponible", Toast.LENGTH_LONG).show();
            }
        }   // if (!_consulta)
    }

    private File createImageFile(int num) throws IOException {
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HH-mm-ss").format(new Date());
        //String imageFileName = "JPEG_"+timeStamp+"_";
        String imageFileName = nombreFotoPivote + String.valueOf(num) + "_";    //Para el nuevo archivo siempre parto del nombre base
        //original File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = new File(AppUtils.dirImagenProcesa);

        File photo = File.createTempFile(imageFileName, ".jpg", storageDir);
        photoUriTemp = Uri.fromFile(photo);
        nombreFotoBDModif = photo.getName(); //Asigno el verdadero o nuevo nombre dado al archivo.

        return photo;

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (photoUriTemp != null) {
            outState.putString("UriPhoto", photoUriTemp.toString());
            outState.putString("NamePhoto", nombreFotoBDModif);
            outState.putInt("UltimaCaptura", ultFotoCapturada);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("UriPhoto")) {
            photoUriTemp = Uri.parse(savedInstanceState.getString("UriPhoto"));
            nombreFotoBDModif = savedInstanceState.getString("NamePhoto");
            ultFotoCapturada = savedInstanceState.getInt("UltimaCaptura");

            Glide
                    .with(this)
                    .load(photoUriTemp)
                    .asBitmap()
                    .error(R.drawable.sin_imagen)
                    .centerCrop()
                    .into(im_Photo[ultFotoCapturada - 1]);
            this.doubleBackToExitPressedOnce = false;
            tv_nombre[ultFotoCapturada - 1].setText(nombreFotoBDModif);
            bt_cambiar[ultFotoCapturada - 1].setVisibility(View.VISIBLE);
            if (nombreFotoBD[ultFotoCapturada - 1].toLowerCase().contains(".jpg") && pantallaOrigen == PANTALLA_NNN)
                bt_nota[ultFotoCapturada - 1].setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int indregreso = 0, tipo = 0;

        if (requestCode == REQUEST_CAMERA_CINCO_1) indregreso = 1;
        else if (requestCode == REQUEST_CAMERA_CINCO_2) indregreso = 2;
        else if (requestCode == REQUEST_CAMERA_CINCO_3) indregreso = 3;
        else if (requestCode == REQUEST_CAMERA_CINCO_4) indregreso = 4;
        else if (requestCode == REQUEST_CAMERA_CINCO_5) indregreso = 5;
        else if (requestCode == REQUEST_GALLERY_CINCO_1) indregreso = 11;
        else if (requestCode == REQUEST_GALLERY_CINCO_2) indregreso = 12;
        else if (requestCode == REQUEST_GALLERY_CINCO_3) indregreso = 13;
        else if (requestCode == REQUEST_GALLERY_CINCO_4) indregreso = 14;
        else if (requestCode == REQUEST_GALLERY_CINCO_5) indregreso = 15;

        if (indregreso > 0) {
            if (indregreso < 10) {   //Es Camara
                tipo = 1;
            } else {  //Es Galeria
                indregreso = indregreso - 10;
                tipo = 2;
            }
            switch (resultCode) {
                case Activity.RESULT_OK:    //Carga de foto tomada
                    if (tipo == 2) {   //Galeria
                        String rutaArchivo = "";
                        Uri uriTmp = data.getData();
                        if (uriTmp.getScheme().toString().compareTo("content") == 0) {
                            Cursor cursor = getContentResolver().query(uriTmp, null, null, null, null);
                            if (cursor.moveToFirst()) {
                                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);//Instead of "MediaStore.Images.Media.DATA" can be used "_data"
                                Uri filePathUri = Uri.parse(cursor.getString(column_index));
                                nombreFotoBDModif = filePathUri.getLastPathSegment().toString();
                                rutaArchivo = filePathUri.getPath();
                                Toast.makeText(this, "Seleccionado archivo:" + nombreFotoBDModif + "\n" + rutaArchivo, Toast.LENGTH_LONG).show();

                            }
                        } else {
                            Toast.makeText(this, "No puede obtener Nombre del archivo, comuniquelo al Proveedor de SalesTrack", Toast.LENGTH_LONG).show();

                            return;
                        }

                        File directorio_interno = new File(rutaArchivo);
                        File directorio_destino = null;
                        try {
                            directorio_destino = createImageFile(indregreso);
                        } catch (Exception e) {
                            Log.e(TAG, "Error createImageFile()", e);
                            e.printStackTrace();
                        }
                        boolean bol = AppUtils.copyFile(directorio_interno, directorio_destino);
                    }

                    Glide
                            .with(this)
                            .load(photoUriTemp)
                            .asBitmap()
                            .error(R.drawable.sin_imagen)
                            .centerCrop()
                            .into(im_Photo[indregreso - 1]);

                    this.doubleBackToExitPressedOnce = false;
                    tv_nombre[indregreso - 1].setText(nombreFotoBDModif);
                    bt_cambiar[indregreso - 1].setVisibility(View.VISIBLE);
                    /*Presenta los datos que debe ingresar*/
                    if (pantallaOrigen == PANTALLA_NNN) {
                        click_Cambiar(bt_cambiar[indregreso - 1]);
                        click_Nota(bt_nota[indregreso - 1]);
                    }

                    break;
                //case Activity.RESULT_CANCELED:  //Carga Inicial de pantalla
                default:
                    Glide
                            .with(this)
                            .load(Uri.parse("file://" + (AppUtils.dirImagenProcesa.substring(0, 1).equals("/") ? "" : "/") + AppUtils.dirImagenProcesa + nombreFotoBD[indregreso - 1]))
                            .asBitmap()
                            .error(R.drawable.sin_imagen)
                            .centerCrop()
                            .into(im_Photo[indregreso - 1]);
                    tv_nombre[indregreso - 1].setText(nombreFotoBD[indregreso - 1]);
                    bt_cambiar[indregreso - 1].setVisibility(View.INVISIBLE);
                    bt_nota[indregreso - 1].setVisibility(View.INVISIBLE);
                    if (nombreFotoBD[indregreso - 1].toLowerCase().contains(".jpg") && pantallaOrigen == PANTALLA_NNN)
                        bt_nota[indregreso - 1].setVisibility(View.VISIBLE);
                    break;
            }   //switch
        }
    }

    public void click_Cambiar(View view) {
        String query = "";
        int posBoton = 0;

        if (view.getId() == R.id.button_cambiar1) posBoton = 1;
        else if (view.getId() == R.id.button_cambiar2) posBoton = 2;
        else if (view.getId() == R.id.button_cambiar3) posBoton = 3;
        else if (view.getId() == R.id.button_cambiar4) posBoton = 4;
        else if (view.getId() == R.id.button_cambiar5) posBoton = 5;

        if (pantallaOrigen == PANTALLA_UNO ||
                pantallaOrigen == PANTALLA_DOS ||
                pantallaOrigen == PANTALLA_TRES ||
                pantallaOrigen == PANTALLA_CUATRO ||
                pantallaOrigen == PANTALLA_CINCO ||
                pantallaOrigen == PANTALLA_SEIS ||
                pantallaOrigen == PANTALLA_SIETE ||
                pantallaOrigen == PANTALLA_OCHO ||
                pantallaOrigen == PANTALLA_NUEVE) {    //ES 2 ó 6
/*            try {
                db.beginTransaction();
                String cuentaReg = "SELECT count(*) from genr_imagen where genimg_tipo = " + pantallaOrigen + " AND genimg_tipo_codigo = '" + code_entidad + "' AND genimg_secuencia = " + posBoton + " ";
                Cursor cursor = db.rawQuery(cuentaReg, null);
                int contReg = 0;
                if (cursor.moveToFirst()) {
                    contReg = cursor.getInt(0);
                }
                cursor.close();

                if (contReg == 0) {
                    query = "INSERT INTO GENR_IMAGEN (genimg_tipo, genimg_tipo_codigo, genimg_nombre, genimg_secuencia, genimg_comentario, enviadofile, Transmitido) ";
                    query = query + " VALUES (" + pantallaOrigen + ", '" + code_entidad + "','" + nombreFotoBDModif + "'," + posBoton + ", '', 'N', 'P') ";
                    db.execSQL(query);
                } else {
                    query = "UPDATE GENR_IMAGEN SET genimg_nombre = '" + nombreFotoBDModif + "', enviadofile='N', Transmitido='P', genimg_deleted_at=NULL, genimg_comentario = '' ";
                    query = query + " WHERE genimg_tipo = " + pantallaOrigen + " AND genimg_tipo_codigo = '" + code_entidad + "' AND genimg_secuencia = " + posBoton + " ";
                    db.execSQL(query);
                }
                db.setTransactionSuccessful();
                db.endTransaction();

                nombreFotoBD[posBoton - 1] = nombreFotoBDModif;   //Como se realizó el cambio se procede a colocar el nuevo nombre de archivo
                photoUriTemp = null;
                nombreFotoBDModif = "";

            } catch (SQLException e) {
                db.endTransaction();
                Log.e(TAG, "Error updating " + query, e);
                toast("Error al grabar en BD");
                return;
            }
        } else if (pantallaOrigen == PANTALLA_NNN) {    //ES 5
            try {
                String cuentaReg;
                Cursor cursor;
                int contReg;
                db.beginTransaction();
/*                cuentaReg = "SELECT count(*) from mcht_actividad_cab where mchcca_codigo = '" + code_entidad + "' ";
                cursor = db.rawQuery(cuentaReg, null);
                contReg=0;
                if (cursor.moveToFirst()) {
                    contReg=cursor.getInt(0);
                }
                cursor.close();

                if (contReg == 0) {
                    String fecEmis = DataGlobal.getDateTime();

                    query = "INSERT INTO mcht_actividad_cab (_id, cxccli_codigo, mchcac_uuid, cxcloc_codigo, rtkmob_uuid, mchcca_codigo, mchcca_fechaemisi, facvdr_secvisita, Transmitido) ";
                    query = query + " SELECT IFNULL(MAX(_id),0)+1, '" + S113ActividadActivity.codCliente + "', " + _varAdic1 + " ," + (_varAdic2.equals("") ? "null" : "'" + _varAdic2 + "'") + ", " + _mobilID + ", '" + code_entidad + "','" + fecEmis + "'," + _secVisita + ", 'P' ";
                    query = query + " FROM mcht_actividad_cab ";
                    db.execSQL(query);

                    db.execSQL("UPDATE FACR_VENDEDOR SET FACVDR_secacthh=FACVDR_secacthh+1");

                }
*/
/*                cuentaReg = "SELECT count(*) from genr_imagen where genimg_tipo = " + pantallaOrigen + " AND genimg_tipo_codigo = '" + code_entidad + "' AND genimg_secuencia = " + posBoton + " ";
                cursor = db.rawQuery(cuentaReg, null);
                contReg = 0;
                if (cursor.moveToFirst()) {
                    contReg = cursor.getInt(0);
                }
                cursor.close();

                if (contReg == 0) {
                    query = "INSERT INTO GENR_IMAGEN (genimg_tipo, genimg_tipo_codigo, genimg_nombre, genimg_secuencia, genimg_comentario, enviadofile, Transmitido) ";
                    query = query + " VALUES (" + pantallaOrigen + ", '" + code_entidad + "','" + nombreFotoBDModif + "'," + posBoton + ", '', 'N', 'P') ";
                    db.execSQL(query);
                } else {
                    query = "UPDATE GENR_IMAGEN SET genimg_nombre = '" + nombreFotoBDModif + "', enviadofile='N', Transmitido='P', genimg_deleted_at=NULL, genimg_comentario ='' ";
                    query = query + " WHERE genimg_tipo = " + pantallaOrigen + " AND genimg_tipo_codigo = '" + code_entidad + "' AND genimg_secuencia = " + posBoton + " ";
                    db.execSQL(query);
                }
                db.setTransactionSuccessful();
                db.endTransaction();
                nombreFotoBD[posBoton - 1] = nombreFotoBDModif;   //Como se realizó el cambio se procede a colocar el nuevo nombre de archivo
                photoUriTemp = null;
                nombreFotoBDModif = "";

            } catch (SQLException e) {
                db.endTransaction();
                Log.e(TAG, "Error updating " + query, e);
                toast("Error al grabar en BD");
                return;
            }
        }
*/
            Toast.makeText(this, "Foto almacenada correctamente", Toast.LENGTH_SHORT).show();
            bt_cambiar[posBoton - 1].setVisibility(View.INVISIBLE);
            bt_nota[posBoton - 1].setVisibility(View.INVISIBLE);
            if (nombreFotoBD[posBoton - 1].toLowerCase().contains(".jpg") && pantallaOrigen == PANTALLA_NNN)
                bt_nota[posBoton - 1].setVisibility(View.VISIBLE);
            ultFotoCapturada = 0;

        }
    }

    public void click_Nota(View view) {

    }

        /*Intent intent = new Intent(this, S901_1DetActividadActivity.class);
        intent.putExtra(S031_0PedidoCab.EXTRA_DATOSCLTE, _variable.toString());
        intent.putExtra(S031_0PedidoCab.EXTRA_CODVENDEDOR, _codVendedor);
        intent.putExtra(S031_0PedidoCab.EXTRA_SECVISITA, _secVisita);
        intent.putExtra(S031_0PedidoCab.EXTRA_MOBILID, _mobilID);
        intent.putExtra(VariasImageCameraActivity.EXTRA_ENTIDAD_CODE, code_entidad);
        intent.putExtra(VariasImageCameraActivity.EXTRA_TITULO, _titulo);
        intent.putExtra(VariasImageCameraActivity.EXTRA_CONSULTA, _consulta);
        intent.putExtra(VariasImageCameraActivity.EXTRA_VAR_ADIC1, _varAdic1);
        intent.putExtra(VariasImageCameraActivity.EXTRA_VAR_ADIC2, _varAdic2);

        if (view.getId() == R.id.button_nota1) {
            intent.putExtra(S901_1DetActividadActivity.EXTRA_POSIC_IMAGEN, 1);
            startActivity(intent);
        }
        else if (view.getId() == R.id.button_nota2) {
            intent.putExtra(S901_1DetActividadActivity.EXTRA_POSIC_IMAGEN, 2);
            startActivity(intent);
        }
        else if (view.getId() == R.id.button_nota3) {
            intent.putExtra(S901_1DetActividadActivity.EXTRA_POSIC_IMAGEN, 3);
            startActivity(intent);
        }
        else if (view.getId() == R.id.button_nota4) {
            intent.putExtra(S901_1DetActividadActivity.EXTRA_POSIC_IMAGEN, 4);
            startActivity(intent);
        }
        else if (view.getId() == R.id.button_nota5) {
            intent.putExtra(S901_1DetActividadActivity.EXTRA_POSIC_IMAGEN, 5);
            startActivity(intent);
        }
*/
    //   }

    ///////////////////////////// Toast Custom //////////////////////////////////////
//   public void toast(String message) {
//        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

/*        Context context = getApplicationContext();
        //CharSequence text = "Hello toast!";
        int duration = 5000;

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
        textToast.setText(mens);

        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setView(layout);
        toast.show();
*/


}