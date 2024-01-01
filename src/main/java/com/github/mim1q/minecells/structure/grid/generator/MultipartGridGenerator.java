package com.github.mim1q.minecells.structure.grid.generator;

import com.github.mim1q.minecells.structure.grid.GridPiecesGenerator;
import com.github.mim1q.minecells.structure.grid.GridPiecesGenerator.RoomGridGenerator;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.structure.Structure;

import java.util.List;

import static com.github.mim1q.minecells.util.MathUtils.getClosestMultiplePosition;

public abstract class MultipartGridGenerator extends RoomGridGenerator {
  private final int xPart;
  private final int zPart;

  public MultipartGridGenerator(int xPart, int zPart) {
    this.xPart = xPart;
    this.zPart = zPart;
  }

  @Override
  public List<GridPiecesGenerator.RoomData> generate(Structure.Context context) {
    var seed = getClosestMultiplePosition(context.chunkPos().getStartPos(), 1024).hashCode() + context.seed();
    context.random().setSeed(seed);
    return super.generate(context);
  }

  @Override
  protected void addRoom(Vec3i pos, BlockRotation rotation, Identifier poolId, Vec3i offset, boolean terrainFit) {
    int x = pos.getX() - 16 * (xPart / 16) - 8;
    int z = pos.getZ() - 16 * (zPart / 16) - 8;
    if (x < -8 || z < -8 || x >= 8 || z >= 8) {
      return;
    }
    super.addRoom(new Vec3i(x, pos.getY(), z), rotation, poolId, offset, terrainFit);
  }
}
