package tn.esprit.foyer.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.repository.EtudiantRepository;

import static org.junit.jupiter.api.Assertions.*;

class EtudiantServiceImplMockTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddEtudiantWithMock() {
        Etudiant e = Etudiant.builder()
                .nomEt("Khedhira")
                .prenomEt("Adam")
                .build();

        when(etudiantRepository.save(e)).thenReturn(e);

        Etudiant result = etudiantService.addEtudiant(e);

        assertNotNull(result);
        assertEquals("Khedhira", result.getNomEt());

        verify(etudiantRepository, times(1)).save(e);
    }
}