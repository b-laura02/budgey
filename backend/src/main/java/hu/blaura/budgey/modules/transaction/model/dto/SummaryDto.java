package hu.blaura.budgey.modules.transaction.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class SummaryDto {
    private Date fromDate;
    private Date toDate;
    private double income;
    private double expense;
    private double profit;
    private String exportLink;
}
