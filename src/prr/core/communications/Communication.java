package prr.core.communications;


import prr.core.terminals.Terminal;

import java.io.Serializable;

public abstract class Communication implements Serializable {
    private static int _idNumber = 0;
    private final int _id;
    private boolean _isPaid;
    private double _cost;
    private boolean _isOngoing;
    private final Terminal _from;
    private final Terminal _to;
    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091753L;


    public Communication(Terminal from, Terminal to) {
        _from = from;
        _to = to;
        _idNumber++;
        _id = _idNumber;
    }

    protected abstract double computeCost();

    protected abstract int getSize();


    public String toString(String type) {
        String onGoing;
        if (_isOngoing)
            onGoing = "ONGOING";
        else
            onGoing = "FINISHED";
        return type + "|" + _id + "|" + _from.getId() + "|" +
                _to.getId() + "|" + getSize() + "|" + Math.round(_cost) + "|" + onGoing;
    }

    public Terminal getFrom() {
        return _from;
    }

    public boolean isPaid() {
        return _isPaid;
    }

    public boolean isOngoing() {
        return _isOngoing;
    }

    public void setIsPaid(boolean isPaid) {
        _isPaid = isPaid;
    }

    public double getCost() {
        return _cost;
    }

    public void stopComm() {
        _isOngoing = false;
        computeCost();
    }

    protected void startComm(){
        _isOngoing = true;
    }
    public int getId() {
        return _id;
    }

    public void setCost(double cost) {
        _cost = cost;
    }

    public Terminal getTo() {
        return _to;
    }
}
