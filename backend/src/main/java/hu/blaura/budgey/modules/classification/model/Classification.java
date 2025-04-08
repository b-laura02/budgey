package hu.blaura.budgey.modules.classification.model;

import hu.blaura.budgey.modules.classification.model.dto.ClassificationDto;
import hu.blaura.budgey.modules.classification.model.enums.BalanceGroup;
import hu.blaura.budgey.modules.classification.model.enums.BalanceMainGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor //default construktor
@AllArgsConstructor //parameteres-osszessel
@Data //adatosztaly -serializacio stb (g/s)
@Entity //objektum
@Table(name = "classification") //postgre-be letrehoz egy tablat
public class Classification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private BalanceMainGroup balanceMainGroup;
    @Enumerated(EnumType.STRING)
    private BalanceGroup balanceGroup;
    private String balanceItem;

    // Entitásból Dto
    public ClassificationDto toDto() {
        return new ClassificationDto(
                this.balanceMainGroup,
                this.balanceGroup,
                this.balanceItem);
    }

    // Dto-ból entitás
    public static Classification fromDto(ClassificationDto dto) {
        return new Classification(
                null,
                dto.getBalanceMainGroup(),
                dto.getBalanceGroup(),
                dto.getBalanceItem());
    }
}