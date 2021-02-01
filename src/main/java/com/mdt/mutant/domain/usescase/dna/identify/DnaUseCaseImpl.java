package com.mdt.mutant.domain.usescase.dna.identify;

import com.mdt.mutant.domain.model.dna.Dna;
import com.mdt.mutant.domain.model.dna.DnaStats;
import com.mdt.mutant.domain.model.dna.gateway.DnaRepository;
import com.mdt.mutant.domain.usescase.dna.identify.analyze.AnalyzeSequencesUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RequiredArgsConstructor
public class DnaUseCaseImpl implements DnaUseCase {

  private final DnaRepository dnaRepository;
  private final AnalyzeSequencesUseCase analyzeSequencesUseCase;

  @Override
  public Mono<Boolean> processDna(Dna dna) {
    return analyzeSequencesUseCase.validate(dna)
        .flatMap(dnaRepository::saveDna)
        .map(Dna::getIsMutant);
  }

  @Override
  public Flux<Dna> findAll() {
    return dnaRepository.findAll();
  }

}
