package org.jikvict.tasks.exposed;

public class HelloWorld {

    public static void main(String[] args) {
    }

    public static String sayHello(String name) {
       return "Hello, " + name + "!";
    }

    public static String sayHelloMultiple(String[] names) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < names.length; i++) {
            if (i > 0) sb.append('\n');
            sb.append(sayHello(names[i]));
        }
        return sb.toString();
    }

    public static int countGreetings(String[] names) {
        return names.length;
    }
}
