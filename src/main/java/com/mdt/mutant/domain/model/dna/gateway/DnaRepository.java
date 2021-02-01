package com.mdt.mutant.domain.model.dna.gateway;

import com.mdt.mutant.domain.model.dna.Dna;
import com.mdt.mutant.domain.model.dna.DnaStats;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DnaRepository {

  Mono<Dna> saveDna(Dna dna);

  Flux<Dna> findAll();

  Mono<DnaStats> findMutantsCounter();
}
