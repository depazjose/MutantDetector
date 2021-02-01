package com.mdt.mutant.domain.usecase.dna.stats;

import com.mdt.mutant.domain.model.dna.DnaStats;
import com.mdt.mutant.domain.model.dna.gateway.DnaRepository;
import com.mdt.mutant.domain.usescase.dna.stats.StatsUseCaseImpl;
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
class StatsUseCaseImplTest {

  @Mock
  private DnaRepository dnaRepository;

  @InjectMocks
  private StatsUseCaseImpl statsUseCase;

  @Test
  void shouldRetrieveStats() {

    DnaStats dnaStats = DnaStats.builder()
        .total(1L)
        .mutants(1L)
        .humans(0L)
        .ratio(0.0d)
        .build();

    Mockito.when(dnaRepository.findMutantsCounter()).thenReturn(Mono.just(dnaStats));

    Mono<DnaStats> dnaStatsMono = statsUseCase.findMutantsStats();

    StepVerifier.create(dnaStatsMono)
        .assertNext((x) -> Assertions.assertEquals(1L, x.getTotal()))
        .verifyComplete();
  }
}
