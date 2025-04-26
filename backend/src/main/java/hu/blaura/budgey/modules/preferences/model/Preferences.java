package hu.blaura.budgey.modules.preferences.model;

import hu.blaura.budgey.modules.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor //default construktor
@AllArgsConstructor //parameteres-osszessel
@Data //adatosztaly -serializacio stb (g/s)
@Entity //objektum
@Table(name = "preferences") //postgre-be letrehoz egy tablat
public class Preferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double targetIncome;
    private double targetExpense;
    private double targetProfit;
    // Megkerdezzuk a user-t, hogy beleegyezik-e abba,
    // hogy az AI feldolgozza a koltesi adatait
    private boolean allowAIProcessing;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
