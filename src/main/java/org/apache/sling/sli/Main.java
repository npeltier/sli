package org.apache.sling.sli;

/**
 * main class
 */
public class Main {

    public static void main(String ... args) throws Exception {
        SliCommand sli = new SliCommand();
        sli.execute(args);
    }
}
