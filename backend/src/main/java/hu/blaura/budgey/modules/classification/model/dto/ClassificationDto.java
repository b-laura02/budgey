package hu.blaura.budgey.modules.classification.model.dto;

import hu.blaura.budgey.modules.classification.model.enums.BalanceGroup;
import hu.blaura.budgey.modules.classification.model.enums.BalanceMainGroup;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClassificationDto {
    private BalanceMainGroup balanceMainGroup;
    private BalanceGroup balanceGroup;
    private String balanceItem;
}
