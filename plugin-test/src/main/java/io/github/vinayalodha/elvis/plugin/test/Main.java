package io.github.vinayalodha.elvis.plugin.test;

/**
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
public class Main {

    public static void main(String[] args) {
        String a = "a";
        //@NullSafe("default")
        String vinay = (a + "b");
        System.out.println(vinay);
    }
}
