package es.arizsystems.restaurante.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import es.arizsystems.restaurante.adapter.DishesRecyclerAdapter;
import es.arizsystems.restaurante.R;
import es.arizsystems.restaurante.activity.DishDetailActivity;
import es.arizsystems.restaurante.model.Plato;

/**
 * Created by Ariz on 4/6/17.
 */

public class DishesListFragment extends Fragment {

    public  static final String ARG_DISHES = "ARG_DISHES";
    public  static final String ARG_EXTRA_INFO = "ARG_EXTRA_INFO";
    public  static final String ARG_ALL_DISHES = "ARG_ALL_DISHES";

    protected OnDishSelectedListener mOnDishSelectedListener;

    private LinkedList<Plato> mDishes;
    private View mRoot;
    private RecyclerView mList;
    private int mInfo = -1;
    private boolean mAllDishes;

    public static DishesListFragment newInstance (LinkedList<Plato> mDishes, int showExtra, boolean allDishes) {
        DishesListFragment fragment = new DishesListFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_DISHES, mDishes);
        args.putSerializable(ARG_EXTRA_INFO, showExtra);
        args.putSerializable(ARG_ALL_DISHES, allDishes);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mDishes = (LinkedList<Plato>) getArguments().getSerializable(ARG_DISHES);
            mInfo = (int) getArguments().getSerializable(ARG_EXTRA_INFO);
            mAllDishes = (boolean) getArguments().getSerializable(ARG_ALL_DISHES);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mRoot = inflater.inflate(R.layout.fragment_dishes_list, container, false);

        mList = (RecyclerView) mRoot.findViewById(R.id.dishes_list);
        //  TODO Aquí debería hacerlo dinámico dependiendo del xml que se cargue, es decir, según el tipo de dispositivo.
        mList.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        mList.setItemAnimator(new DefaultItemAnimator());

        update();

        return mRoot;

    }

    private void update() {

        DishesRecyclerAdapter adapter = new DishesRecyclerAdapter((mDishes));

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mList.getChildAdapterPosition(v);
                Plato pl = mDishes.get(position);

                // TODO Lanzar esto desde una actividad en vez de desde el fragment.
                /*
                Intent intent = new Intent(getActivity(), DishDetailActivity.class);
                intent.putExtra(DishDetailActivity.EXTRA_INFO, mInfo);
                intent.putExtra(DishDetailActivity.EXTRA_DISH, pl);
                Bundle aniOp = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), v, getString(R.string.transition_to_detail)).toBundle();
                startActivity(intent, aniOp);
                */
                if (mOnDishSelectedListener != null) {
                    mOnDishSelectedListener.onDishSelected(pl, mInfo, mAllDishes, position);
                }
            }
        });

        mList.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() instanceof OnDishSelectedListener) {
            mOnDishSelectedListener = (OnDishSelectedListener) getActivity();
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (getActivity() instanceof OnDishSelectedListener) {
            mOnDishSelectedListener = (OnDishSelectedListener) getActivity();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mOnDishSelectedListener = null;
    }

    public interface OnDishSelectedListener {
        void onDishSelected(Plato plato, int extraInfo, boolean mAllDishes, int position_dish);
    }


}
