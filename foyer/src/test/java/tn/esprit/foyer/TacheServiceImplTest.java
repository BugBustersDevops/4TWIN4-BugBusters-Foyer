package tn.esprit.foyer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.entities.Tache;
import tn.esprit.foyer.entities.TypeTache;
import tn.esprit.foyer.repository.EtudiantRepository;
import tn.esprit.foyer.repository.TacheRepository;
import tn.esprit.foyer.services.TacheServiceImpl;

import java.time.LocalDate;
import java.util.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests for TacheServiceImpl")
public class TacheServiceImplTest {

    @Mock
    TacheRepository tacheRepository;

    @Mock
    EtudiantRepository etudiantRepository;

    @InjectMocks
    TacheServiceImpl tacheService;

    @Test
    void testCalculNouveauMontantInscriptionDesEtudiants() {
        Etudiant et = new Etudiant();
        et.setIdEtudiant(1L);
        et.setNomEt("Slim");
        et.setPrenomEt("Khaled");
        et.setMontantInscription(500f);

        when(etudiantRepository.findAll()).thenReturn(List.of(et));
        when(tacheRepository.sommeTacheAnneeEncours(any(), any(), eq(1L)))
                .thenReturn(120f);

        HashMap<String, Float> result = tacheService.calculNouveauMontantInscriptionDesEtudiants();
        assertEquals(380f, result.get("Slim Khaled"));
    }

    @Test
    void testAddTachesAndAffectToEtudiant() {
        Etudiant et = new Etudiant();
        et.setIdEtudiant(2L);
        et.setCin(12345678L);

        Tache t1 = new Tache(LocalDate.now(), 2, 50f);
        t1.setTypeTache(TypeTache.BRICOLAGE);
        Tache t2 = new Tache(LocalDate.now(), 3, 60f);
        t2.setTypeTache(TypeTache.JARDINAGE);
        List<Tache> taches = List.of(t1, t2);

        when(etudiantRepository.findByCin(12345678L)).thenReturn(et);
        when(tacheRepository.saveAll(taches)).thenReturn(taches);

        List<Tache> result = tacheService.addTachesAndAffectToEtudiant(taches, 12345678L);
        assertEquals(et, result.get(0).getEtudiant());
        assertEquals(et, result.get(1).getEtudiant());
    }
}