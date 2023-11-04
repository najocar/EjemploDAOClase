package org.example.model.Entity;

import java.util.Objects;

public class Complejo {
    private int id;
    private String nombre;
    private String localizacion;
    private int area;
    private String jefe;

    public Sede sede;

    public Complejo(int id, String nombre, String localizacion, int area, String jefe) {
        this.id = id;
        this.nombre = nombre;
        this.localizacion = localizacion;
        this.area = area;
        this.jefe = jefe;
    }

    public Complejo(int id) {
        this(id, "", "", -1, "");
    }

    public Complejo() {
        this(-1, "", "", -1, "");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getJefe() {
        return jefe;
    }

    public void setJefe(String jefe) {
        this.jefe = jefe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complejo complejo = (Complejo) o;
        return id == complejo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Complejo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", localizacion='" + localizacion + '\'' +
                ", area=" + area +
                ", jefe='" + jefe + '\'' +
                '}';
    }
}
