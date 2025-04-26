package hu.blaura.budgey.modules.tip.model;

import hu.blaura.budgey.modules.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor //default construktor
@AllArgsConstructor //parameteres-osszessel
@Data //adatosztaly -serializacio stb (g/s)
@Entity //objektum
@Table(name = "tip") //postgre-be letrehoz egy tablat
public class Tip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Lob
    @Column(name = "text", columnDefinition = "TEXT")
    private String text;
    private String date;
    private boolean isAiGenerated;
    @Enumerated(EnumType.STRING)
    private TipType type;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
