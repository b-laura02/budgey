package hu.blaura.budgey.modules.tip.model.dto;

import hu.blaura.budgey.modules.tip.model.TipType;
import lombok.Data;

@Data
public class TipDto {
    private Long id;
    private String title;
    private String text;
    private String date;
    private boolean isAiGenerated;
    private TipType type;
}
