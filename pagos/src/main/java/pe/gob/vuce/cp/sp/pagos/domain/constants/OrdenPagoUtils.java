package pe.gob.vuce.cp.sp.pagos.domain.constants;

import pe.gob.vuce.cp.sp.pagos.domain.model.OrdenPago;
/**
 * La clase OrdenPagoUtils es una clase de utilidad que proporciona métodos auxiliares
 * para la creación y manipulación de objetos de tipo {@code OrdenPago}.
 * Esta clase permite construir una nueva instancia de {@code OrdenPago} basada
 * en otra instancia, copiando sus valores en un nuevo objeto.
 *
 * <p>Características de la clase:
 * <ul>
 *   <li>Es una clase de solo utilidades, diseñada para no ser instanciada.</li>
 *   <li>El constructor privado lanza una excepción para evitar la creación de
 *       instancias de la clase.</li>
 * </ul>
 *
 * <p>Métodos principales:
 * <ul>
 *   <li>{@code buildOrdenPagoFrom(OrdenPago source)}: Método estático que recibe
 *       una instancia de {@code OrdenPago} como parámetro y devuelve una nueva
 *       instancia de {@code OrdenPago} con los mismos valores de atributo que el
 *       objeto de origen. Este método es útil para crear copias o clonar objetos
 *       de {@code OrdenPago} sin necesidad de modificar el objeto original.</li>
 * </ul>
 *
 * <p>Ejemplo de uso:
 * <pre>
 *     OrdenPago nuevaOrden = OrdenPagoUtils.buildOrdenPagoFrom(ordenExistente);
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 * </pre>
 */
public class OrdenPagoUtils {
    /**
     * Constructor privado para evitar la creación de instancias de la clase.
     * Lanza una excepción si se intenta instanciar, ya que esta clase solo contiene utilidades estáticas.
     */
    private OrdenPagoUtils() {
        throw new IllegalStateException("OrdenPagoUtils class");
    }
    /**
     * Crea una nueva instancia de {@code OrdenPago} basada en los atributos de otra
     * instancia de {@code OrdenPago}.
     *
     * @param source la instancia de {@code OrdenPago} desde la cual se copian los atributos.
     * @return una nueva instancia de {@code OrdenPago} con los mismos atributos que {@code source}.
     */
    public static OrdenPago buildOrdenPagoFrom(OrdenPago source) {
        return new OrdenPago(
                source.getOrdenPagoId(),
                source.getEntidadId(),
                source.getDocumentoId(),
                source.getEscalaId(),
                source.getRucAgente(),
                source.getCodigoOrdenPago(),
                source.getMonto(),
                source.getFechaGeneracion(),
                source.getCpb(),
                source.getEstado(),
                source.getFechaVigencia(),
                source.getIdComponente(),
                source.getTextSearch(),
                source.getOrdenPagoInternaId(),
                source.getActividadEntidadPuertoId(),
                source.getFilenetGuid(),
                source.getGpTupa(),
                source.getGpFormato(),
                source.getGpMonto(),
                source.getGpProcedimientoId(),
                source.getGpMonedaSigno(),
                source.getGpEtiquetaTasa(),
                source.getGpProcedimientoTasaVersion(),
                source.getGpProcedimientoVersion(),
                source.getGpDescProcedimiento(),
                source.getGpSecuencia(),
                source.getFechaGuardadoPdfCpb(),
                source.getPpFechaRespuestaCreacionCpb(),
                source.getPpFechaConfGeneracionCpb(),
                source.getPpEstadoCpbTexto(),
                source.getFechaPagado(),
                source.getFechaAnulacionCpb(),
                source.getFechaExtornoOrdenPago(),
                source.getFechaCreacionOrdenPago(),
                source.getPpCodigorechazoSinConexion(),
                source.getPpDescCortaError(),
                source.getPpMensajeRechazoSinConexion(),
                source.getUsuidRegAud(),
                source.getUsuidModAud(),
                source.getTramiteId()
        );
    }
}
