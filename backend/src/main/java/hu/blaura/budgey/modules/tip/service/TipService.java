package hu.blaura.budgey.modules.tip.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hu.blaura.budgey.modules.preferences.model.Preferences;
import hu.blaura.budgey.modules.preferences.repository.PreferencesRepository;
import hu.blaura.budgey.modules.preferences.service.PreferencesService;
import hu.blaura.budgey.modules.tip.model.Tip;
import hu.blaura.budgey.modules.tip.model.TipType;
import hu.blaura.budgey.modules.tip.model.dto.AIResponseDto;
import hu.blaura.budgey.modules.tip.repository.TipRepository;
import hu.blaura.budgey.modules.transaction.model.Transaction;
import hu.blaura.budgey.modules.transaction.model.dto.SummaryDto;
import hu.blaura.budgey.modules.transaction.service.TransactionService;
import hu.blaura.budgey.modules.user.model.User;
import hu.blaura.budgey.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.huggingface.HuggingfaceChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TipService {
    private final TipRepository tipRepository;
    private final TransactionService transactionService;
    private final PreferencesService preferencesService;
    private final UserService userService;
    private final HuggingfaceChatModel chatModel;
    @Value("${spring.ai.huggingface.chat.api-key}")
    private String apiKey;
    @Value("${spring.ai.huggingface.chat.url}")
    private String chatUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private final Gson gson = new Gson();

//    @Scheduled(cron = "0 0 1 * * 1")
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void scheduleGenerate() {
        List<User> users = userService.findAll();
        users.forEach(this::generate);
    }

    @Transactional
    public List<Tip> generate(
            User user
    ) {
        System.out.println("Generating tips for " + user.getEmail());
        Date endDate = new Date();

        // 3 het kell, ezert visszalepunk 3 hetet a naptarban
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.WEEK_OF_YEAR, -3);
        Date startDate = calendar.getTime();

        final List<Transaction> transactions = transactionService.getTransactionsBetweenDates(startDate, endDate, user);
        final Preferences preferences = preferencesService.getByUser(user);
        final SummaryDto summary = transactionService.getMonthlySummaries(0, 1, user).get(0);

        final List<Tip> tips = new ArrayList<>();
        final boolean canUseAi = preferences.isAllowAIProcessing();

        if (canUseAi) {
            final String aiHabitText = generateAIHabit(transactions);
            final String aiPredictionText = generateAIPrediction(transactions);
            final String aiAdviceText = generateAIAdvice(transactions);

            tips.addAll(processTextToTips(aiHabitText, aiPredictionText, aiAdviceText, true, user));
        }
        final String habitText = generateHabit(summary, preferences);
        final String predictionText = generatePrediction(summary, preferences);
        final String adviceText = generateAdvice(summary, preferences);

        tips.addAll(processTextToTips(habitText, predictionText, adviceText, false, user));

        deleteAll(user);
        tipRepository.saveAll(tips);

        tips.forEach(tip -> {
            System.out.println(tip.getType());
            System.out.println(tip.getTitle());
        });

        return tips;
    }

    @Transactional(readOnly = true)
    public List<Tip> getAll(User user) {
        return tipRepository.findByUserOrderByIsAiGenerated(user);
    }

    // Mielott legeneraljuk az uj tipeket a het elejen,
    // kitoroljuk az eddigi tippeket.
    public void deleteAll(User user) {
        tipRepository.deleteAllByUser(user);
    }

    // https://docs.spring.io/spring-framework/reference/integration/rest-clients.html
    private String promptAI(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("inputs", prompt);  // Csak a szöveg

        // Opcionális paraméterek
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("max_new_tokens", 300);
        parameters.put("temperature", 0.5);
        parameters.put("return_full_text", false);
        requestBody.put("parameters", parameters);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    chatUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );
            final String body = response.getBody();
            System.out.println(body);
            return parseResponse(body);
        } catch (HttpClientErrorException e) {
            System.err.println("Hiba: " + e.getResponseBodyAsString());
            throw new RuntimeException("API hiba: " + e.getMessage());
        }
    }

    private String parseResponse(String body) {
        try {
            Type responseType = new TypeToken<List<AIResponseDto>>(){}.getType();
            List<AIResponseDto> responses = gson.fromJson(body, responseType);

            if (responses != null && !responses.isEmpty()) {
                return responses.get(0).getGeneratedText();
            }

            return "";

        } catch (Exception e) {
            System.err.println("Error parsing API response");
            System.err.println(e);
            System.err.println(body);
            return "";
        }
    }

    private List<Tip> processTextToTips(
            String habit,
            String prediction,
            String advice,
            boolean isAi,
            User user
    ) {
        final List<Tip> tips = new ArrayList<>();
        final Tip habitTip = new Tip();
        final Tip predictionTip = new Tip();
        final Tip adviceTip = new Tip();

        habitTip.setTitle("Szokások elemzése:");
        habitTip.setText(habit);
        habitTip.setDate("date");
        habitTip.setAiGenerated(isAi);
        habitTip.setType(TipType.HABIT);
        habitTip.setUser(user);

        predictionTip.setTitle("Pénzügyi előrejelzés:");
        predictionTip.setText(prediction);
        predictionTip.setDate("date");
        predictionTip.setAiGenerated(isAi);
        predictionTip.setType(TipType.PREDICTION);
        predictionTip.setUser(user);

        adviceTip.setTitle("Tanácsok:");
        adviceTip.setText(advice);
        adviceTip.setDate("date");
        adviceTip.setAiGenerated(isAi);
        adviceTip.setType(TipType.ADVICE);
        adviceTip.setUser(user);

        tips.add(habitTip);
        tips.add(predictionTip);
        tips.add(adviceTip);

        return tips;
    }

    private boolean isEndOfMonth() {
        Calendar calendar = Calendar.getInstance();
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return dayOfMonth >= 23;
    }

    //HABIT

    private String generateAIHabit(List<Transaction> transactions) {
        StringBuilder prompt = new StringBuilder("<|system|>Mindig magyarul válaszolj! Maximum 500 karakter hosszú lehet a válaszod! Csak a lényegretörő elemzést add! <|user|>Elemezd a felhasználó pénzügyi szokásait az alábbi tranzakciók alapján:\n");

        for (Transaction transaction : transactions) {
            prompt.append("- ")
                    .append(transaction.getDate())
                    .append(": ")
                    .append(transaction.getCategory().name())
                    .append(", ")
                    .append(transaction.getAmount())
                    .append(" Ft\n");
        }

        prompt.append("\nAdj elemzést, hogy milyen pénzügyi szokásai vannak, és mit érdemes figyelnie!");

        return promptAI(prompt.toString());
    }

    private String generateHabit(SummaryDto summary, Preferences preferences) {
        StringBuilder tip = new StringBuilder("");

        if (summary.getIncome() < preferences.getTargetIncome()) {
            if (isEndOfMonth()) {
                tip.append("- A bevételek elmaradtak a célodtól. Gondold át a plusz jövedelemforrásokat!\n");
            } else {
                tip.append("- A bevételek egyelőre elmaradnak a célodtól, de még van idő javítani! Próbálj több jövedelemre szert tenni!");
            }
        } else {
            tip.append("- Gratulálunk! Bevételi céljaidat teljesítetted.\n  ")
                .append("Cél: ")
                .append(preferences.getTargetIncome())
                .append(" Ft, Elért: ")
                .append(preferences.getTargetIncome())
                .append(" Ft.\n");
        }

        if (summary.getExpense() > preferences.getTargetExpense()) {
            if (isEndOfMonth()) {
                tip.append("- A kiadásaid magasabbak voltak a kitűzöttnél. Érdemes átnézni, hol tudsz spórolni.\n");
            } else {
                tip.append("- A kiadásaid jelenleg magasabbak a tervezettnél, de hó végéig még javíthatsz a profitszinteden, ha további bevételre teszel szert");
            }

        } else {
            tip.append("- Gratulálunk! A kiadásaid sikerült kontroll alatt tartani.\n")
                .append("Limit: ")
                .append(preferences.getTargetExpense())
                .append(" Ft, Elért: ")
                .append(summary.getExpense())
                .append(" Ft.\n");
        }

        if (summary.getProfit() < preferences.getTargetProfit()) {
            if (isEndOfMonth()) {
                tip.append("- A profitszinted kisebb a kívántnál. Próbálj meg több pénzt félretenni vagy tervezz új stratégiát!\n");
            } else {
                tip.append("- A profitszinted jelenleg kisebb a kitűzött célnál, de még javíthatsz rajtuk a hónap végéig.\n");
            }

        } else {
            tip.append("- Gratulálunk! Elérted az általad kitűzött profitcélt!\n")
                .append("Cél: ")
                .append(preferences.getTargetProfit())
                .append(" Ft, Elért: ")
                .append(summary.getProfit())
                .append(" Ft.\n");
        }

        return tip.toString();
    }

    // PREDICTION

    private String generateAIPrediction(List<Transaction> transactions) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("<|system|>Mindig magyarul válaszolj! Maximum 500 karakter hosszú lehet a válaszod! Csak a lényegretörő elemzést add! <|user|>Az alábbi tranzakciók alapján jósolj a következő hónap pénzügyi alakulására:\n");

        for (Transaction transaction : transactions) {
            prompt.append("- ")
                    .append(transaction.getDate())
                    .append(": ")
                    .append(transaction.getCategory().name())
                    .append(", ")
                    .append(transaction.getAmount())
                    .append(" Ft\n");
        }

        prompt.append("\nAdj egy rövid előrejelzést a bevételek és kiadások alakulásáról!");

        return promptAI(prompt.toString());
    }

    private String generatePrediction(SummaryDto summary, Preferences preferences) {
        StringBuilder prediction = new StringBuilder("");

        if (summary.getIncome() >= preferences.getTargetIncome()) {
            prediction.append("- Várhatóan továbbra is elérheted a kitűzött bevételi céljaidat.\n");
        } else {
            if (isEndOfMonth()) {
                prediction.append("- Ha nem változtatsz, a következő hónapban is elmaradhatnak a bevételeid.\n");
            } else {
                prediction.append("- Ha nem változtatsz, a hónap végére elmaradhatnak a bevételeid.\n");
            }
        }

        if (summary.getExpense() > preferences.getTargetExpense()) {
            prediction.append("- Ezzel a stratégiával a továbbiakban is magasak maradnak a költéseid, ami veszélyezteti a megtakarításokat.\n");
        } else {
            if (isEndOfMonth()) {
                prediction.append("- A kiadások valószínűleg kezelhetők maradnak ezzel a stratégiával a jövőben is.\n");
            } else {
                prediction.append("- Ezzel a stratégiával a költéseid valószínűleg kontroll alatt maradnak.\n");
            }
        }

        return prediction.toString();
    }

    // ADVICE

    private String generateAIAdvice(List<Transaction> transactions) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("<|system|>Mindig magyarul válaszolj! Maximum 500 karakter hosszú lehet a válaszod! Csak a lényegretörő elemzést add! <|user|>Az alábbi tranzakciók alapján adj pénzügyi tanácsokat a felhasználónak:\n");

        for (Transaction transaction : transactions) {
            prompt.append("- ")
                    .append(transaction.getDate())
                    .append(": ")
                    .append(transaction.getCategory().name())
                    .append(", ")
                    .append(transaction.getAmount())
                    .append(" Ft\n");
        }

        prompt.append("\nFókuszálj arra, hogy hogyan tudná javítani a pénzügyi helyzetét!");

        return promptAI(prompt.toString());
    }

    private String generateAdvice(SummaryDto summary, Preferences preferences) {
        StringBuilder advice = new StringBuilder("");
        int success = 0;

        if (summary.getExpense() > preferences.getTargetExpense()) {
            if (isEndOfMonth()) {
                advice.append("- Próbáld jobban nyomonkövetni a kiadásaid, majd csökkenteni azokat.\nEzt könnyedén megteheted a Tranzakciók oldalon.");
            } else{
                advice.append("- A kiadásaid magasak ebben a hónapban! Próbálj több bevételre szert tenni, hogy a kívánt profiszinted elérhesd!\n");
            }
        } else {
            success++;
        }

        if (summary.getIncome() < preferences.getTargetIncome()) {
            if (isEndOfMonth()) {
                advice.append("- Vizsgáld meg, hogy tudnál-e extra bevételi forrásokat találni a jövőben!\n");
            } else {
                advice.append("- Még van idő a hónapban, hogy elérhesd a kitűzött jövedelemszintet! Csak így tovább!\n");
            }

        } else {
            success++;
        }

        if (summary.getProfit() >= preferences.getTargetProfit()) {
            if (isEndOfMonth()) {
                advice.append("- Javasolt egy hosszú távú megtakarítási cél (pl. vésztartalék) kitűzése, hogy a profitjaid tovább nőhessenek.\n");
            } else {
                advice.append("- Jól haladsz! Jelenlegi stratégiádat próbáld alkalmazni a hó végéig is!\n");
            }
        } else {
            if (isEndOfMonth()) {
                advice.append("- Próbálj meg új stratégiát alkalmazni, amellyel nagyobb profitot érhetsz el!\n");
            } else {
                advice.append("- Még elérheted a kívánt profitszinted, ha a kiadásaid alacsonyan tartod vagy több jövedelmet szerzel!\n");
            }
        }

        if (success == 2) {
            if (isEndOfMonth()) {
                advice.append("Mindent jól csináltál ebben a hónapban!");
            } else {
                advice.append("Eddig mindent jól csináltál a hónapban, csak így tovább!");
            }
        }

        return advice.toString();
    }
}
