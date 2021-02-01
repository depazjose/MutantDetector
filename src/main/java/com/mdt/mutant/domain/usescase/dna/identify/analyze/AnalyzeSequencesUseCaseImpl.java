package com.mdt.mutant.domain.usescase.dna.identify.analyze;

import com.mdt.mutant.domain.model.dna.Dna;
import com.mdt.mutant.domain.shared.Direction;
import java.util.Arrays;
import java.util.List;
import reactor.core.publisher.Mono;

public class AnalyzeSequencesUseCaseImpl implements AnalyzeSequencesUseCase {

  private static final int MAX_EQUALS_LETTER = 4;
  private static final int MIN_SEQUENCES_FOUND_TO_BE_MUTANT = 2;

  @Override
  public Mono<Dna> validate(Dna dna) {
    return
        sequencesFound(dna.getSequencesRow())
            .map(count -> Dna.builder()
                .isMutant(count >= MIN_SEQUENCES_FOUND_TO_BE_MUTANT)
                .sequencesCount(count)
                .sequencesRow(dna.getSequencesRow())
                .build());
  }

  private Mono<Integer> sequencesFound(List<String> dnaRows) {

    int columns = dnaRows.stream().map(String::length).reduce(0, Integer::max);
    int rows = dnaRows.size();
    int sequencesFound = 0;

    char[][] matrix = new char[rows][columns];
    boolean[][] matrixVisited = new boolean[rows][columns];

    for (int x = 0; x < rows; x++) {
      char[] line = dnaRows.get(x).toUpperCase().toCharArray();
      matrix[x] = Arrays.copyOf(line, columns);
    }

    for (int row = 0; row < rows; row++) {

      for (int col = 0; col < columns; col++) {

        if (!matrixVisited[row][col]) {
          if ((rows - row) >= MAX_EQUALS_LETTER) {
            sequencesFound += findSequence(matrix[row][col], 1, Direction.LEFT_OBLIQUE, matrix, row, col,
                matrixVisited);

            sequencesFound += findSequence(matrix[row][col], 1, Direction.RIGHT_OBLIQUE, matrix, row, col,
                matrixVisited);

            sequencesFound += findSequence(matrix[row][col], 1, Direction.VERTICAL, matrix, row, col,
                matrixVisited);
          }
          sequencesFound += findSequence(matrix[row][col], 1, Direction.HORIZONTAL, matrix, row, col,
              matrixVisited);
        }
      }
    }
    return Mono.just(sequencesFound);
  }

  private Integer findSequence(char letter, int accumulateLetterCount, Direction direction,
                               char[][] matrix, int row, int col, boolean[][] matrixVisited) {
    int nextRow = row;
    int nextCol = col;
    int sequenceFound = 0;

    nextRow++;

    switch (direction) {
      case LEFT_OBLIQUE:
        nextCol--;
        if ((nextCol < 0) || (nextRow >= matrix.length)) {
          return sequenceFound;
        }
        break;
      case RIGHT_OBLIQUE:
        nextCol++;
        if ((nextCol >= matrix.length) || (nextRow >= matrix.length)) {
          return sequenceFound;
        }
        break;
      case HORIZONTAL:
        nextRow--;
        nextCol++;
        if (nextCol >= matrix.length) {
          return sequenceFound;
        }
        break;
      case VERTICAL:
        if (nextRow >= matrix.length) {
          return sequenceFound;
        }
        break;
      default:
        return sequenceFound;
    }

    if (matrix[nextRow][nextCol] == letter) {
      accumulateLetterCount++;
      matrixVisited[nextRow][nextCol] = true;
      if (MAX_EQUALS_LETTER == accumulateLetterCount) {
        sequenceFound = 1;
      } else {
        sequenceFound = findSequence(letter, accumulateLetterCount, direction, matrix, nextRow, nextCol, matrixVisited);
      }
    }
    return sequenceFound;
  }
}
