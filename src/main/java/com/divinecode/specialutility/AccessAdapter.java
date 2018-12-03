package com.divinecode.specialutility;


import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class AccessAdapter extends ClassVisitor {
    private final boolean removeFinals;

    public AccessAdapter(@NotNull final ClassVisitor visitor, final boolean removeFinals) {
        super(ASM4, visitor);
        this.removeFinals = removeFinals;
        this.cv = visitor;
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        return super.visitField(accept(access), name, descriptor, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        return super.visitMethod(accept(access), name, descriptor, signature, exceptions);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, accept(access), name, signature, superName, interfaces);
    }

    private int accept(int code) {
        code = (((code | ACC_PRIVATE | ACC_PROTECTED) ^ ACC_PRIVATE) ^ ACC_PROTECTED) | ACC_PUBLIC;

        if (removeFinals && ((code >> (4-1)) & 1) != 1)
            return (code | ACC_FINAL) ^ ACC_FINAL;

        return code;
    }

}
