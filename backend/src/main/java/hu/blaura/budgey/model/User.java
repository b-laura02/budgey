package hu.blaura.budgey.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor //default construktor
@AllArgsConstructor //parameteres-osszessel
@Data //adatosztaly -serializacio stb (g/s)
@Entity //objektum
@Table(name = "user") //postgre-be letrehoz egy tablat
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String fullName;
    private String password;
}
