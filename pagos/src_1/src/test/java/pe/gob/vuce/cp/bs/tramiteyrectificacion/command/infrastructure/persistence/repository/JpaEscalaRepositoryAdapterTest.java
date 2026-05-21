package pe.gob.vuce.cp.bs.tramiteyrectificacion.command.infrastructure.persistence.repository;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JpaEscalaRepositoryAdapterTest {

    @Mock
    private EscalaRepository escalaRepository;

    private JpaEscalaRepositoryAdapter adapter;
/*
    @BeforeEach
    public void setUp() {
        adapter = new JpaEscalaRepositoryAdapter(escalaRepository);
    }

    @Test
    public void testGetEstadoDueId_WhenEscalaExists() {
        // Arrange: set up an Escala entity with a specific estadoDueId
        Integer escalaId = 1;
        Integer expectedEstadoDueId = 5;
        Escala escala = new Escala();
        escala.setEscalaId(escalaId);
        escala.setEstadoDueId(expectedEstadoDueId);

        // Configure the mock repository to return the Escala entity
        Mockito.when(escalaRepository.findById(escalaId)).thenReturn(Optional.of(escala));

        // Act: call the method under test
        Integer result = adapter.getEstadoDueId(escalaId);

        // Assert: verify that the returned estadoDueId is correct
        assertEquals(expectedEstadoDueId, result);
    }

    @Test
    public void testGetEstadoDueId_WhenEscalaNotFound() {
        // Arrange: configure the mock repository to return an empty Optional
        Integer escalaId = 1;
        Mockito.when(escalaRepository.findById(escalaId)).thenReturn(Optional.empty());

        // Act & Assert: verify that a BusinessError is thrown with the expected details
        BusinessError exception = assertThrows(BusinessError.class, () -> {
            adapter.getEstadoDueId(escalaId);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }*/
}
