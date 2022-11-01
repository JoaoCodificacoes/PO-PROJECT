package prr.core.communications;

import prr.core.tariff.TariffPlan;
import prr.core.terminals.Terminal;

public class VoiceCommunication extends InteractiveCommunication {

    public VoiceCommunication(Terminal from, Terminal to) {
        super(from, to);
    }

    protected double computeCost(TariffPlan plan) {
        //FIXME implement
        return 0;
    }
}
