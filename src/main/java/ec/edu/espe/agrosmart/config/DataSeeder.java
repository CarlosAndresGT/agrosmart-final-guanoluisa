package ec.edu.espe.agrosmart.config;

import ec.edu.espe.agrosmart.entity.ProductoEntity;
import ec.edu.espe.agrosmart.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ProductoRepository productoRepository;

    public DataSeeder(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (productoRepository.count() == 0) {
            List<ProductoEntity> productos = List.of(
                    // 3 Productos válidos (precio > 0 y con correos)
                    new ProductoEntity("Rosas Rojas Premium Exportacion", new BigDecimal("18.50"), 150, "Flores", "ventas@rosas.com,export@rosas.com"),
                    new ProductoEntity("Orquideas Blancas Especiales", new BigDecimal("25.00"), 80, "Flores", "contacto@orquideas.ec"),
                    new ProductoEntity("Girasoles Amarillos de Sol", new BigDecimal("12.00"), 200, "Flores", "info@girasoles.com,pedidos@girasoles.com"),

                    // 1 Producto inválido (precio = 0)
                    new ProductoEntity("Muestra Claveles Silvestres", new BigDecimal("0.00"), 50, "Flores", "muestras@flores.com"),

                    // 1 Producto inválido (lista de correos vacía)
                    new ProductoEntity("Lirios Aromaticos Importados", new BigDecimal("14.00"), 90, "Flores", "")
            );

            productoRepository.saveAll(productos);
            System.out.println("Seeding completado: 5 productos de Flores registrados en tbl_productos_base_77 (3 válidos + 2 inválidos).");
        }
    }
}
