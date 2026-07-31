package ec.edu.espe.agrosmart.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

//Modelo de dominio 100% inmutable.

public final class Producto {

    private final Long id;
    private final String nombre;
    private final String categoria;
    private final BigDecimal precioUsd;
    private final List<String> correosNotificacion;

    public Producto(Long id, String nombre, String categoria, BigDecimal precioUsd, List<String> correosNotificacion) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precioUsd = precioUsd;
        // Copia defensiva de ENTRADA
        this.correosNotificacion = correosNotificacion == null
                ? Collections.emptyList()
                : new ArrayList<>(correosNotificacion);
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public BigDecimal getPrecioUsd() {
        return precioUsd;
    }

    // Copia defensiva de SALIDA
    public List<String> getCorreosNotificacion() {
        return Collections.unmodifiableList(new ArrayList<>(correosNotificacion));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Objects.equals(id, producto.id) &&
                Objects.equals(nombre, producto.nombre) &&
                Objects.equals(categoria, producto.categoria) &&
                Objects.equals(precioUsd, producto.precioUsd) &&
                Objects.equals(correosNotificacion, producto.correosNotificacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, categoria, precioUsd, correosNotificacion);
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", precioUsd=" + precioUsd +
                ", correosNotificacion=" + correosNotificacion +
                '}';
    }
}
