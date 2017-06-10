package es.arizsystems.restaurante.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import es.arizsystems.restaurante.R;
import es.arizsystems.restaurante.fragment.TableDetailPagerFragment;

public class TableDetailPagerActivity extends AppCompatActivity {
    public static final String TABLE_INDEX = "TABLE_INDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_detail);

        int tableIndex = getIntent().getIntExtra(TABLE_INDEX, 0);

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
