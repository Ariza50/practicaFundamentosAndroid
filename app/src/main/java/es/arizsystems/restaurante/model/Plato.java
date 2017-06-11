package es.arizsystems.restaurante.model;

import java.io.Serializable;

/**
 * Created by Ariz on 3/6/17.
 */

public class Plato implements Serializable{
    //  Propiedades de la Mesa
    private String Name;
    private Integer id;
    private Integer photo;
    private float precio;
    private String Descripcion;

    public Plato(String name, Integer id, Integer photo, float precio, String desc) {
        Name = name;
        this.id = id;
        this.photo = photo;
        this.precio = precio;
        this.Descripcion = desc;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPhoto() {
        return photo;
    }

    public void setPhoto(Integer photo) {
        this.photo = photo;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
