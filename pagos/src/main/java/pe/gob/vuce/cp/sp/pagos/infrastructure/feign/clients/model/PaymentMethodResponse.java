package pe.gob.vuce.cp.sp.pagos.infrastructure.feign.clients.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
/**
 * La clase PaymentMethodResponse representa la respuesta con información sobre
 * métodos de pago. Contiene detalles sobre el canal, entidad, y listas de instrucciones,
 * notas, cuentas y bancos, proporcionando toda la información necesaria para
 * describir un método de pago. Implementa {@code Serializable} para permitir
 * la serialización del objeto, facilitando su uso en procesos de almacenamiento o transmisión.
 *
 * <p>Anotaciones:
 * <ul>
 *   <li>{@code @Getter} y {@code @Setter}: Generan automáticamente los métodos de
 *       acceso (getters) y modificación (setters) para cada atributo en la clase
 *       y sus clases internas.</li>
 *   <li>{@code @SuppressWarnings("all")}: Suprime advertencias específicas del compilador
 *       en esta clase.</li>
 * </ul>
 *
 * <p>Atributos:
 * <ul>
 *   <li>{@code canalId}: Identificador del canal del método de pago.</li>
 *   <li>{@code entidadId}: Identificador de la entidad del método de pago.</li>
 *   <li>{@code titulo}: Título del método de pago.</li>
 *   <li>{@code iconoTitulo}: Icono asociado al título del método de pago.</li>
 *   <li>{@code orden}: Orden de visualización o prioridad del método de pago.</li>
 *   <li>{@code listaInstruccion}: Lista de instrucciones específicas para el método de pago.</li>
 *   <li>{@code listaNota}: Lista de notas adicionales sobre el método de pago.</li>
 *   <li>{@code listaCuenta}: Lista de cuentas asociadas al método de pago.</li>
 *   <li>{@code listaBanco}: Lista de bancos disponibles para el método de pago.</li>
 * </ul>
 *
 * <p>Clases internas:
 * <ul>
 *   <li>{@code Instruccion}: Representa una instrucción con una descripción y orden.</li>
 *   <li>{@code Nota}: Representa una nota con una descripción y orden.</li>
 *   <li>{@code Cuenta}: Representa una cuenta con información de cuenta y banco.</li>
 *   <li>{@code Banco}: Representa un banco con nombre, tooltip, URL de imagen y orden.</li>
 * </ul>
 * @author Mateo Huancho
 * @version 1.0
 * @project vuce-cp-api-sp-pagos
 * @date 2024-10-26
 */
@Getter
@Setter
@SuppressWarnings("all")
public class PaymentMethodResponse implements Serializable {
    private Integer canalId;
    private Integer entidadId;
    private String titulo;
    private String iconoTitulo;
    private Integer orden;
    private List<Instruccion> listaInstruccion;
    private List<Nota> listaNota;
    private List<Cuenta> listaCuenta;
    private List<Banco> listaBanco;
    /**
     * La clase interna Instruccion representa una instrucción del método de pago,
     * con una descripción y un orden de visualización.
     */
    @Getter
    @Setter
    public static class Instruccion implements Serializable {
        private String descripcion;
        private Integer orden;
    }
    /**
     * La clase interna Nota representa una nota asociada al método de pago,
     * con una descripción y un orden de visualización.
     */
    @Getter
    @Setter
    public static class Nota implements Serializable {
        private String descripcion;
        private Integer orden;
    }
    /**
     * La clase interna Cuenta representa una cuenta bancaria asociada al método de pago,
     * con información de cuenta y banco.
     */
    @Getter
    @Setter
    public static class Cuenta implements Serializable {
        private String cuenta;
        private String banco;
    }
    /**
     * La clase interna Banco representa un banco disponible para el método de pago,
     * con nombre, tooltip, URL de imagen y orden de visualización.
     */
    @Getter
    @Setter
    public static class Banco implements Serializable {
        private String nombre;
        private String tooltip;
        private String urlImg;
        private Integer orden;
    }
}

