package pe.gob.vuce.cp.sp.pagos.infrastructure.persistence;

import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import pe.gob.vuce.cp.sp.pagos.infrastructure.persistence.entity.OrdenPagoEntity;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

 class OrdenPagoEntityTest {

    private final Validator validator;

    public OrdenPagoEntityTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
     void testGettersAndSetters() {
        // Crear una instancia de la entidad
        OrdenPagoEntity ordenPago = new OrdenPagoEntity();

        // Configurar valores
        Integer ordenPagoId = 1;
        Integer entidadId = 2;
        Integer documentoId = 3;
        Integer escalaId = 4;
        String rucAgente = "12345678901";
        String estadoOrdenPago = "AP";
        Instant fechaCreacionOrdenPago = Instant.now();
        Instant fechaVencimientoOrdenPago = Instant.now().plusSeconds(86400); // 1 día en el futuro

        // Usar los setters
        ordenPago.setOrdenPagoId(ordenPagoId);
        ordenPago.setEntidadId(entidadId);
        ordenPago.setDocumentoId(documentoId);
        ordenPago.setEscalaId(escalaId);
        ordenPago.setRucAgente(rucAgente);
        ordenPago.setEstadoOrdenPago(estadoOrdenPago);
        ordenPago.setFechaCreacionOrdenPago(fechaCreacionOrdenPago);
        ordenPago.setFechaVencimientoOrdenPago(fechaVencimientoOrdenPago);

        // Validar los valores usando los getters
        assertEquals(ordenPagoId, ordenPago.getOrdenPagoId());
        assertEquals(entidadId, ordenPago.getEntidadId());
        assertEquals(documentoId, ordenPago.getDocumentoId());
        assertEquals(escalaId, ordenPago.getEscalaId());
        assertEquals(rucAgente, ordenPago.getRucAgente());
        assertEquals(estadoOrdenPago, ordenPago.getEstadoOrdenPago());
        assertEquals(fechaCreacionOrdenPago, ordenPago.getFechaCreacionOrdenPago());
        assertEquals(fechaVencimientoOrdenPago, ordenPago.getFechaVencimientoOrdenPago());
    }

    @Test
     void testValidation() {
        // Crear una instancia de la entidad sin valores para probar las restricciones de validación
        OrdenPagoEntity ordenPago = new OrdenPagoEntity();

        // Validar que el objeto no es válido
        var violations = validator.validate(ordenPago);
        assertNotNull(violations);
        assertEquals(7, violations.size()); // Debería haber 7 violaciones de validación por los campos @NotNull

        // Establecer valores obligatorios
        ordenPago.setEntidadId(1);
        ordenPago.setDocumentoId(1);
        ordenPago.setEscalaId(1);
        ordenPago.setRucAgente("12345678901");
        ordenPago.setEstadoOrdenPago("AP");
        ordenPago.setFechaCreacionOrdenPago(Instant.now());
        ordenPago.setFechaVencimientoOrdenPago(Instant.now().plusSeconds(86400));

        // Validar que el objeto ahora es válido
        violations = validator.validate(ordenPago);
        assertEquals(0, violations.size()); // No debería haber violaciones de validación
    }


    @Test
    void testGpFields() {
       OrdenPagoEntity ordenPagoEntity = new OrdenPagoEntity();

       String gpMonedaSigno = "USD";
       String gpEtiquetaTasa = "10.00";
       String gpProcedimientoTasaVersion = "01";
       String gpProcedimientoVersion = "02";
       String gpDescProcedimiento = "Description";
       String gpSecuencia = "01";

       ordenPagoEntity.setGpMonedaSigno(gpMonedaSigno);
       ordenPagoEntity.setGpEtiquetaTasa(new String(gpEtiquetaTasa));
       ordenPagoEntity.setGpProcedimientoTasaVersion(gpProcedimientoTasaVersion);
       ordenPagoEntity.setGpProcedimientoVersion(gpProcedimientoVersion);
       ordenPagoEntity.setGpDescProcedimiento(gpDescProcedimiento);
       ordenPagoEntity.setGpSecuencia(gpSecuencia);

       assertEquals(gpMonedaSigno, ordenPagoEntity.getGpMonedaSigno());
       assertEquals(new String(gpEtiquetaTasa), ordenPagoEntity.getGpEtiquetaTasa());
       assertEquals(gpProcedimientoTasaVersion, ordenPagoEntity.getGpProcedimientoTasaVersion());
       assertEquals(gpProcedimientoVersion, ordenPagoEntity.getGpProcedimientoVersion());
       assertEquals(gpDescProcedimiento, ordenPagoEntity.getGpDescProcedimiento());
       assertEquals(gpSecuencia, ordenPagoEntity.getGpSecuencia());
    }

    @Test
    void testHighlightedFields() {
       OrdenPagoEntity ordenPagoEntity = new OrdenPagoEntity();

       Integer tramiteId = 123;
       String gpTupa = "TUPA123";
       String gpFormato = "Formato1";

       ordenPagoEntity.setTramiteId(tramiteId);
       ordenPagoEntity.setGpTupa(gpTupa);
       ordenPagoEntity.setGpFormato(gpFormato);

       assertEquals(tramiteId, ordenPagoEntity.getTramiteId());
       assertEquals(gpTupa, ordenPagoEntity.getGpTupa());
       assertEquals(gpFormato, ordenPagoEntity.getGpFormato());
    }


    @Test
    void testReasignacionFields() {
       OrdenPagoEntity ordenPagoEntity = new OrdenPagoEntity();

       Instant fechaReasignacionOrdenPago = Instant.now();
       String codAutorizadorReasignacion = "AUTH123";
       String motivoAutorizacionReasignacion = "Motivo de reasignación";
       String sustentoReasignacionFilenetGuid = "GUID12345";

       ordenPagoEntity.setFechaReasignacionOrdenPago(fechaReasignacionOrdenPago);
       ordenPagoEntity.setCodAutorizadorReasignacion(codAutorizadorReasignacion);
       ordenPagoEntity.setMotivoAutorizacionReasignacion(motivoAutorizacionReasignacion);
       ordenPagoEntity.setSustentoReasignacionFilenetGuid(sustentoReasignacionFilenetGuid);

       assertEquals(fechaReasignacionOrdenPago, ordenPagoEntity.getFechaReasignacionOrdenPago());
       assertEquals(codAutorizadorReasignacion, ordenPagoEntity.getCodAutorizadorReasignacion());
       assertEquals(motivoAutorizacionReasignacion, ordenPagoEntity.getMotivoAutorizacionReasignacion());
       assertEquals(sustentoReasignacionFilenetGuid, ordenPagoEntity.getSustentoReasignacionFilenetGuid());
    }
}