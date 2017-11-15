package hello.repository;

import hello.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    // Hibernate automates the following SQL query:
    User findByEmail(String email);
}