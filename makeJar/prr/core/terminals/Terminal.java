package prr.core.terminals;


import prr.core.clients.Client;
import prr.core.communications.Communication;

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
    private Map<String, Terminal> _friends;
    private List<Client> _toNotify;
    private Map<String,Communication> _madeCommunications;
    private Map<String ,Communication> _receivedCommunications;

    private boolean _new;


    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091753L;


    public Terminal(String id, Client c) {
        _id = id;
        _owner = c;
        _mode = TerminalMode.IDLE;
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
        return _mode.equals(TerminalMode.BUSY);
    }

    /**
     * Checks if this terminal can start a new communication.
     *
     * @return true if this terminal is neither off neither busy, false otherwise.
     **/
    public boolean canStartCommunication() {
        return !(_mode.equals(TerminalMode.OFF) || _mode.equals(TerminalMode.BUSY));
    }

    public boolean setOnSilent() {
        _mode = TerminalMode.SILENCE;
        return true;
    }

    public boolean turnOff() {
        _mode = TerminalMode.OFF;
        return true;
    }

    public boolean setOnIdle() {
        _mode = TerminalMode.IDLE;
        return true;
    }

    public void endOngoingCommunication(int size) {
        //FIXME implement
    }

    public void makeVoiceCall(Terminal to) {
        //FIXME implement
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

    public TerminalMode getTerminalStatus() {
        return _mode;
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
                getTerminalStatus(),
                Math.round(getBalancePayments()),
                Math.round(getBalanceDebt()),
                getFriendsString());
    }
}