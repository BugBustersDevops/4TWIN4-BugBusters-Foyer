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

import static org.junit.jupiter.api.Assertions.*;
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
        et.setNomEt("nacer");
        et.setPrenomEt("adhoum");
        et.setMontantInscription(500f);

        when(etudiantRepository.findAll()).thenReturn(List.of(et));
        when(tacheRepository.sommeTacheAnneeEncours(any(), any(), eq(1L)))
                .thenReturn(120f);

        HashMap<String, Float> result = tacheService.calculNouveauMontantInscriptionDesEtudiants();
        assertEquals(380f, result.get("nacer adhoum"));
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

    @Test
    void swapTaches_success_sameWeek() {
        Etudiant e1 = new Etudiant(); e1.setIdEtudiant(1L);
        Etudiant e2 = new Etudiant(); e2.setIdEtudiant(2L);
        LocalDate now = LocalDate.now();

        Tache t1 = new Tache(now, 2, 50f);
        t1.setIdTache(10L);
        t1.setEtudiant(e1);
        Tache t2 = new Tache(now, 3, 60f);
        t2.setIdTache(20L);
        t2.setEtudiant(e2);

        when(tacheRepository.findById(10L)).thenReturn(Optional.of(t1));
        when(tacheRepository.findById(20L)).thenReturn(Optional.of(t2));

        tacheService.swapTaches(10L, 20L);
        assertEquals(e2, t1.getEtudiant());
        assertEquals(e1, t2.getEtudiant());
        verify(tacheRepository).saveAll(List.of(t1, t2));
    }

    @Test
    void swapTaches_fail_differentWeek() {
        Etudiant e1 = new Etudiant();
        Etudiant e2 = new Etudiant();
        LocalDate now = LocalDate.now();
        LocalDate lastWeek = now.minusWeeks(1);

        Tache t1 = new Tache(now, 2, 50f);
        t1.setIdTache(11L);
        t1.setEtudiant(e1);
        Tache t2 = new Tache(lastWeek, 3, 60f);
        t2.setIdTache(22L);
        t2.setEtudiant(e2);

        when(tacheRepository.findById(11L)).thenReturn(Optional.of(t1));
        when(tacheRepository.findById(22L)).thenReturn(Optional.of(t2));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> tacheService.swapTaches(11L, 22L));
        assertTrue(ex.getMessage().contains("same week"));
    }
}