package hu.blaura.budgey.modules.classification.model.dto;

import hu.blaura.budgey.modules.classification.model.enums.BalanceGroup;
import hu.blaura.budgey.modules.classification.model.enums.BalanceMainGroup;
import lombok.Data;

@Data
public class BalanceGroupDto {
    BalanceMainGroup balanceMainGroup;
    BalanceGroup balanceGroup;
}
