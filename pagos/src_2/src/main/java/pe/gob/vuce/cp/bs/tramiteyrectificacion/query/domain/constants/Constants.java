package pe.gob.vuce.cp.bs.tramiteyrectificacion.query.domain.constants;

/**
 * ErrorCodes defines constant error codes used throughout the application.
 * This class cannot be instantiated.
 *
 * @project vuce-cp-bs-impedimentozarpe-command
 * @autor Jonathan Pizarro
 * @date 02/07/2024
 */
public final class Constants {

    private Constants() {
        throw new AssertionError("Cannot instantiate Constants class");
    }

    public static final int ONE = 1;
    public static final int ZERO = 0;
    public static final String NO_VALUE = "NO VALUE";

    public static final String NULL_VALUE = "null";

    public static final String ESCALA = "escala";
    public static final String PUERTO_ESCALA_ID = "puertoEscalaId";
    public static final String ANNO_ESCALA = "annoEscala";
    public static final String NUMERO_ESCALA = "numeroEscala";
    public static final String DECLARACIONES_JURADAS = "declaracionesJuradas";
    public static final String FECHA_SOLICITUD_DDJJ = "fechaSolicitudDdjj";
    public static final String FECHA_REGISTRO = "fechaRegAud";

    public static final String ACTIVIDAD_ENTIDAD_PUERTO = "actividadEntidadPuerto";
    public static final String ENTIDAD = "entidad";
    public static final String ID = "id";
    public static final String AGENCIA = "agencia";
    public static final String TRAMITE = "tramite";

    public static final String FICHA_TECNICA_DET_IN = "fichaTecnicaDetIn";
    public static final String NOMBRE_NAVE = "nombreNave";

    public static final String ESTADO = "estado";
    public static final String ESTADO_ACTIVO = "S";

    public static final String ESTADO_TRAMITE = "estadoTramite";
    public static final String NUMERO_SUCE = "numeroSuce";
    public static final String NUMERO_TRAMITE_ENTIDAD = "numeroTramiteEntidad";
    public static final String ORDENES_DE_PAGO = "ordenesDePago";
    public static final String PP_CPB = "ppCpb";

    public static final String FECHA_TRAMITE = "fechaTramite";
    public static final String FECHA_TRAMITE_MANUAL = "fechaTramiteManual";
    public static final String FUE_TRAMITE_MANUAL = "fueTramiteManual";
    public static final String TUPA = "tupa";

    public static final String NUMERO_DECLARACION_JURADA = "numeroDdjj";

    public static final String ENTIDAD_ID = "entidadId";

    public static final String RUC_AGENTE = "rucAgente";

    public static final String WILDCARD = "%";
}
