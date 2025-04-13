package tn.esprit.foyer.services;

import jakarta.validation.constraints.Null;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tn.esprit.foyer.entities.Bloc;
import tn.esprit.foyer.entities.Foyer;
import tn.esprit.foyer.repository.BlocRepository;
import tn.esprit.foyer.repository.FoyerRepository;
import tn.esprit.foyer.services.FoyerServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoyerServiceImplTest {

    private FoyerRepository foyerRepository;
    private BlocRepository blocRepository;
    private FoyerServiceImpl foyerService;

    @BeforeEach
    void setUp() {
        // Création manuelle des mocks
        foyerRepository = Mockito.mock(FoyerRepository.class);
        blocRepository = Mockito.mock(BlocRepository.class);

        // Initialisation du service avec les mocks
        foyerService = new FoyerServiceImpl(foyerRepository, blocRepository);
    }

    // Test pour retrieveAllFoyers()
    @Test
    void testRetrieveAllFoyers() {
        // Arrange
        Foyer foyer1 = Foyer.builder()
                .idFoyer(1L)
                .nomFoyer("Foyer A")
                .capaciteFoyer(100L)
                .build();

        Foyer foyer2 = Foyer.builder()
                .idFoyer(2L)
                .nomFoyer("Foyer B")
                .capaciteFoyer(200L)
                .build();

        when(foyerRepository.findAll()).thenReturn(Arrays.asList(foyer1, foyer2));

        // Act
        List<Foyer> result = foyerService.retrieveAllFoyers();

        // Assert
        assertEquals(2, result.size());
        verify(foyerRepository).findAll();
    }

    // Test pour addFoyer()
    @Test
    void testAddFoyer() {
        // Arrange
        Foyer foyer = Foyer.builder()
                .nomFoyer("Nouveau Foyer")
                .capaciteFoyer(150L)
                .build();

        Foyer savedFoyer = Foyer.builder()
                .idFoyer(1L)
                .nomFoyer("Nouveau Foyer")
                .capaciteFoyer(150L)
                .build();
        when(foyerRepository.save(foyer)).thenReturn(savedFoyer);

        // Act
        Foyer result = foyerService.addFoyer(foyer);

        // Assert
        assertEquals(1L, result.getIdFoyer());
        verify(foyerRepository).save(foyer);
    }

    // Test pour updateFoyer()
    @Test
    void testUpdateFoyer() {
        // Arrange
        Foyer existingFoyer = Foyer.builder()
                .idFoyer(1L)
                .nomFoyer("Foyer Existant")
                .capaciteFoyer(100L)
                .build();
        when(foyerRepository.save(existingFoyer)).thenReturn(existingFoyer);

        // Act
        Foyer result = foyerService.updateFoyer(existingFoyer);

        // Assert
        assertEquals("Foyer Existant", result.getNomFoyer());
        verify(foyerRepository).save(existingFoyer);
    }

    // Test pour retrieveFoyer() avec ID existant
    @Test
    void testRetrieveFoyerFound() {
        // Arrange
        Foyer foyer = Foyer.builder()
                .idFoyer(1L)
                .nomFoyer("Foyer Trouvé")
                .capaciteFoyer(200L)
                .build();

        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));

        // Act
        Foyer result = foyerService.retrieveFoyer(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Foyer Trouvé", result.getNomFoyer());
    }

    // Test pour retrieveFoyer() avec ID non trouvé
    @Test
    void testRetrieveFoyerNotFound() {
        // Arrange
        when(foyerRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Foyer result = foyerService.retrieveFoyer(99L);

        // Assert
        assertNull(result);
    }

    // Test pour removeFoyer()
    @Test
    void testRemoveFoyer() {
        // Arrange
        doNothing().when(foyerRepository).deleteById(1L);

        // Act & Assert
        assertDoesNotThrow(() -> foyerService.removeFoyer(1L));
        verify(foyerRepository).deleteById(1L);
    }

    // Test pour getFoyersByUniversiteAndBloc()
    @Test
    void testGetFoyersByUniversiteAndBloc() {
        // Arrange
        Foyer foyer = Foyer.builder()
                .idFoyer(1L)
                .nomFoyer("Foyer X")
                .capaciteFoyer(300L)
                .build();
        when(foyerRepository.findByUniversiteNomUniversiteAndBlocsNomBloc("ESPRIT", "B1"))
                .thenReturn(Collections.singletonList(foyer));

        // Act
        List<Foyer> result = foyerService.getFoyersByUniversiteAndBloc("ESPRIT", "B1");

        // Assert
        assertEquals(1, result.size());
        assertEquals("Foyer X", result.get(0).getNomFoyer());
    }

    // Test pour addFoyerWithBloc() avec blocs
    @Test
    void testAddFoyerWithBloc() {
        // Arrange
        Foyer foyer = Foyer.builder()
                .nomFoyer("Foyer avec Blocs")
                .capaciteFoyer(500L)
                .build();

        Bloc bloc1 = Bloc.builder()
                .idBloc(1L)
                .nomBloc("Bloc A")
                .build();

        Bloc bloc2 = Bloc.builder()
                .idBloc(2L)
                .nomBloc("Bloc B")
                .build();

        foyer.setBlocs(Arrays.asList(bloc1, bloc2));

        Foyer savedFoyer = Foyer.builder()
                .idFoyer(1L)
                .nomFoyer("Foyer avec Blocs")
                .capaciteFoyer(500L)
                .build();
        when(foyerRepository.save(foyer)).thenReturn(savedFoyer);
        when(blocRepository.save(any(Bloc.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Foyer result = foyerService.addFoyerWithBloc(foyer);

        // Assert
        assertAll(
                () -> assertEquals(1L, result.getIdFoyer()),
                () -> verify(blocRepository, times(2)).save(any(Bloc.class)),
                () -> verify(foyerRepository).save(foyer)
        );
    }

    // Test pour getFoyersStats()
    @Test
    void testGetFoyersStats() {
        // Arrange
        Foyer foyerStats = Foyer.builder()
                .idFoyer(1L)
                .nomFoyer("Foyer Stats")
                .capaciteFoyer(1000L)
                .build();

        Object[] mockData = {
                foyerStats, // foyer
                5L, // nombreBlocs
                2000L // capaciteTotale
        };

        when(foyerRepository.findFoyersWithStats("ESPRIT", 1500L))
                .thenReturn(Collections.singletonList(mockData));

        // Act
        List<Map<String, Object>> results = foyerService.getFoyersStats("ESPRIT", 1500L);

        // Assert
        assertAll(
                () -> assertEquals(1, results.size()),
                () -> assertEquals("Foyer Stats", ((Foyer) results.get(0).get("foyer")).getNomFoyer()),
                () -> assertEquals(5L, results.get(0).get("nombreBlocs")),
                () -> assertEquals(2000L, results.get(0).get("capaciteTotale"))
        );
    }
}