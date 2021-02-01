package com.mdt.mutant.infrastructure.entrypoints.receivers.web.dna.stats.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mdt.mutant.domain.model.dna.DnaStats;
import com.mdt.mutant.infrastructure.entrypoints.receivers.web.dna.identify.dto.DnaResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import reactor.core.publisher.Mono;

public interface StatsResponse {

  @Builder
  @Getter
  @ToString
  @JsonInclude(JsonInclude.Include.NON_NULL)
  class StatsDetailResponse {
    private Long countMutantDna;
    private Long countHumanDna;
    private Double ratio;

    public static Mono<StatsDetailResponse> fromModel(Mono<DnaStats> dnaStats) {
      return dnaStats.map(result ->
          StatsDetailResponse.builder()
              .countMutantDna(result.getMutants())
              .countHumanDna(result.getHumans())
              .ratio(result.getRatio())
              .build());
    }
  }
}
