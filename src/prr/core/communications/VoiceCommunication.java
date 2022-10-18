package prr.core.communications;

import prr.core.TariffPlan;

public class VoiceCommunication extends InteractiveCommunication {

    protected double computeCost(TariffPlan plan){
        return 0;
    }
}
