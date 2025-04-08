package hu.blaura.budgey.modules.transaction.model.dto;

import hu.blaura.budgey.modules.transaction.model.TransactionCategory;
import lombok.Data;

@Data
public class TransactionDto {
    private TransactionCategory category;
    private Double amount;
    private String title;
    private String date;
}
