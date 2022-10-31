package prr.core.communications;

import prr.core.tariff.TariffPlan;
import prr.core.terminals.Terminal;

import java.io.Serializable;

public abstract class Communication implements Serializable {

    private int _id;
    private boolean _isPaid;
    private double _cost;
    private boolean _isOngoing;
    private Terminal _from;
    private Terminal _to;
    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091753L;

    protected abstract double computeCost(TariffPlan plan);
    protected abstract int getSize();

    public String toString(){
        //FIXME implement
        return null;
    }

    public Terminal getFrom() {
        return _from;
    }
}
