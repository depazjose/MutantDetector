package com.mdt.mutant.infrastructure.adapters.database.dna;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "dna_data")
@Getter
@Setter
public class DnaData {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String dna;
  private Integer sequences;
  private Boolean isMutant;

}
