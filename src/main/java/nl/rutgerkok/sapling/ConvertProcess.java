package nl.rutgerkok.sapling;

import java.io.IOException;
import java.util.Objects;

import nl.rutgerkok.hammer.ChunkAccess;
import nl.rutgerkok.hammer.anvil.AnvilChunk;
import nl.rutgerkok.hammer.anvil.AnvilWorld;
import nl.rutgerkok.hammer.anvil.tag.AnvilFormat.LevelTag;
import nl.rutgerkok.hammer.pocket.PocketChunk;
import nl.rutgerkok.hammer.pocket.PocketWorld;
import nl.rutgerkok.hammer.tag.CompoundTag;
import nl.rutgerkok.hammer.util.Progress;
import nl.rutgerkok.hammer.util.Result;
import nl.rutgerkok.hammer.util.Visitor;

/**
 * Executes the whole convert process.
 */
final class ConvertProcess {

    private final ErrorLog errorLog;

    /**
     * Executes the converting process.
     * 
     * @param source
     *            Source world.
     * @param destination
     *            Destination world, must be empty.
     */
    void convert(PocketWorld source, AnvilWorld destination) {
        convertLevelDat(source, destination);
        convertChunks(source, destination);
    }

    private void convertLevelDat(PocketWorld source, AnvilWorld destination) {
        CompoundTag levelTag = destination.getLevelTag();
        levelTag.clear();
        levelTag.addAll(source.getLevelTag());

        levelTag.setInt(LevelTag.VERSION, 19133); // Mark as Anvil map
        // PE stores time as number of seconds since epoch, PC as milliseconds
        levelTag.setLong(LevelTag.LAST_PLAYED, levelTag.getLong(LevelTag.LAST_PLAYED) * 1000);

        try {
            destination.saveLevelTag();
        } catch (IOException e) {
            errorLog.log("Conversion of level.dat failed", e);
        }
    }

    private void convertChunks(PocketWorld source, AnvilWorld destination) {
        try (final ChunkAccess<AnvilChunk> chunkDestination = destination.getChunkAccess()) {
            final ChunkConverter chunkConverter = new ChunkConverter(errorLog);

            source.walkPocketChunks(new Visitor<PocketChunk>() {

                @Override
                public Result accept(PocketChunk pocketChunk, Progress progress) {
                    int chunkX = pocketChunk.getChunkX();
                    int chunkZ = pocketChunk.getChunkZ();
                    try {
                        AnvilChunk anvilChunk = chunkDestination.getChunk(chunkX, chunkZ);
                        chunkConverter.convertChunk(pocketChunk, anvilChunk);
                        chunkDestination.saveChunk(anvilChunk);
                    } catch (IOException e) {
                        errorLog.log("Failed to convert chunk (" + chunkX + "," + chunkZ + ")", e);
                    }
                    return Result.NO_CHANGES;
                }
            });
        } catch (IOException e) {
            errorLog.log("Chunk conversion failed", e);
        }
    }

    public ConvertProcess(ErrorLog errorLog) {
        this.errorLog = Objects.requireNonNull(errorLog, "errorLog");
    }
}
