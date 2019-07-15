package ec.pure.naportec.eir.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.system.ErrnoException;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ec.pure.naportec.eir.R;

public class AgregaFotosActivity extends AppCompatActivity {

    private ArrayList<Bitmap> bitmapFotos;
    private ArrayList<String> arrayStringFotos;
    private ArrayList<String> arrayTmpStringFotos;
    private ArrayList<String> lstNombreFoto;
    private static final int NUM_MAXIMO_DE_IMAGENES = 6;

    private ArrayList<Uri> imagesUriArrayList;
    private ImageButton imgB[];
    //nose porque razón, pero PICK_IMAGE_MULTIPLE debe ser igual a 5(talvez el array empieza en cero, y por eso solo puedo cargar 6 fotos)
    private int PICK_IMAGE_MULTIPLE = 5;

    private int posBit = 0;//posicion en el array
    private boolean seTomoFoto; //para controlar si se toma una foto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agrega_fotos);

        setResult(10);

        //de entrada pedimos permiso para acceder al almacenamiento interno
        checkLocationPermission();

        bitmapFotos = new ArrayList<>();

        lstNombreFoto = new ArrayList<>();
        arrayStringFotos = new ArrayList<>();
        imagesUriArrayList = new ArrayList<>();
        arrayTmpStringFotos = new ArrayList<>();

        seTomoFoto = false;

        imgB = new ImageButton[6];
        imgB[0] = findViewById(R.id.imgB1);
        imgB[1] = findViewById(R.id.imgB2);
        imgB[2] = findViewById(R.id.imgB3);
        imgB[3] = findViewById(R.id.imgB4);
        imgB[4] = findViewById(R.id.imgB5);
        imgB[5] = findViewById(R.id.imgB6);

