package es.arizsystems.restaurante.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.LinkedList;

import es.arizsystems.restaurante.model.Plato;

/**
 * Created by Ariz on 4/6/17.
 */

public class DishesListFragment extends Fragment {

    public  static final String ARG_DISHES = "ARG_DISHES";

    private LinkedList<Plato> mDishes;

    public static DishesListFragment newInstance (LinkedList<Plato> mDishes) {
        DishesListFragment fragment = new DishesListFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_DISHES, mDishes);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mDishes = (LinkedList<Plato>) getArguments().getSerializable(ARG_DISHES);
        }
    }
}
