package ec.pure.naportec.eir.ui.mainmenu.inspeccion.menu.contenedor.grid_imagen;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class GridViewItem extends AppCompatImageView {

    private static final String TAG_GridViewItem = "eir." + GridViewItem.class.getSimpleName();

    public GridViewItem(Context context) {
        super(context);
    }

    public GridViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec); // This is the key that will make the height equivalent to its width
        //System.out.println("widthMeasureSpec " + widthMeasureSpec + " heightMeasureSpec " + heightMeasureSpec);
    }

}