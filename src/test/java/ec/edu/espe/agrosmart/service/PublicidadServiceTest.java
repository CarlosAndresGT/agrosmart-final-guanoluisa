package ec.edu.espe.agrosmart.service;

import ec.edu.espe.agrosmart.ai.AgroSmartAIService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;

// Pruebas unitarias para la integracion de IA con LangChain4j y resilencia onErrorResume
class PublicidadServiceTest {

    @Test
    void generarPublicidad_caminoFeliz_debeEmitirTextoDeIA() {
        // Arrange
        AgroSmartAIService ia = Mockito.mock(AgroSmartAIService.class);
        Mockito.when(ia.generarPublicidad("Rosas Rojas", "floristerias premium"))
                .thenReturn("Frase publicitaria de prueba para floristerias");
        ProductoService service = new ProductoService(null, ia);

        // Act
        Mono<String> mono = service.generarPublicidad("Rosas Rojas", "floristerias premium");

        // Assert
        StepVerifier.create(mono)
                .expectNext("Frase publicitaria de prueba para floristerias")
                .verifyComplete();
    }

    @Test
    void generarPublicidad_cuandoElProveedorFalla_debeEmitirMensajeDeRespaldo() {
        // Arrange
        AgroSmartAIService ia = Mockito.mock(AgroSmartAIService.class);
        Mockito.when(ia.generarPublicidad(any(), any()))
                .thenThrow(new RuntimeException("429 Too Many Requests"));
        ProductoService service = new ProductoService(null, ia);

        // Act
        Mono<String> mono = service.generarPublicidad("Rosas Rojas", "floristerias premium");

        // Assert
        StepVerifier.create(mono)
                .expectNextMatches(texto -> texto.contains("Publicidad no disponible"))
                .verifyComplete();
    }
}
