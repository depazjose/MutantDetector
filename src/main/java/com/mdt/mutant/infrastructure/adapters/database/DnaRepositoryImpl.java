package com.mdt.mutant.infrastructure.adapters.database;

import com.mdt.mutant.domain.model.dna.Dna;
import com.mdt.mutant.domain.model.dna.DnaStats;
import com.mdt.mutant.domain.model.dna.gateway.DnaRepository;
import com.mdt.mutant.infrastructure.adapters.database.dna.DnaRepositoryAdapter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DnaRepositoryImpl implements DnaRepository {

  private final DnaRepositoryAdapter dnaRepositoryAdapter;

  public DnaRepositoryImpl(DnaRepositoryAdapter dnaRepositoryAdapter) {
    this.dnaRepositoryAdapter = dnaRepositoryAdapter;
  }

  @Override
  public Mono<Dna> saveDna(Dna dna) {
    return dnaRepositoryAdapter.saveDna(dna);
  }

  @Override
  public Flux<Dna> findAll() {
    return dnaRepositoryAdapter.findAll();
  }

  @Override
  public Mono<DnaStats> findMutantsCounter() {
    return dnaRepositoryAdapter.findMutantsAndHumansCounter();
  }
}
