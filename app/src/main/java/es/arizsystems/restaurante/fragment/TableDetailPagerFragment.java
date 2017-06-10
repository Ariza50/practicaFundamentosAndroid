package es.arizsystems.restaurante.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import es.arizsystems.restaurante.R;
import es.arizsystems.restaurante.model.Restaurante;

/**
 * Created by Ariz on 4/6/17.
 */

public class TableDetailPagerFragment extends Fragment {

    public static final String INIT_TABLE = "INIT_TABLE";
    private int mInitTable = 0;
    private ViewPager mPager;

    public static TableDetailPagerFragment newInstance(int initWithTableIndex) {

        TableDetailPagerFragment fragment = new TableDetailPagerFragment();

        Bundle args = new Bundle();
        args.putInt(INIT_TABLE, initWithTableIndex);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if(getArguments() != null) {
            mInitTable = getArguments().getInt(INIT_TABLE, 0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_table_detail, container, false);

        mPager = (ViewPager) root.findViewById(R.id.view_pager);
        TableDetailPagerAdapter adapter = new TableDetailPagerAdapter(getFragmentManager(), Restaurante.getInstance());

        //  Le damos el adapter al viewPager para que gestione los Fragments
        mPager.setAdapter(adapter);

        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                updateTable(position);
            }
        });

        changeTable(mInitTable);
        updateTable(mInitTable);

        return root;
    }

    public void changeTable(int tableIndex) {
        mPager.setCurrentItem(tableIndex);
    }

    private void updateTable(int position) {
        String nTable = Restaurante.getInstance().getMesa(position).getName();
        if(getActivity() instanceof AppCompatActivity) {
            AppCompatActivity superActivity = (AppCompatActivity) getActivity();
            ActionBar toolBar = superActivity.getSupportActionBar();
            if (toolBar != null) {
                toolBar.setTitle(nTable);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        //  De momento no quiero meter ningún menu.
        //inflater.inflate(R.menu);
    }
}

class TableDetailPagerAdapter extends FragmentPagerAdapter {

    private Restaurante r;

    public TableDetailPagerAdapter (FragmentManager fm, Restaurante rest) {
        super(fm);

        r = rest;
    }


    @Override
    public Fragment getItem(int position) {

        DishesListFragment fragment = DishesListFragment.newInstance(r.getMesa(position).getPlatos());

        return fragment;
    }

    @Override
    public int getCount() {
        return r.getCount();
    }
}
