package com.github.mim1q.minecells.structure.grid.generator;

import com.github.mim1q.minecells.MineCells;
import com.github.mim1q.minecells.structure.grid.GridPiecesGenerator;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;

public class PrisonGridGenerator extends GridPiecesGenerator.RoomGridGenerator {
  private static final Identifier SPAWN = MineCells.createId("prison/spawn");
  private static final Identifier MAIN_CORRIDOR = MineCells.createId("prison/main_corridor");
  private static final Identifier MAIN_CORRIDOR_END = MineCells.createId("prison/main_corridor_end");
  private static final Identifier CORRIDOR = MineCells.createId("prison/corridor");
  private static final Identifier CORRIDOR_END = MineCells.createId("prison/corridor_end");
  private static final Identifier CHAIN_UPPER = MineCells.createId("prison/chain_upper");
  private static final Identifier CHAIN_LOWER = MineCells.createId("prison/chain_lower");
  private static final Identifier END = MineCells.createId("prison/end");
  private static final Identifier END_SEWERS = MineCells.createId("prison/end_sewers");

  @Override
  protected void addRooms(Random random) {
    Vec3i end1 = generateFloor(Vec3i.ZERO, BlockRotation.NONE, SPAWN, CHAIN_UPPER, random, random.nextBoolean(), true);
    generateFloor(end1.add(0, -1, 0), BlockRotation.CLOCKWISE_180, CHAIN_LOWER, END, random, random.nextBoolean(), false);
//    generateFloor(end2.add(0, -1, 0), BlockRotation.CLOCKWISE_180, CHAIN_LOWER, END, random, random.nextBoolean(), false);
  }

  protected Vec3i generateFloor(Vec3i pos, BlockRotation rotation, Identifier startPool, Identifier endPool, Random random, boolean specialLeft, boolean sewersExit) {
    addRoom(pos, BlockRotation.NONE.rotate(rotation), startPool);
    Vec3i unit = rotation.rotate(Direction.SOUTH).getVector();
    Vec3i rotatedUnit = rotation.rotate(Direction.EAST).getVector();

    Vec3i endPos = pos.add(0, -1, 0);

    int specialCorridor = random.nextInt(4) + 1;

    for (int i = 1; i < 5; i++) {
      addRoom(pos.add(unit.multiply(i)), BlockRotation.NONE.rotate(rotation), MAIN_CORRIDOR);
      // Left corridors
      int length1 = random.nextInt(2) + 1;
      for (int j = 1; j <= length1; j++) {
        addRoom(pos.add(unit.multiply(i)).add(rotatedUnit.multiply(j)), BlockRotation.COUNTERCLOCKWISE_90.rotate(rotation), CORRIDOR);
      }
      if (i == specialCorridor && specialLeft) {
        addRoom(pos.add(unit.multiply(i)).add(rotatedUnit.multiply(length1 + 1)), BlockRotation.COUNTERCLOCKWISE_90.rotate(rotation), endPool);
        endPos = pos.add(unit.multiply(i)).add(rotatedUnit.multiply(length1 + 1));
      } else {
        addRoom(pos.add(unit.multiply(i)).add(rotatedUnit.multiply(length1 + 1)), BlockRotation.COUNTERCLOCKWISE_90.rotate(rotation), CORRIDOR_END);
      }
      // Right corridors
      int length2 = random.nextInt(2) + 1;
      for (int j = 1; j <= length2; j++) {
        addRoom(pos.add(unit.multiply(i)).add(rotatedUnit.multiply(-j)), BlockRotation.CLOCKWISE_90.rotate(rotation), CORRIDOR);
      }
      if (i == specialCorridor && !specialLeft) {
        addRoom(pos.add(unit.multiply(i)).add(rotatedUnit.multiply(-length2 - 1)), BlockRotation.CLOCKWISE_90.rotate(rotation), endPool);
        endPos = pos.add(unit.multiply(i)).add(rotatedUnit.multiply(-length2 - 1));
      } else {
        addRoom(pos.add(unit.multiply(i)).add(rotatedUnit.multiply(-length2 - 1)), BlockRotation.CLOCKWISE_90.rotate(rotation), CORRIDOR_END);
      }
    }

    addRoom(pos.add(unit.multiply(5)), BlockRotation.NONE.rotate(rotation), sewersExit ? END_SEWERS : MAIN_CORRIDOR_END);

    return endPos;
  }
}
