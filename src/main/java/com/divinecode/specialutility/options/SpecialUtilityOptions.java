package com.divinecode.specialutility.options;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

/**
 * Command-line options
 */
public class SpecialUtilityOptions extends OptionsBase implements Cloneable {

    @Option(
            name = "input",
            abbrev = 'i',
            help = "Path to input jar.",
            category = "general",
            defaultValue = ""
    )
    public String input;

    @Option(
            name = "output",
            abbrev = 'o',
            help = "Path to output jar.",
            category = "general",
            defaultValue = ""
    )
    public String output;

    @Option(
            name = "specified",
            abbrev = 's',
            help = "Package that should be transformed.",
            category = "general",
            defaultValue = ""
    )
    public String specified;

    @Option(
            name = "gui",
            abbrev = 'g',
            help = "Open gui on fail or not.",
            category = "general",
            defaultValue = "false"
    )
    public boolean noGui;

    @Option(
            name = "finals",
            abbrev = 'f',
            help = "Remove finals from methods, classes and variables.",
            category = "general",
            defaultValue = "false"
    )
    public boolean removeFinals;


}
