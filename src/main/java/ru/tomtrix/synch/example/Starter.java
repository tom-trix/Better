package ru.tomtrix.synch.example;

import ru.tomtrix.synch.platform.AbstractModel;

/**
 * Supermarket example
 */
public class Starter {

    class Model_1 extends AbstractModel {
        public Model_1() {
            _agents.put("Guard", new Guard(this, "Guard"));
            _agents.put("Cashier1", new Casher1(this, "Cashier1"));
            _agents.put("Cashier2", "node2");
            _agents.put("Purchaser0", new Purchaser(this, "Purchaser0"));
            _agents.put("Purchaser1", new Purchaser(this, "Purchaser1"));
            _agents.put("Purchaser2", new Purchaser(this, "Purchaser2"));
            _agents.put("Purchaser3", new Purchaser(this, "Purchaser3"));
            _agents.put("Purchaser4", new Purchaser(this, "Purchaser4"));
            _agents.put("Purchaser5", new Purchaser(this, "Purchaser5"));
            _agents.put("Purchaser6", new Purchaser(this, "Purchaser6"));
            _agents.put("Purchaser7", new Purchaser(this, "Purchaser7"));
            _agents.put("Purchaser8", "node2");
            _agents.put("Purchaser9", "node2");
            _agents.put("PurchaserA", "node2");
            _agents.put("PurchaserB", "node2");
            _agents.put("PurchaserC", "node2");
            _agents.put("PurchaserD", "node2");
            _agents.put("PurchaserE", "node2");
            _agents.put("PurchaserF", "node2");
        }
    }

    class Model_2 extends AbstractModel {
        public Model_2() {
            _agents.put("Guard", "node1");
            _agents.put("Cashier1", "node1");
            _agents.put("Cashier2", new Casher2(this, "Cashier2"));
            _agents.put("Purchaser0", "node1");
            _agents.put("Purchaser1", "node1");
            _agents.put("Purchaser2", "node1");
            _agents.put("Purchaser3", "node1");
            _agents.put("Purchaser4", "node1");
            _agents.put("Purchaser5", "node1");
            _agents.put("Purchaser6", "node1");
            _agents.put("Purchaser7", "node1");
            _agents.put("Purchaser8", new Purchaser(this, "Purchaser8"));
            _agents.put("Purchaser9", new Purchaser(this, "Purchaser9"));
            _agents.put("PurchaserA", new Purchaser(this, "PurchaserA"));
            _agents.put("PurchaserB", new Purchaser(this, "PurchaserB"));
            _agents.put("PurchaserC", new Purchaser(this, "PurchaserC"));
            _agents.put("PurchaserD", new Purchaser(this, "PurchaserD"));
            _agents.put("PurchaserE", new Purchaser(this, "PurchaserE"));
            _agents.put("PurchaserF", new Purchaser(this, "PurchaserF"));
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
