package es.arizsystems.restaurante.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import es.arizsystems.restaurante.R;
import es.arizsystems.restaurante.fragment.DishesListFragment;
import es.arizsystems.restaurante.fragment.TableDetailPagerFragment;
import es.arizsystems.restaurante.model.Plato;
import es.arizsystems.restaurante.model.Restaurante;

public class TableDetailPagerActivity extends AppCompatActivity implements DishesListFragment.OnDishSelectedListener {
    public static final String TABLE_INDEX = "TABLE_INDEX";

    private static final int REQUEST_DETAILS = 1;

    int tableIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_detail);

        tableIndex = getIntent().getIntExtra(TABLE_INDEX, 0);

        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentById(R.id.view_detail_fragment) == null) {

            TableDetailPagerFragment fragment = TableDetailPagerFragment.newInstance(tableIndex);
            fm.beginTransaction().add(R.id.view_detail_fragment, fragment).commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Añadir un plato en la mesa", Snackbar.LENGTH_LONG)
                        .setAction("Añadir", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showDishesList();
                            }
                        }).show();
            }
        });
    }

    private void showDishesList() {
        Intent intent = new Intent(this, DishesListActivity.class);
        intent.putExtra(DishesListActivity.ARG_TABLE, tableIndex);
        startActivityForResult(intent, REQUEST_DETAILS);
    }

    @Override
    public void onDishSelected(Plato plato, int extraInfo, boolean mAllDishes, int position_dish) {

        if (mAllDishes) { //  Si nos envia true, es que tenemos que mostrar el detalle de un plato no asignado a ninguna mesa, aun.


        }else {//   Si no, es que estamos vamos a mostrar los detalles de un plato de la carta asignado a una mesa en concreto.
            Intent intent = new Intent(this, DishDetailActivity.class);
            intent.putExtra(DishDetailActivity.EXTRA_DISH, plato);
            intent.putExtra(DishDetailActivity.EXTRA_POSITION, extraInfo);
            intent.putExtra(DishDetailActivity.POSITION_DISH, position_dish);
            intent.putExtra(DishDetailActivity.EXTRA_SHOW, mAllDishes);
            //startActivityForResult(intent, REQUEST_DETAILS);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_DETAILS) {
            if (resultCode == Activity.RESULT_OK) {

                FragmentManager fm = getFragmentManager();
                if (fm.findFragmentById(R.id.view_detail_fragment) == null) {
                    //  Aquí tengo que refrescar puesto que me han introducido un nuevo plato en la mesa.
                    TableDetailPagerFragment fragment = TableDetailPagerFragment.newInstance(1);
                    fm.beginTransaction().add(R.id.view_detail_fragment, fragment).commit();
                }
            }
        }

    }
}
