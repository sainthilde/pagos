package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.application.service.TramiteService;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.ApiResponseTramiteDesistResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.ApiResponseTramiteResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.OrdenPagoErrorResponse;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.TramiteCrearRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.TramiteDesistRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.TramiteDesistResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.TramiteResponseDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.TramiteUpdateNumeroTramiteEntidadRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.dto.TramiteUpdateRequestDto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.enums.MetaResults;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.exceptions.OrdenPagoAnulacionException;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.domain.model.exception.ApiResponseMeta;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.mapper.TramiteMapper;

public class TramiteControllerTest {

        @Mock
        private TramiteService tramiteService;

        @Mock
        private TramiteMapper tramiteMapper;

        @Mock
        private HttpServletRequest request; // Mock para HttpServletRequest

        @InjectMocks
        private TramiteController tramiteController;

        private TramiteCrearRequestDto tramiteCrearRequestDto;
        private TramiteModel tramiteModel;
        private TramiteResponseDto tramiteResponseDto;
        private ApiResponseTramiteResponseDto apiResponseTramiteResponseDto;

        private static final String USER = "testUser";
        private static final String RUC = "testRuc";

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);

                // Configurar el mock para HttpServletRequest
                when(request.getHeader("user")).thenReturn(USER);
                when(request.getHeader("ruc")).thenReturn(RUC);

                tramiteCrearRequestDto = new TramiteCrearRequestDto();
                tramiteModel = new TramiteModel();
                tramiteModel.setTramiteId(1);
                tramiteModel.setNumeroSuce("numero123");
                tramiteModel.setUsuidRegAud("user");
                tramiteResponseDto = new TramiteResponseDto(1, "numero123");

                apiResponseTramiteResponseDto = new ApiResponseTramiteResponseDto();
                apiResponseTramiteResponseDto.setData(List.of(tramiteResponseDto));
                ApiResponseMeta apiResponseMeta = new ApiResponseMeta();
                apiResponseMeta.setResult("SUCCESS");
                apiResponseMeta.setCantidadRegistros(1);
                apiResponseMeta.setCantidadRegistrosTotal(1);
                apiResponseMeta.setAtributos(Map.of());
                apiResponseTramiteResponseDto.setMeta(apiResponseMeta);
        }

        @Test
        void testCreateTramite() {
                // Arrange
                TramiteResponseDto tramiteResponseDto = new TramiteResponseDto(tramiteModel.getTramiteId(),
                                tramiteModel.getNumeroSuce());

                // Crea y configura ApiResponseMeta usando setters
                ApiResponseMeta expectedMeta = new ApiResponseMeta();
                expectedMeta.setResult(MetaResults.SUCCESS.getValue());
                expectedMeta.setCantidadRegistros(1);
                expectedMeta.setCantidadRegistrosTotal(1);
                expectedMeta.setAtributos(Map.of());

                // Crea ApiResponseTramiteResponseDto esperado
                ApiResponseTramiteResponseDto expectedResponse = new ApiResponseTramiteResponseDto();
                expectedResponse.setData(List.of(tramiteResponseDto));
                expectedResponse.setMeta(expectedMeta);

                // Configura mocks
                when(tramiteMapper.dtoToModelCrear(any(TramiteCrearRequestDto.class), anyString()))
                                .thenReturn(tramiteModel);
                when(tramiteService.create(any(TramiteModel.class), anyString(), anyString()))
                                .thenReturn(tramiteModel);
                when(tramiteMapper.modelToDto(any(TramiteModel.class)))
                                .thenReturn(tramiteResponseDto);

                // Act
                ResponseEntity<ApiResponseTramiteResponseDto> response = tramiteController
                                .createTramite(tramiteCrearRequestDto);

                // Assert
                assertNotNull(response);
                assertEquals(200, response.getStatusCode().value());
                assertNotNull(response.getBody());

                ApiResponseTramiteResponseDto actualResponse = response.getBody();

                // Compara meta values
                assertEquals(expectedMeta.getResult(), actualResponse.getMeta().getResult());
                assertEquals(expectedMeta.getCantidadRegistros(), actualResponse.getMeta().getCantidadRegistros());
                assertEquals(expectedMeta.getCantidadRegistrosTotal(),
                                actualResponse.getMeta().getCantidadRegistrosTotal());
                assertEquals(expectedMeta.getAtributos(), actualResponse.getMeta().getAtributos());

                // Compara data values
                List<TramiteResponseDto> expectedData = expectedResponse.getData();
                List<TramiteResponseDto> actualData = actualResponse.getData();

                assertEquals(expectedData.size(), actualData.size());
                for (int i = 0; i < expectedData.size(); i++) {
                        assertEquals(expectedData.get(i).getNumeroSuce(), actualData.get(i).getNumeroSuce());
                }
        }

        @Test
        void testUpdateTramite() {
                // Arrange
                TramiteUpdateRequestDto tramiteUpdateRequestDto = new TramiteUpdateRequestDto();
                tramiteUpdateRequestDto.setTramiteId(1);
                tramiteUpdateRequestDto.setRucUsuario("rucUser");
                tramiteUpdateRequestDto.setOperacion("updateOp");

                TramiteModel tramiteModel = new TramiteModel();
                tramiteModel.setTramiteId(1);
                tramiteModel.setNumeroSuce("numeroUpdated");

                TramiteResponseDto tramiteResponseDto = new TramiteResponseDto(1, "numeroUpdated");

                ApiResponseTramiteResponseDto expectedResponse = new ApiResponseTramiteResponseDto();
                expectedResponse.setData(List.of(tramiteResponseDto));
                ApiResponseMeta expectedMeta = new ApiResponseMeta();
                expectedMeta.setResult(MetaResults.SUCCESS.getValue());
                expectedMeta.setCantidadRegistros(1);
                expectedMeta.setCantidadRegistrosTotal(1);
                expectedMeta.setAtributos(Map.of());
                expectedResponse.setMeta(expectedMeta);

                when(tramiteMapper.dtoToModelUpdate(any(TramiteUpdateRequestDto.class), anyString()))
                                .thenReturn(tramiteModel);
                when(tramiteService.update(any(TramiteModel.class), anyString(), anyString(), anyString()))
                                .thenReturn(tramiteModel);
                when(tramiteMapper.modelToDto(any(TramiteModel.class)))
                                .thenReturn(tramiteResponseDto);

                // Act
                ResponseEntity<ApiResponseTramiteResponseDto> response = tramiteController
                                .updateTramite(tramiteUpdateRequestDto);

                // Assert
                assertNotNull(response);
                assertEquals(200, response.getStatusCode().value());
                assertNotNull(response.getBody());

                ApiResponseTramiteResponseDto actualResponse = response.getBody();

                // Compara meta values
                assertEquals(expectedMeta.getResult(), actualResponse.getMeta().getResult());
                assertEquals(expectedMeta.getCantidadRegistros(), actualResponse.getMeta().getCantidadRegistros());
                assertEquals(expectedMeta.getCantidadRegistrosTotal(),
                                actualResponse.getMeta().getCantidadRegistrosTotal());
                assertEquals(expectedMeta.getAtributos(), actualResponse.getMeta().getAtributos());

                // Compara data values
                List<TramiteResponseDto> expectedData = expectedResponse.getData();
                List<TramiteResponseDto> actualData = actualResponse.getData();

                assertEquals(expectedData.size(), actualData.size());
                for (int i = 0; i < expectedData.size(); i++) {
                        assertEquals(expectedData.get(i).getNumeroSuce(), actualData.get(i).getNumeroSuce());
                }
        }

        @Test
        void testDesistirTramites() {
                // Arrange
                TramiteDesistRequestDto tramiteDesistRequestDto = new TramiteDesistRequestDto();
                tramiteDesistRequestDto.setEscalaId(1);
                tramiteDesistRequestDto.setTramiteId(1);

                TramiteModel tramiteModel = new TramiteModel();
                tramiteModel.setTramiteId(1);
                tramiteModel.setNumeroSuce("numero123");
                tramiteModel.setFechaTramite(LocalDateTime.of(2024, 8, 19, 0, 0));
                tramiteModel.setEscalaId(1);
                tramiteModel.setDocumentoId(101);
                tramiteModel.setActividadEntidadPuertoId(202);
                tramiteModel.setIndicadorEs("Sí");
                tramiteModel.setEstadoTramite("Activo");
                tramiteModel.setPpCpbPayments(List.of("1", "2"));
                TramiteDesistResponseDto tramiteDesistResponseDto = new TramiteDesistResponseDto(
                                1, "2024-08-19", "numero123", 1, 101, 202, "Sí", "Activo",
                                List.of("1", "2"), true, "", List.of("1", "2"));

                ApiResponseTramiteDesistResponseDto expectedResponse = new ApiResponseTramiteDesistResponseDto();
                expectedResponse.setData(List.of(tramiteDesistResponseDto));
                ApiResponseMeta expectedMeta = new ApiResponseMeta();
                expectedMeta.setResult(MetaResults.SUCCESS.getValue());
                expectedMeta.setCantidadRegistros(1);
                expectedMeta.setCantidadRegistrosTotal(1);
                expectedMeta.setAtributos(Map.of());
                expectedResponse.setMeta(expectedMeta);

                when(tramiteService.desist(anyInt(), anyInt(), anyString()))
                                .thenReturn(List.of(tramiteModel));
                when(tramiteMapper.modelListToDtoList(anyList()))
                                .thenReturn(List.of(tramiteDesistResponseDto));

                // Act
                ResponseEntity<Object> response = tramiteController.desistirTramites(tramiteDesistRequestDto);

                // Assert
                assertNotNull(response);
                assertEquals(200, response.getStatusCode().value());
                assertNotNull(response.getBody());

                ApiResponseTramiteDesistResponseDto actualResponse = (ApiResponseTramiteDesistResponseDto) response
                                .getBody();

                // Compara meta values
                assertEquals(expectedMeta.getResult(), actualResponse.getMeta().getResult());
                assertEquals(expectedMeta.getCantidadRegistros(), actualResponse.getMeta().getCantidadRegistros());
                assertEquals(expectedMeta.getCantidadRegistrosTotal(),
                                actualResponse.getMeta().getCantidadRegistrosTotal());
                assertEquals(expectedMeta.getAtributos(), actualResponse.getMeta().getAtributos());

                // Compara data values
                List<TramiteDesistResponseDto> expectedData = expectedResponse.getData();
                List<TramiteDesistResponseDto> actualData = actualResponse.getData();

                assertEquals(expectedData.size(), actualData.size());
                for (int i = 0; i < expectedData.size(); i++) {
                        assertEquals(expectedData.get(i).getTramiteId(), actualData.get(i).getTramiteId());
                        assertEquals(expectedData.get(i).getFechaTramite(), actualData.get(i).getFechaTramite());
                        assertEquals(expectedData.get(i).getNumeroSuce(), actualData.get(i).getNumeroSuce());
                        assertEquals(expectedData.get(i).getEscalaId(), actualData.get(i).getEscalaId());
                        assertEquals(expectedData.get(i).getDocumentoId(), actualData.get(i).getDocumentoId());
                        assertEquals(expectedData.get(i).getActividadEntidadPuertoId(),
                                        actualData.get(i).getActividadEntidadPuertoId());
                        assertEquals(expectedData.get(i).getIndicadorEs(), actualData.get(i).getIndicadorEs());
                        assertEquals(expectedData.get(i).getEstadoTramite(), actualData.get(i).getEstadoTramite());
                }
        }

        @Test
        void testUpdateTramiteFromFichaSanitaria() {
                // Arrange
                Integer escalaId = 1;
                TramiteUpdateRequestDto tramiteUpdateRequestDto = new TramiteUpdateRequestDto();
                tramiteUpdateRequestDto.setRucUsuario("rucUser");
                tramiteUpdateRequestDto.setOperacion("updateOp");

                TramiteModel tramiteModel1 = new TramiteModel();
                tramiteModel1.setTramiteId(1);
                tramiteModel1.setNumeroSuce("numero123");

                TramiteModel tramiteModel2 = new TramiteModel();
                tramiteModel2.setTramiteId(2);
                tramiteModel2.setNumeroSuce("numero456");

                List<TramiteModel> tramiteModelFound = List.of(tramiteModel1, tramiteModel2);

                TramiteModel updatedTramiteModel1 = new TramiteModel();
                updatedTramiteModel1.setTramiteId(1);
                updatedTramiteModel1.setNumeroSuce("numeroUpdated123");

                TramiteModel updatedTramiteModel2 = new TramiteModel();
                updatedTramiteModel2.setTramiteId(2);
                updatedTramiteModel2.setNumeroSuce("numeroUpdated456");

                List<TramiteModel> updatedTramites = List.of(updatedTramiteModel1, updatedTramiteModel2);

                TramiteResponseDto updatedTramiteResponseDto1 = new TramiteResponseDto(1, "numeroUpdated123");
                TramiteResponseDto updatedTramiteResponseDto2 = new TramiteResponseDto(2, "numeroUpdated456");

                ApiResponseTramiteResponseDto expectedResponse = new ApiResponseTramiteResponseDto();
                expectedResponse.setData(List.of(updatedTramiteResponseDto1, updatedTramiteResponseDto2));
                ApiResponseMeta expectedMeta = new ApiResponseMeta();
                expectedMeta.setResult(MetaResults.SUCCESS.getValue());
                expectedMeta.setCantidadRegistros(2);
                expectedMeta.setCantidadRegistrosTotal(2);
                expectedMeta.setAtributos(Map.of());
                expectedResponse.setMeta(expectedMeta);

                // Configura mocks
                when(tramiteService.search(escalaId)).thenReturn(tramiteModelFound);
                when(tramiteMapper.dtoToModelUpdate(any(TramiteUpdateRequestDto.class), anyString()))
                                .thenReturn(updatedTramiteModel1, updatedTramiteModel2);
                when(tramiteService.update(anyList(), anyString(), anyString(), anyString()))
                                .thenReturn(updatedTramites);
                when(tramiteMapper.modelListTramiteToDtoList(updatedTramites))
                                .thenReturn(List.of(updatedTramiteResponseDto1, updatedTramiteResponseDto2));

                // Act
                ResponseEntity<ApiResponseTramiteResponseDto> response = tramiteController
                                .updateTramiteFromFichaSanitaria(escalaId, tramiteUpdateRequestDto);

                // Assert
                assertNotNull(response);
                assertEquals(200, response.getStatusCode().value());
                assertNotNull(response.getBody());

                ApiResponseTramiteResponseDto actualResponse = response.getBody();

                // Compara meta values
                assertEquals(expectedMeta.getResult(), actualResponse.getMeta().getResult());
                assertEquals(expectedMeta.getCantidadRegistros(), actualResponse.getMeta().getCantidadRegistros());
                assertEquals(expectedMeta.getCantidadRegistrosTotal(),
                                actualResponse.getMeta().getCantidadRegistrosTotal());
                assertEquals(expectedMeta.getAtributos(), actualResponse.getMeta().getAtributos());

                // Compara data values
                List<TramiteResponseDto> expectedData = expectedResponse.getData();
                List<TramiteResponseDto> actualData = actualResponse.getData();

                assertEquals(expectedData.size(), actualData.size());
                for (int i = 0; i < expectedData.size(); i++) {
                        assertEquals(expectedData.get(i).getIdSuce(), actualData.get(i).getIdSuce());
                        assertEquals(expectedData.get(i).getNumeroSuce(), actualData.get(i).getNumeroSuce());
                }

                // Verifica que se llamaron los mocks
                verify(tramiteService, times(1)).search(escalaId);
                verify(tramiteMapper, times(tramiteModelFound.size()))
                                .dtoToModelUpdate(any(TramiteUpdateRequestDto.class), eq(USER));
                verify(tramiteService, times(1)).update(anyList(), eq(tramiteUpdateRequestDto.getRucUsuario()),
                                eq(USER), eq(tramiteUpdateRequestDto.getOperacion()));
                verify(tramiteMapper, times(1)).modelListTramiteToDtoList(updatedTramites);
        }

        @Test
        void testDesistirTramites_OrdenPagoAnulacionExceptionParsedJson() {
                TramiteDesistRequestDto dto = new TramiteDesistRequestDto();
                dto.setEscalaId(2);
                dto.setTramiteId(5);
                OrdenPagoErrorResponse error = new OrdenPagoErrorResponse(409, "{\"reason\":\"conflict\"}", 77);
                when(tramiteService.desist(anyInt(), anyInt(), anyString()))
                                .thenThrow(new OrdenPagoAnulacionException("msg", List.of(error)));

                ResponseEntity<Object> resp = tramiteController.desistirTramites(dto);
                assertEquals(409, resp.getStatusCode().value());
                assertTrue(resp.getBody() instanceof Map);
                @SuppressWarnings("unchecked")
                Map<String, Object> body = (Map<String, Object>) resp.getBody();
                assertEquals("conflict", body.get("reason"));
        }

        @Test
        void testDesistirTramites_OrdenPagoAnulacionExceptionInvalidJson() {
                TramiteDesistRequestDto dto = new TramiteDesistRequestDto();
                dto.setEscalaId(3);
                dto.setTramiteId(8);
                OrdenPagoErrorResponse error = new OrdenPagoErrorResponse(502, "<html>bad</html>", 88);
                when(tramiteService.desist(anyInt(), anyInt(), anyString()))
                                .thenThrow(new OrdenPagoAnulacionException("msg", List.of(error)));

                ResponseEntity<Object> resp = tramiteController.desistirTramites(dto);
                assertEquals(502, resp.getStatusCode().value());
                assertEquals("<html>bad</html>", resp.getBody());
        }

        @Test
        void testDesistirTramites_OrdenPagoAnulacionExceptionEmptyErrors() {
                TramiteDesistRequestDto dto = new TramiteDesistRequestDto();
                dto.setEscalaId(4);
                dto.setTramiteId(9);
                when(tramiteService.desist(anyInt(), anyInt(), anyString()))
                                .thenThrow(new OrdenPagoAnulacionException("msg", List.of()));

                ResponseEntity<Object> resp = tramiteController.desistirTramites(dto);
                assertEquals(500, resp.getStatusCode().value());
                assertTrue(resp.getBody() instanceof Map);
                @SuppressWarnings("unchecked")
                Map<String, Object> body = (Map<String, Object>) resp.getBody();
                @SuppressWarnings("unchecked")
                Map<String, Object> meta = (Map<String, Object>) body.get("meta");
                assertEquals("ERROR", meta.get("result"));
        }

        @Test
        void testUpdateTramiteFromFichaSanitaria_NotFound() {
                Integer escalaId = 99;
                TramiteUpdateRequestDto dto = new TramiteUpdateRequestDto();
                when(tramiteService.search(escalaId)).thenReturn(null);
                ResponseEntity<ApiResponseTramiteResponseDto> resp = tramiteController
                                .updateTramiteFromFichaSanitaria(escalaId, dto);
                assertEquals(404, resp.getStatusCode().value());
        }

        @Test
        void testUpdateNumeroTramiteEntidad() {
                // Arrange
                TramiteUpdateNumeroTramiteEntidadRequestDto dto = new TramiteUpdateNumeroTramiteEntidadRequestDto();
                dto.setTramiteId(5);
                dto.setEscalaId(50);
                dto.setNumeroTramiteEntidad("EXP-999");
                dto.setTupa("TUPA-9");
                dto.setIndAsTramiteManual(Boolean.TRUE);

                TramiteModel updated = new TramiteModel();
                updated.setTramiteId(5);
                updated.setNumeroSuce("SUCE-5");
                updated.setNumeroTramiteEntidad("EXP-999");

                TramiteResponseDto responseDto = new TramiteResponseDto(5, "SUCE-5");

                when(tramiteService.updateNumeroTramiteEntidad(5, 50, "EXP-999", "TUPA-9", true)).thenReturn(updated);
                when(tramiteMapper.modelToDto(updated)).thenReturn(responseDto);

                // Act
                ResponseEntity<ApiResponseTramiteResponseDto> response = tramiteController
                                .updateNumeroTramiteEntidad(dto);

                // Assert
                assertNotNull(response);
                assertEquals(200, response.getStatusCode().value());
                assertNotNull(response.getBody());
                ApiResponseTramiteResponseDto body = response.getBody();
                assertEquals(1, body.getData().size());
                assertEquals("SUCE-5", body.getData().get(0).getNumeroSuce());
                assertEquals(1, body.getMeta().getCantidadRegistros());
                verify(tramiteService).updateNumeroTramiteEntidad(5, 50, "EXP-999", "TUPA-9", true);
                verify(tramiteMapper).modelToDto(updated);
        }
}
