package hu.blaura.budgey.modules.tip.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class AIResponseDto {
    @SerializedName("generated_text")
    private String generatedText;
}
