package com.mdt.mutant.infrastructure.entrypoints.receivers.web.dna.identify;

import static org.mockito.ArgumentMatchers.any;

import com.mdt.mutant.domain.shared.BadSequenceException;
import com.mdt.mutant.domain.shared.ProcessDnaException;
import com.mdt.mutant.domain.usescase.dna.identify.DnaUseCase;
import com.mdt.mutant.infrastructure.entrypoints.receivers.web.dna.identify.dto.DnaRequest;
import com.mdt.mutant.infrastructure.entrypoints.receivers.web.dna.identify.dto.DnaResponse;
import java.util.Arrays;
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
class DnaControllerTest {

  @Mock
  private DnaUseCase dnaUseCase;

  @InjectMocks
  private DnaController dnaController;

  @Test
  void shouldVerifyMutantDnaWithSuccess() {

    String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};

    DnaRequest.CreateDnaSequencesRequest request = new DnaRequest.CreateDnaSequencesRequest();
    request.setDna(Arrays.asList(dna));

    Mockito.when(dnaUseCase.processDna(any())).thenReturn(Mono.just(Boolean.TRUE));

    Mono<DnaResponse.DnaDetailResponse> result = dnaController.isMutant(request).getBody();

    StepVerifier.create(result).assertNext((x) -> Assertions.assertEquals(true, x.getIsMutant())).verifyComplete();
  }

  @Test
  void shouldVerifyMutantDnaWithException() {

    String[] dna = {"TTGCGA", "CAGTGC", "TTATGT", "AGAAGG", "ACCCTA", "TCACTG"};

    DnaRequest.CreateDnaSequencesRequest request = new DnaRequest.CreateDnaSequencesRequest();
    request.setDna(Arrays.asList(dna));

    Mockito.when(dnaUseCase.processDna(any())).thenReturn(Mono.just(Boolean.FALSE));

    Mono<DnaResponse.DnaDetailResponse> result = dnaController.isMutant(request).getBody();

    StepVerifier.create(result).expectError(ProcessDnaException.class).verify();
  }

  @Test
  void shouldVerifyMutantDnaWithBadSequenceException() {

    String[] dna = {"TTGCG", "CAGTGC", "TTATGT", "AGAAG", "ACCCTA", "TCACTG"};

    DnaRequest.CreateDnaSequencesRequest request = new DnaRequest.CreateDnaSequencesRequest();
    request.setDna(Arrays.asList(dna));

    Assertions.assertThrows(BadSequenceException.class, () -> dnaController.isMutant(request));
  }

}
