package com.divinecode.specialutility.options;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class OptionsValidator {

    public void validate(@NotNull final SpecialUtilityOptions options) throws IllegalArgumentException {
        if (options.input.isEmpty())
            throw new IllegalArgumentException("Input jar path can't be empty!");

        if (options.output.isEmpty())
            throw new IllegalArgumentException("Output jar path can't be empty!");

        if (!options.output.endsWith(".jar")) {
            final String[] split = options.input.split(File.separator);
            final String name;

            if(split.length < 2) name = "SpecialUtility - Output.jar";
            else name = split[split.length - 1];

            options.output += File.separator + name;
        }

    }



}
