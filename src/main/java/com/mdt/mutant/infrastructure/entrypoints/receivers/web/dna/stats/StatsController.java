package com.mdt.mutant.infrastructure.entrypoints.receivers.web.dna.stats;

import com.mdt.mutant.domain.usescase.dna.stats.StatsUseCase;
import com.mdt.mutant.infrastructure.entrypoints.receivers.web.dna.stats.dto.StatsResponse.StatsDetailResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/mutants", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatsController {

  private final StatsUseCase statsUseCase;

  public StatsController(StatsUseCase statsUseCase) {
    this.statsUseCase = statsUseCase;
  }

  @GetMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Get current stats")
  public ResponseEntity<Mono<StatsDetailResponse>> getStats() {
    return new ResponseEntity<>(StatsDetailResponse.fromModel(statsUseCase.findMutantsStats()), HttpStatus.OK);
  }
}
