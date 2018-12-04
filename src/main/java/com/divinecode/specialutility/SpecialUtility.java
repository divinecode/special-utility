package com.divinecode.specialutility;

import com.divinecode.specialutility.struct.SpecialData;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class SpecialUtility {
    private final @NotNull JavaTransformer transformer = new JavaTransformer(this);
    private final boolean removeFinals;
    private final @NotNull SpecialData data;
    private final @NotNull Set<String[]> specified;

    public SpecialUtility(@NotNull final String input, @NotNull final String output,
                          @NotNull final Set<String[]> specified, final boolean removeFinals) throws IOException {
        this.removeFinals = removeFinals;
        this.specified = specified;

        final File inputFile = new File(input);
        final File outputFile = new File(output);

        if (!inputFile.exists()) {
            System.out.println("File '" + inputFile.getAbsolutePath() + "' doesn't exist!");
            System.exit(1);
        }

        System.out.println("Forming data copy...");
        data = new SpecialData(this, inputFile);
        data.setup();

        transformer.transformAndSave(data.getStruct());

        data.pack(outputFile);
        data.removeUnzipped();

    }

    public @NotNull JavaTransformer getTransformer() {
        return transformer;
    }

    public @NotNull SpecialData getData() {
        return data;
    }

    public boolean isRemoveFinals() {
        return removeFinals;
    }

    public @NotNull Set<String[]> getSpecified() {
        return specified;
    }

    public static @NotNull Set<String[]> parsePackages(@NotNull final String packages) {
        if (packages.trim().isEmpty())
            return new HashSet<>();

        final Set<String[]> specified = new HashSet<>();

        Arrays.stream(packages.split(";"))
                .forEach(pack -> specified.add(pack.split(Pattern.quote(String.valueOf(File.separatorChar)))));

        return specified;
    }

}
