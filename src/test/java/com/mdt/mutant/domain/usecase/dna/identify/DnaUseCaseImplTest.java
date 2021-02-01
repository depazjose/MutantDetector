package com.mdt.mutant.domain.usecase.dna.identify;

import static org.mockito.ArgumentMatchers.any;

import com.mdt.mutant.domain.model.dna.Dna;
import com.mdt.mutant.domain.model.dna.DnaStats;
import com.mdt.mutant.domain.model.dna.gateway.DnaRepository;
import com.mdt.mutant.domain.usescase.dna.identify.DnaUseCaseImpl;
import com.mdt.mutant.domain.usescase.dna.identify.analyze.AnalyzeSequencesUseCase;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class DnaUseCaseImplTest {

  @Mock
  private DnaRepository dnaRepository;

  @Mock
  private AnalyzeSequencesUseCase analyzeSequencesUseCase;

  @InjectMocks
  private DnaUseCaseImpl dnaUseCaseImpl;

  @Test
  void shouldProcessDnaWithSuccess() {
    String[] arrayDna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
    List<String> dnaRows = Arrays.asList(arrayDna);

    Dna dnaResult = Dna.builder()
        .id(1L)
        .isMutant(Boolean.TRUE)
        .sequencesCount(3)
        .sequencesRow(dnaRows)
        .build();

    Dna dnaRequest = Dna.builder()
        .sequencesRow(dnaRows)
        .build();

    Mockito.when(dnaRepository.saveDna(any())).thenReturn(Mono.just(dnaResult));

    Mockito.when(analyzeSequencesUseCase.validate(any())).thenReturn(Mono.just(dnaResult));

    Mono<Boolean> result = dnaUseCaseImpl.processDna(dnaRequest);

    StepVerifier.create(result).assertNext((x) -> Assertions.assertEquals(true, x)).verifyComplete();
  }
}
