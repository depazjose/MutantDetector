package com.mdt.mutant.domain.shared;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ProcessDnaException extends RuntimeException {

  public ProcessDnaException() {
    super(String.format("Not found MUTANT sequences."));
  }
}
