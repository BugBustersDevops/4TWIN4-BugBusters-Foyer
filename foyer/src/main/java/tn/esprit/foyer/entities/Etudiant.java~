package tn.esprit.foyer.entities;


import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
public class Etudiant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEtudiant; // Clé primaire

    @NonNull
    private String nomEt;

    @NonNull
    private String prenomEt;

    @NonNull
    private Long cin;

    @NonNull
    private String ecole;

    @NonNull
    private LocalDate dateNaissance;

    private float montantInscription = 500;

    @NonNull
    @Enumerated(EnumType.STRING)
    private TypeEtudiant typeEtudiant;

    @OneToMany(mappedBy = "etudiant")
    private List<Tache> taches;

    @ManyToMany(mappedBy = "etudiants",fetch = FetchType.EAGER)
    List<Reservation> reservations;
}

