import java.io.*;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

/**
 * 以下の原稿にあるクラスファイルを作成するためのクラス．
 * ASMを使ってバイトコードを直接生成している．
 * <ul>
 *   <li>Heewan Park, Hyun-il Lim, Seokwoo Choi, Taisook Han, ``Detecting Common Modules in Java Packages Based on Static Object Trace Birthmark,'' The Computer Journal (Advance Access), Nov 5 2009, doi:10.1093/comjnl/bxp095</li>
 * </ul>
 */
public class MyServerBuilder{
    public MyServerBuilder(){
    }

    public void write(OutputStream out) throws Exception{
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

        writer.visit(50, Opcodes.ACC_PUBLIC, "MyServer", null, "java/lang/Object", null);
        FieldVisitor fv1 = writer.visitField(Opcodes.ACC_PRIVATE, "Port", "I", null, 9999);
        fv1.visitEnd();
        FieldVisitor fv2 = writer.visitField(Opcodes.ACC_PRIVATE, "Sock", "Ljava/net/ServerSocket;", null, null);
        fv2.visitEnd();
        MethodVisitor mv = writer.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "(I)V", null, null);

        Label label1 = new Label();
        Label label2 = new Label();
        Label label3 = new Label();
        Label startTry = new Label();
        Label catchLabel = new Label();
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitIntInsn(Opcodes.SIPUSH, 9999);
        mv.visitFieldInsn(Opcodes.PUTFIELD, "MyServer", "Port", "I");
        mv.visitLabel(startTry);
        mv.visitVarInsn(Opcodes.ILOAD, 1);
        mv.visitJumpInsn(Opcodes.IFLE, label1);
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitVarInsn(Opcodes.ILOAD, 1);
        mv.visitFieldInsn(Opcodes.PUTFIELD, "MyServer", "Port", "I");
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitTypeInsn(Opcodes.NEW, "java/net/ServerSocket");
        mv.visitInsn(Opcodes.DUP);
        mv.visitVarInsn(Opcodes.ILOAD, 1);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/net/ServerSocket", "<init>", "(I)V");
        mv.visitFieldInsn(Opcodes.PUTFIELD, "MyServer", "Sock", "Ljava/net/ServerSocket;");
        mv.visitJumpInsn(Opcodes.GOTO, label2);
        mv.visitLabel(label1);
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitTypeInsn(Opcodes.NEW, "java/net/ServerSocket");
        mv.visitInsn(Opcodes.DUP);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/net/ServerSocket", "<init>", "()V");
        mv.visitFieldInsn(Opcodes.PUTFIELD, "MyServer", "Sock", "Ljava/net/ServerSocket;");
        mv.visitLabel(label2);
        mv.visitJumpInsn(Opcodes.GOTO, label3);
        mv.visitLabel(catchLabel);
        mv.visitVarInsn(Opcodes.ASTORE, 2);
        mv.visitLabel(label3);
        mv.visitInsn(Opcodes.RETURN);
        mv.visitTryCatchBlock(startTry, label2, catchLabel, "java/io/IOException");
        mv.visitEnd();
        writer.visitEnd();

        out.write(writer.toByteArray());
    }

    public static void main(String[] args) throws Exception{
        MyServerBuilder builder = new MyServerBuilder();

        OutputStream out = new FileOutputStream(args[0]);
        builder.write(out);
        out.close();
    }
}