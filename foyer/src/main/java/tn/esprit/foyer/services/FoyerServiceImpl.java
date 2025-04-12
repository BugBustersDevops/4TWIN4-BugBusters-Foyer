package tn.esprit.foyer.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.foyer.entities.Foyer;
import tn.esprit.foyer.repository.BlocRepository;
import tn.esprit.foyer.repository.FoyerRepository;
import tn.esprit.foyer.services.IFoyerService;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class FoyerServiceImpl implements IFoyerService {

    FoyerRepository foyerRepository;
    BlocRepository blocRepository;

    @Override
    public List<Foyer> retrieveAllFoyers() {
        log.info("Récupération de tous les foyers");
        List<Foyer> foyers = foyerRepository.findAll();
        log.debug("Nombre de foyers trouvés : {}", foyers.size());
        return foyers;
    }

    @Override
    public Foyer addFoyer(Foyer f) {
        log.info("Ajout d'un nouveau foyer : {}", f.getNomFoyer());
        Foyer savedFoyer = foyerRepository.save(f);
        log.info("Foyer ajouté avec ID : {}", savedFoyer.getIdFoyer());
        return savedFoyer;
    }

    @Override
    public Foyer addFoyerWithBloc(Foyer f) {
        log.info("Ajout d'un foyer avec blocs : {}", f.getNomFoyer());

        Foyer foyer = foyerRepository.save(f);
        log.debug("Foyer sauvegardé, ID : {}", foyer.getIdFoyer());

        if (f.getBlocs() == null || f.getBlocs().isEmpty()) {
            log.warn("Aucun bloc associé au foyer {}", foyer.getIdFoyer());
            return foyer;
        }

        f.getBlocs().forEach(bloc -> {
            bloc.setFoyer(foyer);
            blocRepository.save(bloc);
            log.debug("Bloc {} associé au foyer {}", bloc.getNomBloc(), foyer.getIdFoyer());
        });

        log.info("{} blocs ajoutés au foyer {}", f.getBlocs().size(), foyer.getIdFoyer());
        return foyer;
    }

    @Override
    public Foyer updateFoyer(Foyer f) {
        log.info("Mise à jour du foyer ID : {}", f.getIdFoyer());
        try {
            Foyer updatedFoyer = foyerRepository.save(f);
            log.info("Foyer ID {} mis à jour avec succès", f.getIdFoyer());
            return updatedFoyer;
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour du foyer ID {} : {}", f.getIdFoyer(), e.getMessage());
            throw e;
        }
    }

    @Override
    public Foyer retrieveFoyer(Long idFoyer) {
        log.info("Recherche du foyer ID : {}", idFoyer);
        return foyerRepository.findById(idFoyer).orElseGet(() -> {
            log.warn("Foyer ID {} non trouvé", idFoyer);
            return null;
        });
    }

    @Override
    public void removeFoyer(Long idFoyer) {
        log.info("Suppression du foyer ID : {}", idFoyer);
        try {
            foyerRepository.deleteById(idFoyer);
            log.info("Foyer ID {} supprimé avec succès", idFoyer);
        } catch (Exception e) {
            log.error("Échec de la suppression du foyer ID {} : {}", idFoyer, e.getMessage());
            throw e;
        }
    }
}