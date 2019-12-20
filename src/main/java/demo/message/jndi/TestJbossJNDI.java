package demo.message.jndi;

import javax.naming.*;

public class TestJbossJNDI {

    public static void main(String[] args) throws NamingException {
        //得到初始目录环境的一个引用
        Context context = new InitialContext();
        //返回绑定在特定上下文中指定属性名对象的清单列表
        NamingEnumeration<Binding> cntxName = context.listBindings("cntxName");
        //循环列出所有名字、类和对象
        while (cntxName.hasMore()) {
            Binding next = cntxName.next();
            String name = next.getName();
            String className = next.getClassName();
            Object object = next.getObject();
            System.out.println("name:" + name + "\t" +
                    "className:" + className + "\t" +
                    "object:" + object);
        }
    }
}
