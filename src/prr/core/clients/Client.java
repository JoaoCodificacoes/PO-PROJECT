package prr.core.clients;

import prr.core.TariffPlan;
import prr.core.terminals.Terminal;

import java.io.Serializable;
import java.util.*;

public class Client implements Serializable {
    private String _key;
    private String _name;
    private int _taxNumber;
    private ClientLevel _level;
    private boolean _receiveNotifications;
    private TariffPlan _tariffPlan;
    private Map<String, Terminal> _terminals;
    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091753L;

    public Client(String key, String name, int taxNumber) {
        _key = key;
        _name = name;
        _taxNumber = taxNumber;
        _level = ClientLevel.NORMAL;
        _receiveNotifications = true;
        _terminals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        _level = ClientLevel.NORMAL;

    }

    public String getClientKey() {
        return _key;
    }


    public double getClientPaymentBalance() {
        double sum = 0;
        for (Terminal terminal : _terminals.values())
            sum += terminal.getBalancePayments();
        return sum;

    }

    public double getClientDebtBalance() {
        double sum = 0;
        for (Terminal terminal : _terminals.values())
            sum += terminal.getBalanceDebt();
        return sum;

    }


    public void addTerminal(Terminal t) {
        _terminals.put(t.getId(), t);
    }

    @Override
    public String toString() {
        String notifications = "NO";
        if (_receiveNotifications)
            notifications = "YES";
        return "CLIENT|%s|%s|%d|%s|%s|%d|%d|%d".formatted(_key,
                _name,
                _taxNumber,
                _level.toString(),
                notifications,
                _terminals.size(),
                Math.round(getClientPaymentBalance()),
                Math.round(getClientDebtBalance()));
    }
}
