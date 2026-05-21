package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.OrdenDePagoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.out.OrdenPagoRepositoryPort;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

public class UpdateOrdenDePagoUseCaseImplTest {

    private OrdenPagoRepositoryPort ordenPagoRepositoryPort;
    private UpdateOrdenDePagoUseCaseImpl updateOrdenDePagoUseCase;

    @BeforeEach
    void setUp() {
        ordenPagoRepositoryPort = mock(OrdenPagoRepositoryPort.class);
        updateOrdenDePagoUseCase = new UpdateOrdenDePagoUseCaseImpl(ordenPagoRepositoryPort);
    }

    @Test
    void testUpdateOrdenDePagoDelegatesToRepository() {
        // Arrange
        OrdenDePagoModel inputModel = new OrdenDePagoModel();
        inputModel.setId(123);
        inputModel.setEstadoOrdenPago("ACTIVO");

        OrdenDePagoModel updatedModel = new OrdenDePagoModel();
        updatedModel.setId(123);
        updatedModel.setEstadoOrdenPago("ACTUALIZADO");

        when(ordenPagoRepositoryPort.updateV2(inputModel)).thenReturn(updatedModel);

        // Act
        OrdenDePagoModel result = updateOrdenDePagoUseCase.update(inputModel);

        // Assert
        assertNotNull(result);
        assertEquals(123, result.getId());
        assertEquals("ACTUALIZADO", result.getEstadoOrdenPago());
        verify(ordenPagoRepositoryPort, times(1)).updateV2(inputModel);
    }
}
