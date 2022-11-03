package prr.core.terminals;


import prr.core.clients.Client;
import prr.core.communications.*;
import prr.core.exception.*;
import prr.core.notification.Notification;
import prr.core.notification.Notificator;
import prr.core.notification.Notifiable;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract terminal.
 */
public abstract class Terminal implements Serializable /* FIXME maybe add more interfaces */ {
    private final Client _owner;
    private final String _id;
    private double _debt;
    private double _payments;
    private TerminalMode _mode;
    private TerminalMode previousMode;
    private Map<String, Terminal> _friends;
    private Set<Notifiable> _toNotify;
    private Map<Integer, Communication> _madeCommunications;
    private Map<Integer, Communication> _receivedCommunications;


    private InteractiveCommunication _ongoingCommunication;

    private boolean _new;


    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091753L;


    public abstract class TerminalMode implements Notificator, Serializable {
        private String _notificationType;

        /* if terminal mode is the same that is asked for return false
           if terminal mode is changed or can't be changed return true
           by default all set to true, can be overriden in child class
         */
        public boolean toOff() {return true;}

        public boolean toIdle() {
            return true;
        }

        public boolean toBusy() {
            return true;
        }

        public boolean toSilence() {
            return true;
        }

        public boolean canEndComm() {
            return false;
        }
        public void toPrevious(){}

        public boolean canStartComm() {
            return true;
        }

        public void getText(Client from) throws DestinationOffException {
        }

        public abstract void getCall(Client from) throws DestinationOffException,
                DestinationSilentException, DestinationBusyException;

        public void setMode(TerminalMode mode) {
            _mode = mode;
        }

        public Terminal getTerminal() {
            return Terminal.this;
        }

        public void attach(Notifiable o) {
            if ( o.wantsNotifications())
                _toNotify.add(o);
        }


        public void sendNotifications() {
            Terminal fromTerminal = getTerminal();
            for (Notifiable o : _toNotify) {
                Notification noti = new Notification(_notificationType, fromTerminal);
                o.getNotification(noti);
            }
            _toNotify.clear();
        }

        public TerminalMode getPreviousMode() {
            return previousMode;
        }

        public void setPreviousMode(TerminalMode mode) {
            previousMode = mode;
        }

        public void setNotificationType(String notificationType) {
            _notificationType = notificationType;
        }
    }

    public Terminal(String id, Client c) {
        _id = id;
        _owner = c;
        _friends = new TreeMap<>();
        _toNotify = new HashSet<>();
        _madeCommunications = new TreeMap<>();
        _receivedCommunications = new TreeMap<>();
        _new = true;
        _mode = new IdleMode(this);
    }


    /**
     * Checks if this terminal can end the current interactive communication.
     *
     * @return true if this terminal is busy (i.e., it has an active interactive communication) and
     * it was the originator of this communication.
     **/
    public boolean canEndCurrentCommunication() {
        return _mode.canEndComm() && _ongoingCommunication != null && _ongoingCommunication.getFrom().equals(this);
    }

    /**
     * Checks if this terminal can start a new communication.
     *
     * @return true if this terminal is neither off neither busy, false otherwise.
     **/
    public boolean canStartCommunication() {
        return _mode.canStartComm();
    }

    public boolean setOnSilent() {

        return _mode.toSilence();
    }

    public boolean turnOff() {
        return _mode.toOff();
    }

    public boolean setOnIdle() {
        return _mode.toIdle();
    }

    public double endOngoingCommunication(int size) {
        _ongoingCommunication.setDuration(size);
        _ongoingCommunication.stopComm();
        double cost = _ongoingCommunication.getCost();
        _debt += _ongoingCommunication.getCost();
        _madeCommunications.put(_ongoingCommunication.getId(), _ongoingCommunication);
        _owner.getClientLevel().checkClientLevelComm();
        Terminal to = _ongoingCommunication.getTo();
        to.setOngoingComm(null);
        _ongoingCommunication = null;
        to.setOnPrevious();
        setOnPrevious();
        return cost;
    }

