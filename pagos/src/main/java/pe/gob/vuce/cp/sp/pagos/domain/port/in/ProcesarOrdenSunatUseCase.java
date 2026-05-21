package pe.gob.vuce.cp.sp.pagos.domain.port.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ProcedimientosResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.TasaResponse;

@Component
public interface ProcesarOrdenSunatUseCase {

     void procesarOrdenSunat(ProcedimientosResponse.Procedimiento procedimiento,
                         TasaResponse.Tasa tasa,
                         OrdenPago ordenPago,
                         Integer cantidadOrden,
                         String user) throws JsonProcessingException;
}
