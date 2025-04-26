package hu.blaura.budgey.modules.preferences.service;

import hu.blaura.budgey.modules.preferences.model.Preferences;
import hu.blaura.budgey.modules.preferences.model.dto.PreferencesDto;
import hu.blaura.budgey.modules.preferences.repository.PreferencesRepository;
import hu.blaura.budgey.modules.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PreferencesService {
    private final PreferencesRepository preferencesRepository;

    public Preferences getByUser(User user) {
        return preferencesRepository.findByUser(user);
    }

    public Preferences update(User user, PreferencesDto preferencesDto) {
        Preferences preferences = getByUser(user);
        preferences.setAllowAIProcessing(preferencesDto.isAllowAIProcessing());
        preferences.setTargetExpense(preferencesDto.getTargetExpense());
        preferences.setTargetIncome(preferencesDto.getTargetIncome());
        preferences.setTargetProfit(preferencesDto.getTargetProfit());
        return preferencesRepository.save(preferences);
    }
}
