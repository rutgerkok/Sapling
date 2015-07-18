package nl.rutgerkok.sapling;

import java.util.List;
import java.util.Objects;

import nl.rutgerkok.hammer.anvil.AnvilChunk;
import nl.rutgerkok.hammer.pocket.PocketChunk;
import nl.rutgerkok.hammer.tag.CompoundTag;
import nl.rutgerkok.hammer.util.MaterialNotFoundException;

/**
 * Converts a single chunk.
 *
 */
final class ChunkConverter {

    private final ErrorLog errorLog;

    /**
     * Converts a single chunk.
     * 
     * @param source
     *            Chunk to retrieve data from.
     * @param destination
     *            Chunk to put data in.
     */
    void convertChunk(PocketChunk source, AnvilChunk destination) {
        convertBlocks(source, destination);
        convertEntities(source, destination);
        convertTileEntities(source, destination);
    }

    public ChunkConverter(ErrorLog errorLog) {
        this.errorLog = Objects.requireNonNull(errorLog, "errorLog");
    }

    private void convertBlocks(PocketChunk source, AnvilChunk destination) {
        for (int i = 0; i < source.getSizeX(); i++) {
            for (int j = 0; j < source.getSizeY(); j++) {
                for (int k = 0; k < source.getSizeZ(); k++) {
                    try {
                        destination.setMaterial(i, j, k, source.getMaterial(i, j, k));
                    } catch (MaterialNotFoundException e) {
                        // Uh oh, skip this block
                        errorLog.log("Material not found in chunk (" + source.getChunkX() + "," + source.getChunkZ() + ")", e);
                    }
                }
            }
        }
    }

    private void convertEntities(PocketChunk source, AnvilChunk destination) {
        List<CompoundTag> destinationList = destination.getEntities();
        destinationList.clear();
        destinationList.addAll(source.getEntities());
    }

    private void convertTileEntities(PocketChunk source, AnvilChunk destination) {
        List<CompoundTag> destinationList = destination.getTileEntities();
        destinationList.clear();
        destinationList.addAll(source.getTileEntities());
    }
}
