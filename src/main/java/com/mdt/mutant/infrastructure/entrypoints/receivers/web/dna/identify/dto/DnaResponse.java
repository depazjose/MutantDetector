package com.mdt.mutant.infrastructure.entrypoints.receivers.web.dna.identify.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdt.mutant.domain.model.dna.Dna;
import java.util.List;
import com.mdt.mutant.domain.model.dna.DnaStats;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DnaResponse {
  @Builder
  @Getter
  @ToString
  @JsonInclude(JsonInclude.Include.NON_NULL)
  class DnaDetailResponse {
    private Boolean isMutant;

    public static Mono<DnaDetailResponse> fromModel(Mono<Boolean> isMutant) {
      return isMutant.map(response -> DnaDetailResponse.builder().isMutant(response).build());
    }
  }

  @Builder
  @Getter
  @ToString
  @JsonInclude(JsonInclude.Include.NON_NULL)
  class DnaDetailsResponse {
    private String dna;
    private Integer sequences;
    private Boolean isMutant;

    private static DnaDetailsResponse fromModel(Dna dna) {
      return DnaDetailsResponse.builder()
          .dna(fromModel(dna.getSequencesRow()))
          .sequences(dna.getSequencesCount())
          .isMutant(dna.getIsMutant())
          .build();
    }

    public static Flux<DnaDetailsResponse> fromModel(Flux<Dna> dnaFlux) {
      return dnaFlux.map(DnaDetailsResponse::fromModel);
    }

    private static String fromModel(List<String> list) {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
      try {
        return objectMapper.writeValueAsString(list);
      } catch (JsonProcessingException e) {
        return "";
      }
    }
  }

  @Builder
  @Getter
  @ToString
  @JsonInclude(JsonInclude.Include.NON_NULL)
  class DnaStatsDetailResponse {
    private Long countMutantDna;
    private Long countHumanDna;
    private Double ratio;

    public static Mono<DnaStatsDetailResponse> fromModel(Mono<DnaStats> dnaStats) {
      return dnaStats.map(result ->
          DnaStatsDetailResponse.builder()
          .countMutantDna(result.getMutants())
          .countHumanDna(result.getHumans())
          .ratio(result.getRatio())
          .build());
    }
  }
}
