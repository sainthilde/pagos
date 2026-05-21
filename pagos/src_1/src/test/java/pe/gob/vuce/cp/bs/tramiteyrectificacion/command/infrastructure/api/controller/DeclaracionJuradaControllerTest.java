package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.service.DeclaracionJuradaService;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.DeclaracionJuradaRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.DeclaracionJuradaResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.CommonResponse;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.DocumentoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.Meta;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper.DeclaracionJuradaMapper;

public class DeclaracionJuradaControllerTest {

        @Mock
        private DeclaracionJuradaService declaracionJuradaService;

        @Mock
        private DeclaracionJuradaMapper declaracionJuradaMapper;

        @InjectMocks
        private DeclaracionJuradaController declaracionJuradaController;

        private DeclaracionJuradaRequestDto declaracionJuradaRequestDto;
        private DeclaracionJuradaResponseDto declaracionJuradaResponseDto;
        private CommonResponse commonResponse;

        private static final String USER = "testUser";
        private static final String RUC = "12345678901";

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);

                // Initialize the request DTO (set properties as needed)
                declaracionJuradaRequestDto = new DeclaracionJuradaRequestDto();
                // e.g., declaracionJuradaRequestDto.setSomeField("someValue");

                // Initialize a sample response DTO
                DocumentoModel documento = new DocumentoModel();
                documento.setDocumentoId(1);
                documento.setDescAcronimo("DMS");

                declaracionJuradaResponseDto = new DeclaracionJuradaResponseDto(
                                1,
                                "estadoPago",
                                "numero123",
                                LocalDateTime.now(),
                                documento,
                                123,
                                "motivo",
                                "mensajeError",
                                new TramiteModel(),
                                "estado",
                                "rucAgente");

                // Prepare a common response as expected in the controller
                commonResponse = new CommonResponse();
                commonResponse.setMeta(new Meta());
                commonResponse.setData(declaracionJuradaResponseDto);
        }

        @Test
        void testSaveDeclaracionJurada() {
                // Arrange
                // The controller now sends the request DTO directly to the service.
                // So we mock the service to return a new DeclaracionJuradaModel.
                when(declaracionJuradaService.createDeclaracionJurada(any(DeclaracionJuradaRequestDto.class),anyString()))
                                .thenReturn(new DeclaracionJuradaModel());

                // And we mock the mapper to convert that model to the expected response DTO.
                when(declaracionJuradaMapper.modelToDto(any(DeclaracionJuradaModel.class)))
                                .thenReturn(declaracionJuradaResponseDto);

                // Act
                ResponseEntity<CommonResponse> response = declaracionJuradaController.save(declaracionJuradaRequestDto,
                                USER, RUC);

                // Assert
                assertNotNull(response, "The response should not be null");
                assertEquals(200, response.getStatusCodeValue(), "The HTTP status should be 200 OK");
                assertNotNull(response.getBody(), "The response body should not be null");
                assertEquals(declaracionJuradaResponseDto, response.getBody().getData(),
                                "The response data should match the expected DTO");
        }
}
