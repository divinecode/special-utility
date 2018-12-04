package com.divinecode.specialutility.struct;

import com.divinecode.specialutility.SpecialUtility;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Set;
import java.util.regex.Pattern;

public class SpecialClass {
    private final @NotNull SpecialUtility utility;
    private final @NotNull File file;
    private final @NotNull String[] pack;
    private boolean mustBeTransformed;
    private boolean removeFinals;

    public SpecialClass(@NotNull final SpecialUtility utility, @NotNull final File file) {
        this.utility = utility;
        this.file = file;
        this.pack = file.getAbsolutePath()
                .substring(utility.getData().getUnzippedFolder().getAbsolutePath().length() + 1)
                .split(Pattern.quote(File.separator));
        this.removeFinals = utility.isRemoveFinals() && !file.getName().contains("$");

        System.out.println(file.getName() + " - " + isSpecifiedContentPath());
        this.mustBeTransformed = isSpecifiedContentPath();
    }

    public @NotNull File getFile() {
        return file;
    }

    public @NotNull File getOutFile() {
        return file;
    }

    public boolean isSpecifiedContentPath() {
        Set<String[]> specified = utility.getSpecified();
        if (specified.isEmpty()) return true;

        for (String[] specify : specified)
            if (startsWith(pack, specify)) return true;

        return false;
    }

    private boolean startsWith(@NotNull final String[] target, @NotNull final String[] with) {
        if (target.length < with.length) return false;

        for (int i = 0; i < with.length; i++)
            if (!target[i].equals(with[i])) return false;

        return true;
    }

    public boolean isMustBeTransformed() {
        return mustBeTransformed;
    }

    public void setMustBeTransformed(boolean mustBeTransformed) {
        this.mustBeTransformed = mustBeTransformed;
    }

    public boolean isRemoveFinals() {
        return removeFinals;
    }

    public void setRemoveFinals(boolean removeFinals) {
        this.removeFinals = removeFinals;
    }

}
