package tn.esprit.foyer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
public class Tache implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTache;

    @NonNull
    private LocalDate dateTache;

    @NonNull
    Integer duree;

    @NonNull
    Float tarifHoraire;

    @Enumerated(EnumType.STRING)
    TypeTache typeTache;

    @ManyToOne
    @JsonIgnore
    Etudiant etudiant;
}

