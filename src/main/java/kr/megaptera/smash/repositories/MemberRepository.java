package kr.megaptera.smash.repositories;

import kr.megaptera.smash.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
   List<Member> findAllByTeamId(Long teamId);
   List<Member> findAllByRoleId(Long roleId);
}
