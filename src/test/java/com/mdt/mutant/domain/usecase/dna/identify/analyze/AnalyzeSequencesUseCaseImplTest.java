package com.mdt.mutant.domain.usecase.dna.identify.analyze;

import com.mdt.mutant.domain.model.dna.Dna;
import com.mdt.mutant.domain.usescase.dna.identify.analyze.AnalyzeSequencesUseCaseImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class AnalyzeSequencesUseCaseImplTest {

  private AnalyzeSequencesUseCaseImpl analyzeSequencesUseCase;

  @BeforeEach
  void init() {
    analyzeSequencesUseCase = new AnalyzeSequencesUseCaseImpl();
  }

  @Test
  void shouldGetCorrectMutantDna() {
    String[] arrayDna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
    List<String> dnaRows = Arrays.asList(arrayDna);

    Dna dnaRequest = Dna.builder()
        .sequencesRow(dnaRows)
        .build();

    StepVerifier.create(analyzeSequencesUseCase.validate(dnaRequest))
        .assertNext((x) -> Assertions.assertEquals(true, x.getIsMutant()))
        .verifyComplete();
  }

  @Test
  void shouldGetFalseMutantDna() {
    String[] arrayDna = {"TTGCGA", "CAGTGC", "TTATGT", "AGAAGG", "ACCCTA", "TCACTG"};
    List<String> dnaRows = Arrays.asList(arrayDna);

    Dna dnaRequest = Dna.builder()
        .sequencesRow(dnaRows)
        .build();

    StepVerifier.create(analyzeSequencesUseCase.validate(dnaRequest))
        .assertNext((x) -> Assertions.assertEquals(false, x.getIsMutant()))
        .verifyComplete();
  }

}
