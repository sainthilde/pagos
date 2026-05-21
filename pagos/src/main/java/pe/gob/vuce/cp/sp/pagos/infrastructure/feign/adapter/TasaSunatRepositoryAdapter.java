package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.adapter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.sp.pagos.domain.port.out.TasaSunatRepositoryPort;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.ComunesQueryClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.GestorProcedimientoClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.OAuthClient;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.config.OAuthClientConfig;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ComunesQueryResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.OAuthResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.ProcedimientosResponse;
import pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model.TasaResponse;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.ENTIDAD;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.COD_ENTIDAD_GP;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.NUMBER_1;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.STRING;
import static pe.gob.vuce.cp.sp.pagos.domain.constants.ConstantsPagos.BEARER;

@AllArgsConstructor
@Component
public class TasaSunatRepositoryAdapter implements TasaSunatRepositoryPort {

    private final OAuthClient oAuthClient;
    private final OAuthClientConfig oAuthClientConfig;
    private final GestorProcedimientoClient gestorProcedimientoClient;
    private final ComunesQueryClient comunesQueryClient;

    @Override
    public TasaResponse.Tasa obtenerTasa(Integer entidadId, String idComponente, String textSearch) {
        ComunesQueryResponse comunesQueryResponse = comunesQueryClient.getAllByCodeAndAttribute(entidadId, ENTIDAD);

        if (comunesQueryResponse != null && !comunesQueryResponse.getData().isEmpty()) {
            OAuthResponse oAuthResponse = oAuthClient.getToken(oAuthClientConfig.getGrantType(), oAuthClientConfig.getScope());
            String authorization = BEARER + oAuthResponse.getAccessToken();

            ProcedimientosResponse procedimientosResponse = gestorProcedimientoClient.getProcedimientos(
                    authorization,
                    idComponente,
                    Integer.parseInt(comunesQueryResponse.getData().get(0).getOthersColumns().get(COD_ENTIDAD_GP)),
                    STRING,
                    STRING,
                    textSearch
            );

            if (!procedimientosResponse.getProcedimientos().isEmpty()) {
                ProcedimientosResponse.Procedimiento procedimiento = procedimientosResponse.getProcedimientos().get(0);

                TasaResponse tasaResponse = gestorProcedimientoClient.getTasa(authorization, procedimiento.getProcedimientoId(), NUMBER_1);
                if (!tasaResponse.getTasas().isEmpty()) {
                    tasaResponse.getTasas().get(0).setDescripcion(procedimiento.getNombreCut());
                    tasaResponse.getTasas().get(0).setTupa(procedimiento.getTupa());
                    return tasaResponse.getTasas().get(0);
                }
            }
        }
        return null;
    }
}
