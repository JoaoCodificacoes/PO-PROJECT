package prr.core.communications;

import prr.core.TariffPlan;
import prr.core.terminals.Terminal;

public abstract class Communication {
    private int _id;
    private boolean _isPaid;
    private double _cost;
    private boolean _isOngoing;
    private Terminal _from;
    private Terminal _to;

    protected abstract double computeCost(TariffPlan plan);
    protected abstract int getSize();

    public String toString(){
        return null;
    }
}
