package prr.core.clients;

import prr.core.communications.Communication;
import prr.core.notification.Notification;
import prr.core.notification.Observer;
import prr.core.terminals.Terminal;

import java.io.Serializable;
import java.util.*;

public class Client implements Serializable, Observer {
    private final String _key;
    private final String _name;
    private final int _taxNumber;
    private ClientLevel _level;
    private boolean _receiveNotifications;

    private Queue<Notification> _notifications;
    private List<Terminal> _terminals;//List
    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091753L;


    public abstract class ClientLevel implements Serializable {

        private static int COMMS_NEEDED = 5;
        private int _textCount;
        private int _videoCount;

        public abstract void checkClientLevelComm();

        public void checkClientLevelPayment() {
        }

        protected void setLevel(ClientLevel level) {
            _level = level;
        }

        protected Client getClient() {
            return Client.this;
        }

        public abstract double computeTextCommCost(int n);

        public abstract double computeVideoCommCost(int n);

        public abstract double computeVoiceCommCost(int n);

        public int getTextCount() {
            return _textCount;
        }

        public void setTextCount(int textCount) {
            _textCount = textCount;
        }

        public int getVideoCount() {
            return _videoCount;
        }

        public void setVideoCount(int videoCount) {
            _videoCount = videoCount;
        }
    }

    public Client(String key, String name, int taxNumber) {
        _key = key;
        _name = name;
        _taxNumber = taxNumber;
        _level = new NormalLevel(this);
        _receiveNotifications = true;
        _terminals = new ArrayList<>();
        _notifications = new LinkedList<>();
    }

    public String getClientKey() {
        return _key;
    }


    public double getClientPaymentBalance() {
        double sum = 0;
        for (Terminal terminal : _terminals)
            sum += terminal.getBalancePayments();
        return sum;

    }

    public double getClientDebtBalance() {
        double sum = 0;
        for (Terminal terminal : _terminals)
            sum += terminal.getBalanceDebt();
        return sum;

    }

    public double getClientBalance() {
        return getClientPaymentBalance() - getClientDebtBalance();
    }

    public void addTerminal(Terminal t) {
        _terminals.add(t);
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

    public ClientLevel getClientLevel() {
        return _level;
    }

    @Override
    public void update(Notification noti) {
        _notifications.add(noti);
    }

    @Override
    public boolean wantsSubject() {
        return _receiveNotifications;
    }


    public Collection<Communication> getAllMadeCommunications() {
        ArrayList<Communication> allMadeComms = new ArrayList<>();
        for (Terminal terminal : _terminals) {
             allMadeComms.addAll(terminal.getMadeCommunications());
        }
        allMadeComms.sort(Comparator.comparing(Communication::getId));
        return allMadeComms;
    }

    public Collection<Communication> getAllReceivedCommunications() {
        ArrayList<Communication> allReceivedComms = new ArrayList<>();
        for (Terminal terminal : _terminals) {
            allReceivedComms.addAll(terminal.getReceivedCommunications());
        }
        allReceivedComms.sort(Comparator.comparing(Communication::getId));
        return allReceivedComms;
    }

    public Collection<Notification> readNotifications(){
        List<Notification> notifications = new LinkedList<>(_notifications);
        _notifications.clear();
        return notifications;
    }
}