    public void setOnPrevious(){_mode.toPrevious();}

    public VoiceCommunication makeVoiceCall(Terminal to) throws DestinationOffException,
            DestinationSilentException, DestinationBusyException {

        to.acceptVoiceCall(this);
        VoiceCommunication c = new VoiceCommunication(this, to);
        to.addReceivedComm(c);
        addMadeComm(c);
        to.setOngoingComm(c);
        setOngoingComm(c);
        _mode.toBusy();
        return c;
    }

    protected void acceptVoiceCall(Terminal from) throws DestinationOffException,
            DestinationSilentException, DestinationBusyException {
        _mode.getCall(from.getOwner());
        _mode.toBusy();
    }

    public TextCommunication makeSMS(Terminal to, String message) throws DestinationOffException {
        to.acceptSMS(this);
        TextCommunication communication = new TextCommunication(message, this, to);
        communication.stopComm();
        to.addReceivedComm(communication);
        addMadeComm(communication);
        addDebt(communication.getCost());
        getOwner().getClientLevel().checkClientLevelComm();
        return communication;
    }

    protected void acceptSMS(Terminal from) throws DestinationOffException {
        _mode.getText(from.getOwner());
    }

    public abstract VideoCommunication makeVideoCall(Terminal to) throws UnsupportedAtOriginException,
            DestinationSilentException, DestinationOffException,
            DestinationBusyException, UnsupportedAtDestinationException;

    protected abstract void acceptVideoCall(Terminal to) throws UnsupportedAtDestinationException,
            DestinationBusyException, DestinationOffException, DestinationSilentException;

    public String getId() {
        return _id;
    }

    public Client getOwner() {
        return _owner;
    }


    public Collection<Terminal> getFriends() {
        return _friends.values();
    }

    public TerminalMode getTerminalMode() {
        return _mode;
    }


    public double getBalanceDebt() {
        return _debt;
    }

    public double getBalancePayments() {
        return _payments;
    }

    public double getBalance(){return _payments-_debt;}

    public void addDebt(double debt) {
        _debt += debt;
    }

    public boolean payComm(int commId) {
        Communication comm = _madeCommunications.get(commId);
        if (comm == null || comm.isOngoing() || comm.isPaid())
            return false;
        double cost = comm.getCost();
        _debt -= cost;
        _payments += cost;
        comm.setIsPaid(true);
        _owner.getClientLevel().checkClientLevelPayment();
        return true;
    }

    public boolean isNew() {
        return _new;
    }

    public void useTerminal() {
        _new = false;
    }

    public void addMadeComm(Communication c) {
        _madeCommunications.put(c.getId(), c);
    }

    public void addReceivedComm(Communication c) {
        _receivedCommunications.put(c.getId(), c);
    }

    public void addFriend(Terminal friend) {
        _friends.putIfAbsent(friend.getId(), friend);
    }

    public void removeFriend(String friend) {
        _friends.remove(friend);
    }

    public boolean isFriend(Terminal terminal) {
        return _friends.containsKey(terminal.getId());
    }

    public InteractiveCommunication getOngoingCommunication() {
        return _ongoingCommunication;
    }

    public String getFriendsString() {
        String friends = "";
        List<Terminal> friendList = new ArrayList<>(getFriends());
        if (!friendList.isEmpty())
            friends = "|" + friendList.stream()
                    .map(Terminal::getId)
                    .collect(Collectors.joining(","));
        return friends;

    }


    public void setOngoingComm(InteractiveCommunication c) {
        _ongoingCommunication = c;
    }

    public String toString(String type) {

        return "%s|%s|%s|%s|%d|%d%s".formatted(type,
                getId(),
                getOwner().getClientKey(),
                getTerminalMode(),
                Math.round(getBalancePayments()),
                Math.round(getBalanceDebt()),
                getFriendsString());
    }

    public Collection<Communication> getMadeCommunications(){
        return _madeCommunications.values();
    }
}