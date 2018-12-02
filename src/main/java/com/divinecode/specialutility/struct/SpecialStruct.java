package com.divinecode.specialutility.struct;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class SpecialStruct {

    private final @NotNull File file;
    private @Nullable Set<SpecialClass> classes;
    private @Nullable Set<SpecialStruct> structs;
    private @NotNull Set<String> specified;

    public SpecialStruct(@NotNull final File file, @NotNull final Set<String> specified) {
        System.out.println("Forming " + file.getAbsolutePath());

        this.file = file;
        this.specified = specified;

        this.classes = loadClasses();
        this.structs = loadStructs();
    }

    private @Nullable Set<SpecialClass> loadClasses() {
        final Set<SpecialClass> classes = new HashSet<>();

        for (File content : file.listFiles()) {
            if (!content.getName().endsWith(".class")) continue;
            classes.add(new SpecialClass(content, specified));
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

        return new SpecialStruct(file, specified);
    }

    public File getFile() {
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
