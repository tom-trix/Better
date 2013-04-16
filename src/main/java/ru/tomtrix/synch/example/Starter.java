package ru.tomtrix.synch.example;

import ru.tomtrix.synch.platform.AbstractModel;

/**
 * Created with IntelliJ IDEA.
 * User: tom-trix
 * Date: 4/15/13
 * Time: 12:04 PM
 */
public class Starter {

    class Model_1 extends AbstractModel {
        public Model_1() {
            _agents.put("Guard", new Guard(this));
            _agents.put("Cashier2", "trix2");
        }
    }

    class Model_2 extends AbstractModel {
        public Model_2() {
            _agents.put("Guard", "trix1");
            _agents.put("Cashier2", new Casher2(this));
        }
    }

    public static void main( String[] args ) {
        new Starter().start(args[0]);
    }

    public void start(String arg) {
        switch (arg) {
            case "1": new Model_1(); break;
            case "2": new Model_2(); break;
        }
    }
}
