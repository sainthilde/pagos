package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.application.usecase;

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
import pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.port.out.OrdenDePagoRepositoryPort;

@SpringBootTest
class ObtenerOrdenDePagoUseCaseImplTest {

    @Mock
    private OrdenDePagoRepositoryPort ordenDePagoRepositoryPort;

    @InjectMocks
    private ObtenerOrdenDePagoUseCaseImpl obtenerDeclaracionJuradaUseCaseImpl;

    @Test
    void returnDeclaracionJuradaListWhenFilterEscalaIdAndDocumentiD() {
        // Arrange
        Integer escalaId = 1;
        Integer documentId = 1;
        String rucAgente = "123456789";
        String estadoordenDePago = "PG";

    when(ordenDePagoRepositoryPort.findOrdenesDePago(
                anyInt(), anyInt(), anyString(), anyString())).thenReturn(mockOrdenPagoModelList());

        // Act
    List<OrdenDePagoModel> result = obtenerDeclaracionJuradaUseCaseImpl.findOrdenesDePago(
        escalaId, documentId, rucAgente, estadoordenDePago);

        assertNotNull(result);
    }

    public OrdenDePagoModel mockOrdenPagoModel() {

        OrdenDePagoModel ordenDePagoEn = new OrdenDePagoModel();
        ordenDePagoEn.setId(1);
        ordenDePagoEn.setDocumentoId(1);
        ordenDePagoEn.setEscalaId(1);
        ordenDePagoEn.setRucAgente("10457726606");
        ordenDePagoEn.setEstadoOrdenPago("PG");
        return ordenDePagoEn;
    }

    public List<OrdenDePagoModel> mockOrdenPagoModelList() {
        List<OrdenDePagoModel> ordenPagoList = new ArrayList<>();
        ordenPagoList.add(mockOrdenPagoModel());
        return ordenPagoList;

    }
}
