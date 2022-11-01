package prr.core.communications;

import prr.core.tariff.TariffPlan;
import prr.core.terminals.Terminal;

public class TextCommunication extends Communication {
    private String _message;

    public TextCommunication(String message, Terminal from, Terminal to) {
        super(from,to);
        _message = message;
    }

    protected double computeCost(TariffPlan plan) {
        int n = getSize();

        return 0;
    }

    protected int getSize() {
        return _message.length();
    }
}
