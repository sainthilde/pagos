package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Component;
import pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.configuration.util.DataSourceUtil;

/**
 * Clase encargada de inicializar el datasource
 *
 * @project cp-api-bs-cambioagencia-command

 */
@Component
public class DataSourceInitializer {

    /**
     * Constructor que inicializa el datasource
     *
     * @param props propiedades del datasource
     */
    @Autowired
    public DataSourceInitializer(DataSourceProperties props) {
        DataSourceUtil.setUsername(props.getUsername());
    }
}

