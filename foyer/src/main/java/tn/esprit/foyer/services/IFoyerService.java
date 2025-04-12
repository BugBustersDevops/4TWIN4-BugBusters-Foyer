package tn.esprit.foyer.services;

import tn.esprit.foyer.entities.Foyer;

import java.util.List;
import java.util.Map;

public interface IFoyerService {

    List<Foyer> retrieveAllFoyers();
    Foyer addFoyer(Foyer f);
    Foyer updateFoyer(Foyer f);
    Foyer retrieveFoyer(Long idFoyer);
    void removeFoyer(Long idFoyer);

    public List<Foyer> getFoyersByUniversiteAndBloc(String nomUniversite, String nomBloc);

    Foyer addFoyerWithBloc(Foyer f);

    public List<Map<String, Object>> getFoyersStats(String universiteNom, Long capaciteMin);

}
