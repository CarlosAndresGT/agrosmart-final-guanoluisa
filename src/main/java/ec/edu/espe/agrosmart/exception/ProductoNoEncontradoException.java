package ec.edu.espe.agrosmart.exception;

// Excepción lanzada cuando no se encuentra un producto por su ID.
public class ProductoNoEncontradoException extends RuntimeException {

    public ProductoNoEncontradoException(Long id) {
        super("Producto no encontrado con el id: " + id);
    }

    public ProductoNoEncontradoException(String message) {
        super(message);
    }
}
