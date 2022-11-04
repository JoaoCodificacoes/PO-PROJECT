package prr.core.terminals;


import prr.core.clients.Client;
import prr.core.communications.*;
import prr.core.exception.*;
import prr.core.notification.Notifiable;
import prr.core.notification.Notification;
import prr.core.notification.NotificationType;
import prr.core.notification.Notificator;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Abstract terminal.
 */
public abstract class Terminal implements Serializable {

    /**
     * Client that owns the terminal
     */
    private final Client _owner;
    /**
     * Terminal id
     */
    private final String _id;

    /**
     * Terminal debts
     */
    private double _debt;

    /**
     * Terminal payments
     */
    private double _payments;

    /**
     * Terminal mode
     */
    private TerminalMode _mode;

    /**
     * Previous terminal mode
     */
    private TerminalMode previousMode;

    /**
     * All terminal friends
     */
    private final SortedMap<String, Terminal> _friends;

    /**
     * All Notifiables to notify
     */
    private final Set<Notifiable> _toNotify;

    /**
     * All communications made
     */
    private final SortedMap<Integer, Communication> _madeCommunications;
    /**
     * All communications received
     */
    private final SortedMap<Integer, Communication> _receivedCommunications;

    /**
     * Communication that is happening / null if none happening
     */
    private InteractiveCommunication _ongoingCommunication;

    /**
     * if used _new = true / else _new = false
     */
    private boolean _new;


    /**
     * Serial number for serialization.
     */
    @Serial
    private static final long serialVersionUID = 202208091753L;

    /**
     * Class that controls the TerminalMode and its transitions and changes(State Pattern)
     */
    protected abstract class TerminalMode implements Notificator, Serializable {

        /**
         * Stores the types of notifcations that can be sent
         */
        private final NotificationType _notificationTypes = NotificationType.getInstance();
        @Serial
        private static final long serialVersionUID = 202208091753L;


        /**
         * @throws AlreadyInModeException if the terminal is already off
         *Changes mode to off
         */
        public abstract void toOff() throws AlreadyInModeException;

        /**
         * @throws AlreadyInModeException if terminal is already idle
         *  Changes mode to idle
         */
        public abstract void toIdle() throws AlreadyInModeException;

        /**
         * Changes mode to busy
         * Overriden in modes that use it
         */
        public void toBusy() {
        }

        /**
         * @throws AlreadyInModeException if mode is already silent
         * Changes mode to silent
         */
        public abstract void toSilence() throws AlreadyInModeException;


        /**
         * Changes to previous mode
         * Overriden in modes that use it
         */
        public void toPrevious() {
        }


        /**
         * @return true if it can end communication false otherwise
         * Overriden in modes that can start communication
         */
        public boolean canEndComm() {
            return false;
        }


        /**
         * @return true if it can start communication false otherwise
         * Overriden in modes that can't start communication
         */
        public boolean canStartComm() {
            return true;
        }


        /**
         * @param from terminal of communication origin
         * @throws DestinationOffException if terminal is off
         */
        public void getText(Client from) throws DestinationOffException {
        }


        /**
         * @param from terminal of communication origin
         * @throws DestinationOffException    if the terminal is off
         * @throws DestinationSilentException if the terminal is silent
         * @throws DestinationBusyException   if the terminal is busy
         */
        public abstract void getCall(Terminal from) throws DestinationOffException,
                DestinationSilentException, DestinationBusyException;


        /**
         * @param mode mode to set _mode to
         */
        public void setMode(TerminalMode mode) {
            _mode = mode;
        }

        /**
         * Get terminal where the mode is in
         */
        public Terminal getTerminal() {
            return Terminal.this;
        }

        @Override
        public void attach(Notifiable n) {
            if (n.wantsNotifications())
                _toNotify.add(n);
        }

        @Override
        public void dettach(Notifiable n) {
            _toNotify.remove(n);
        }

        @Override
        public void sendNotifications(String notiType) {
            Terminal fromTerminal = getTerminal();
            for (Notifiable o : _toNotify) {
                Notification noti = new Notification(notiType, fromTerminal);
                o.getNotification(noti);
            }
            _toNotify.clear();
        }

        /**
         * @return last TerminalMode
         */
        public TerminalMode getPreviousMode() {
            return previousMode;
        }


