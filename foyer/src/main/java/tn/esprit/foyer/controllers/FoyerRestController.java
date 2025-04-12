package tn.esprit.foyer.controllers;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.foyer.entities.Foyer;
import tn.esprit.foyer.services.IFoyerService;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/foyer")

public class FoyerRestController {

    IFoyerService foyerService;
    // http://localhost:8089/foyer/foyer/retrieve-all-foyers
    @GetMapping("/retrieve-all-foyers")
    @ResponseBody
    public List<Foyer> getFoyers() {
        List<Foyer> listFoyers = foyerService.retrieveAllFoyers();
        return listFoyers;
    }

    // http://localhost:8089/foyer/foyer/retrieve-foyer/8
    @GetMapping("/retrieve-foyer/{foyerId}")
    @ResponseBody
    public Foyer retrieveFoyer(@PathVariable("foyerId") Long foyerId) {
        return foyerService.retrieveFoyer(foyerId);
    }

    // http://localhost:8089/foyer/foyer/add-foyer
    @PostMapping("/add-foyer")
    @ResponseBody
    public Foyer addFoyer(@RequestBody Foyer f) {
        Foyer foyer= foyerService.addFoyer(f);
        return foyer;
    }

    // http://localhost:8089/foyer/foyer/add-foyer-with-bloc
    @PostMapping("/add-foyer-with-bloc")
    @ResponseBody
    public Foyer addFoyerWithBloc(@RequestBody Foyer f) {
        Foyer foyer= foyerService.addFoyerWithBloc(f);
        return foyer;
    }

    // http://localhost:8089/foyer/foyer/update-foyer
    @PutMapping("/update-foyer")
    @ResponseBody
    public Foyer updateFoyer(@RequestBody Foyer f) {
        Foyer foyer= foyerService.updateFoyer(f);
        return foyer;
    }
    // http://localhost:8089/foyer/foyer/stats
    @GetMapping("/stats")
    public List<Map<String, Object>> getFoyersStats(
            @RequestParam String universiteNom,
            @RequestParam Long capaciteMin) {

        return foyerService.getFoyersStats(universiteNom, capaciteMin);
    };
    // http://localhost:8089/foyer/foyer/removeFoyer
    @DeleteMapping("/removeFoyer/{idFoyer}")
    @ResponseBody
    public void removeFoyer(@PathVariable("idFoyer") Long idFoyer) {
        foyerService.removeFoyer(idFoyer);
    }

    // http://localhost:8089/foyer/foyer/search

    @GetMapping("/search")
    public List<Foyer> searchFoyersByUniversiteAndBloc(
            @RequestParam String universite,
            @RequestParam String bloc) {

        List<Foyer> result = foyerService.getFoyersByUniversiteAndBloc(universite, bloc);
        return result;
    }
}
