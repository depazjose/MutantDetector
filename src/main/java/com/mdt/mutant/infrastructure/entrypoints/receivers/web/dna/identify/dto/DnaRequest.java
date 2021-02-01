package com.mdt.mutant.infrastructure.entrypoints.receivers.web.dna.identify.dto;

import com.mdt.mutant.domain.model.dna.Dna;
import com.mdt.mutant.domain.shared.BadSequenceException;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

public interface DnaRequest {

  String LETTER_ACCEPTED_REGEX = "[ATCG]+";

  @Getter
  @Setter
  class CreateDnaSequencesRequest {
    private List<String> dna;

    public static Dna toModel(CreateDnaSequencesRequest request) {

      ValidateCharacters(request.getDna());

      return Dna.builder()
            .sequencesRow(request.getDna())
            .build();
    }

    private static void ValidateCharacters(List<String> sequences) {
      String result = sequences.stream()
          .filter(sequence ->
                  (!sequence.toUpperCase().matches(LETTER_ACCEPTED_REGEX))
                  ||
                  (sequence.length() != sequences.size()))
          .collect(Collectors.joining(", "));

      if (!result.isEmpty()) {
        throw new BadSequenceException(result);
      }
    }

  }
}
