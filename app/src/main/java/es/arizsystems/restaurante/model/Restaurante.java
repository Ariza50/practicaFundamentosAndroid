package es.arizsystems.restaurante.model;

import java.util.LinkedList;

/**
 * Created by Ariz on 3/6/17.
 */

public class Restaurante {

    private static Restaurante mInstance;

    //  Propiedades del Restaurante
    private LinkedList<Mesa> mMesas;
    private LinkedList<Plato> mPlatos;

    public static Restaurante getInstance() {
        if (mInstance == null) {
            // No existe aún una instancia estática de la clase, la creo
            mInstance = new Restaurante();
        }

        return mInstance;
    }

    public Restaurante() {
        //  Creo mesas de ejemplo para tener algunas de ejemplo.
        mMesas = new LinkedList<>();
        mMesas.add(new Mesa("Mesa 1"));
        mMesas.add(new Mesa("Mesa 2"));
        mMesas.add(new Mesa("Mesa 3"));
        mMesas.add(new Mesa("Mesa 4"));
        mMesas.add(new Mesa("Mesa 5"));
        mMesas.add(new Mesa("Mesa 6"));

        //  Inicializo la lista de Platos disponibles en la carta del Restaurante
        mPlatos = new LinkedList<>();
    }

    public Mesa getMesa(int index) {
        return mMesas.get(index);
    }

    public LinkedList<Mesa> getMesas() {
        return mMesas;
    }

    public int getCount() {
        return mMesas.size();
    }

    public LinkedList<Plato> getPlatos() { return mPlatos; }

    public void setPlatos(LinkedList<Plato> platos) { mPlatos = platos; }

}
