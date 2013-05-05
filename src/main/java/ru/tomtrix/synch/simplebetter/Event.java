package ru.tomtrix.synch.simplebetter;

import ru.tomtrix.synch.ApacheLogger;
import ru.tomtrix.synch.HashSerializable;

/**
 * Event
 */
public class Event implements Comparable<Event>, HashSerializable {

    public final double t;
    public final String agent;
    public final String action;
    public final String arg;
    public final String author;

    public Event(double t, String agent, String action, String arg, String author) {
        this.t = t;
        this.agent = agent;
        this.action = action;
        this.arg = arg;
        this.author = author;
    }

    @Override
    public int compareTo(Event o) {
        return (int) Math.signum(t - o.t); //DON'T FORGET ABOUT SIGN(X)
    }

    @Override
    public String toString() {
        return String.format("%.2f: %s.%s(%s) by %s", t, agent, action, arg, author);
    }

    @Override
    public String toHash() {
        StringBuilder result = new StringBuilder();
        switch (agent) {
            case "Guard": result.append("0"); break;
            case "Cashier1": result.append("1"); break;
            case "Cashier2": result.append("2"); break;
            case "Purchaser0": result.append("3"); break;
            case "Purchaser1": result.append("3"); break;
            case "Purchaser2": result.append("3"); break;
            case "Purchaser3": result.append("3"); break;
            case "Purchaser4": result.append("3"); break;
            case "Purchaser5": result.append("3"); break;
            case "Purchaser6": result.append("3"); break;
            case "Purchaser7": result.append("3"); break;
            case "Purchaser8": result.append("3"); break;
            case "Purchaser9": result.append("3"); break;
            case "PurchaserA": result.append("3"); break;
            case "PurchaserB": result.append("3"); break;
            case "PurchaserC": result.append("3"); break;
            case "PurchaserD": result.append("3"); break;
            case "PurchaserE": result.append("3"); break;
            case "PurchaserF": result.append("3"); break;
            case "SuperMarket": result.append("4"); break;
            default: ApacheLogger.logger().error(String.format("Agent %s not found", agent));
        }
        switch (action) {
            case "goWC": result.append("0"); break;
            case "goBack": result.append("1"); break;
            case "servePurchaser": result.append("2"); break;
            case "requestToSmoke": result.append("3"); break;
            case "responseToSmoke": result.append("4"); break;
            case "suspect": result.append("5"); break;
            case "appear": result.append("6"); break;
            case "bringGoods": result.append("7"); break;
            case "goToCashdesk": result.append("8"); break;
            case "accepted": result.append("9"); break;
            case "setVariable": result.append("A"); break;
            case "checkVariable": result.append("B"); break;
            case "incVariable": result.append("C"); break;
            case "decVariable": result.append("D"); break;
            default: ApacheLogger.logger().error(String.format("Action %s not found", action));
        }
        switch (author) {
            case "Guard": result.append("0"); break;
            case "Cashier1": result.append("1"); break;
            case "Cashier2": result.append("2"); break;
            case "Purchaser0": result.append("3"); break;
            case "Purchaser1": result.append("3"); break;
            case "Purchaser2": result.append("3"); break;
            case "Purchaser3": result.append("3"); break;
            case "Purchaser4": result.append("3"); break;
            case "Purchaser5": result.append("3"); break;
            case "Purchaser6": result.append("3"); break;
            case "Purchaser7": result.append("3"); break;
            case "Purchaser8": result.append("3"); break;
            case "Purchaser9": result.append("3"); break;
            case "PurchaserA": result.append("3"); break;
            case "PurchaserB": result.append("3"); break;
            case "PurchaserC": result.append("3"); break;
            case "PurchaserD": result.append("3"); break;
            case "PurchaserE": result.append("3"); break;
            case "PurchaserF": result.append("3"); break;
            case "SuperMarket": result.append("4"); break;
            default: ApacheLogger.logger().error(String.format("Author %s not found", author));
        }
        return result.toString();
    }
}
