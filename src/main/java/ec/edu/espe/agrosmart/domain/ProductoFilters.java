package ec.edu.espe.agrosmart.domain;

import java.math.BigDecimal;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

// Lógica funcional de filtrado, trazabilidad y transformación sobre Producto.

public final class ProductoFilters {

        private ProductoFilters() {
                // Clase de utilidades/constantes funcionales
        }

        // Un producto es comercializable (válido) si precioUsd > 0 Y la lista d correos no está vacía

        public static final Predicate<Producto> IS_VALID = p -> p != null &&
                        p.getPrecioUsd() != null &&
                        p.getPrecioUsd().compareTo(BigDecimal.ZERO) > 0 &&
                        p.getCorreosNotificacion() != null &&
                        !p.getCorreosNotificacion().isEmpty();

        // Imprime por consola id y nombre del producto procesado

            public static final Consumer<Producto> LOG_PRODUCTO = p ->
            System.out.println("Procesando producto [" + Thread.currentThread().getName() + "]: ID=" + p.getId() + ", Nombre=" + p.getNombre());

        // Devuelve una NUEVA instancia de Producto con el nombre en mayúsculas


        public static final Function<Producto, Producto> A_MAYUSCULAS = p -> new Producto(
                        p.getId(),
                        p.getNombre() == null ? null : p.getNombre().toUpperCase(),
                        p.getCategoria(),
                        p.getPrecioUsd(),
                        p.getCorreosNotificacion());
}
