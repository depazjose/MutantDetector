package com.mdt.mutant.domain.usescase.dna.identify.analyze;

import com.mdt.mutant.domain.model.dna.Dna;
import reactor.core.publisher.Mono;

public interface AnalyzeSequencesUseCase {

  Mono<Dna> validate(Dna dna);
}
