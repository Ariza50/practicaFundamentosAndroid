package es.arizsystems.restaurante.model;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Ariz on 3/6/17.
 */

public class Mesa implements Serializable {
    //  Propiedades de la Mesa
    private String mName;
    private LinkedList<Plato> mPlatos;
    private LinkedList<String> mDetalles;

    public Mesa(String name, LinkedList<Plato> platos) {
        mName = name;
        mPlatos = platos;
    }
    //  Añado constructor de conveniencia.
    public Mesa(String name) {
        this(name, null);
        mPlatos = new LinkedList<>();
        mDetalles = new LinkedList<>();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public LinkedList<Plato> getPlatos() {
        return mPlatos;
    }

    public void setPlatos(LinkedList<Plato> platos) {
        mPlatos = platos;
        for (int i=0; i<platos.size();i++){
            mDetalles.add(i, "Detalles de la comanda");
        }
    }

    @Override
    public String toString() {
        return getName();
    }

    public LinkedList<String> getDetalles() {
        return mDetalles;
    }

    public void setDetalles(LinkedList<String> detalles) {
        mDetalles = detalles;
    }
}
