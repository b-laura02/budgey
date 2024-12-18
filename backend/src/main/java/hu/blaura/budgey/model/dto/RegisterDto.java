package hu.blaura.budgey.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor //default construktor
@AllArgsConstructor //parameteres-osszessel
@Data //adatosztaly -serializacio stb (g/s)
public class RegisterDto {
    private String email;
    private String fullName;
    private String password;
}
