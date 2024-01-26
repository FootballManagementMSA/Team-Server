package sejong.team.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sejong.team.repository.SquadRepository;

@Service
@RequiredArgsConstructor
public class SquadService {
    private final SquadRepository squadRepository;
}
