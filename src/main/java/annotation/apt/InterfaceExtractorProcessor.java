package annotation.apt;


import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.Modifier;
import com.sun.mirror.declaration.ParameterDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class InterfaceExtractorProcessor implements AnnotationProcessor {
    private final AnnotationProcessorEnvironment env;

    /**
     * 接口方法
     */
    private ArrayList<MethodDeclaration> interfaceMethods=new ArrayList<>();

    public InterfaceExtractorProcessor(AnnotationProcessorEnvironment env) {
        this.env = env;
    }

    @Override
    public void process() {
        for (TypeDeclaration typeDecl : env.getSpecifiedTypeDeclarations()){
            ExtractInterface annot = typeDecl.getAnnotation(ExtractInterface.class);
            if (annot == null){
                break;
            }
            for (MethodDeclaration m:typeDecl.getMethods()){
                //找出修饰符是public但是不是static的
                if (m.getModifiers().contains(Modifier.PUBLIC) &&
                        !(m.getModifiers().contains(Modifier.STATIC))){
                    interfaceMethods.add(m);
                }
            }
            if (interfaceMethods.size() > 0){
                try {
                    PrintWriter writer=env.getFiler().createSourceFile(annot.value());
                    writer.println("public interface "+annot.value()+"{");//接口开始
                    for (MethodDeclaration m:interfaceMethods){
                        writer.print(" public ");
                        writer.print(m.getReturnType() + " ");
                        writer.print(m.getSimpleName()+" (");
                        int i=0;
                        for (ParameterDeclaration param:m.getParameters()){
                            writer.print(param.getType()+" "+param.getSimpleName());
                            if (++i < m.getParameters().size()){
                                writer.print(", ");
                            }
                        }
                        writer.print(");");//接口的结束
                    }
                    writer.print("}");//类结束
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
