package 算法.设计模式.抽象工厂.factory;

public abstract class Factory {

    public static Factory getFactory(String className){
        Factory factory=null;
        try {
            factory= (Factory) Class.forName(className).newInstance();
        }  catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("没有找到");
        }catch (Exception ee){
            ee.printStackTrace();
        }
        return factory;
    }

    public abstract Link createLink(String caption,String url);
    public abstract Tray createTray(String caption);
    public abstract Page createPage(String title,String author);
}
