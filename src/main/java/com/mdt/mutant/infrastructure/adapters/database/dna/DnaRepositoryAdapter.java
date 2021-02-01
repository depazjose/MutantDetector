package com.mdt.mutant.infrastructure.adapters.database.dna;

import static reactor.core.publisher.Mono.fromSupplier;

import com.mdt.mutant.domain.model.dna.Dna;
import java.util.function.Supplier;

import com.mdt.mutant.domain.model.dna.DnaStats;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Repository
public class DnaRepositoryAdapter {

  private final DnaDataRepository dnaDataRepository;

  public DnaRepositoryAdapter(DnaDataRepository dnaDataRepository) {
    this.dnaDataRepository = dnaDataRepository;
  }

  public Mono<Dna> saveDna(Dna dna) {
    DnaData dnaData = new DnaData();
    dnaData.setId(0L);
    dnaData.setDna(Dna.toJsonString(dna.getSequencesRow()));
    dnaData.setSequences(dna.getSequencesCount());
    dnaData.setIsMutant(dna.getIsMutant());
    return saveData(dnaData);
  }

  public Flux<Dna> findAll() {
   return doQueryMany(dnaDataRepository::findAll);
  }

  public Mono<DnaStats> findMutantsAndHumansCounter() {
    return fromSupplier(dnaDataRepository::countTotalAndMutants)
        .subscribeOn(Schedulers.elastic())
        .map(results -> DnaStats.builder()
            .mutants(results.getTotalMutants())
            .total(results.getTotal())
            .build());
  }

  private Flux<Dna> doQueryMany(Supplier<Iterable<DnaData>> query) {
    return fromSupplier(query)
        .subscribeOn(Schedulers.elastic())
        .flatMapMany(Flux::fromIterable)
        .map(this::toEntity);
  }

  private Mono<Dna> saveData(DnaData data) {
    return fromSupplier(() -> dnaDataRepository.save(data))
        .subscribeOn(Schedulers.elastic())
        .map(this::toEntity);
  }

  private Dna toEntity(DnaData data) {
    return Dna.fromModel(data);
  }

}
