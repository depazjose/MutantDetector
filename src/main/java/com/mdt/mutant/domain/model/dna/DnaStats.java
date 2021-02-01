package com.mdt.mutant.domain.model.dna;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class DnaStats {
  private Long total;
  private Long mutants;
  private Long humans;
  private Double ratio;
}
