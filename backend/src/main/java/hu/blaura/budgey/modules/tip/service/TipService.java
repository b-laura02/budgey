package hu.blaura.budgey.modules.tip.service;

import hu.blaura.budgey.modules.tip.model.Tip;
import hu.blaura.budgey.modules.tip.model.dto.TipDto;
import hu.blaura.budgey.modules.tip.repository.TipRepository;
import hu.blaura.budgey.modules.transaction.model.Transaction;
import hu.blaura.budgey.modules.transaction.model.dto.TransactionDto;
import hu.blaura.budgey.modules.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipService {
    private final TipRepository tipRepository;

    public Tip create(TipDto tipDto) {
        Tip tip = new Tip();
        tip.setTitle(tipDto.getTitle());
        tip.setText(tipDto.getText());
        tip.setDate(tipDto.getDate());
        tip.setAiGenerated(tipDto.isAiGenerated());
        tip.setType(tipDto.getType());
        return tipRepository.save(tip);
    }

    public List<Tip> getAll() {
        return tipRepository.findAll();
    }

    public Tip getById(Long id) {
        return tipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tip not found with id: " + id));
    }

    public Tip update(Long id, TipDto tipDto) {
        Tip tip = getById(id);
        tip.setTitle(tipDto.getTitle());
        tip.setText(tipDto.getText());
        tip.setDate(tipDto.getDate());
        tip.setAiGenerated(tipDto.isAiGenerated());
        tip.setType(tipDto.getType());
        return tipRepository.save(tip);
    }

    public void delete(Long id) {
        Tip tip = getById(id);
        tipRepository.delete(tip);
    }
}
