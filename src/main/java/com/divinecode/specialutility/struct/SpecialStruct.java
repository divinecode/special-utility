package com.divinecode.specialutility.struct;

import com.divinecode.specialutility.SpecialUtility;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class SpecialStruct {

    private final @NotNull File file;
    private @Nullable Set<SpecialClass> classes;
    private @Nullable Set<SpecialStruct> structs;
    private @NotNull SpecialUtility utility;

    public SpecialStruct(@NotNull final SpecialUtility utility, @NotNull final File file) {
        System.out.println("Forming " + file.getAbsolutePath());

        this.utility = utility;
        this.file = file;

        this.classes = loadClasses();
        this.structs = loadStructs();
    }

    private @Nullable Set<SpecialClass> loadClasses() {
        final Set<SpecialClass> classes = new HashSet<>();
        File[] files = file.listFiles();
        if (files == null) return null;

        for (File content : files) {
            if (!content.getName().endsWith(".class")) continue;
            classes.add(new SpecialClass(utility, content));
        }

        if (classes.isEmpty()) return null;
        return classes;
    }

    private @Nullable Set<SpecialStruct> loadStructs() {
        final File[] files = file.listFiles();
        if (files == null || files.length == 0) return null;

        final Set<SpecialStruct> classes = new HashSet<>();

        for (File file : files) {
            final SpecialStruct struct = parseStruct(file);
            if (struct == null) continue;
            classes.add(struct);
        }

        return classes;
    }

    private @Nullable SpecialStruct parseStruct(@NotNull final File file) {
        final File[] files = file.listFiles();
        if (files == null || files.length == 0 || !file.isDirectory()) return null;

        if (files.length == 1 && files[0].isDirectory())
            return parseStruct(files[0]);

        return new SpecialStruct(utility, file);
    }

    public @NotNull File getFile() {
        return file;
    }

    public @Nullable Set<SpecialClass> getClasses() {
        return classes;
    }

    public @Nullable Set<SpecialStruct> getStructs() {
        return structs;
    }

    public void addClass(@NotNull final SpecialClass clazz) {
        if (classes == null) classes = new HashSet<>();
        classes.add(clazz);
    }

    public void addStaruct(@NotNull final SpecialStruct struct) {
        if (structs == null) structs = new HashSet<>();
        structs.add(struct);
    }

}