        for (int i = 0; i < imgB.length; i++) {
            imgB[i].setVisibility(View.GONE);
        }
    }

    public void onClickAgregaFotos(View view) {
        startActivityForResult(getPickImageChooserIntent(), PICK_IMAGE_MULTIPLE);
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(AgregaFotosActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(AgregaFotosActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                //este es preciso para mostrar dialogos
                new AlertDialog.Builder(AgregaFotosActivity.this)
                        .setTitle("OpenCloset")
                        .setMessage("!Al otorgar permiso podrás mostrar las imágenes de tu producto¡")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(AgregaFotosActivity.this
                                        , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}
                                        , MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(AgregaFotosActivity.this
                        , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}
                        , MY_PERMISSIONS_REQUEST_LOCATION);
            }
        } else {
            // On load image button click, start pick image chooser activity.
            startActivityForResult(getPickImageChooserIntent(), PICK_IMAGE_MULTIPLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri imageUri;
        if (resultCode == RESULT_OK) {
            //si data es nulo se ha tomado una foto y si data.getClipData() es nulo se ha obtenido una imagen de archivo
            if (data == null || data.getClipData() == null) {
                if (data == null)
                    seTomoFoto = true;
                else
                    seTomoFoto = false;

                imageUri = getPickImageResultUri(data);

                // For API >= 23 we need to check specifically that we have permissions to read external storage,
                // but we don't know if we need to for the URI so the simplest is to try open the stream and see if we get error.
                /*boolean requirePermissions = false;*/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED

                        && isUriRequiresPermissions(imageUri)) {

                    // request permissions and handle the result in onRequestPermissionsResult()
                    /*requirePermissions = true;*/

                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                }
                /*if (!requirePermissions) { }*/
                imagesUriArrayList.add(imageUri);
                System.out.println("la uri de la foto " + imageUri);
            } else {
                seTomoFoto = false;
                if (requestCode == PICK_IMAGE_MULTIPLE) {
                    // Log.e("++count ", "" + data.getClipData().getItemCount());// Get count of image here.
                    if (data.getClipData().getItemCount() > NUM_MAXIMO_DE_IMAGENES) {
                        Snackbar snackbar = Snackbar
                                .make(findViewById(R.id.layoutAddFotos), "Tu no puedes seleccionar mas de " + NUM_MAXIMO_DE_IMAGENES + " imágenes", Snackbar.LENGTH_LONG)
                                .setAction("REINTENTAR", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent();
                                        intent.setType("image/*");
                                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                                        intent.setAction(Intent.ACTION_GET_CONTENT);
                                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 5);
                                    }
                                });
                        snackbar.setActionTextColor(Color.BLUE);
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                        textView.setTextColor(Color.RED);
                        snackbar.show();
                    } else {
                        for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                            imagesUriArrayList.add(data.getClipData().getItemAt(i).getUri());
                        }
                    }
                }
            }
        } else {
            //Finalizamos cuando no se selecciona imagen o se canceló la seleccion de una imagen
            //finish();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // preguntamos si los permisos fueron aprobados
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // en caso de que si aprobaron el permiso
            // On load image button click, start pick image chooser activity.
            startActivityForResult(getPickImageChooserIntent(), PICK_IMAGE_MULTIPLE);
        } else {
            Toast.makeText(this, "El permiso requerido no fue otorgado", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    /**
     * Create a chooser intent to select the source to get image from.<br/>
     * The source can be camera's (ACTION_IMAGE_CAPTURE) or gallery's (ACTION_GET_CONTENT).<br/>
     * All possible sources are added to the intent chooser.
     */
    public Intent getPickImageChooserIntent() {
        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();
        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        // collect all camera intents
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }
        // collect all gallery intents
/*        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);//con esta instruccion se selecciona multiples imagenes
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }
*/
        // the main intent is the last in the list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);
        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));
        return chooserIntent;
    }

    /**
     * Get URI to image received from capture by camera.
     */
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "pickImageResult.jpeg"));
        }
        return outputFileUri;
    }

    /**
     * Get the URI of the selected image from {@link #getPickImageChooserIntent()}.<br/>
     * Will return the correct URI for camera and gallery image.
     *
     * @param data the returned data of the activity result
     */
    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null && data.getData() != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    /**
     * Test if we can open the given Android URI to test if permission required error is thrown.<br>
     */
    public boolean isUriRequiresPermissions(Uri uri) {
        try {
            ContentResolver resolver = getContentResolver();
            InputStream stream = resolver.openInputStream(uri);
            stream.close();
            return false;
        } catch (FileNotFoundException e) {
            if (e.getCause() instanceof ErrnoException) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Bitmap bitmap;
        String newPath = null;
        if (!imagesUriArrayList.isEmpty()) {

            if (posBit >= NUM_MAXIMO_DE_IMAGENES)//controlamos el numero de imágenes que deben de mostrarse
                Toast.makeText(this, "Máximo " + NUM_MAXIMO_DE_IMAGENES + " imágenes", Toast.LENGTH_LONG).show();
            else {
                System.out.println("imagesUriArrayList.size() " + imagesUriArrayList.size());

                for (Uri uri : imagesUriArrayList) {
                    //si se toma una foto
                    if (seTomoFoto) {
                        // con esto la foto ya no va a rotar
                        bitmap = verificaRotacionDeImagen(uri.getPath(), uri);
                        bitmapFotos.add(bitmap);
                    } else {
                        String strUri = getRealPathFromUri(uri);
                        /*System.out.println("uri " + uri);
                        System.out.println("getPath " + uri.getPath());
                        System.out.println("getFileName " + getFileName(uri));
                        System.out.println("getRealPathFromUri " + getRealPathFromUri(uri));
                        */
                        if (strUri == null) {

                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                uri = getImageUri(getApplicationContext(), bitmap);

                                newPath = resizeAndCompressImageBeforeSend(getApplicationContext(), getPathFromUri(this, uri), getFileName(uri));// uri.getPath(), getFileName(uri));
                                //Transformamos el newPath en un Bitmap
                                bitmap = getBitmap(newPath);
                                bitmapFotos.add(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            bitmap = verificaRotacionDeImagen(getRealPathFromUri(uri), uri);
                            bitmapFotos.add(bitmap);
                        }
                    }
                }
                for (Bitmap bmp : bitmapFotos) {
                    //converting image to base64 string
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    arrayTmpStringFotos.add(imageString);

                    imgB[posBit].setVisibility(View.VISIBLE);
                    imgB[posBit].setImageBitmap(bmp);
                    posBit++;
                }
                for (String strFoto : arrayTmpStringFotos) {
                    arrayStringFotos.add(strFoto);
                }
                imagesUriArrayList = new ArrayList<>();
                arrayTmpStringFotos = new ArrayList<>();
                bitmapFotos = new ArrayList<>();
                /*System.out.println("---------");
                System.out.println("arrayStringFotos.size() " + arrayStringFotos.size());
                System.out.println("---------");*/
            }
        }
    }

    /////////////////////////////////////////////////   SECCION PARA OBTENER LA URI
    public static String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
/////////////////////////////////////////////////   SECCION PARA OBTENER LA URI

    public Bitmap verificaRotacionDeImagen(String path, Uri uri) {
        Bitmap bitmap = null;
        boolean changeOrientacion = false;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            //Log.d("EXIF", "Exif: " + orientation);
            //System.out.println("Exif: strUri == null " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                changeOrientacion = true;
                matrix.postRotate(90);
            } else if (orientation == 3) {
                changeOrientacion = true;
                matrix.postRotate(180);
            } else if (orientation == 8) {
                changeOrientacion = true;
                matrix.postRotate(270);
            }
            String newPath = resizeAndCompressImageBeforeSend(getApplicationContext(), path, getFileName(uri));
            //Transformamos el Path en un Bitmap
            bitmap = getBitmap(newPath);
            if (changeOrientacion)
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true); // rotating bitmap

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromUri(Uri contentUri) {
        String yourRealPath = null;
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, filePathColumn, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            yourRealPath = cursor.getString(columnIndex);
        } else {
            //boooo, cursor doesn't have rows ...
        }
        cursor.close();
        return yourRealPath;
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public Bitmap getBitmap(String path) {
        Bitmap bitmap = null;
        try {
            File f = new File(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    public String resizeAndCompressImageBeforeSend(Context context, String filePath, String fileName) {
        final int MAX_IMAGE_SIZE = 700 * 1024; // max final file size in kilobytes
        // First decode with inJustDecodeBounds=true to check dimensions of image
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize(First we are going to resize the image to 800x800 image, in order to not have a big but very low quality image.
        //resizing the image will already reduce the file size, but after resizing we will check the file size and start to compress image
        options.inSampleSize = calculateInSampleSize(options, 800, 800);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        System.out.println("filePath " + filePath);
        Bitmap bmpPic = BitmapFactory.decodeFile(filePath, options);

        int compressQuality = 100; // quality decreasing by 5 every loop.
        int streamLength;
        do {
            ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
            //Log.d("compressBitmap", "Quality: " + compressQuality);
            bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream);
            byte[] bmpPicByteArray = bmpStream.toByteArray();
            streamLength = bmpPicByteArray.length;
            compressQuality -= 5;
            //Log.d("compressBitmap", "Size: " + streamLength / 1024 + " kb");
        } while (streamLength >= MAX_IMAGE_SIZE);

        try {
            //save the resized and compressed file to disk cache
            //Log.d("compressBitmap", "cacheDir: " + context.getCacheDir());
            FileOutputStream bmpFile = new FileOutputStream(context.getCacheDir() + fileName);
            bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpFile);
            bmpFile.flush();
            bmpFile.close();
        } catch (Exception e) {
            //Log.e("compressBitmap", "Error on saving file");
        }
        //return the path of resized and compressed file
        return context.getCacheDir() + fileName;
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        String debugTag = "MemoryInformation";
        // Image nin islenmeden onceki genislik ve yuksekligi
        final int height = options.outHeight;
        final int width = options.outWidth;
        //Log.d(debugTag, "image height: " + height + "---image width: " + width);
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        //Log.d(debugTag, "inSampleSize: " + inSampleSize);
        return inSampleSize;
    }

    public void onClickHecho(View view) {
        //enviamos la lista de nombres de las fotos
        Intent intent = new Intent();
        intent.putExtra("lstNombreFoto", (Serializable) lstNombreFoto);
        setResult(Activity.RESULT_OK, intent);

        finish();
    }
}