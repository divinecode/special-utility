package com.divinecode.specialutility.struct;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Set;

public class SpecialClass {
    private final @NotNull File file;
    private final @NotNull Set<String> specified;
    private boolean mustBeTransformed;

    public SpecialClass(@NotNull final File file, @NotNull Set<String> specified) {
        this.file = file;
        this.specified = specified;
        this.mustBeTransformed = isSpecifiedContentPath();
    }

    public @NotNull File getFile() {
        return file;
    }

    public @NotNull File getOutFile() {
        return file;
    }

    public boolean isSpecifiedContentPath() {
        if (this.specified.isEmpty()) return true;

        for (String specify : this.specified)
            if (file.getAbsolutePath().contains(specify)) return true;

        return false;
    }

    public boolean isMustBeTransformed() {
        return mustBeTransformed;
    }

    public void setMustBeTransformed(boolean mustBeTransformed) {
        this.mustBeTransformed = mustBeTransformed;
    }

}
