package pe.gob.vuce.cp.sp.pagos.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.PENDIENTE_PAGO;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.PP;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.EN_PROCESO;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.EP;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.Constants.CR;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.core.JsonProcessingException;
import pe.gob.vuce.cp.sp.pagos.domain.exception.FeignExceptionHandler;
import pe.gob.vuce.cp.sp.pagos.domain.model.EscalaModel;
import pe.gob.vuce.cp.sp.pagos.domain.model.FichaTecnicaDetModel;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.UpdateArchivoPDFUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.in.UpdateOrdenPagoUseCase;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.EscalaRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.FeignRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.FichaTecnicaDetRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.OrdenPagoRequestDTO;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ProcedimientosResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.TasaResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.listener.dto.response.OrdenPagoResponseDTO;

@ExtendWith(MockitoExtension.class)
class ProcesarOrdenSunatUseCaseImplTest {

    @Mock
    private FeignRepositoryPort feignRepositoryPort;

    @Mock
    private UpdateOrdenPagoUseCase updateOrdenPagoUseCase;

    @Mock
    private UpdateArchivoPDFUseCase updateArchivoPDFUseCase;

    @Mock
    private FeignExceptionHandler feignExceptionHandler;

    @Mock
    private EscalaRepositoryPort escalaRepository;

    @Mock
    private FichaTecnicaDetRepositoryPort fichaTecnicaDetRepositoryPort;

    @InjectMocks
    private ProcesarOrdenSunatUseCaseImpl procesarOrdenSunatUseCase;

    private ProcedimientosResponse.Procedimiento procedimiento;
    private TasaResponse.Tasa tasa;
    private OrdenPago ordenPago;
    private EscalaModel escala;
    private FichaTecnicaDetModel fichaTecnicaDet;
    private OrdenPagoResponseDTO ordenPagoResponseDto;

    @BeforeEach
    void setUp() {
        procedimiento = new ProcedimientosResponse.Procedimiento();
        procedimiento.setEntidadId(1);
        procedimiento.setFormato("FORMATO");
        procedimiento.setTupa("TUPA");

        tasa = new TasaResponse.Tasa();

        ordenPago = new OrdenPago();
        ordenPago.setEscalaId(123);
        ordenPago.setMonto(100.0);
        ordenPago.setRucAgente("12345678901");
        ordenPago.setFechaVigencia(String.valueOf(LocalDateTime.now()));

        fichaTecnicaDet = new FichaTecnicaDetModel();
        fichaTecnicaDet.setFichaTecnicaDetId(456);
        fichaTecnicaDet.setNombreNave("NAVE TEST");

        escala = new EscalaModel();
        escala.setEscalaId(123);
        escala.setFichaTecnicaDetIn(fichaTecnicaDet);
        escala.setPuertoEscalaId("P01");
        escala.setAnoEscala(2023);
        escala.setNumeroEscala(123);

        ordenPagoResponseDto = new OrdenPagoResponseDTO();
        ordenPagoResponseDto.setCpb("CPB123");
        ordenPagoResponseDto.setCodigoOrdenPago("ORD123");
        ordenPagoResponseDto.setOrdenPagoId(1);
    }

