package prr.core.terminals;


import prr.core.notification.Observer;
import prr.core.notification.Subject;
import prr.core.clients.Client;
import prr.core.communications.Communication;
import prr.core.communications.InteractiveCommunication;
import prr.core.notification.Notification;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract terminal.
 */
public abstract class Terminal implements Serializable , Subject/* FIXME maybe add more interfaces */ {
    private final Client _owner;
    private final String _id;
    private double _debt;
    private double _payments;
    private TerminalMode _mode;
    private Map<String, Terminal> _friends;
    private List<Observer> _toNotify;
    private Map<Integer, Communication> _madeCommunications;
    private Map<Integer, Communication> _receivedCommunications;

    private InteractiveCommunication _ongoingCommunication;

    private boolean _new;


    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091753L;

    public abstract class TerminalMode {
        /* if terminal mode is the same that is asked for return false
           if terminal mode is changed or can't be changed return true
           by default all set to true, can be overriden in child class
         */
        public boolean toOff() {
            return true;
        }

        public boolean toIdle() {
            return true;
        }

        public boolean toBusy() {
            return true;
        }

        public boolean toSilence() {
            return true;
        }

        public boolean canStartComm() {
            return true;
        }

        public boolean canEndComm() {
            return false;
        }

        public void setMode(TerminalMode mode) {
            _mode = mode;
        }

        public Terminal getTerminal() {
            return Terminal.this;
        }
    }

    public Terminal(String id, Client c) {
        _id = id;
        _owner = c;
        _mode = new IdleMode(this);
        _friends = new TreeMap<>();
        _toNotify = new ArrayList<>();
        _madeCommunications = new TreeMap<>();
        _receivedCommunications = new TreeMap<>();
        _new = true;
    }


    /**
     * Checks if this terminal can end the current interactive communication.
     *
     * @return true if this terminal is busy (i.e., it has an active interactive communication) and
     * it was the originator of this communication.
     **/
    public boolean canEndCurrentCommunication() {
        return _mode.canEndComm() && _ongoingCommunication.getFrom().equals(this);
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

    public void endOngoingCommunication(int size) {
        if (!canEndCurrentCommunication())
            return;
        _ongoingCommunication.setDuration(size);
        _ongoingCommunication.setIsOngoing(false);
        _debt += _ongoingCommunication.getCost();
        _madeCommunications.put(_ongoingCommunication.getId(),_ongoingCommunication);
        _ongoingCommunication = null;
        setOnIdle();
    }

    public void makeVoiceCall(Terminal to) {
        if (canStartCommunication())
            _mode.toBusy();
    }

    protected void acceptVoiceCall(Terminal from) {
        //FIXME implement
    }

    public void makeSMS(Terminal to, String message) {
        //FIXME implement
    }

    protected void acceptSMS(Terminal from) {
        //FIXME implement
    }

    protected abstract void makeVideoCall(Terminal to);

    protected abstract void acceptVideoCall(Terminal to);

    public String getId() {
        return _id;
    }

    public Client getOwner() {
        return _owner;
    }


    public Collection<Terminal> getFriends() {
        return _friends.values();
    }

    public String getTerminalMode() {
        return _mode.toString();
    }


    public double getBalanceDebt() {
        return _debt;
    }

    public double getBalancePayments() {
        return _payments;
    }

    public Collection<Communication> getMadeCommunications() {
        return _madeCommunications.values();
    }

    public Collection<Communication> getReceivedCommunications() {
        return _receivedCommunications.values();
    }

    public boolean payComm(int commId) {
        Communication comm = _madeCommunications.get(commId);
        if (comm == null || comm.isOngoing() || comm.isPaid())
            return false;
        double cost = comm.getCost();
        _debt -= cost;
        _payments += cost;
        comm.setIsPaid(true);
        _owner.checkClientLevel(true);
        return true;
    }

    public boolean isNew() {
        return _new;
    }

    public void useTerminal() {
        _new = false;
    }


    public void addFriend(Terminal friend) {
        _friends.putIfAbsent(friend.getId(), friend);
    }

    public void removeFriend(String friend) {
        _friends.remove(friend);
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

    public String toString(String type) {

        return "%s|%s|%s|%s|%d|%d%s".formatted(type,
                getId(),
                getOwner().getClientKey(),
                getTerminalMode(),
                Math.round(getBalancePayments()),
                Math.round(getBalanceDebt()),
                getFriendsString());
    }

    public void attach(Observer o){
        _toNotify.add(o);
    }
    public void dettach(Observer o){
        _toNotify.remove(o);
    }

    public void notify(Notification noti){
        for (Observer o:_toNotify)
            o.update(noti);
    }
}