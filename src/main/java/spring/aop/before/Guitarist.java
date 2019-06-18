package spring.aop.before;

public class Guitarist implements Singer{
    private String lyric="You're gonna forever in me";

    @Override
    public void sing() {
        System.out.println(lyric);
    }

    public String play(){
        return lyric;
    }
}
