package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.DeclaracionJuradaRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.port.in.CreateDeclaracionJuradaUseCase;

public class DeclaracionJuradaServiceTest {

    @Mock
    private CreateDeclaracionJuradaUseCase createDeclaracionJuradaUseCase;

    @InjectMocks
    private DeclaracionJuradaService declaracionJuradaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDeclaracionJurada() {
        // Prepare test data: a request DTO and an expected model result.
        DeclaracionJuradaRequestDto requestDto = new DeclaracionJuradaRequestDto();
        // Optionally set properties on the requestDto if needed.
        // e.g., requestDto.setSomeField("someValue");
        String user = "User";
        DeclaracionJuradaModel expectedModel = new DeclaracionJuradaModel();
        expectedModel.setDeclaracionJuradaId(1);

        // Simulate the behavior of the use case.
        when(createDeclaracionJuradaUseCase.save(requestDto,user)).thenReturn(expectedModel);

        // Call the service method under test.
        DeclaracionJuradaModel result = declaracionJuradaService.createDeclaracionJurada(requestDto,user);

        // Verify results.
        assertEquals(expectedModel, result, "The service should return the expected model");
        verify(createDeclaracionJuradaUseCase, times(1)).save(requestDto,user);
    }
}
