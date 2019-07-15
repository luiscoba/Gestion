package ec.pure.naportec.eir.utils;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import ec.pure.naportec.eir.AppConstants;
import ec.pure.naportec.eir.R;

public class PersonalizadoToolBar extends AppCompatActivity implements AppConstants {

    public void poner_placa_y_evento(String placa_ingresada, String evento_seleccionado) {

        colocar_toolbar();

        getSupportActionBar().setTitle("\t\t" + placa_ingresada);
        getSupportActionBar().setSubtitle(R.string.text_placa_subtitulo);

        TextView evento = findViewById(R.id.txv_evento);
        evento.setText(evento_seleccionado);
    }

    public void colocar_toolbar() {
        // <-- con esta sección obtenmos y creamos el toolbar el botón de atrás
        Toolbar toolbar = findViewById(R.id.toolbarManual);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.naportec_logo_chico);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        // finaliza sección con la que se crea el botón de atrás -->
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();//aqui ya regresa al anterior activity
    }

    // este metodo es para la flecha de atrás
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//aca debes colocar el metodo que deseas que retorne
        return true;
    }

}
