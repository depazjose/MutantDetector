package com.mdt.mutant.infrastructure.adapters.database.dna;

import static org.mockito.ArgumentMatchers.any;

import com.mdt.mutant.domain.model.dna.Dna;
import java.util.Arrays;
import java.util.List;

import com.mdt.mutant.domain.model.dna.DnaStats;
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
class DnaRepositoryAdapterTest {

  @Mock
  private DnaDataRepository dnaDataRepository;

  @InjectMocks
  private DnaRepositoryAdapter dnaRepositoryAdapter;

  @Test
  void shouldSaveDnaData() {

    String[] arrayDna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
    List<String> dnaRows = Arrays.asList(arrayDna);

    Dna dna = Dna.builder()
        .isMutant(Boolean.TRUE)
        .sequencesCount(3)
        .sequencesRow(dnaRows)
        .build();

    DnaData dnaData = new DnaData();
    dnaData.setId(1L);
    dnaData.setDna(Dna.toJsonString(dna.getSequencesRow()));
    dnaData.setIsMutant(dna.getIsMutant());
    dnaData.setSequences(dna.getSequencesCount());

    Mockito.when(dnaDataRepository.save(any())).thenReturn(dnaData);

    Mono<Dna> result = dnaRepositoryAdapter.saveDna(dna);

    StepVerifier.create(result).assertNext(Assertions::assertNotNull).verifyComplete();
  }

  @Test
  void shouldFindAndRetrieveDnaStats() {

    MutantsCount mutantsCount = new MutantsCount() {
      @Override
      public Long getTotal() {
        return 140L;
      }

      @Override
      public Long getTotalMutants() {
        return 40L;
      }
    };

    Mockito.when(dnaDataRepository.countTotalAndMutants()).thenReturn(mutantsCount);

    Mono<DnaStats> dnaStatsMono = dnaRepositoryAdapter.findMutantsAndHumansCounter();

    StepVerifier.create(dnaStatsMono)
        .assertNext(x-> Assertions.assertEquals(140L, x.getTotal()))
        .verifyComplete();
  }

}
