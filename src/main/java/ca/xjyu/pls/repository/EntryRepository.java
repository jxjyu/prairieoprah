package ca.xjyu.pls.repository;

import ca.xjyu.pls.model.Entry;
import ca.xjyu.pls.model.Passcode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, Long> {
    long countByPasscode(Passcode passcode);
    boolean existsByEmailAndPasscode(String email, Passcode passcode);
}