        /**
         * @param mode mode to set previousMode to
         */
        public void setPreviousMode(TerminalMode mode) {
            previousMode = mode;
        }


        /**
         * Checks if mode can send communication after transition
         */
        public void canSendNotification() {
            if (getPreviousMode() != null) {
                String notiType = getNotificationType()
                        .makeValidNotificationType(getPreviousMode().toString(), toString());
                if (notiType != null) {
                    sendNotifications(notiType);
                }
            }
        }


        /**
         * @return class that stores the notificationtypes and checks if other types are valid
         */
        public NotificationType getNotificationType() {
            return _notificationTypes;
        }
    }

    /**
     * @param id Terminal id
     * @param c  Client that owns the terminal
     */
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

    /**
     * @throws AlreadyInModeException if is already silent
     *                                Changes current mode to silent
     */
    public void setOnSilent() throws AlreadyInModeException {
        _mode.toSilence();
    }

    /**
     * @throws AlreadyInModeException if is already off
     *                                Changes current mode to off
     */
    public void turnOff() throws AlreadyInModeException {
        _mode.toOff();
    }

    /**
     * @throws AlreadyInModeException if is already idle
     *                                Changes current mode to idle
     */
    public void setOnIdle() throws AlreadyInModeException {
        _mode.toIdle();
    }

    /**
     * Changes current mode to previous one
     */
    public void setOnPrevious() {
        _mode.toPrevious();
    }

    /**
     * @param size communication size
     */
    public void endOngoingCommunication(int size) {
        _ongoingCommunication.setDuration(size);
        _ongoingCommunication.finishInteractiveComm();
        _owner.checkClientLevelAfterComm();
    }


    /**
     * @param to terminal that receives the communication
     * @return communication made
     * @throws DestinationOffException    if terminal "to" is off
     * @throws DestinationSilentException if terminal "to" is silent
     * @throws DestinationBusyException   if terminal "to" is busy
     */
    public VoiceCommunication makeVoiceCall(Terminal to) throws DestinationOffException,
            DestinationSilentException, DestinationBusyException {
        _mode.toBusy();
        to.acceptVoiceCall(this);
        VoiceCommunication communication = new VoiceCommunication(this, to);

        to.addReceivedComm(communication);
        addMadeComm(communication);

        to.setOngoingComm(communication);
        setOngoingComm(communication);
        _owner.resetConsecutiveTextComm();
        _owner.resetConsecutiveVideoComm();

        return communication;
    }

    /**
     * @param from communication's origin terminal
     * @throws DestinationOffException    if this terminal is off
     * @throws DestinationSilentException if this terminal is silent
     * @throws DestinationBusyException   if this terminal is busy
     */
    protected void acceptVoiceCall(Terminal from) throws DestinationOffException,
            DestinationSilentException, DestinationBusyException {

        _mode.getCall(from);
        _mode.toBusy();
    }

    /**
     * @param to      terminal that receives the communication
     * @param message message to send
     * @return communication made
     * @throws DestinationOffException if destination is off
     */
    public TextCommunication makeSms(Terminal to, String message) throws DestinationOffException {
        to.acceptSms(this);
        TextCommunication communication = new TextCommunication(message, this, to);

        to.addReceivedComm(communication);
        addMadeComm(communication);
        addDebt(communication.getCost());

        _owner.resetConsecutiveVideoComm();
        _owner.addConsecutiveTextComm();

        getOwner().checkClientLevelAfterComm();
        return communication;
    }

    /**
     * @param from communication's terminal of origin
     * @throws DestinationOffException if this terminal is off
     */
    protected void acceptSms(Terminal from) throws DestinationOffException {
        _mode.getText(from.getOwner());
    }

    /**
     * @param to terminal that receives communication
     * @return communication made
     * @throws UnsupportedAtOriginException      if video call is unsupported at origin
     * @throws DestinationSilentException        if destination is silent
     * @throws DestinationOffException           if destination is off
     * @throws DestinationBusyException          if destination is busy
     * @throws UnsupportedAtDestinationException if video call is unsupported at destination
     */
    public abstract VideoCommunication makeVideoCall(Terminal to) throws UnsupportedAtOriginException,
            DestinationSilentException, DestinationOffException,
            DestinationBusyException, UnsupportedAtDestinationException;

