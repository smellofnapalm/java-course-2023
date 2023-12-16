package edu.hw11.task3;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

public enum FibImpl implements ByteCodeAppender {

    INSTANCE;

    @Override
    public ByteCodeAppender.Size apply(MethodVisitor mv, Implementation.Context context, MethodDescription md) {
        final Label fibSumLabel = new Label();
        final int maxStackSize = 10;
        final int maxLocals = 3;
        mv.visitCode();
        // Проверка, если n >= 2, то прыгаем на fibSumLabel
        mv.visitVarInsn(Opcodes.ILOAD, 1);
        mv.visitInsn(Opcodes.ICONST_2);
        mv.visitJumpInsn(Opcodes.IF_ICMPGE, fibSumLabel);

        // Иначе возвращаем само число n
        mv.visitVarInsn(Opcodes.ILOAD, 1);
        mv.visitInsn(Opcodes.I2L);
        mv.visitInsn(Opcodes.LRETURN);

        // После goto необходимо обновить frame
        mv.visitLabel(fibSumLabel);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

        // Считаем fib(n-1), fib(n-2)
        calcFibMinus(mv, md, Opcodes.ICONST_1);
        calcFibMinus(mv, md, Opcodes.ICONST_2);

        // Складываем лежащие на стеке результаты
        mv.visitInsn(Opcodes.LADD);
        mv.visitInsn(Opcodes.LRETURN);
        mv.visitMaxs(maxStackSize, maxLocals);
        mv.visitEnd();

        return new ByteCodeAppender.Size(maxStackSize, maxLocals);
    }

    private void calcFibMinus(MethodVisitor mv, MethodDescription md, int minusCode) {
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitVarInsn(Opcodes.ILOAD, 1);
        mv.visitInsn(minusCode);
        mv.visitInsn(Opcodes.ISUB);
        mv.visitMethodInsn(
            Opcodes.INVOKEVIRTUAL,
            md.getDeclaringType().getTypeName(),
            md.getName(),
            md.getDescriptor(),
            false
        );
    }
}
