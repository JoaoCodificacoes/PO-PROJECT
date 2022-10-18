package prr.core.clients;

import prr.core.TariffPlan;
import prr.core.terminals.Terminal;

import java.util.*;

public class Client {
    private String _key;
    private String _name;
    private int _taxNumber;
    private ClientLevel _level;
    private boolean _receiveNotifications;
    private TariffPlan _tariffPlan;
    private Map<String, Terminal> _terminals;

    public Client(String key, String name, int taxNumber) {
        _key = key;
        _name = name;
        _taxNumber = taxNumber;
        _level = ClientLevel.NORMAL;
        _receiveNotifications = true;
        _terminals = new HashMap<>();

    }
    public String getClientKey(){
        return _key;
    }

    public Collection<Double> getClientPayments(){
        List<Double> payments = new ArrayList<>();
        for (Terminal t : _terminals.values()){
            payments.addAll(t.getPayments());
        }
        return (Collection<Double>) Collections.unmodifiableList(payments);
    }
    public double getClientPaymentBalance(){
        double sum = 0;
        for(double d : getClientPayments())
            sum += d;
        return sum;

    }

    public Collection<Double> getClientDebts(){
        List<Double> debts = new ArrayList<>();
        for (Terminal t : _terminals.values()){
            debts.addAll(t.getDebts());
        }
        return (Collection<Double>) Collections.unmodifiableList(debts);
    }
    public double getClientDebtBalance(){
        double sum = 0;
        for(double d : getClientDebts())
            sum += d;
        return sum;
    }



    public void addTerminal(Terminal t){
        _terminals.put(t.getId(),t);
    }

    @Override
    public String toString() {
        String notifications = "NO";
        if (_receiveNotifications)
            notifications = "YES";
        return "CLIENT|%s|%s|%d|%s|%d|%d|%d".formatted(_key,
                _name,
                _taxNumber,
                notifications,
                _terminals.size(),
                Math.round(getClientPaymentBalance()),
                Math.round(getClientDebtBalance()));
    }
}