    /**
     * @param to terminal where the communication originates from
     * @throws UnsupportedAtDestinationException if this terminal doesn't support video calls
     * @throws DestinationBusyException          if this terminal is busy
     * @throws DestinationOffException           if this terminal is off
     * @throws DestinationSilentException        if this terminal is silent
     */
    protected abstract void acceptVideoCall(Terminal to) throws UnsupportedAtDestinationException,
            DestinationBusyException, DestinationOffException, DestinationSilentException;

    /**
     * @return terminal's id
     */
    public String getId() {
        return _id;
    }

    /**
     * @return terminal's owner
     */
    public Client getOwner() {
        return _owner;
    }


    /**
     * @return terminal's friends
     */
    public Collection<Terminal> getFriends() {
        return _friends.values();
    }

    /**
     * @return terminal's mode
     */
    public TerminalMode getTerminalMode() {
        return _mode;
    }


    /**
     * @return terminal's debts
     */
    public double getBalanceDebt() {
        return _debt;
    }

    /**
     * @return terminal's payments
     */
    public double getBalancePayments() {
        return _payments;
    }

    /**
     * @return terminal's balance
     */
    public double getBalance() {
        return _payments - _debt;
    }

    /**
     * @param debt debt to add to current debt
     */
    public void addDebt(double debt) {
        _debt += debt;
    }


    /**
     * @param commId Communication's id
     * @throws InvalidCommunicationException if Communication provided can't be paid
     */
    public void payComm(int commId) throws InvalidCommunicationException {
        Communication comm = _madeCommunications.get(commId);
        if (comm == null || comm.isOngoing() || comm.isPaid())
            throw new InvalidCommunicationException();
        double cost = comm.getCost();
        _debt -= cost;
        _payments += cost;
        comm.payCommunication();
        _owner.checkClientLevelAfterPay();
    }

    /**
     * @return variable that stores if is used or not
     */
    public boolean isNew() {
        return _new;
    }


    public void useTerminal() {
        _new = false;
    }

    /**
     * @param c communication
     *          add to made communications stored
     */
    public void addMadeComm(Communication c) {
        _madeCommunications.put(c.getId(), c);
    }

    /**
     * @param c communication
     *          add to received communications stored
     */
    public void addReceivedComm(Communication c) {
        _receivedCommunications.put(c.getId(), c);
    }

    /**
     * @param friend to be added to friends stored
     */
    public void addFriend(Terminal friend) {
        _friends.putIfAbsent(friend.getId(), friend);
    }

    /**
     * @param friend to be removed from friends stored
     */
    public void removeFriend(String friend) {
        _friends.remove(friend);
    }

    /**
     * @param terminal to check if is friend
     * @return true if is friend / false otherwise
     */
    public boolean isFriend(Terminal terminal) {
        return _friends.containsKey(terminal.getId());
    }

    /**
     * @return ongoing communication
     */
    public InteractiveCommunication getOngoingCommunication() {
        return _ongoingCommunication;
    }

    /**
     * @return generate a string with all the friends concatenated
     */
    protected String getFriendsString() {
        String friends = "";
        List<Terminal> friendList = new ArrayList<>(getFriends());
        if (!friendList.isEmpty())
            friends = "|" + friendList.stream()
                    .map(Terminal::getId)
                    .collect(Collectors.joining(","));
        return friends;

    }


    /**
     * @param c communication to set ongoing communication to
     */
    public void setOngoingComm(InteractiveCommunication c) {
        _ongoingCommunication = c;
    }

    /**
     * @param type of terminal
     * @return string with terminal description
     */
    public String toString(String type) {

        return "%s|%s|%s|%s|%d|%d%s".formatted(type,
                getId(),
                getOwner().getClientKey(),
                getTerminalMode(),
                Math.round(getBalancePayments()),
                Math.round(getBalanceDebt()),
                getFriendsString());
    }

    /**
     * @return communications that this terminal made
     */
    public Collection<Communication> getMadeCommunications() {
        return _madeCommunications.values();
    }

    /**
     * @return communications that this terminal received
     */
    public Collection<Communication> getReceivedCommunications() {
        return _receivedCommunications.values();
    }
}