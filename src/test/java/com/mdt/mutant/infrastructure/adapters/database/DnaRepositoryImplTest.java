package com.mdt.mutant.infrastructure.adapters.database;

import static org.mockito.ArgumentMatchers.any;

import com.mdt.mutant.domain.model.dna.Dna;
import com.mdt.mutant.domain.model.dna.DnaStats;
import com.mdt.mutant.infrastructure.adapters.database.dna.DnaRepositoryAdapter;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class DnaRepositoryImplTest {

  @Mock
  private DnaRepositoryAdapter dnaRepositoryAdapter;

  @InjectMocks
  private DnaRepositoryImpl dnaRepositoryImpl;

  @Test
  void shouldSaveDnaWithSuccess() {
    String[] arrayDna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
    List<String> dnaRows = Arrays.asList(arrayDna);

    Dna dna = Dna.builder()
        .isMutant(Boolean.TRUE)
        .sequencesCount(3)
        .sequencesRow(dnaRows)
        .build();

    Dna dnaSaved = Dna.builder()
        .id(1L)
        .isMutant(Boolean.TRUE)
        .sequencesCount(3)
        .sequencesRow(dnaRows)
        .build();

    Mockito.when(dnaRepositoryAdapter.saveDna(any())).thenReturn(Mono.just(dnaSaved));

    Mono<Dna> result = dnaRepositoryImpl.saveDna(dna);

    StepVerifier.create(result)
        .assertNext(x-> Assertions.assertEquals(1L, x.getId()))
        .verifyComplete();
  }

  @Test
  void shouldRetrieveStats() {

    DnaStats dnaStats = DnaStats.builder()
        .total(1L)
        .mutants(1L)
        .humans(0L)
        .ratio(0.0d)
        .build();

    Mockito.when(dnaRepositoryAdapter.findMutantsAndHumansCounter()).thenReturn(Mono.just(dnaStats));

    Mono<DnaStats> dnaStatsMono = dnaRepositoryImpl.findMutantsCounter();

    StepVerifier.create(dnaStatsMono)
        .assertNext(x-> Assertions.assertEquals(1L, x.getTotal()))
        .verifyComplete();
  }

}
