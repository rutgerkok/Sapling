package nl.rutgerkok.sapling;

import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import nl.rutgerkok.hammer.anvil.AnvilWorld;
import nl.rutgerkok.hammer.material.GlobalMaterialMap;
import nl.rutgerkok.hammer.pocket.PocketWorld;
import nl.rutgerkok.sapling.gui.Window;

import com.google.common.base.Joiner;

/**
 * The startup class.
 *
 */
public final class Startup {

    public static final String APPLICATION_NAME = "Sapling";
    public static final String APPLICATION_VERSION = "0.0.1-SNAPSHOT";

    public static void main(String[] args) {
        if (args.length == 0 && !GraphicsEnvironment.isHeadless()) {
            new Window();
            return;
        }
        if (args.length == 0 || args[0].equals("--help") || args[0].equals("help")) {
            System.out.println("Run as java -jar Sapling.jar path/to/pocket/world/level.dat");
            return;
        }
        String path = Joiner.on(' ').join(args);

        Path levelDat = Paths.get(path);
        init(new SystemErrErrorLog(), new SystemOutProgressUpdater(), levelDat);
        System.out.println("Done.");
    }

    public static void init(ErrorLog errorLog, ProgressUpdater progressUpdater, Path levelDat) {
        Path worldDir = levelDat.getParent();
        Path worldDirAnvil = worldDir.getParent().resolve(worldDir.getFileName() + "_anvil");
        Path levelDatAnvil = worldDirAnvil.resolve(AnvilWorld.LEVEL_DAT_NAME);
        GlobalMaterialMap dictionary = new GlobalMaterialMap();
        try {
            PocketWorld pocketWorld = new PocketWorld(dictionary, levelDat);
            AnvilWorld anvilWorld = new AnvilWorld(dictionary, levelDatAnvil);
            ConvertProcess convertProcess = new ConvertProcess(errorLog, progressUpdater);
            convertProcess.convert(pocketWorld, anvilWorld);
        } catch (IOException e) {
            errorLog.log("Error opening world", e);
        }
    }
}
