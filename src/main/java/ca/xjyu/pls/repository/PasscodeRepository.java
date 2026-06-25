package ca.xjyu.pls.repository;

import ca.xjyu.pls.model.Passcode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PasscodeRepository extends JpaRepository<Passcode, Long> {
    Optional<Passcode> findByPassword(String password);
    List<Passcode> findByExpiryTimeAfter(LocalDateTime time);
    List<Passcode> findByExpiryTimeBefore(LocalDateTime time);
}
