package prr.core.clients;

import prr.core.communications.Communication;
import prr.core.exception.NotificationPreferenceAlreadySelectedException;
import prr.core.notification.DefaultDelivery;
import prr.core.notification.Notifiable;
import prr.core.notification.Notification;
import prr.core.notification.NotificationDelivery;
import prr.core.terminals.Terminal;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class Client implements Serializable, Notifiable {
    /**
     * Client key
     */
    private final String _key;
    /**
     * Client name
     */
    private final String _name;
    /**
     * Client tax number
     */
    private final int _taxNumber;
    /**
     * Client level
     */
    private ClientLevel _level;
    /**
     * Can receive notifications
     */
    private boolean _receiveNotifications;
    /**
     * Notification delivery method
     */
    private NotificationDelivery _deliveryMethod;

    /**
     * terminals that client owns
     */
    private final List<Terminal> _terminals;
    /**
     * Serial number for serialization.
     */
    @Serial
    private static final long serialVersionUID = 202208091753L;


    /**
     * Controls in which level client should be in (state pattern)
     */
    protected abstract class ClientLevel implements Serializable {

        /**
         * Consecutive text communications
         */
        private int _textCount;
        /**
         * Consecutive video communications
         */
        private int _videoCount;

        /**
         * Checks if client should be demoted or promoted to be used after a communication
         */
        public abstract void checkClientLevelComm();

        /**
         * Checks if client should be demoted or promoted to be used after payment
         */
        public void checkClientLevelPayment() {
        }

        /**
         * @param level to set client level to
         */
        protected void setLevel(ClientLevel level) {
            _level = level;
        }

        /**
         * @return client which this level belongs to
         */
        protected Client getClient() {
            return Client.this;
        }

        /**
         * @param n message length
         * @return communication cost
         */
        public abstract double computeTextCommCost(int n);

        /**
         * @param n communication duration
         * @return communication cost
         */
        public abstract double computeVideoCommCost(int n);

        /**
         * @param n communication duration
         * @return communication cost
         */
        public abstract double computeVoiceCommCost(int n);

        /**
         * @return text count
         */
        public int getTextCount() {
            return _textCount;
        }

        /**
         * @param textCount to set stored text count to
         */
        public void setTextCount(int textCount) {
            _textCount = textCount;
        }

        /**
         * @return video count
         */
        public int getVideoCount() {
            return _videoCount;
        }

        /**
         * @param videoCount to set stored video count to
         */
        public void setVideoCount(int videoCount) {
            _videoCount = videoCount;
        }
    }

    /**
     * @param key       Client's key
     * @param name      Client's name
     * @param taxNumber Client's number
     */
    public Client(String key, String name, int taxNumber) {
        _key = key;
        _name = name;
        _taxNumber = taxNumber;
        _level = new NormalLevel(this);
        _receiveNotifications = true;
        _terminals = new ArrayList<>();
        _deliveryMethod = new DefaultDelivery();
    }

    /**
     * @return Client's key
     */
    public String getClientKey() {
        return _key;
    }

    @Override
    public void setDeliveryMethod(NotificationDelivery deliveryMethod) {
        _deliveryMethod = deliveryMethod;
    }

    /**
     * @return payments gathered from all the terminals
     */
    public double getClientPaymentBalance() {
        double sum = 0;
        for (Terminal terminal : _terminals)
            sum += terminal.getBalancePayments();
        return sum;

    }

    /**
     * @return debts gathered from all the terminals
     */
    public double getClientDebtBalance() {
        double sum = 0;
        for (Terminal terminal : _terminals)
            sum += terminal.getBalanceDebt();
        return sum;

    }


    /**
     * @return Client's total balance
     */
    public double getClientBalance() {
        return getClientPaymentBalance() - getClientDebtBalance();
    }

    /**
     * @param t terminal to add
     */
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
     */
    public void changeNotificationPreference(boolean notisOn) throws NotificationPreferenceAlreadySelectedException {
        /* if state and receiveNotifications are equal it means it is already on the wanted state */
        if (notisOn == _receiveNotifications)
            throw new NotificationPreferenceAlreadySelectedException();
        /* if it isn't on the wanted state set it*/
        _receiveNotifications = notisOn;
    }

    @Override
    public void getNotification(Notification noti) {
        _deliveryMethod.deliver(noti);
    }

    @Override
    public boolean wantsNotifications() {
        return _receiveNotifications;
    }


    /**
     * @return All made communications
     */
    public Collection<Communication> getAllMadeCommunications() {
        ArrayList<Communication> allMadeComms = new ArrayList<>();
        for (Terminal terminal : _terminals) {
            allMadeComms.addAll(terminal.getMadeCommunications());
        }
        allMadeComms.sort(Comparator.comparing(Communication::getId));
        return allMadeComms;
    }

    /**
     * @return All received communications
     */
    public Collection<Communication> getAllReceivedCommunications() {
        ArrayList<Communication> allReceivedComms = new ArrayList<>();
        for (Terminal terminal : _terminals) {
            allReceivedComms.addAll(terminal.getReceivedCommunications());
        }
        allReceivedComms.sort(Comparator.comparing(Communication::getId));
        return allReceivedComms;
    }

    /**
     * @return notifications to read
     * delegates job to delivery method
     */
    public Collection<Notification> readNotifications() {
        return _deliveryMethod.readNotifications();
    }

    /**
     * increment consecutive text count
     */
    public void addConsecutiveTextComm() {
        _level.setTextCount(_level.getTextCount() + 1);
    }

    /**
     * increment consecutive video count
     */
    public void addConsecutiveVideoComm() {
        _level.setVideoCount(_level.getVideoCount() + 1);
    }

    /**
     * reset consecutive text count
     */
    public void resetConsecutiveTextComm() {
        _level.setTextCount(0);
    }

    /**
     * reset consecutive video count
     */
    public void resetConsecutiveVideoComm() {
        _level.setVideoCount(0);
    }

    /**
     * @param n communication duration
     * @return communication cost
     */
    public double computeVideoCommCost(int n) {
        return _level.computeVideoCommCost(n);
    }

    /**
     * @param n message length
     * @return communication cost
     */
    public double computeTextCommCost(int n) {
        return _level.computeTextCommCost(n);
    }

    /**
     * @param n communication duration
     * @return communication cost
     */
    public double computeVoiceCommCost(int n) {
        return _level.computeVoiceCommCost(n);
    }

    /**
     * check Client's level after communication
     */
    public void checkClientLevelAfterComm() {
        _level.checkClientLevelComm();
    }

    /**
     * check Client's level after payment
     */
    public void checkClientLevelAfterPay() {
        _level.checkClientLevelPayment();
    }
}
