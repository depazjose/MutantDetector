package com.mdt.mutant.infrastructure.adapters.database.dna;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = DnaData.class, idClass = Long.class)
public interface DnaDataRepository extends JpaSpecificationExecutor<DnaData> {

  DnaData save(DnaData dnaData);

  List<DnaData> findAll();

  @Query(value = "SELECT COUNT(*) as total, SUM(is_mutant=true) as totalMutants FROM dna_data", nativeQuery = true)
  MutantsCount countTotalAndMutants();
}
