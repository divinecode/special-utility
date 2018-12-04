package com.divinecode.specialutility.struct;

import com.divinecode.specialutility.FileUtils;
import com.divinecode.specialutility.SpecialUtility;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class SpecialData {
    private final  @NotNull SpecialUtility utility;
    private final @NotNull File jarFile;
    private SpecialStruct struct;
    private File unzippedFolder;

    public SpecialData(@NotNull final SpecialUtility utility, @NotNull final File jarFile) {
        this.utility = utility;
        this.jarFile = jarFile;
    }

    public void setup() throws IOException {
        System.out.println("Unzipping archive...");
        this.unzippedFolder = Files.createTempDirectory("SpecialUtility-").toFile();
        FileUtils.unzip(jarFile, unzippedFolder);

        System.out.println("Generates inner file system...");
        this.struct = new SpecialStruct(utility, unzippedFolder);
    }

    public void removeUnzipped() throws IOException {
        Files.walk(unzippedFolder.toPath())
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    public void pack(@NotNull final File output) {
        System.out.println("Packing to jar file...");
        FileUtils.packToJar(unzippedFolder, output);
    }

    public @NotNull File getJarFile() {
        return jarFile;
    }

    public @NotNull SpecialStruct getStruct() {
        return struct;
    }

    public File getUnzippedFolder() {
        return unzippedFolder;
    }

}
