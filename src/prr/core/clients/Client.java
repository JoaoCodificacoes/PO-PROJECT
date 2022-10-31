package prr.core.clients;

import prr.core.tariff.TariffPlan;
import prr.core.terminals.Terminal;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Client implements Serializable {
    private final String _key;
    private final String _name;
    private final int _taxNumber;



    private ClientLevel _level;
    private boolean _receiveNotifications;

    private TariffPlan _tariffPlan;
    private Map<String, Terminal> _terminals;
    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091753L;

    public abstract class ClientLevel implements Serializable{
        public abstract void checkLevel();
        public abstract String getLevel();
        protected void setLevel(ClientLevel level){
            _level = level;
        }
        protected Client getClient(){
            return Client.this;
        }

    }

    public Client(String key, String name, int taxNumber) {
        _key = key;
        _name = name;
        _taxNumber = taxNumber;
        _level = new NormalLevel(this);
        _receiveNotifications = true;
        _terminals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

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

    public double getClientBalance(){
        return getClientPaymentBalance()-getClientDebtBalance();
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

    /**
     * @param notisOn true = notification On / false = notification Off
     * @return true if state changed / false otherwise
     */
    public boolean changeNotificationState(boolean notisOn) {
        /* if state and receiveNotifications are equal it means it is already on the wanted state */
        if (notisOn == _receiveNotifications)
            return false;
        /* if it isn't on the wanted state set it*/
        _receiveNotifications = notisOn;
        return true;
    }

}
