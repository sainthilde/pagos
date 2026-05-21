package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.application.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.models.OrdenDePagoModel;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.port.in.ObtenerOrdenDePagoUseCase;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.mae.Entidad;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.infrastructure.persistence.entity.pago.OrdenDePago;

@SpringBootTest
 class OrdenDePagoServiceTest {

    @Mock
    private ObtenerOrdenDePagoUseCase obtenerOrdenDePagoUseCase;

    @InjectMocks
    private OrdenDePagoService ordenDePagoService;



    @Test
    void returnDeclaracionJuradaListWhenFilterEscalaIdAndDocumentiD() {
        // Arrange
        Integer escalaId = 1;
        Integer documentId = 1;
        String rucAgente = "123456789";
        String estadoordenDePago = "PG";


        when(obtenerOrdenDePagoUseCase.findOrdenesDePago(
                anyInt(), anyInt(), anyString(),anyString() )).thenReturn(mockOrdenPagoModelList());

        // Act
        List<OrdenDePagoModel> result = ordenDePagoService.findOrdenesDePago(
                escalaId , documentId,estadoordenDePago ,rucAgente );


        assertNotNull(result);
    }

    public OrdenDePago mockOrdenPago(){

        OrdenDePago ordenDePagoEn = new OrdenDePago();
        ordenDePagoEn.setId(1);
        Entidad entidad = new Entidad();
        entidad.setId(1);
        ordenDePagoEn.setEntidad(entidad);
        ordenDePagoEn.setEstadoOrdenPago("PG");
        return  ordenDePagoEn;
    }

    public OrdenDePagoModel mockOrdenPagoModel(){

        OrdenDePagoModel ordenDePagoEn = new OrdenDePagoModel();
        ordenDePagoEn.setId(1);
        ordenDePagoEn.setDocumentoId(1);
        ordenDePagoEn.setEscalaId(1);
        ordenDePagoEn.setRucAgente("10457726606");
        ordenDePagoEn.setEstadoOrdenPago("PG");
        return  ordenDePagoEn;
    }

    public  List<OrdenDePago> mockOrdenPagoList(){
        List<OrdenDePago>  ordenPagoList = new ArrayList<>();
        ordenPagoList.add(mockOrdenPago());
        return ordenPagoList;

    }

    public  List<OrdenDePagoModel> mockOrdenPagoModelList(){
        List<OrdenDePagoModel>  ordenPagoList = new ArrayList<>();
        ordenPagoList.add(mockOrdenPagoModel());
        return ordenPagoList;

    }
}
