package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.configuration.util;

import lombok.Getter;

/**
 * Clase utilitaria para manejar el datasource
 *
 * @project cp-api-bs-cambioagencia-command
 */
public final class DataSourceUtil {

    @Getter
    private static String username;

    /**
     * Constructor privado para evitar la instanciación de la clase
     */
    private DataSourceUtil() {
        throw new AssertionError("CANNOT_INSTANTIATE");
    }

    /**
     * Método para establecer el nombre de usuario del datasource
     *
     * @param username nombre de usuario del datasource
     */
    public static void setUsername(String username) {
        DataSourceUtil.username = username;
    }
}
