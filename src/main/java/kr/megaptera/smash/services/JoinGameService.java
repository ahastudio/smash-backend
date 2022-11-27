package kr.megaptera.smash.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.megaptera.smash.dtos.RegisterGameResultDto;
import kr.megaptera.smash.exceptions.GameNotFound;
import kr.megaptera.smash.exceptions.UserNotFound;
import kr.megaptera.smash.models.Game;
import kr.megaptera.smash.models.Register;
import kr.megaptera.smash.models.User;
import kr.megaptera.smash.repositories.GameRepository;
import kr.megaptera.smash.repositories.RegisterRepository;
import kr.megaptera.smash.repositories.UserRepository;

@Service
@Transactional
public class JoinGameService {
    private final GameRepository gameRepository;
    private final RegisterRepository registerRepository;
    private final UserRepository userRepository;

    public JoinGameService(GameRepository gameRepository,
                           RegisterRepository registerRepository,
                           UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.registerRepository = registerRepository;
        this.userRepository = userRepository;
    }

    public RegisterGameResultDto joinGame(Long gameId,
                                          Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(UserNotFound::new);

        Game game = gameRepository.findById(gameId)
                .orElseThrow(GameNotFound::new);

        List<Register> registers = registerRepository.findByGameId(gameId);

        Register register = game.join(currentUser, registers);

        registerRepository.save(register);

        return new RegisterGameResultDto(gameId);
    }
}