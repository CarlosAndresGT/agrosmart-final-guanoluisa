package ec.edu.espe.agrosmart.mapper;

import ec.edu.espe.agrosmart.domain.Producto;
import ec.edu.espe.agrosmart.entity.ProductoEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir la entidad JPA ProductoEntity al modelo de dominio inmutable Producto.
 */
public final class ProductoMapper {

    private ProductoMapper() {
        // Clase utilitaria estática
    }

    public static Producto toDominio(ProductoEntity entity) {
        if (entity == null) {
            return null;
        }

        List<String> correos = convertStringToList(entity.getCorreosNotificacion());

        return new Producto(
                entity.getIdProducto(),
                entity.getNombreProducto(),
                entity.getCategoria(),
                entity.getPrecioUsd(),
                correos
        );
    }

    private static List<String> convertStringToList(String str) {
        if (str == null || str.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(str.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}