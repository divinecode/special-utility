package com.divinecode.specialutility;

import com.divinecode.specialutility.struct.SpecialData;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class SpecialUtility {
    private final @NotNull JavaTransformer transformer = new JavaTransformer();
    private SpecialData data;

    public SpecialUtility(@NotNull final String input, @NotNull final String output, @NotNull final Set<String> specified) {
        try {
            final File inputFile = new File(input);
            final File outputFile = new File(output);

            if (!inputFile.exists()) {
                System.out.println("File '" + inputFile.getAbsolutePath() + "' doesn't exist!");
                System.exit(1);
            }

            System.out.println("Forming data copy...");
            data = new SpecialData(inputFile, specified);
            data.setup();

            transformer.transformAndSave(data.getStruct());

            data.pack(outputFile);
            data.removeUnzipped();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
