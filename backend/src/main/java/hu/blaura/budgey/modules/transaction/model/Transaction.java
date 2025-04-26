package hu.blaura.budgey.modules.transaction.model;

import hu.blaura.budgey.modules.classification.model.Classification;
import hu.blaura.budgey.modules.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor //default construktor
@AllArgsConstructor //parameteres-osszessel
@Data //adatosztaly -serializacio stb (g/s)
@Entity //objektum
@Table(name = "transaction") //postgre-be letrehoz egy tablat
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Simplified categorizing
    @Enumerated(EnumType.STRING)
    private TransactionCategory category;
//    @ManyToOne
//    @JoinColumn(name = "classification_id", nullable = false)
//    private Classification classification;
    private Double amount;
    private String title;
    private String date;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
