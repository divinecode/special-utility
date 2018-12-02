package com.divinecode.specialutility;

import com.divinecode.specialutility.struct.SpecialClass;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.FileInputStream;
import java.io.IOException;

public class ClassWorker {

    public ClassReader reader;
    public ClassWriter writer;

    public void accept(@NotNull final SpecialClass specialClass) throws IOException {
        reader = new ClassReader(new FileInputStream(specialClass.getFile()));
        writer = new ClassWriter(reader, 0);
    }

}
