package prr.core.communications;


import prr.core.terminals.Terminal;

import java.io.Serial;
import java.io.Serializable;

public abstract class Communication implements Serializable {
    /**
     * keep track of number of communications
     */
    private static int _idNumber = 0;
    /**
     * Communication id
     */
    private final int _id;
    /**
     * Is communication paid or not
     */
    private boolean _isPaid;
    /**
     * Communication cost
     */
    private double _cost;
    /**
     * Is communication ongoing
     */
    private boolean _isOngoing;
    /**
     * Source terminal
     */
    private final Terminal _from;
    /**
     * Origin terminal
     */
    private final Terminal _to;
    /**
     * Serial number for serialization.
     */
    @Serial
    private static final long serialVersionUID = 202208091753L;


    /**
     * @param from Source terminal
     * @param to   Destination terminal
     */
    public Communication(Terminal from, Terminal to) {
        _from = from;
        _to = to;
        _idNumber++;
        _id = _idNumber;
    }


    /**
     * sets the cost of the communication
     */
    protected abstract void computeCost();

    /**
     * @return the size of the communication: length for text/ duration for interactive
     */
    protected abstract int getSize();


    /**
     * @param type communication type (TEXT/VIDEO/VOICE)
     * @return string descrpition of communication
     */
    public String toString(String type) {
        String onGoing;
        if (_isOngoing)
            onGoing = "ONGOING";
        else
            onGoing = "FINISHED";
        return type + "|" + _id + "|" + _from.getId() + "|" +
                _to.getId() + "|" + getSize() + "|" + Math.round(_cost) + "|" + onGoing;
    }

    /**
     * @return origin terminal
     */
    public Terminal getFrom() {
        return _from;
    }

    /**
     * @return true if communication is paid  / false if it isn't
     */
    public boolean isPaid() {
        return _isPaid;
    }

    /**
     * @return true if communication is ongoing  / false if it isn't
     */
    public boolean isOngoing() {
        return _isOngoing;
    }

    /**
     * set isPaid to true, not allowing anymore payments of this communication
     */
    public void payCommunication() {
        _isPaid = true;
    }

    /**
     * @return communication cost
     */
    public double getCost() {
        return _cost;
    }


    /**
     * finish communication,setting its cost
     */
    protected void finishComm() {
        _isOngoing = false;
        computeCost();
    }

    /**
     * start the communication
     */
    protected void startComm() {
        _isOngoing = true;
    }

    /**
     * @return Communication's id
     */
    public int getId() {
        return _id;
    }

    /**
     * @param cost Communication cost
     */
    public void setCost(double cost) {
        _cost = cost;
    }

    /**
     * @return Destination terminal
     */
    public Terminal getTo() {
        return _to;
    }
}
