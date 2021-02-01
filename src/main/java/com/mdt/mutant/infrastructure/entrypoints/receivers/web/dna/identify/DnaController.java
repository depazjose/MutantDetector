package com.mdt.mutant.infrastructure.entrypoints.receivers.web.dna.identify;

import com.mdt.mutant.domain.model.dna.Dna;
import com.mdt.mutant.domain.shared.ProcessDnaException;
import com.mdt.mutant.domain.usescase.dna.identify.DnaUseCase;
import com.mdt.mutant.infrastructure.entrypoints.receivers.web.dna.identify.dto.DnaRequest;
import com.mdt.mutant.infrastructure.entrypoints.receivers.web.dna.identify.dto.DnaResponse;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/mutants", produces = MediaType.APPLICATION_JSON_VALUE)
public class DnaController {

  private final DnaUseCase dnaUseCase;

  public DnaController(DnaUseCase dnaUseCase) {
    this.dnaUseCase = dnaUseCase;
  }

  @PostMapping(value = "/verify", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "verify DNA sequence for discovery MUTANTS")
  public ResponseEntity<Mono<DnaResponse.DnaDetailResponse>> isMutant(
      final @Valid @RequestBody DnaRequest.CreateDnaSequencesRequest createDnaSequencesRequest) {

    Dna dna = DnaRequest.CreateDnaSequencesRequest.toModel(createDnaSequencesRequest);

    Mono<Boolean> isMutant = dnaUseCase.processDna(dna).map(result -> {
      if (!Boolean.TRUE.equals(result)) {
        throw new ProcessDnaException();
      }
      return true;
    });

    return new ResponseEntity<>(DnaResponse.DnaDetailResponse.fromModel(isMutant), HttpStatus.OK);
  }

  @GetMapping(value = "/results", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Get all DNA sequences processed")
  public ResponseEntity<Flux<DnaResponse.DnaDetailsResponse>> getAllMutant() {
    return new ResponseEntity<>(DnaResponse.DnaDetailsResponse.fromModel(dnaUseCase.findAll()), HttpStatus.OK);
  }

}
