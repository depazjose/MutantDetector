package com.mdt.mutant.domain.usescase.dna.stats;

import com.mdt.mutant.domain.model.dna.DnaStats;
import reactor.core.publisher.Mono;

public interface StatsUseCase {
  Mono<DnaStats> findMutantsStats();
}
