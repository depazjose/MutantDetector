package com.mdt.mutant.infrastructure.entrypoints.receivers.web.dna.stats;

import com.mdt.mutant.domain.model.dna.DnaStats;
import com.mdt.mutant.domain.usescase.dna.stats.StatsUseCase;
import com.mdt.mutant.infrastructure.entrypoints.receivers.web.dna.stats.dto.StatsResponse;
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
class StatsControllerTest {

  @Mock
  private StatsUseCase statsUseCase;

  @InjectMocks
  private StatsController statsController;

  @Test
  void shouldRetrieveAllStatsWithSuccess() {

    DnaStats dnaStats = DnaStats.builder()
        .ratio(0.4d)
        .humans(100L)
        .mutants(40L)
        .build();

    Mockito.when(statsUseCase.findMutantsStats()).thenReturn(Mono.just(dnaStats));

    Mono<StatsResponse.StatsDetailResponse> result = statsController.getStats().getBody();

    StepVerifier.create(result).assertNext((x) -> Assertions.assertNotNull(x.getRatio())).verifyComplete();
  }
}
