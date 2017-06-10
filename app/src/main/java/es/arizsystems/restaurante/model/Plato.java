package es.arizsystems.restaurante.model;

/**
 * Created by Ariz on 3/6/17.
 */

public class Plato {
    //  Propiedades de la Mesa
    private String Name;
    private Integer id;
    private Integer photo;
    private float precio;

    public Plato(String name, Integer id, Integer photo, float precio) {
        Name = name;
        this.id = id;
        this.photo = photo;
        this.precio = precio;
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
}
