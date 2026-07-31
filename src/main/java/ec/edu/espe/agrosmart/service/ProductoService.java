package ec.edu.espe.agrosmart.service;

import ec.edu.espe.agrosmart.domain.Producto;
import ec.edu.espe.agrosmart.domain.ProductoFilters;
import ec.edu.espe.agrosmart.exception.ProductoNoEncontradoException;
import ec.edu.espe.agrosmart.mapper.ProductoMapper;
import ec.edu.espe.agrosmart.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductoService {

    private static final Producto PRODUCTO_GENERICO = new Producto(
            0L,
            "PRODUCTO GENERICO DE FLORES",
            "Flores",
            new BigDecimal("10.00"),
            List.of("contacto@agrosmart.ec"));

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    // Obtiene el flujo reactivo de productos comercializables
    public Flux<Producto> obtenerProductosComercializables() {
        // fromCallable: difiere la ejecución bloqueante de repository.findAll() hasta que exista suscripción
        return Mono.fromCallable(repository::findAll)
                // subscribeOn(boundedElastic): aísla la llamada JPA bloqueante fuera del event loop de Netty
                .subscribeOn(Schedulers.boundedElastic())
                // flatMapMany: desenvuelve la List<ProductoEntity> emitida a un flujo continuo Flux<ProductoEntity>
                .flatMapMany(Flux::fromIterable)
                // map: transforma la entidad del ORM al modelo de dominio inmutable
                .map(ProductoMapper::toDominio)
                // map: aplica la transformación funcional A_MAYUSCULAS retornando una nueva instancia inmutable
                .map(ProductoFilters.A_MAYUSCULAS)
                // filter: descarta los productos no validos (precio <= 0 o sin correos)
                .filter(ProductoFilters.IS_VALID)
                // doOnNext: ejecuta un efecto secundario de trazabilidad sin alterar el flujo
                .doOnNext(ProductoFilters.LOG_PRODUCTO)
                // defaultIfEmpty: emite un producto genérico de respaldo si el filtro vació el flujo completo
                .defaultIfEmpty(PRODUCTO_GENERICO);
    }

    // Busca un producto por id de forma reactiva.
    public Mono<Producto> buscarPorId(Long id) {
        // fromCallable: difiere la llamada bloqueante repository.findById(id).
        return Mono.fromCallable(() -> repository.findById(id))
                // subscribeOn(boundedElastic): ejecuta el acceso a PostgreSQL en el pool boundedElastic
                .subscribeOn(Schedulers.boundedElastic())
                // flatMap: convierte Optional<ProductoEntity> en un Mono vacío si Optional no tiene valor
                .flatMap(Mono::justOrEmpty)
                // map: convierte la entidad al modelo de dominio.
                .map(ProductoMapper::toDominio)
                // switchIfEmpty: si el Mono resultó estar vacío es decir la id no existe, emite un error reactivo con la excepción personalizada
                .switchIfEmpty(Mono.error(new ProductoNoEncontradoException(id)));
    }
}
