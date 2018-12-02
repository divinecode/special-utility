package com.divinecode.specialutility;


import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class AccessAdapter extends ClassVisitor {

    public AccessAdapter(@NotNull final ClassVisitor visitor) {
        super(ASM4, visitor);
        this.cv = visitor;
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        return super.visitField(toPublic(access), name, descriptor, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        return super.visitMethod(toPublic(access), name, descriptor, signature, exceptions);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, toPublic(access), name, signature, superName, interfaces);
    }

    private int toPublic(int access) {
        return (((access | ACC_PRIVATE | ACC_PROTECTED) ^ ACC_PRIVATE) ^ ACC_PROTECTED) | ACC_PUBLIC;
    }

}
