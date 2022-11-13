package kr.megaptera.smash.services;

import kr.megaptera.smash.dtos.RegisterGameResultDto;
import kr.megaptera.smash.models.Exercise;
import kr.megaptera.smash.models.Game;
import kr.megaptera.smash.models.GameDate;
import kr.megaptera.smash.models.GameTargetMemberCount;
import kr.megaptera.smash.models.Member;
import kr.megaptera.smash.models.MemberName;
import kr.megaptera.smash.models.Place;
import kr.megaptera.smash.models.User;
import kr.megaptera.smash.models.UserGender;
import kr.megaptera.smash.models.UserName;
import kr.megaptera.smash.repositories.GameRepository;
import kr.megaptera.smash.repositories.MemberRepository;
import kr.megaptera.smash.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PostRegisterGameServiceTest {
    private PostRegisterGameService postRegisterGameService;

    private GameRepository gameRepository;
    private MemberRepository memberRepository;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        gameRepository = mock(GameRepository.class);
        memberRepository = mock(MemberRepository.class);
        userRepository = mock(UserRepository.class);

        postRegisterGameService = new PostRegisterGameService(
            gameRepository,
            memberRepository,
            userRepository
        );
    }

    @Test
    void registerGame() {
        Long gameId = 1L;
        Game game = new Game(
            gameId,
            1L,
            new Exercise("운동 종류"),
            new GameDate("운동 날짜"),
            new Place("운동 장소"),
            new GameTargetMemberCount(5)
        );
        given(gameRepository.findById(gameId)).willReturn(Optional.of(game));

        List<Member> members = List.of(
            new Member(1L, 2L, gameId, new MemberName("참가자 1")),
            new Member(2L, 3L, gameId, new MemberName("참가자 2"))
        );
        given(memberRepository.findByGameId(gameId)).willReturn(members);

        Long userId = 1L;
        User user = new User(userId, new UserName("사용자명"), new UserGender("남성"));
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        Member registeredMember = new Member(3L, userId, gameId, new MemberName(user.name().value()));
        given(memberRepository.save(any(Member.class))).willReturn(registeredMember);

        RegisterGameResultDto registerGameResultDto
            = postRegisterGameService.registerGame(gameId, userId);

        assertThat(registerGameResultDto).isNotNull();
        assertThat(registerGameResultDto.getGameId()).isEqualTo(gameId);

        verify(gameRepository).findById(gameId);
        verify(memberRepository).findByGameId(gameId);
        verify(memberRepository).save(any(Member.class));
    }
}
