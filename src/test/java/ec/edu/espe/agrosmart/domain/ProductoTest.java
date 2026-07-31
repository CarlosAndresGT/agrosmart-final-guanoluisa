package ec.edu.espe.agrosmart.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas unitarias sobre el modelo de dominio inmutable Producto
class ProductoTest {

    @Test
    void getters_cuandoSeInstancia_debenRetornarValoresDelConstructor() {
        // Arrange
        List<String> correos = List.of("ventas@rosas.com");
        Producto producto = new Producto(1L, "Rosas Rojas", "Flores", new BigDecimal("18.50"), correos);

        // Act & Assert
        assertEquals(1L, producto.getId());
        assertEquals("Rosas Rojas", producto.getNombre());
        assertEquals("Flores", producto.getCategoria());
        assertEquals(new BigDecimal("18.50"), producto.getPrecioUsd());
        assertEquals(1, producto.getCorreosNotificacion().size());
        assertEquals("ventas@rosas.com", producto.getCorreosNotificacion().get(0));
    }

    @Test
    void getCorreosNotificacion_alMutarLaListaOriginal_noDebeAfectarAlProducto() {
        // Arrange
        List<String> correos = new ArrayList<>();
        correos.add("ventas@rosas.com");
        Producto producto = new Producto(1L, "Rosas Rojas", "Flores", new BigDecimal("18.50"), correos);

        // Act
        correos.add("intruso@mail.com");

        // Assert
        assertEquals(1, producto.getCorreosNotificacion().size());
        assertNotSame(correos, producto.getCorreosNotificacion());
    }

    @Test
    void getCorreosNotificacion_alIntentarModificarLaListaRetornada_debeLanzarExcepcion() {
        // Arrange
        List<String> correos = List.of("ventas@rosas.com");
        Producto producto = new Producto(1L, "Rosas Rojas", "Flores", new BigDecimal("18.50"), correos);

        // Act & Assert
        List<String> correosObtenidos = producto.getCorreosNotificacion();
        assertThrows(UnsupportedOperationException.class, () -> correosObtenidos.add("nuevo@mail.com"));
    }
}
