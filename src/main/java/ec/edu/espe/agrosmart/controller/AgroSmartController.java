package ec.edu.espe.agrosmart.controller;

import ec.edu.espe.agrosmart.domain.Producto;
import ec.edu.espe.agrosmart.exception.ProductoNoEncontradoException;
import ec.edu.espe.agrosmart.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// Controlador REST reactivo con WebFlux
@RestController
public class AgroSmartController {

    private final ProductoService productoService;

    public AgroSmartController(ProductoService productoService) {
        this.productoService = productoService;
    }


    // GET /api/productos - Obtiene el flujo reactivo de productos comercializables
    @GetMapping("/api/productos")
    public Flux<Producto> obtenerProductosComercializables() {
        return productoService.obtenerProductosComercializables();
    }

    // GET /api/productos/{id} - Busca un producto por id reactivamente
    @GetMapping("/api/productos/{id}")
    public Mono<Producto> buscarPorId(@PathVariable Long id) {
        return productoService.buscarPorId(id);
    }

    // GET /api/agrosmart/publicidad - Genera publicidad con IA en texto plano
    @GetMapping(value = "/api/agrosmart/publicidad", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<String> generarPublicidad(
            @RequestParam String producto,
            @RequestParam String audiencia) {
        return productoService.generarPublicidad(producto, audiencia);
    }

    // Manejador de excepcion para responder HTTP 404 cuando no exista la id
    @ExceptionHandler(ProductoNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<String> handleProductoNoEncontrado(ProductoNoEncontradoException ex) {
        return Mono.just(ex.getMessage());
    }
}
