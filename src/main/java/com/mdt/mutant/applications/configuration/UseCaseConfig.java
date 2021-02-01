package com.mdt.mutant.applications.configuration;

import com.mdt.mutant.domain.model.dna.gateway.DnaRepository;
import com.mdt.mutant.domain.usescase.dna.identify.DnaUseCaseImpl;
import com.mdt.mutant.domain.usescase.dna.identify.analyze.AnalyzeSequencesUseCase;
import com.mdt.mutant.domain.usescase.dna.identify.analyze.AnalyzeSequencesUseCaseImpl;
import com.mdt.mutant.domain.usescase.dna.stats.StatsUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

  @Bean
  public AnalyzeSequencesUseCaseImpl analyzeDnaUseCaseImpl() {
    return new AnalyzeSequencesUseCaseImpl();
  }

  @Bean
  public DnaUseCaseImpl dnaUseCaseImpl(final DnaRepository dnaRepository,
                                       final AnalyzeSequencesUseCase analyzeSequencesUseCase) {
    return new DnaUseCaseImpl(dnaRepository, analyzeSequencesUseCase);
  }

  @Bean
  public StatsUseCaseImpl statsUseCaseImpl(final DnaRepository dnaRepository) {
    return new StatsUseCaseImpl(dnaRepository);
  }

}
