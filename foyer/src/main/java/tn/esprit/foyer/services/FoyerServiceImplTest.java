package tn.esprit.foyer.services;

import org.junit.jupiter.api.extension.ExtendWith;
import tn.esprit.foyer.entities.Bloc;
import tn.esprit.foyer.entities.Foyer;
import tn.esprit.foyer.entities.Universite;
import tn.esprit.foyer.repository.BlocRepository;
import tn.esprit.foyer.repository.FoyerRepository;
import tn.esprit.foyer.repository.UniversiteRepository;
import tn.esprit.foyer.services.IFoyerService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class FoyerServiceImplTest {


     UniversiteRepository universiteRepository;

     FoyerRepository foyerRepository;


     BlocRepository blocRepository;


    IFoyerService foyerService;

     Long savedFoyerId;

    @BeforeAll
    static void globalSetup() {
        System.out.println("Initialisation globale des tests Foyer");
    }

    @AfterAll
    static void globalCleanup() {
        System.out.println("Nettoyage final des tests Foyer");
    }

    @BeforeEach
    void testSetup() {
        System.out.println("Préparation avant chaque test");
    }

    @Test
    @Order(1)
    void testAddFoyer() {
        // Arrange
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("FoyerEsprit");

        // Act
        Foyer result = foyerService.addFoyer(foyer);
        savedFoyerId = result.getIdFoyer();

        // Assert
        assertEquals("FoyerEsprit", result.getNomFoyer());
        assertEquals(true, result.getIdFoyer() != null);
    }

    @Test
    @Order(2)
    void testRetrieveFoyer() {
        // Act
        Foyer result = foyerService.retrieveFoyer(savedFoyerId);

        // Assert
        assertEquals("FoyerEsprit", result.getNomFoyer());
        assertEquals(Foyer.class, result.getClass());
    }

    @Test
    @Order(3)
    void testRetrieveAllFoyers() {
        // Act
        List<Foyer> foyers = foyerService.retrieveAllFoyers();

        // Assert
        assertEquals(true, foyers.size() > 0);
        assertEquals(Foyer.class, foyers.get(0).getClass());
    }

    @Test
    @Order(4)
    void testUpdateFoyer() {
        // Arrange
        Foyer foyer = foyerService.retrieveFoyer(savedFoyerId);
        foyer.setNomFoyer("FoyerEsprit Modifié");

        // Act
        Foyer updated = foyerService.updateFoyer(foyer);

        // Assert
        assertEquals("FoyerEsprit Modifié", updated.getNomFoyer());
        assertEquals(savedFoyerId, updated.getIdFoyer());
    }

    @Test
    @Order(5)
    void testRemoveFoyer() {
        // Act
        foyerService.removeFoyer(savedFoyerId);

        // Assert
        Foyer result = foyerService.retrieveFoyer(savedFoyerId);
        assertEquals(null, result);
    }

    @Test
    @Order(6)
    void testGetFoyersByUniversiteAndBloc() {
        // Arrange
        Universite universite = new Universite();
        universite.setNomUniversite("ESPRIT");
        universiteRepository.save(universite);

        Foyer foyer = new Foyer();
        foyer.setNomFoyer("FoyerEsprit");
        foyer.setUniversite(universite);
        foyerRepository.save(foyer);

        Bloc bloc = new Bloc();
        bloc.setNomBloc("B1");
        bloc.setFoyer(foyer);
        blocRepository.save(bloc);

        // Act
        List<Foyer> result = foyerService.getFoyersByUniversiteAndBloc("ESPRIT", "B1");

        // Assert
        assertEquals(1, result.size());
        assertEquals("FoyerEsprit", result.get(0).getNomFoyer());
        assertEquals(foyer.getIdFoyer(), result.get(0).getIdFoyer());
    }

    @Test
    @Order(7)
    void testGetFoyersStats() {
        // Arrange
        Universite universite = new Universite();
        universite.setNomUniversite("ESPRIT");
        universiteRepository.save(universite);

        Foyer foyer = new Foyer();
        foyer.setNomFoyer("FoyerEsprit");
        foyer.setCapaciteFoyer(1500L);
        foyer.setUniversite(universite);
        foyerRepository.save(foyer);

        IntStream.rangeClosed(1, 3).forEach(i -> {
            Bloc bloc = new Bloc();
            bloc.setNomBloc("B" + i);
            bloc.setFoyer(foyer);
            blocRepository.save(bloc);
        });

        // Act
        List<Map<String, Object>> results = foyerService.getFoyersStats("ESPRIT", 1000L);

        // Assert
        assertEquals(1, results.size());
        assertEquals("FoyerEsprit", ((Foyer) results.get(0).get("foyer")).getNomFoyer());
        assertEquals(3L, results.get(0).get("nombreBlocs"));
        assertEquals(1500L, results.get(0).get("capaciteTotale"));
    }

}