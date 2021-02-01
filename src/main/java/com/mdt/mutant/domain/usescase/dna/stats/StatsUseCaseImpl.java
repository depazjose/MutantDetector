package com.mdt.mutant.domain.usescase.dna.stats;

import com.mdt.mutant.domain.model.dna.DnaStats;
import com.mdt.mutant.domain.model.dna.gateway.DnaRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class StatsUseCaseImpl implements StatsUseCase {

  private final DnaRepository dnaRepository;

  @Override
  public Mono<DnaStats> findMutantsStats() {
    return dnaRepository.findMutantsCounter()
        .map(stats -> DnaStats.builder()
            .total(stats.getTotal())
            .mutants(Objects.isNull(stats.getMutants()) ? 0: stats.getMutants())
            .humans(calculateHumans(stats.getTotal(), stats.getMutants()))
            .ratio(calculateRatio(stats.getTotal(), stats.getMutants()))
            .build());
  }

  private Double calculateRatio(Long total, Long mutants) {
    if (Objects.isNull(total) || total == 0) {
      return 0.0d;
    } else {
      Long humans = total - mutants;
      return humans == 0 ? 1.0: (double) mutants / humans;
    }
  }

  private Long calculateHumans(Long total, Long mutants) {
    if (Objects.isNull(total) || total == 0) {
      return 0L;
    } else {
      return total - mutants;
    }
  }
}
