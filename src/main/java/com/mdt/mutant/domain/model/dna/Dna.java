package com.mdt.mutant.domain.model.dna;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdt.mutant.infrastructure.adapters.database.dna.DnaData;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class Dna {

  private static final Logger LOGGER = Logger.getLogger(Dna.class.getName());
  private static final String ERROR_CONVERT_FROM_JSON = "Can´t convert JSON to object";
  private static final String ERROR_CONVERT_TO_JSON = "Can´t convert object to JSON";

  private Long id;
  private List<String> sequencesRow;
  private Integer sequencesCount;
  private Boolean isMutant;

  public static Dna fromModel(DnaData dnaData) {
    return Dna.builder()
        .id(dnaData.getId())
        .sequencesRow(fromJsonString(dnaData.getDna()))
        .sequencesCount(dnaData.getSequences())
        .isMutant(dnaData.getIsMutant())
        .build();
  }

  public static List<String> fromJsonString(String json) {
    try {
      return new ObjectMapper().readValue(json, new TypeReference<>() { });
    } catch (JsonProcessingException e) {
      LOGGER.severe(ERROR_CONVERT_FROM_JSON);
    }
    return new ArrayList<>();
  }

  public static String toJsonString(List<String> list) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.writeValueAsString(list);
    } catch (JsonProcessingException e) {
      LOGGER.severe(ERROR_CONVERT_TO_JSON);
    }
    return "";
  }

}
