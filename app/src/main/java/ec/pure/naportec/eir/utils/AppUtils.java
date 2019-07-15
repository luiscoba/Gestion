package ec.pure.naportec.eir.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Environment;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AppUtils {

    private static Date getDate(String aDate) {
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;

    }

    public static String getFormattedDate(String dateString) {
        Date date = getDate(dateString);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int day = cal.get(Calendar.DATE);
        switch (day % 10) {
            case 1:
                return new SimpleDateFormat("MMMM d'st', yyyy").format(date);
            case 2:
                return new SimpleDateFormat("MMMM d'nd', yyyy").format(date);
            case 3:
                return new SimpleDateFormat("MMMM d'rd', yyyy").format(date);
            default:
                return new SimpleDateFormat("MMMM d'th', yyyy").format(date);
        }
    }

    public static int getScreenWidth(Context mContext) {
        boolean width = false;
        WindowManager wm = (WindowManager)mContext.getSystemService("window");
        Display display = wm.getDefaultDisplay();
        int width1;
        if(Build.VERSION.SDK_INT > 12) {
            Point size = new Point();
            display.getSize(size);
            width1 = size.x;
        } else {
            width1 = display.getWidth();
        }

        return width1;
    }

    public static int getScreenHeight(Context mContext) {
        boolean height = false;
        WindowManager wm = (WindowManager)mContext.getSystemService("window");
        Display display = wm.getDefaultDisplay();
        int height1;
        if(Build.VERSION.SDK_INT > 12) {
            Point size = new Point();
            display.getSize(size);
            height1 = size.y;
        } else {
            height1 = display.getHeight();
        }

        return height1;
    }

    public static void closeKeyboard(Activity activity) {
        final InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            if (activity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    /***INICIO PARA USO DE CAMARA***/
    public static boolean bool_sdDisponible = false, bool_sdAccesoEscritura = false, bool_dirExist=false;
    public static String dirImagenProcesa="";

    /** Verifica que Memoria externa esta habilitada y verifica directorio de uso
     *  llena variables globales que se usan en VariasImageCameraActivity
     */
    public static void verificaSD_Directory(String dirProceso) {
        //Comprobamos el estado de la memoria externa (tarjeta SD)
        bool_sdDisponible = bool_sdAccesoEscritura = bool_dirExist=false;
        dirImagenProcesa="";

        String estado = Environment.getExternalStorageState();
        boolean isRemovable = Environment.isExternalStorageRemovable();
        if (estado.equals(Environment.MEDIA_MOUNTED))         {
            bool_sdDisponible = bool_sdAccesoEscritura = true;
        }
        else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY))       {
            bool_sdDisponible = true;         bool_sdAccesoEscritura = false;
        }
        else       {
            bool_sdDisponible = false;    bool_sdAccesoEscritura = false;
        }

        if (bool_sdDisponible && bool_sdAccesoEscritura) {
            File ruta_sd = Environment.getExternalStorageDirectory();
            dirImagenProcesa = ruta_sd.getPath() + dirProceso;
            File verifDir = new File(dirImagenProcesa);
            if (verifDir.exists())
                bool_dirExist = true;
            else {  //No existe directorio
                if (verifDir.mkdir())   {   //Se pudo crear el directorio
                    bool_dirExist = true;
                }
            }
        }

    }

    /** Copiar un archivo de un directorio a otro indicando incluso del nombre del archivo destino
     * @param File s    Ruta y archivo de origen
     * @param File t    Ruta y archivo de destino
     * @return boolean verdadero o false dependiendo si se realizó con exito la copia.
     */
    public static boolean copyFile(File s, File t)
    {
        Boolean bol=false;
        try{
            FileChannel in = (new FileInputStream(s)).getChannel();
            FileChannel out = (new FileOutputStream(t)).getChannel();
            in.transferTo(0, s.length(), out);
            in.close();
            out.close();
            bol=true;
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return bol;
    }
    /***FIN PARA USO DE CAMARA***/

}
