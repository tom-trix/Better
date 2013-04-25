package ru.tomtrix.synch.example;

import ru.tomtrix.synch.simplebetter.AbstractModel;

/**
 * Supermarket example
 */
public class Starter {

    class Model_1 extends AbstractModel {
        public Model_1() {
            _state.agents.put("Guard",      new Guard("Guard"));
            _state.agents.put("Cashier1",   new Cashier1("Cashier1"));
            _state.agents.put("Purchaser0", new Purchaser("Purchaser0"));
            _state.agents.put("Purchaser1", new Purchaser("Purchaser1"));
            _state.agents.put("Purchaser2", new Purchaser("Purchaser2"));
            _state.agents.put("Purchaser3", new Purchaser("Purchaser3"));
            _state.agents.put("Purchaser4", new Purchaser("Purchaser4"));
            _state.agents.put("Purchaser5", new Purchaser("Purchaser5"));
            _state.agents.put("Purchaser6", new Purchaser("Purchaser6"));
            _state.agents.put("Purchaser7", new Purchaser("Purchaser7"));
            _state.remoteAgents.put("SuperMarket", "node2");
            _state.remoteAgents.put("Cashier2",    "node2");
            _state.remoteAgents.put("Purchaser8",  "node2");
            _state.remoteAgents.put("Purchaser9",  "node2");
            _state.remoteAgents.put("PurchaserA",  "node2");
            _state.remoteAgents.put("PurchaserB",  "node2");
            _state.remoteAgents.put("PurchaserC",  "node2");
            _state.remoteAgents.put("PurchaserD",  "node2");
            _state.remoteAgents.put("PurchaserE",  "node2");
            _state.remoteAgents.put("PurchaserF",  "node2");
        }
    }

    class Model_2 extends AbstractModel {
        public Model_2() {
            _state.agents.put("SuperMarket", new SuperMarket("SuperMarket"));
            _state.agents.put("Cashier2",    new Cashier2("Cashier2"));
            _state.agents.put("Purchaser8",  new Purchaser("Purchaser8"));
            _state.agents.put("Purchaser9",  new Purchaser("Purchaser9"));
            _state.agents.put("PurchaserA",  new Purchaser("PurchaserA"));
            _state.agents.put("PurchaserB",  new Purchaser("PurchaserB"));
            _state.agents.put("PurchaserC",  new Purchaser("PurchaserC"));
            _state.agents.put("PurchaserD",  new Purchaser("PurchaserD"));
            _state.agents.put("PurchaserE",  new Purchaser("PurchaserE"));
            _state.agents.put("PurchaserF",  new Purchaser("PurchaserF"));
            _state.remoteAgents.put("Guard",      "node1");
            _state.remoteAgents.put("Cashier1",   "node1");
            _state.remoteAgents.put("Purchaser0", "node1");
            _state.remoteAgents.put("Purchaser1", "node1");
            _state.remoteAgents.put("Purchaser2", "node1");
            _state.remoteAgents.put("Purchaser3", "node1");
            _state.remoteAgents.put("Purchaser4", "node1");
            _state.remoteAgents.put("Purchaser5", "node1");
            _state.remoteAgents.put("Purchaser6", "node1");
            _state.remoteAgents.put("Purchaser7", "node1");
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
