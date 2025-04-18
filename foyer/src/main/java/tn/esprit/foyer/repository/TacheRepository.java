package tn.esprit.foyer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.foyer.entities.Tache;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface TacheRepository extends JpaRepository<Tache,Long> {
    //calculates the total cost of taches between two dates for an etudiant
    @Query("select sum(t.tarifHoraire*t.duree) from Tache t where t.dateTache " +
            "between :t1 and :t2 and t.etudiant.idEtudiant=:idEtudiant")
    Float sommeTacheAnneeEncours(@Param("t1") LocalDate t1,
                                 @Param("t2")LocalDate t2,
                                 @Param("idEtudiant")Long idEtudiant);
    List<Tache> findByDeadLineBeforeAndIsCompleteFalse(LocalDateTime deadLine);
}
