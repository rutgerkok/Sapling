package nl.rutgerkok.sapling;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import nl.rutgerkok.hammer.anvil.AnvilWorld;
import nl.rutgerkok.hammer.pocket.PocketWorld;

import com.google.common.base.Joiner;

/**
 * The startup class.
 *
 */
public final class Startup {

    public static void main(String[] args) {
        String path = Joiner.on(' ').join(args);

        Path levelDat = Paths.get(path);
        init(new SystemErrErrorLog(), levelDat);
        System.out.println("Done.");
    }

    private static void init(ErrorLog errorLog, Path levelDat) {
        Path worldDir = levelDat.getParent();
        Path worldDirAnvil = worldDir.getParent().resolve(worldDir.getFileName() + "_anvil");
        Path levelDatAnvil = worldDirAnvil.resolve(AnvilWorld.LEVEL_DAT_NAME);
        GlobalMaterialMap dictionary = new GlobalMaterialMap();
        try {
            PocketWorld pocketWorld = new PocketWorld(dictionary, levelDat);
            AnvilWorld anvilWorld = new AnvilWorld(dictionary, levelDatAnvil);
            ConvertProcess convertProcess = new ConvertProcess(errorLog);
            convertProcess.convert(pocketWorld, anvilWorld);
        } catch (IOException e) {
            errorLog.log("Error opening world", e);
        }
    }
}
