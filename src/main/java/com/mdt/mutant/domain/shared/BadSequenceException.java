package com.mdt.mutant.domain.shared;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadSequenceException extends RuntimeException {

  public BadSequenceException(String sequences) {
    super(String.format("Sequences not accepted: %s", sequences));
  }
}
