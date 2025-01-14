package kr.megaptera.smash.services;

import kr.megaptera.smash.dtos.ApplicantDetailDto;
import kr.megaptera.smash.dtos.ApplicantsDetailDto;
import kr.megaptera.smash.exceptions.UserNotFound;
import kr.megaptera.smash.models.Register;
import kr.megaptera.smash.models.RegisterStatus;
import kr.megaptera.smash.models.User;
import kr.megaptera.smash.repositories.RegisterRepository;
import kr.megaptera.smash.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GetProcessingRegisterService {
    private final RegisterRepository registerRepository;
    private final UserRepository userRepository;

    public GetProcessingRegisterService(RegisterRepository registerRepository,
                                        UserRepository userRepository) {
        this.registerRepository = registerRepository;
        this.userRepository = userRepository;
    }

    public ApplicantsDetailDto findApplicants(Long targetGameId) {
        List<Register> registers = registerRepository.findAllByGameId(targetGameId);

        List<ApplicantDetailDto> applicantDetailDtos = registers.stream()
            .filter(register -> register.status().value()
                .equals(RegisterStatus.PROCESSING))
            .map(register -> {
                User user = userRepository.findById(register.userId())
                    .orElseThrow(UserNotFound::new);
                return new ApplicantDetailDto(
                    register.id(),
                    user.name().value(),
                    user.gender().value(),
                    user.phoneNumber().value()
                );
            })
            .toList();

        return new ApplicantsDetailDto(applicantDetailDtos);
    }
}
