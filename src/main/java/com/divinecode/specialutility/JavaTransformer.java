package com.divinecode.specialutility;

import com.divinecode.specialutility.struct.SpecialClass;
import com.divinecode.specialutility.struct.SpecialStruct;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Collection;

public class JavaTransformer {
    private final @NotNull SpecialUtility utility;
    private final @NotNull ClassWorker worker;

    public JavaTransformer(@NotNull final SpecialUtility utility) {
        this.utility = utility;
        this.worker = new ClassWorker();
    }

    public void transformAndSave(@NotNull final SpecialStruct target) throws IOException {

        if (target.getClasses() != null)
            for (SpecialClass clazz : target.getClasses())
                transformAndSave(clazz);

        if (target.getStructs() != null)
            for (SpecialStruct struct : target.getStructs())
                transformAndSave(struct);
    }

    public void transformAndSave(@NotNull final Collection<SpecialClass> targets) throws IOException {
        for (SpecialClass target : targets)
            transformAndSave(target);
    }

    public void transformAndSave(@NotNull final SpecialClass target) throws IOException {
        final byte[] bytes = transform(target);
        if (bytes == null) return;

        try {
            FileUtils.writeByteArrayToFile(target.getOutFile(), bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public @Nullable byte[] transform(@NotNull final SpecialClass target) throws IOException {
        if (!target.isMustBeTransformed())
            return null;

        worker.accept(target);

        System.out.println("Transforming " + target.getFile().getName());

        final AccessAdapter adapter = new AccessAdapter(worker.writer, target.isRemoveFinals());
        worker.reader.accept(adapter, 0);

        return worker.writer.toByteArray();
    }

    public @NotNull ClassWorker getWorker() {
        return worker;
    }

}
