package tn.esprit.foyer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.foyer.entities.Foyer;

import java.util.List;
import java.util.Map;

public interface FoyerRepository extends JpaRepository<Foyer,Long> {

    List<Foyer> findByUniversiteNomUniversite(String nom);

    List<Foyer> findByBlocsNomBloc(String s);

    List<Foyer> findByUniversiteNomUniversiteAndBlocsNomBloc(String nom,String s);

    Foyer findByNomFoyer(String nomFoyer);




    @Query("SELECT f, COUNT(b), SUM(b.capaciteBloc) " +
            "FROM Foyer f " +
            "LEFT JOIN f.blocs b " +
            "JOIN f.universite u " +
            "WHERE u.nomUniversite = :universiteNom " +
            "GROUP BY f " +
            "HAVING SUM(b.capaciteBloc) >= :capaciteMin")
    List<Object[]> findFoyersWithStats(
            @Param("universiteNom") String universiteNom,
            @Param("capaciteMin") Long capaciteMin);



}
