package org.example.model.Entity;

import java.util.List;
import java.util.Objects;

public class Sede {
    private int id;
    private String nombre;
    private int presupuesto;

    protected List<Complejo> complejos;

    public Sede(int id, String nombre, int presupuesto) {
        this.id = id;
        this.nombre = nombre;
        this.presupuesto = presupuesto;
    }

    public Sede(int id) {
        this(id, "",-1);
    }

    public Sede() {
        this(-1, "",-1);
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

    public int getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(int presupuesto) {
        this.presupuesto = presupuesto;
    }

    public List<Complejo> getComplejos() {
        return complejos;
    }

    public void setComplejos(List<Complejo> complejos) {
        this.complejos = complejos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sede sede = (Sede) o;
        return id == sede.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Sede{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", presupuesto=" + presupuesto +
                '}';
    }
}
