package es.arizsystems.restaurante.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import es.arizsystems.restaurante.R;
import es.arizsystems.restaurante.model.Plato;
import es.arizsystems.restaurante.model.Restaurante;

/**
 * Created by Ariz on 11/6/17.
 */

public class DishDetailActivity extends AppCompatActivity {

    public static final String EXTRA_DISH = "EXTRA_DISH";
    public static final String EXTRA_SHOW = "EXTRA_SHOW";
    public static final String EXTRA_POSITION = "EXTRA_POSITION";
    public static final String POSITION_DISH = "POSITION_DISH";

    public static final String EXTRA_DETAILS = "EXTRA_DETAILS";

    private ImageView mDishImage;
    private TextView mDishName;
    private TextView mDishPvp;
    private TextView mDishDescription;
    private EditText mEditText;
    private Button mOkButton;
    private Button mCancelButton;

    private int position_table;
    private int position_dish;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  TODO yo creo que aquí, segun el valor booleano que rescatemos en los parámetros,
        //  podríamos mostrar un layout con el plato o uno con el plato y un campo más para añadir los extras, cantidad etc.
        setContentView(R.layout.activity_dish_detail);

        Plato plato = (Plato) getIntent().getSerializableExtra(EXTRA_DISH);
        position_table = (int) getIntent().getSerializableExtra(EXTRA_POSITION);
        position_dish = (int) getIntent().getSerializableExtra(POSITION_DISH);
        boolean extra = (boolean) getIntent().getSerializableExtra(EXTRA_SHOW);

        mDishImage = (ImageView) findViewById(R.id.dish_image);
        mDishName = (TextView) findViewById(R.id.dish_name);
        mDishPvp = (TextView) findViewById(R.id.dish_pvp);
        mDishDescription = (TextView) findViewById(R.id.dish_description);
        mEditText = (EditText) findViewById(R.id.detalles_edit);
        mOkButton = (Button) findViewById(R.id.ok_button);
        mCancelButton = (Button) findViewById(R.id.cancel_button);

        mDishImage.setImageResource(plato.getPhoto());
        mDishName.setText(plato.getName());
        //mDishPvp.setText((int) plato.getPrecio());
        mDishDescription.setText(plato.getDescripcion());

        if (!extra){
            mEditText.setVisibility(View.VISIBLE);
            //  TODO: Aquí hay que poner el valor guardado, si lo hay, con los detalles de la mesa.
            Restaurante r = Restaurante.getInstance();
            String s = r.getMesa(position_table).getDetalles().get(position_dish).toString();
            mEditText.setText(s);
            mOkButton.setVisibility(View.VISIBLE);
            mCancelButton.setVisibility(View.VISIBLE);
        }else {
            mEditText.setVisibility(View.INVISIBLE);
            mOkButton.setVisibility(View.INVISIBLE);
            mCancelButton.setVisibility(View.INVISIBLE);
        }

        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardamosDetalles();
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelamosDetalles();
            }
        });

    }

    private void cancelamosDetalles() {
        //setResult(RESULT_CANCELED);
        finish();
    }

    private void guardamosDetalles() {

        Restaurante.getInstance().getMesa(position_table).getDetalles().add(position_dish, String.valueOf(mEditText.getText()));
        finish();
        //Intent devIntent = new Intent();
        //devIntent.putExtra(EXTRA_DETAILS, mEditText.getText());
        //setResult(RESULT_OK, devIntent);
    }
}
