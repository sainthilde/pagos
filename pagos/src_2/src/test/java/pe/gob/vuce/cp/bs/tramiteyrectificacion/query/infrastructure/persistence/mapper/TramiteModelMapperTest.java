package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.DeclaracionJuradaModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.TramiteModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.escala.Escala;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.fictec.FichaTecnica;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.fictec.FichaTecnicaDet;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.mae.ActividadEntidadPuerto;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.mae.Agencia;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.mae.Documento;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.mae.Entidad;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.DeclaracionJurada;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.OrdenDePago;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.Tramite;

class TramiteModelMapperTest {

    private TramiteModelMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(TramiteModelMapper.class);
    }

    @Test
    void testAgenciaEntity() {
        // Arrange
        Agencia agencia = new Agencia();
        agencia.setId(1);
        agencia.setRucAgencia("12345678901");
        agencia.setRazonSocialAgencia("Agencia Test");
        agencia.setTipo("Test Tipo");
        agencia.setEstado("S");

        // Act & Assert
        assertNotNull(agencia);
        assertEquals(1, agencia.getId());
        assertEquals("12345678901", agencia.getRucAgencia());
        assertEquals("Agencia Test", agencia.getRazonSocialAgencia());
        assertEquals("Test Tipo", agencia.getTipo());
        assertEquals("S", agencia.getEstado());
    }

    @Test
    void testActividadEntidadPuertoEntity() {
        // Arrange
        Entidad entidad = new Entidad();
        entidad.setId(10);
        entidad.setNombre("Entidad Test");
        entidad.setRuc("12345678901");
        entidad.setObservacion("Test Observation");
        entidad.setEstado("A");
        entidad.setUsubdModAud("user123");
        entidad.setUsubdRegAud("user456");
        entidad.setGrupoEntidadId(100);

        ActividadEntidadPuerto actividadEntidadPuerto = new ActividadEntidadPuerto();
        actividadEntidadPuerto.setId(100);
        actividadEntidadPuerto.setEntidad(entidad);
        actividadEntidadPuerto.setActividadId(20);
        actividadEntidadPuerto.setCodPuertoNacional("PN1");
        actividadEntidadPuerto.setCodReglaNegocio("REG001");
        actividadEntidadPuerto.setEstado("A");

        // Act & Assert
        assertNotNull(actividadEntidadPuerto);
        assertEquals(100, actividadEntidadPuerto.getId());
        assertEquals(entidad.getId(), actividadEntidadPuerto.getEntidad().getId());
        assertEquals(20, actividadEntidadPuerto.getActividadId());
        assertEquals("PN1", actividadEntidadPuerto.getCodPuertoNacional());
        assertEquals("REG001", actividadEntidadPuerto.getCodReglaNegocio());
        assertEquals("A", actividadEntidadPuerto.getEstado());

        assertEquals(10, actividadEntidadPuerto.getEntidad().getId());

        assertEquals("Entidad Test", actividadEntidadPuerto.getEntidad().getNombre());
        assertEquals("12345678901", actividadEntidadPuerto.getEntidad().getRuc());
        assertEquals("Test Observation", actividadEntidadPuerto.getEntidad().getObservacion());
        assertEquals("A", actividadEntidadPuerto.getEntidad().getEstado());
        assertEquals("user123", actividadEntidadPuerto.getEntidad().getUsubdModAud());
        assertEquals("user456", actividadEntidadPuerto.getEntidad().getUsubdRegAud());
    }

    @Test
    void testFichaTecnicaDetEntity() {
        // Arrange
        FichaTecnica fichaTecnica = new FichaTecnica();

        FichaTecnicaDet fichaTecnicaDet = new FichaTecnicaDet();
        fichaTecnicaDet.setId(101);
        fichaTecnicaDet.setFichaTecnica(fichaTecnica);
        fichaTecnicaDet.setVersionFt(1);
        fichaTecnicaDet.setEstadoVersionFt(2);
        fichaTecnicaDet.setFechaMatricula(LocalDate.of(2024, 8, 15));
        fichaTecnicaDet.setNombreNave("Test Nave");
        fichaTecnicaDet.setCallSign("TestCallSign");
        fichaTecnicaDet.setInmarsat("TestInmarsat");
        fichaTecnicaDet.setMmsi("TestMMSI");
        fichaTecnicaDet.setSociedadClasificadora("TestSociedad");
        fichaTecnicaDet.setDocumentoMatricula("TestDocumento");
        fichaTecnicaDet.setDta("TestDTA");
        fichaTecnicaDet.setAnoConstructor(2020);
        fichaTecnicaDet.setTonelajePesoMuerto("10000");
        fichaTecnicaDet.setVelocidad(25);
        fichaTecnicaDet.setEslora(300);
        fichaTecnicaDet.setManga(50);
        fichaTecnicaDet.setPuntal(20);
        fichaTecnicaDet.setArqueoNeto(5000);
        fichaTecnicaDet.setArqueoBruto(7000);
        fichaTecnicaDet.setCaladoMinimo(10);
        fichaTecnicaDet.setCaladoMaximo(15);
        fichaTecnicaDet.setCantidadMaquinas(5);
        fichaTecnicaDet.setDobleCaso(true);

        // Act & Assert
        assertNotNull(fichaTecnicaDet);
        assertEquals(101, fichaTecnicaDet.getId());
        assertEquals(1, fichaTecnicaDet.getVersionFt());
        assertEquals(2, fichaTecnicaDet.getEstadoVersionFt());
        assertEquals(LocalDate.of(2024, 8, 15), fichaTecnicaDet.getFechaMatricula());
        assertEquals("Test Nave", fichaTecnicaDet.getNombreNave());
        assertEquals("TestCallSign", fichaTecnicaDet.getCallSign());
        assertEquals("TestInmarsat", fichaTecnicaDet.getInmarsat());
        assertEquals("TestMMSI", fichaTecnicaDet.getMmsi());
        assertEquals("TestSociedad", fichaTecnicaDet.getSociedadClasificadora());
        assertEquals("TestDocumento", fichaTecnicaDet.getDocumentoMatricula());
        assertEquals("TestDTA", fichaTecnicaDet.getDta());
        assertEquals(2020, fichaTecnicaDet.getAnoConstructor());
        assertEquals("10000", fichaTecnicaDet.getTonelajePesoMuerto());
        assertEquals(25, fichaTecnicaDet.getVelocidad());
        assertEquals(300, fichaTecnicaDet.getEslora());
        assertEquals(50, fichaTecnicaDet.getManga());
        assertEquals(20, fichaTecnicaDet.getPuntal());
        assertEquals(5000, fichaTecnicaDet.getArqueoNeto());
        assertEquals(7000, fichaTecnicaDet.getArqueoBruto());
        assertEquals(10, fichaTecnicaDet.getCaladoMinimo());
        assertEquals(15, fichaTecnicaDet.getCaladoMaximo());
        assertEquals(5, fichaTecnicaDet.getCantidadMaquinas());
        assertEquals(true, fichaTecnicaDet.getDobleCaso());
    }

    @Test
    void testToTramiteModel() {
        // Arrange
        Escala escala = new Escala();
        escala.setPuertoEscalaId("PE");
        escala.setAnnoEscala(2024);
        escala.setNumeroEscala(123);

        Tramite tramite = new Tramite();
        tramite.setId(1);
        tramite.setNumeroTramiteEntidad("TRAMITE-001");
        tramite.setEscala(escala);
        tramite.setIndicadorEs("S");
        tramite.setEstado("S");
        tramite.setIndNoRequierePago(false);

        // Initialize the declaracionesJuradas list to avoid NullPointerException
        tramite.setDeclaracionesJuradas(Collections.emptyList());

        OrdenDePago ordenDePago = new OrdenDePago();
        ordenDePago.setGpMonto(new BigDecimal("1000.00"));
        ordenDePago.setEstadoOrdenPago("Pagado");
        Documento documento = new Documento();
        documento.setId(1);
        ordenDePago.setDocumento(documento);
        ordenDePago.setRucAgente("12345678901");
        ordenDePago.setFechaCreacionOrdenPago(LocalDateTime.of(2024, 8, 15, 10, 0));
        ordenDePago.setFechaVencimientoOrdenPago(LocalDateTime.of(2024, 9, 15, 10, 0));

        // Act
        TramiteModel tramiteModel = mapper.toTramiteModel(tramite, ordenDePago);

        // Assert
        assertEquals(tramite.getId(), tramiteModel.getId());
        assertEquals("PE-2024-00123", tramiteModel.getDue());
        assertEquals(tramite.getNumeroTramiteEntidad(), tramiteModel.getNumeroTramite());
        assertEquals(ordenDePago.getGpMonto().doubleValue(), tramiteModel.getMonto());
        assertEquals(ordenDePago.getEstadoOrdenPago(), tramiteModel.getEstadoDePago());
    }

    @Test
    void testMapDue() {
        // Arrange
        Escala escala = new Escala();
        escala.setPuertoEscalaId("PE");
        escala.setAnnoEscala(2024);
        escala.setNumeroEscala(123);

        Tramite tramite = new Tramite();
        tramite.setEscala(escala);

        // Act
        String due = mapper.mapDue(tramite);

        // Assert
        assertEquals("PE-2024-00123", due);
    }

    @Test
    void testMapFechaDeclaracionJuradaActual() {
        // Arrange
        DeclaracionJurada dj1 = new DeclaracionJurada();
        dj1.setFechaSolicitudDdjj(LocalDateTime.of(2024, 8, 1, 12, 0));

        DeclaracionJurada dj2 = new DeclaracionJurada();
        dj2.setFechaSolicitudDdjj(LocalDateTime.of(2024, 8, 5, 12, 0));

        Tramite tramite = new Tramite();
        tramite.setDeclaracionesJuradas(Arrays.asList(dj1, dj2));

        // Act
        LocalDateTime fecha = mapper.mapFechaDeclaracionJuradaActual(tramite);

        // Assert
        assertEquals(dj2.getFechaSolicitudDdjj(), fecha);
    }

    @Test
    void testMapDeclaracionesJuradas() {
        // Arrange
        DeclaracionJurada dj1 = new DeclaracionJurada();
        dj1.setNumeroDdjj("DJJ-001");
        dj1.setEstadoDdjjPago("Aprobada");
        dj1.setRucAgente("12345678901");
        dj1.setMotivoDeclaracion("Motivo 1");
        dj1.setMensajeError("No errors");
        dj1.setFechaSolicitudDdjj(LocalDateTime.now());

        List<DeclaracionJurada> declaracionesJuradas = Collections.singletonList(dj1);

        // Act
        List<DeclaracionJuradaModel> models = mapper.mapDeclaracionesJuradas(declaracionesJuradas);

        // Assert
        assertNotNull(models);
        assertEquals(1, models.size());
        assertEquals(dj1.getNumeroDdjj(), models.get(0).getNumeroDeclaracionJurada());
        assertEquals(dj1.getEstadoDdjjPago(), models.get(0).getEstadoDeclaracionJurada());
        assertEquals(dj1.getRucAgente(), models.get(0).getRucAgente());
        assertEquals(dj1.getMotivoDeclaracion(), models.get(0).getMotivo());
        assertEquals(dj1.getMensajeError(), models.get(0).getError());
        assertEquals(dj1.getFechaSolicitudDdjj(), models.get(0).getFechaDeclaracionJurada());
    }

    @Test
    void testToTramiteModels() {
        // Arrange
        OrdenDePago orden1 = new OrdenDePago();
        orden1.setFechaCreacionOrdenPago(LocalDateTime.of(2024, 8, 1, 12, 0));

        OrdenDePago orden2 = new OrdenDePago();
        orden2.setFechaCreacionOrdenPago(LocalDateTime.of(2024, 8, 5, 12, 0));

        Tramite tramite = new Tramite();
        tramite.setOrdenesDePago(Arrays.asList(orden1, orden2));

        // Initialize declaracionesJuradas to prevent NullPointerException
        tramite.setDeclaracionesJuradas(Collections.emptyList());

        // Act
        List<TramiteModel> models = mapper.toTramiteModels(tramite);

        // Assert
        assertNotNull(models);
        assertEquals(1, models.size());
        assertNull(models.get(0).getFechaDeclaracionJuradaActual()); // Since declaracionesJuradas is empty
    }

    @Test
    void testToDeclaracionJuradaModel() {
        // Arrange
        DeclaracionJurada declaracionJurada = new DeclaracionJurada();
        declaracionJurada.setNumeroDdjj("DJJ-001");
        declaracionJurada.setEstadoDdjjPago("Aprobada");
        declaracionJurada.setRucAgente("12345678901");
        declaracionJurada.setMotivoDeclaracion("Motivo 1");
        declaracionJurada.setMensajeError("No errors");
        declaracionJurada.setFechaSolicitudDdjj(LocalDateTime.now());

        // Act
        DeclaracionJuradaModel model = mapper.toDeclaracionJuradaModel(declaracionJurada);

        // Assert
        assertNotNull(model);
        assertEquals(declaracionJurada.getNumeroDdjj(), model.getNumeroDeclaracionJurada());
        assertEquals(declaracionJurada.getEstadoDdjjPago(), model.getEstadoDeclaracionJurada());
        assertEquals(declaracionJurada.getRucAgente(), model.getRucAgente());
        assertEquals(declaracionJurada.getMotivoDeclaracion(), model.getMotivo());
        assertEquals(declaracionJurada.getMensajeError(), model.getError());
        assertEquals(declaracionJurada.getFechaSolicitudDdjj(), model.getFechaDeclaracionJurada());
    }

    @Test
    void testOrdenDePagoEntity() {
        // Arrange
        OrdenDePago ordenDePago = new OrdenDePago();
        ordenDePago.setId(1);
        Documento documento = new Documento();
        documento.setId(1);
        ordenDePago.setDocumento(documento);
        ordenDePago.setRucAgente("12345678901");
        ordenDePago.setEstadoOrdenPago("PG");
        ordenDePago.setFechaCreacionOrdenPago(LocalDateTime.of(2024, 8, 15, 10, 0));
        ordenDePago.setFechaVencimientoOrdenPago(LocalDateTime.of(2024, 9, 15, 10, 0));
        ordenDePago.setFechaPagado(LocalDateTime.of(2024, 8, 20, 10, 0));
        ordenDePago.setCodAutorizadorReasignacion("AUTH123");
        ordenDePago.setMotivoAutorizacionReasignacion("Motivo de Reasignación");
        ordenDePago.setSustentoReasignacionFilenetGuid("GUID123");
        ordenDePago.setPdfCpbFilenetGuid("PDFGUID123");
        ordenDePago.setFechaGuardadoPdfCpb(LocalDateTime.of(2024, 8, 21, 10, 0));
        ordenDePago.setGpTupa("TUPA1");
        ordenDePago.setGpFormato("FMT1");
        ordenDePago.setGpMonto(new BigDecimal("2000.00"));
        ordenDePago.setGpProcedimientoId("PRC1");
        ordenDePago.setGpMonedaSigno("$");
        ordenDePago.setGpEtiquetaTasa("100.00");
        ordenDePago.setGpProcedimientoTasaVersion("01");
        ordenDePago.setGpProcedimientoVersion("02");
        ordenDePago.setGpDescProcedimiento("Desc Procedimiento");
        ordenDePago.setGpSecuencia("01");
        ordenDePago.setPpFechaRespuestaCreacionCpb(LocalDateTime.of(2024, 8, 22, 10, 0));
        ordenDePago.setPpIdOrdenPagoInterna(1001);
        ordenDePago.setPpCodOrdenPago("COD123");
        ordenDePago.setPpCpb("CPB1");
        ordenDePago.setPpMonto(new BigDecimal("1500.00"));
        ordenDePago.setPpFechaConfGeneracionCpb(LocalDateTime.of(2024, 8, 23, 10, 0));
        ordenDePago.setPpEstadoCpbTexto("CONF");
        ordenDePago.setPpCodigorechazoSinConexion("REJ001");
        ordenDePago.setPpDescCortaError("Error Corta");
        ordenDePago.setPpMensajeRechazoSinConexion("Mensaje de Rechazo");

        // Act & Assert
        assertNotNull(ordenDePago);
        assertEquals(1, ordenDePago.getId());
        assertEquals(documento.getId(), ordenDePago.getDocumento().getId());
        assertEquals("12345678901", ordenDePago.getRucAgente());
        assertEquals("PG", ordenDePago.getEstadoOrdenPago());
        assertEquals(LocalDateTime.of(2024, 8, 15, 10, 0), ordenDePago.getFechaCreacionOrdenPago());
        assertEquals(LocalDateTime.of(2024, 9, 15, 10, 0), ordenDePago.getFechaVencimientoOrdenPago());
        assertEquals(LocalDateTime.of(2024, 8, 20, 10, 0), ordenDePago.getFechaPagado());
        assertEquals("AUTH123", ordenDePago.getCodAutorizadorReasignacion());
        assertEquals("Motivo de Reasignación", ordenDePago.getMotivoAutorizacionReasignacion());
        assertEquals("GUID123", ordenDePago.getSustentoReasignacionFilenetGuid());
        assertEquals("PDFGUID123", ordenDePago.getPdfCpbFilenetGuid());
        assertEquals(LocalDateTime.of(2024, 8, 21, 10, 0), ordenDePago.getFechaGuardadoPdfCpb());
        assertEquals("TUPA1", ordenDePago.getGpTupa());
        assertEquals("FMT1", ordenDePago.getGpFormato());
        assertEquals(new BigDecimal("2000.00"), ordenDePago.getGpMonto());
        assertEquals("PRC1", ordenDePago.getGpProcedimientoId());
        assertEquals("$", ordenDePago.getGpMonedaSigno());
        assertEquals("100.00", ordenDePago.getGpEtiquetaTasa());
        assertEquals("01", ordenDePago.getGpProcedimientoTasaVersion());
        assertEquals("02", ordenDePago.getGpProcedimientoVersion());
        assertEquals("Desc Procedimiento", ordenDePago.getGpDescProcedimiento());
        assertEquals("01", ordenDePago.getGpSecuencia());
        assertEquals(LocalDateTime.of(2024, 8, 22, 10, 0), ordenDePago.getPpFechaRespuestaCreacionCpb());
        assertEquals(1001, ordenDePago.getPpIdOrdenPagoInterna());
        assertEquals("COD123", ordenDePago.getPpCodOrdenPago());
        assertEquals("CPB1", ordenDePago.getPpCpb());
        assertEquals(new BigDecimal("1500.00"), ordenDePago.getPpMonto());
        assertEquals(LocalDateTime.of(2024, 8, 23, 10, 0), ordenDePago.getPpFechaConfGeneracionCpb());
        assertEquals("CONF", ordenDePago.getPpEstadoCpbTexto());
        assertEquals("REJ001", ordenDePago.getPpCodigorechazoSinConexion());
        assertEquals("Error Corta", ordenDePago.getPpDescCortaError());
        assertEquals("Mensaje de Rechazo", ordenDePago.getPpMensajeRechazoSinConexion());
    }
}
