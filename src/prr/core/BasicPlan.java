package prr.core;

import prr.core.clients.Client;
import prr.core.communications.TextCommunication;
import prr.core.communications.VideoCommunication;
import prr.core.communications.VoiceCommunication;

public class BasicPlan extends TariffPlan{

    public BasicPlan(String name){
        super(name);
    }

    @Override
    protected double computeCost(Client cl, TextCommunication com) {
        return 0;
    }

    @Override
    protected double computeCost(Client cl, VoiceCommunication com) {
        return 0;
    }

    @Override
    protected double computeCost(Client cl, VideoCommunication com) {
        return 0;
    }


}
