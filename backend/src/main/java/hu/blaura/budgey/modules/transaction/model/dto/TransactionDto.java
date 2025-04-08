package hu.blaura.budgey.modules.transaction.model.dto;

import hu.blaura.budgey.modules.classification.model.dto.ClassificationDto;
import lombok.Data;

@Data
public class TransactionDto {
    private String type;
    private ClassificationDto classification;
    private Double amount;
    private String title;
}
