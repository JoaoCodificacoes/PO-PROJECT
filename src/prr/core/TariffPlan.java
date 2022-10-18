package prr.core;


import prr.core.clients.Client;
import prr.core.communications.TextCommunication;
import prr.core.communications.VideoCommunication;
import prr.core.communications.VoiceCommunication;

public abstract class TariffPlan {
    private String _name;

    public TariffPlan(String name){
        _name = name;
    }

    protected abstract double computeCost(Client cl, TextCommunication com);
    protected abstract double computeCost(Client cl, VoiceCommunication com);
    protected abstract double computeCost(Client cl, VideoCommunication com);

}
