package es.arizsystems.restaurante.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import es.arizsystems.restaurante.R;
import es.arizsystems.restaurante.fragment.DishesListFragment;
import es.arizsystems.restaurante.fragment.TableListFragment;
import es.arizsystems.restaurante.model.Plato;
import es.arizsystems.restaurante.model.Restaurante;

/**
 * Created by Ariz on 11/6/17.
 */

public class DishesListActivity extends AppCompatActivity implements DishesListFragment.OnDishSelectedListener {

    public final static String ARG_TABLE = "ARG_TABLE";

    int tableIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dishes_list);

        FragmentManager fm = getFragmentManager();
        if (findViewById(R.id.list_dishes_list) != null) {
            if(fm.findFragmentById(R.id.list_dishes_list) == null) {
                tableIndex = getIntent().getIntExtra(ARG_TABLE, 0);
                DishesListFragment fragment = DishesListFragment.newInstance(Restaurante.getInstance().getPlatos(), tableIndex, true);
                fm.beginTransaction().add(R.id.list_dishes_list, fragment).commit();

            }
        }

    }

    @Override
    public void onDishSelected(Plato plato, int extraInfo, boolean mAllDishes, int position_dish) {
        Restaurante r = Restaurante.getInstance();

        r.getMesa(extraInfo).getPlatos().add(plato);
        Intent devIntent = new Intent();
        devIntent.putExtra(ARG_TABLE, tableIndex);
        setResult(RESULT_OK, devIntent);
        finish();
    }



}
