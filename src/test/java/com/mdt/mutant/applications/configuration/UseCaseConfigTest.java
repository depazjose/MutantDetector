package com.mdt.mutant.applications.configuration;

import com.mdt.mutant.domain.model.dna.gateway.DnaRepository;
import com.mdt.mutant.domain.usescase.dna.identify.DnaUseCaseImpl;
import com.mdt.mutant.domain.usescase.dna.identify.analyze.AnalyzeSequencesUseCase;
import com.mdt.mutant.domain.usescase.dna.stats.StatsUseCaseImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UseCaseConfigTest {

  private final UseCaseConfig configuration = new UseCaseConfig();

  @Mock
  private DnaRepository dnaRepository;

  @Mock
  private AnalyzeSequencesUseCase analyzeSequencesUseCase;

  @Test
  void shouldCreateDnaUseCase() {
    final DnaUseCaseImpl dnaUseCase = configuration.dnaUseCaseImpl(
        dnaRepository, analyzeSequencesUseCase);
    Assertions.assertNotNull(dnaUseCase);
  }

  @Test
  void shouldCreateStatsUseCase() {
    final StatsUseCaseImpl statsUseCase = configuration.statsUseCaseImpl(dnaRepository);
    Assertions.assertNotNull(statsUseCase);
  }
}
