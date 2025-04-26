package hu.blaura.budgey.modules.preferences.repository;

import hu.blaura.budgey.modules.preferences.model.Preferences;
import hu.blaura.budgey.modules.transaction.model.Transaction;
import hu.blaura.budgey.modules.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PreferencesRepository extends JpaRepository<Preferences, Long> {
    Preferences findByUser(User user);
}
