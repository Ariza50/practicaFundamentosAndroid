package es.arizsystems.restaurante.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

import es.arizsystems.restaurante.R;
import es.arizsystems.restaurante.model.Plato;
import es.arizsystems.restaurante.model.Restaurante;

/**
 * Created by Ariz on 10/6/17.
 */

public class DishesRecyclerAdapter extends RecyclerView.Adapter<DishesRecyclerAdapter.DishesViewHolder>{

    private LinkedList<Plato> mPlatos;
    private String mDetails;
    private View.OnClickListener mOnClickListener;

    public DishesRecyclerAdapter(LinkedList<Plato> platos) {
        super();
        mPlatos = platos;
        //mDetails = detalles;

    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public DishesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_dishes, parent, false);
        view.setOnClickListener(mOnClickListener);
        return new DishesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DishesViewHolder holder, int position) {

        holder.bindDish(mPlatos.get(position));

    }

    @Override
    public int getItemCount() {
        return mPlatos.size();
    }

    protected class DishesViewHolder extends RecyclerView.ViewHolder{

        private final ImageView mDishImage;
        private final TextView mDishName;
        private final TextView mDishPvp;
        private final TextView mDishDescription;
        private final TextView mDishDetails;

        public DishesViewHolder(View itemView) {
            super(itemView);

            mDishImage = (ImageView) itemView.findViewById(R.id.dish_image);
            mDishName = (TextView) itemView.findViewById(R.id.dish_name);
            mDishPvp = (TextView) itemView.findViewById(R.id.dish_pvp);
            mDishDescription = (TextView) itemView.findViewById(R.id.dish_description);
            mDishDetails = (TextView) itemView.findViewById(R.id.dish_details);
        }

        public void bindDish(Plato plato) {

            mDishImage.setImageResource(plato.getPhoto());
            mDishName.setText(plato.getName());
            //mDishPvp.setText((int) plato.getPrecio());
            mDishDescription.setText(plato.getDescripcion());
            //mDishDetails.setText(Restaurante.getInstance().getPlatos(posit));
        }

    }

}
