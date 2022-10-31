package prr.core.communications;

import prr.core.tariff.TariffPlan;

public class TextCommunication extends Communication{
    private String _message;

    public TextCommunication(String message) {
        _message = message;
    }

    protected double computeCost(TariffPlan plan){
        int n = getSize();

        return 0;
    }
    protected int getSize() {
        return _message.length();
    }
}
