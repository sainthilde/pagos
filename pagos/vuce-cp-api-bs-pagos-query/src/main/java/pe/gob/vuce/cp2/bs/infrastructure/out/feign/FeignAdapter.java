package pe.gob.vuce.cp2.bs.infrastructure.out.feign;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import pe.gob.vuce.cp2.bs.domain.model.ComprobanteModel;
import pe.gob.vuce.cp2.bs.domain.model.DataModel;
import pe.gob.vuce.cp2.bs.domain.model.GestorProcedimientoModel;
import pe.gob.vuce.cp2.bs.domain.model.OperacionModel;
import pe.gob.vuce.cp2.bs.domain.port.out.FeignPort;
import pe.gob.vuce.cp2.bs.infrastructure.out.feign.gestorprocedimiento.cc003.Procedimiento003FeignClient;
import pe.gob.vuce.cp2.bs.infrastructure.out.feign.gestorprocedimiento.cc003.ProcedimientosResponse;
import pe.gob.vuce.cp2.bs.infrastructure.out.feign.gestorprocedimiento.cc006.Procedimiento006FeignClient;
import pe.gob.vuce.cp2.bs.infrastructure.out.feign.gestorprocedimiento.cc006.ProcedimientoTasaResponseDto;
import pe.gob.vuce.cp2.bs.infrastructure.out.feign.gestorprocedimiento.cc006.TasaDto;
import pe.gob.vuce.cp2.bs.infrastructure.out.feign.gestorprocedimiento.token.OAuthFeignClient;
import pe.gob.vuce.cp2.bs.infrastructure.out.feign.gestorprocedimiento.token.OAuthTokenResponse;

@AllArgsConstructor
@Component
public class FeignAdapter implements FeignPort {
    
    private final OAuthFeignClient oauthFeignClient;
    private final Procedimiento003FeignClient procedimiento003FeignClient;
    private final Procedimiento006FeignClient procedimiento006FeignClient;
    @Override
    public String obtenerToken() {
        String clientId = "vOW7SdrWwm6VyxpB7fjAV3sNN8Aa";
        String clientSecret = "WWOGek_xEpTAIXCXhKGRVSfHs9Ma";
        String credentials = clientId + ":" + clientSecret;

        String basicAuth = "Basic " + Base64.getEncoder()
                .encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        OAuthTokenResponse response = oauthFeignClient.getToken(
            basicAuth,
            "client_credentials",
            "gp:gp_read"
        );
        return response.getAccessToken();
    }

    @Override
    public OperacionModel obtenerProcedimientoComponente(OperacionModel model) {
        GestorProcedimientoModel gestorProcedimientoModel = model.getGestorProcedimientoModel();
        ProcedimientosResponse response = procedimiento003FeignClient.obtenerProcedimientos(
            gestorProcedimientoModel.getToken(),
            gestorProcedimientoModel.getComponente(),
            gestorProcedimientoModel.getEntidadId(),
            gestorProcedimientoModel.getTextSearch()
        );
        gestorProcedimientoModel.setSecuencia(1);
        gestorProcedimientoModel.setProcedimiento(response.getProcedimientos().get(0).getProcedimientoId());
        return model;
    }

    @Override
    public OperacionModel obtenerProcedimientoTasa(OperacionModel model) {
        GestorProcedimientoModel gestorProcedimientoModel = model.getGestorProcedimientoModel();
        DataModel dataModel = model.getDataModel();
        ComprobanteModel comprobanteModel = dataModel.getComprobanteModel();
        ProcedimientoTasaResponseDto response = procedimiento006FeignClient.obtenerTasa(
            gestorProcedimientoModel.getToken(),
            gestorProcedimientoModel.getProcedimiento(),
            gestorProcedimientoModel.getSecuencia()
        );
        TasaDto tasa = response.getTasas().get(0);

        comprobanteModel.setMonto(tasa.getMonto());
        comprobanteModel.setCodigoMoneda(tasa.getCodigoMoneda());   
        comprobanteModel.setEtiqueta(tasa.getEtiqueta());
        comprobanteModel.setDescripcion(tasa.getDescripcion());
        comprobanteModel.setMonedaDescripcion(tasa.getMonedaDescripcion());
        comprobanteModel.setMonedaSigno(tasa.getMonedaSigno());
        comprobanteModel.setProcedimientoId(tasa.getProcedimientoId());
        comprobanteModel.setProcedimientoVersion(tasa.getProcedimientoVersion());
        comprobanteModel.setProcedimientoTasaVersion(tasa.getProcedimientoTasaVersion());

        return model;
    }

}
