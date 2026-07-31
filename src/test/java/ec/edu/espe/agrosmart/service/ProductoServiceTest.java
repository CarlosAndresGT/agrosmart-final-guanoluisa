package ec.edu.espe.agrosmart.service;

import ec.edu.espe.agrosmart.domain.Producto;
import ec.edu.espe.agrosmart.entity.ProductoEntity;
import ec.edu.espe.agrosmart.exception.ProductoNoEncontradoException;
import ec.edu.espe.agrosmart.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

// Pruebas unitarias con StepVerifier sobre el servicio reactivo ProductoService
class ProductoServiceTest {

    @Test
    void obtenerProductosComercializables_conTresValidosYDosInvalidos_debeEmitirSoloLosValidos() {
        // Arrange
        ProductoRepository repo = Mockito.mock(ProductoRepository.class);
        Mockito.when(repo.findAll()).thenReturn(List.of(
                new ProductoEntity("Rosas Rojas", new BigDecimal("18.50"), 150, "Flores", "ventas@rosas.com"),
                new ProductoEntity("Orquideas", new BigDecimal("25.00"), 80, "Flores", "contacto@orquideas.ec"),
                new ProductoEntity("Girasoles", new BigDecimal("12.00"), 200, "Flores", "info@girasoles.com"),
                new ProductoEntity("Muestra Claveles", new BigDecimal("0.00"), 50, "Flores", "muestras@flores.com"),
                new ProductoEntity("Lirios", new BigDecimal("14.00"), 90, "Flores", "")
        ));
        ProductoService service = new ProductoService(repo, null);

        // Act
        Flux<Producto> flujo = service.obtenerProductosComercializables();

        // Assert
        StepVerifier.create(flujo)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void obtenerProductosComercializables_conTodosInvalidos_debeEmitirProductoGenericoDefault() {
        // Arrange
        ProductoRepository repo = Mockito.mock(ProductoRepository.class);
        Mockito.when(repo.findAll()).thenReturn(List.of(
                new ProductoEntity("Muestra Claveles", new BigDecimal("0.00"), 50, "Flores", "muestras@flores.com"),
                new ProductoEntity("Lirios", new BigDecimal("14.00"), 90, "Flores", "")
        ));
        ProductoService service = new ProductoService(repo, null);

        // Act
        Flux<Producto> flujo = service.obtenerProductosComercializables();

        // Assert
        StepVerifier.create(flujo)
                .expectNextMatches(p -> p.getId().equals(0L) && p.getNombre().contains("GENERICO"))
                .verifyComplete();
    }

    @Test
    void buscarPorId_conIdInexistente_debeLanzarProductoNoEncontradoException() {
        // Arrange
        ProductoRepository repo = Mockito.mock(ProductoRepository.class);
        Mockito.when(repo.findById(9999L)).thenReturn(Optional.empty());
        ProductoService service = new ProductoService(repo, null);

        // Act
        Mono<Producto> mono = service.buscarPorId(9999L);

        // Assert
        StepVerifier.create(mono)
                .expectError(ProductoNoEncontradoException.class)
                .verify();
    }
}
