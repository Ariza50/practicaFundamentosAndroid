package es.arizsystems.restaurante.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import es.arizsystems.restaurante.R;
import es.arizsystems.restaurante.fragment.TableDetailPagerFragment;
import es.arizsystems.restaurante.fragment.TableListFragment;
import es.arizsystems.restaurante.model.Mesa;
import es.arizsystems.restaurante.model.Restaurante;

public class TablesActivity extends AppCompatActivity implements TableListFragment.onTableSelListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        FragmentManager fm = getFragmentManager();

        //  Si soy un dispositivo tipo movil
        if (findViewById(R.id.listTablesFragment) != null) {
            if(fm.findFragmentById(R.id.listTablesFragment) == null) {

                TableListFragment fragment = TableListFragment.newInstance(Restaurante.getInstance().getMesas());
                fm.beginTransaction().add(R.id.listTablesFragment, fragment).commit();

            }
        }

        //  Si soy un dispositivo tipo tablet y me caben más cosas habré cargado el activity_tables sw400dp y por lo tanto me cabe la lista de mesas y la lista de comida de la mesa seleccionada.
        if (findViewById(R.id.view_detail_fragment) != null) {
            if(fm.findFragmentById(R.id.view_detail_fragment) == null) {

                TableDetailPagerFragment fragment = TableDetailPagerFragment.newInstance(0);
                fm.beginTransaction().add(R.id.view_detail_fragment, fragment).commit();

            }
        }

    }

    @Override
    public void onTableSelected(Mesa mesa, int position) {

        //  Si llegamos aquí es que desde el fragment que hemos lanzado se ha pulsado sobre algo, así que hay que lanzar la actividad correspondiente
        FragmentManager fm = getFragmentManager();


        Intent intent = new Intent(this, TableDetailPagerActivity.class);
        intent.putExtra(TableDetailPagerActivity.TABLE_INDEX, position);
        startActivity(intent);

    }
}
