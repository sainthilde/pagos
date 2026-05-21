package pe.gob.vuce.cp.sp.pagos.domain.constants;

import pe.gob.vuce.cp.sp.pagos.infrastructure.api.dto.SeguimientoRequestDto;

public class SeguimientoUtils {

    private SeguimientoUtils() {
        throw new IllegalStateException("SeguimientoUtils class");
    }
    public static final Integer GENERADO = 41;
    public static final Integer ANULADO = 43;
    public static final Integer PAGADO = 42;
    public static final Integer EXTORNADO = 55;
    public static final Integer EXPIRADO = 54;
    public static SeguimientoRequestDto generarRequestSeguimiento(
            Integer escalaId,
            Integer tipoSeguimiento,
            String indicadorEntradaSalida,
            String ruc,
            String acronimoDocumento,
            String comentario) {
        SeguimientoRequestDto seguimientoRequestDto = new SeguimientoRequestDto();
        seguimientoRequestDto.setTipoSegId(tipoSeguimiento);
        seguimientoRequestDto.setRucUsuario(ruc);
        seguimientoRequestDto.setIndNil(null);
        seguimientoRequestDto.setEscalaId(escalaId);
        seguimientoRequestDto.setAcronimoDocumento(acronimoDocumento);
        seguimientoRequestDto.setIndicadorEs(indicadorEntradaSalida);
        seguimientoRequestDto.setComentario(comentario);
        seguimientoRequestDto.setEstado("S");
        return seguimientoRequestDto;
    }
}
