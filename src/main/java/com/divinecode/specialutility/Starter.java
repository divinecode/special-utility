package com.divinecode.specialutility;

import com.divinecode.specialutility.gui.ApplicationGui;
import com.divinecode.specialutility.options.OptionsValidator;
import com.divinecode.specialutility.options.SpecialUtilityOptions;
import com.google.devtools.common.options.OptionsParser;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class Starter {

    public static void main(String... args) {
        final OptionsParser parser = OptionsParser.newOptionsParser(SpecialUtilityOptions.class);
        parser.parseAndExitUponError(args);

        final SpecialUtilityOptions options = Objects.requireNonNull(parser.getOptions(SpecialUtilityOptions.class));
        final OptionsValidator validator = new OptionsValidator();

        try {
            validator.validate(options);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            printUsage(parser);

            if (!options.noGui) {
                ApplicationGui.main(args);
                System.out.println(!options.noGui);
            }

            return;
        }

        try {
            final Set<String[]> specified = SpecialUtility.parsePackages(options.specified);
            new SpecialUtility(options.input, options.output, specified, options.removeFinals);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printUsage(@NotNull final OptionsParser parser) {
        System.out.println("Usage: java -jar utility.jar OPTIONS");
        System.out.println(parser.describeOptions(Collections.emptyMap(), OptionsParser.HelpVerbosity.LONG));
    }

}
