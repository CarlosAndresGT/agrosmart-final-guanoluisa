package ec.edu.espe.agrosmart.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas unitarias sobre la logica funcional ProductoFilters
class ProductoFiltersTest {

    @Test
    void isValid_conProductoValido_debeRetornarTrue() {
        // Arrange
        Producto producto = new Producto(1L, "Rosas Rojas", "Flores", new BigDecimal("18.50"), List.of("ventas@rosas.com"));

        // Act
        boolean esValido = ProductoFilters.IS_VALID.test(producto);

        // Assert
        assertTrue(esValido);
    }

    @Test
    void isValid_conPrecioCero_debeRetornarFalse() {
        // Arrange
        Producto producto = new Producto(2L, "Muestra Claveles", "Flores", BigDecimal.ZERO, List.of("muestras@flores.com"));

        // Act
        boolean esValido = ProductoFilters.IS_VALID.test(producto);

        // Assert
        assertFalse(esValido);
    }

    @Test
    void isValid_conListaCorreosVacia_debeRetornarFalse() {
        // Arrange
        Producto producto = new Producto(3L, "Lirios", "Flores", new BigDecimal("14.00"), Collections.emptyList());

        // Act
        boolean esValido = ProductoFilters.IS_VALID.test(producto);

        // Assert
        assertFalse(esValido);
    }

    @Test
    void aMayusculas_alTransformar_debeRetornarNuevaInstanciaConNombreEnMayusculas() {
        // Arrange
        Producto productoOriginal = new Producto(1L, "rosas rojas", "Flores", new BigDecimal("18.50"), List.of("ventas@rosas.com"));

        // Act
        Producto productoTransformado = ProductoFilters.A_MAYUSCULAS.apply(productoOriginal);

        // Assert
        assertNotSame(productoOriginal, productoTransformado);
        assertEquals("ROSAS ROJAS", productoTransformado.getNombre());
    }
}
