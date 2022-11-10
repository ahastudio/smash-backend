package kr.megaptera.smash.repositories;

import kr.megaptera.smash.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByPostId(Long postId);
}
