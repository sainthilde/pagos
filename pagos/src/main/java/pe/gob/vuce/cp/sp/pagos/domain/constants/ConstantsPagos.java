package pe.gob.vuce.cp.sp.pagos.domain.constants;
/**
 * La clase ConstantsPagos es una clase de utilidad que define constantes específicas
 * para manejar información de pagos, principalmente en relación con operaciones
 * marítimas y tipos de tráficos. Estas constantes permiten estandarizar los valores
 * relacionados con mensajes, entidades y formatos de datos en la aplicación.
 *
 * <p>Características de la clase:
 * <ul>
 *   <li>Es una clase de solo constantes, diseñada para no ser instanciada.</li>
 *   <li>El constructor privado lanza una excepción para evitar la creación de
 *       instancias de la clase.</li>
 * </ul>
 *
 * <p>Constantes principales:
 * <ul>
 *   <li>{@code ENTIDAD}, {@code COD_ENTIDAD_GP}: Códigos específicos de entidad
 *       utilizados en las operaciones de pago.</li>
 *   <li>{@code BEARER}: Prefijo para autenticación tipo Bearer en encabezados HTTP.</li>
 *   <li>{@code APPLICATION_PDF}: Formato MIME para documentos PDF, utilizado en
 *       respuestas o solicitudes HTTP.</li>
 *   <li>{@code NOT_PAY}, {@code NOT_PAY_DEPORT}, {@code NOT_PAY_NAVE}, {@code YES_PAY_NT},
 *       etc.: Mensajes para determinar si una operación debe pagar tarifas
 *       de acuerdo con el tipo de nave o tráfico. Estos mensajes cubren distintas
 *       situaciones, como operaciones de cabotaje, refugio, y tráfico internacional.</li>
 *   <li>{@code NUMBER_500}, {@code NUMBER_1}, {@code NUMBER_18}: Valores numéricos
 *       relevantes para condiciones de pago o cálculos específicos.</li>
 *   <li>{@code REGEX}: Expresión regular que puede ser usada para validar o
 *       extraer ciertos tipos de datos JSON específicos.</li>
 * </ul>
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
public class ConstantsPagos {
    /**
     * Constructor privado para evitar la creación de instancias de la clase.
     * Lanza una excepción si se intenta instanciar, ya que esta clase solo contiene constantes.
     */
    private ConstantsPagos() {
        throw new IllegalStateException("Constants Pagos class");
    }

    public static final String ENTIDAD = "entidad";
    public static final String PARAMETROS_GENERALES = "PARAMETROS_GENERALES";
    public static final String COMPONENTE_FILENET = "ID_COMPONENTE_FILENET";
    public static final String BEARER = "Bearer ";
    public static final String COD_ENTIDAD_GP = "cod_entidad_gp";
    public static final String APPLICATION_PDF = "application/pdf";
    public static final String STRING = "";
    public static final Integer NUMBER_1 = 1;
    public static final String NOT_PAY = "No paga por Motivo de Escala “Refugio/Arribo Forzoso”";
    public static final String NOT_PAY_DEPORT = "No paga por Tipo de Nave Deportiva y de Recreo (20)";
    public static final String NOT_PAY_NAVE = "No paga por Tipo de Nave Científica (15)";
    public static final String NOT_PAY_BUQUE = " No paga por Tipo de Nave Buque de Armada – Visita Oficial (42)";
    public static final String NOT_PAY_NAVE_HOSP = " No paga por Tipo de Nave Hospital (24)";
    public static final String YES_PAY_NT = "Sí paga por Tipo de Tráfico Internacional";
    public static final String NO_PAY_NT = "No paga. Operación de Cabotaje con puerto de Procedencia Nacional";
    public static final String NO_PAY_PD = "No paga. Operación de Cabotaje con puerto de Destino Nacional";
    public static final String NOT_PAY_NAVE_CAB = " No paga. Operación de Cabotaje con la APR Ancash";
    public static final String NOT_PAY_CABOTAJE = "No paga. Operación de Cabotaje con arribo a puerto de ámbito Marítimo";
    public static final String YES_PAY_OCA = "Sí paga. Operación de Cabotaje con arribo a puerto de ámbito Fluvial/Lacustre con Total Arqueo Bruto mayor igual que 500: ";
    public static final String NOT_PAY_OCA = "No paga. Operación de Cabotaje con arribo a puerto de ámbito Fluvial/Lacustre con Total Arqueo Bruto menor a 500: ";
    public static final String DOES_NOT_COMPLY = "No se cumplen las condiciones para determinar si debe pagar o no.";
    public static final String POINT = ". ";
    public static final Integer NUMBER_500 = 500;
    public static final Integer NUMBER_18 = 18;
    public static final String REGEX = "\\{\\s*\"type\"\\s*:\\s*\"[^\"]*\"[^}]*\\}";

}
