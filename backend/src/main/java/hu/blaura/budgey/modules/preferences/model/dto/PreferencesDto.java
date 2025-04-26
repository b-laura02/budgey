package hu.blaura.budgey.modules.preferences.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PreferencesDto {
    private double targetIncome;
    private double targetExpense;
    private double targetProfit;
    private boolean allowAIProcessing;
}
