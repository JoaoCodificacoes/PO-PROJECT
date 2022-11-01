package prr.core.communications;

import prr.core.tariff.TariffPlan;
import prr.core.terminals.Terminal;

public class VideoCommunication extends InteractiveCommunication {
    public VideoCommunication(Terminal from, Terminal to) {
        super(from, to);
    }

    @Override
    protected double computeCost(TariffPlan plan) {
        return 0;
    }
    //FIXME implement
}
