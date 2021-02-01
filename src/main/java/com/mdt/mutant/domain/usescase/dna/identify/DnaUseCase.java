package com.mdt.mutant.domain.usescase.dna.identify;

import com.mdt.mutant.domain.model.dna.Dna;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DnaUseCase {

  Mono<Boolean> processDna(Dna dna);

  Flux<Dna> findAll();
}