    @Test
    void testProcesarOrdenSunat_EscalaNoEncontrada() {
        when(escalaRepository.findById(ordenPago.getEscalaId())).thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
            procesarOrdenSunatUseCase.procesarOrdenSunat(procedimiento, tasa, ordenPago, 1, "admin");
        });

        verify(feignRepositoryPort, never()).createOrdenPago(any());
    }

    @Test
    void testProcesarOrdenSunat_FichaTecnicaNoEncontrada() {
        when(escalaRepository.findById(ordenPago.getEscalaId())).thenReturn(escala);
        when(fichaTecnicaDetRepositoryPort.findByFichaTecnicaId(escala.getFichaTecnicaDetIn().getFichaTecnicaId())).thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
            procesarOrdenSunatUseCase.procesarOrdenSunat(procedimiento, tasa, ordenPago, 1, "admin");
        });

        verify(feignRepositoryPort, never()).createOrdenPago(any());
    }

    @Test
    void testProcesarOrdenSunat_Success_PendientePago() throws JsonProcessingException {
        ordenPagoResponseDto.setEstado(PENDIENTE_PAGO);
        ordenPagoResponseDto.setMonto(100.0);

        when(escalaRepository.findById(ordenPago.getEscalaId())).thenReturn(escala);
        when(fichaTecnicaDetRepositoryPort.findByFichaTecnicaId(escala.getFichaTecnicaDetIn().getFichaTecnicaId())).thenReturn(fichaTecnicaDet);
        when(feignRepositoryPort.createOrdenPago(any(OrdenPagoRequestDTO.class))).thenReturn(ordenPagoResponseDto);

        procesarOrdenSunatUseCase.procesarOrdenSunat(procedimiento, tasa, ordenPago, 1, "admin");

        verify(updateOrdenPagoUseCase, times(1)).updateOrdenPago(ordenPago);
        assertEquals("CPB123", ordenPago.getCpb());
        assertEquals("ORD123", ordenPago.getCodigoOrdenPago());
        assertEquals(1, ordenPago.getOrdenPagoInternaId());
        assertEquals(PP, ordenPago.getEstado());
        assertEquals(100.0, ordenPago.getMonto());
        assertNotNull(ordenPago.getPpFechaRespuestaCreacionCpb());
        assertNotNull(ordenPago.getPpFechaConfGeneracionCpb());
    }

    @Test
    void testProcesarOrdenSunat_Success_EnProceso() throws JsonProcessingException {
        ordenPagoResponseDto.setEstado(EN_PROCESO);

        when(escalaRepository.findById(ordenPago.getEscalaId())).thenReturn(escala);
        when(fichaTecnicaDetRepositoryPort.findByFichaTecnicaId(escala.getFichaTecnicaDetIn().getFichaTecnicaId())).thenReturn(fichaTecnicaDet);
        when(feignRepositoryPort.createOrdenPago(any(OrdenPagoRequestDTO.class))).thenReturn(ordenPagoResponseDto);

        procesarOrdenSunatUseCase.procesarOrdenSunat(procedimiento, tasa, ordenPago, 1, "admin");

        verify(updateOrdenPagoUseCase, times(1)).updateOrdenPago(ordenPago);
        assertEquals(EP, ordenPago.getEstado());
    }

    /*@Test
    void testProcesarOrdenSunat_FeignException() throws JsonProcessingException {
        FeignException feignException = mock(FeignException.class);

        when(escalaRepository.findById(ordenPago.getEscalaId())).thenReturn(escala);
        when(fichaTecnicaDetRepositoryPort.findByFichaTecnicaId(escala.getFichaTecnicaDetIn().getFichaTecnicaId())).thenReturn(fichaTecnicaDet);
        when(feignRepositoryPort.createOrdenPago(any(OrdenPagoRequestDTO.class))).thenThrow(feignException);
        doNothing().when(feignExceptionHandler).handleFeignClientException(any(FeignException.class), any(OrdenPago.class));

        procesarOrdenSunatUseCase.procesarOrdenSunat(procedimiento, tasa, ordenPago, 1, "admin");

        verify(feignExceptionHandler, times(1)).handleFeignClientException(feignException, ordenPago);
        verify(updateOrdenPagoUseCase, times(1)).updateOrdenPago(ordenPago);
    }*/

    @Test
    void testGetOrdenPagoRequestDto_ValidNumber() {
        escala.setNumeroEscala(42);

        OrdenPagoRequestDTO result = ProcesarOrdenSunatUseCaseImpl.getOrdenPagoRequestDto(
                procedimiento, ordenPago, escala, fichaTecnicaDet, "admin");

        assertNotNull(result);
        assertEquals(procedimiento.getEntidadId(), result.getEntidadId());
        assertEquals(456, result.getPerfilId());
        assertEquals(procedimiento.getFormato(), result.getFormato());
        assertEquals(procedimiento.getNombreCut(), result.getDesFormato());
        assertEquals(procedimiento.getTupa(), result.getTupa());
        assertEquals(ordenPago.getMonto(), result.getMontoExacto());
        assertEquals(ordenPago.getFechaVigencia(), result.getFechaVigencia());
        assertEquals("P01-2023-0042", result.getCodReferencia1());
        assertEquals("NAVE TEST", result.getCodReferencia2());
    }

    @Test
    void testGetOrdenPagoRequestDto_NullNumber() {
        escala.setNumeroEscala(null);

        OrdenPagoRequestDTO result = ProcesarOrdenSunatUseCaseImpl.getOrdenPagoRequestDto(
                procedimiento, ordenPago, escala, fichaTecnicaDet, "admin");

        assertEquals("P01-2023-", result.getCodReferencia1());
    }

    @Test
    void testGetOrdenPagoRequestDto_InvalidNumber() {
        escala.setNumeroEscala(0);

        OrdenPagoRequestDTO result = ProcesarOrdenSunatUseCaseImpl.getOrdenPagoRequestDto(
                procedimiento, ordenPago, escala, fichaTecnicaDet, "admin");

        assertEquals("P01-2023-0000", result.getCodReferencia1());
    }

    @Test
    void testGetOrdenPagoRequestDto_ShortNumber() {
        escala.setNumeroEscala(7);

        OrdenPagoRequestDTO result = ProcesarOrdenSunatUseCaseImpl.getOrdenPagoRequestDto(
                procedimiento, ordenPago, escala, fichaTecnicaDet, "admin");

        assertEquals("P01-2023-0007", result.getCodReferencia1());
    }

    @Test
    void testGetOrdenPagoRequestDto_LongNumber() {
        escala.setNumeroEscala(12345);

        OrdenPagoRequestDTO result = ProcesarOrdenSunatUseCaseImpl.getOrdenPagoRequestDto(
                procedimiento, ordenPago, escala, fichaTecnicaDet, "admin");

        assertEquals("P01-2023-12345", result.getCodReferencia1());
    }

    @Test
    void testProcesarOrdenSunat_EstadoExento() throws JsonProcessingException {
        ordenPagoResponseDto.setEstado("EXENTO");
        ordenPagoResponseDto.setMonto(0.0);

        when(escalaRepository.findById(ordenPago.getEscalaId())).thenReturn(escala);
        when(fichaTecnicaDetRepositoryPort.findByFichaTecnicaId(escala.getFichaTecnicaDetIn().getFichaTecnicaId())).thenReturn(fichaTecnicaDet);
        when(feignRepositoryPort.createOrdenPago(any(OrdenPagoRequestDTO.class))).thenReturn(ordenPagoResponseDto);

        procesarOrdenSunatUseCase.procesarOrdenSunat(procedimiento, tasa, ordenPago, 1, "admin");

        verify(updateOrdenPagoUseCase, times(1)).updateOrdenPago(ordenPago);
        assertEquals(CR, ordenPago.getEstado());
        assertEquals(100.0, ordenPago.getMonto());
    }

    @Test
    void testProcesarOrdenSunat_UpdateArchivoPDF() throws JsonProcessingException {
        ordenPagoResponseDto.setEstado(PENDIENTE_PAGO);
        ordenPagoResponseDto.setMonto(100.0);
        ordenPagoResponseDto.setOrdenPagoId(1);

        when(escalaRepository.findById(ordenPago.getEscalaId())).thenReturn(escala);
        when(fichaTecnicaDetRepositoryPort.findByFichaTecnicaId(escala.getFichaTecnicaDetIn().getFichaTecnicaId())).thenReturn(fichaTecnicaDet);
        when(feignRepositoryPort.createOrdenPago(any(OrdenPagoRequestDTO.class))).thenReturn(ordenPagoResponseDto);
        when(updateArchivoPDFUseCase.updateArchivoPDF(1, ordenPago)).thenReturn(ordenPago);

        procesarOrdenSunatUseCase.procesarOrdenSunat(procedimiento, tasa, ordenPago, 1, "admin");

        verify(updateArchivoPDFUseCase, times(1)).updateArchivoPDF(1, ordenPago);
        verify(updateOrdenPagoUseCase, times(2)).updateOrdenPago(ordenPago);
    }

    /*@Test
    void testProcesarOrdenSunat_AsyncAnnotation() throws NoSuchMethodException {
        var method = ProcesarOrdenSunatUseCaseImpl.class.getMethod(
                "procesarOrdenSunat",
                ProcedimientosResponse.Procedimiento.class,
                TasaResponse.Tasa.class,
                OrdenPago.class,
                Integer.class,
                String.class);

        Async asyncAnnotation = method.getAnnotation(Async.class);

        assertNotNull(asyncAnnotation);
        assertEquals("taskExecutorDefault", asyncAnnotation.value());
    }*/
}