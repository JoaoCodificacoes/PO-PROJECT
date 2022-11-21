package prr.core;

import prr.core.clients.Client;
import prr.core.communications.*;
import prr.core.exception.*;
import prr.core.terminals.BasicTerminal;
import prr.core.terminals.FancyTerminal;
import prr.core.terminals.Terminal;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * Class Network implements Serializable.
 * This class manages a several terminals, communications and clients, provisioning several services to its users
 *
 * @authors Joao Ferreira (103680) and Miguel Teixeira (103449)
 * @group 49
 */

public class Network implements Serializable {
    /**
     * Stores every Communication that happens on the network mapping it to its ID
     */
    private final SortedMap<Integer, Communication> _communications;
    /**
     * Stores every Client in the network mapping them to their ID's
     */
    private final SortedMap<String, Client> _clients;
    /**
     * Stores every Terminal in the network mapping them to their ID's
     */
    private final SortedMap<String, Terminal> _terminals;

    /**
     * Serial number for serialization.
     */
    @Serial
    private static final long serialVersionUID = 202208091753L;

    /**
     * Instances the network by initializing its several Collections
     */
    public Network() {
        _clients = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        _terminals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        _communications = new TreeMap<>();
    }


    /**
     * Read text input file and create corresponding domain entities.
     *
     * @param filename name of the text input file
     * @throws UnrecognizedEntryException if some entry is not correct
     * @throws IOException                if there is an IO error while processing the text file
     */
    void importFile(String filename) throws UnrecognizedEntryException, IOException {
        Parser parser = new Parser(this);
        parser.parseFile(filename);
    }

    /**
     * @param terminalId ID of the terminal that is being added a friend
     * @param friend     ID of the terminal that is being added as a friend
     * @throws UnknownTerminalKeyException if there is no terminal with the ids provided
     */
    public void addFriend(String terminalId, String friend) throws UnknownTerminalKeyException {
        if (!terminalId.equals(friend))
            getTerminal(terminalId).addFriend(getTerminal(friend));
    }

    public void removeFriend(String terminalId, String friend) throws UnknownTerminalKeyException {
        getTerminal(terminalId).removeFriend(friend);
    }

    /**
     * @param type       whether the terminal is BASIC or FANCY
     * @param idTerminal ID of the terminal to register
     * @param idClient   ID of the client that owns the terminal
     */
    public Terminal registerTerminal(String type, String idTerminal, String idClient) throws DuplicateTerminalKeyException,
            InvalidTerminalKeyException, UnknownClientKeyException {

        if (idTerminal.length() != 6 || !idTerminal.matches("[0-9]+"))
            throw new InvalidTerminalKeyException(idTerminal);

        if (_terminals.containsKey(idTerminal))
            throw new DuplicateTerminalKeyException(idTerminal);

        Client client = getClient(idClient);

        Terminal terminal;
        if (type.equals("FANCY"))
            terminal = new FancyTerminal(idTerminal, client);
        else
            terminal = new BasicTerminal(idTerminal, client);

        _terminals.put(idTerminal, terminal);
        client.addTerminal(terminal);
        return terminal;
    }

    /**
     * @param name  Client's name
     * @param id    Client's id
     * @param taxId Client's tax ID
     */
    public Client registerClient(String id, String name, int taxId) throws DuplicateClientKeyException {
        Client c = new Client(id, name, taxId);
        if (_clients.putIfAbsent(id, c) != null)
            throw new DuplicateClientKeyException(id);
        return c;
    }

    /**
     * @return network's client list
     */
    public Collection<Client> getClients() {
        return _clients.values();
    }


    /**
     * @param id Client's ID
     * @return Client whose ID was provided
     * @throws UnknownClientKeyException if there is no client in the network with the ID provided
     */
    public Client getClient(String id) throws UnknownClientKeyException {
        Client client;
        if ((client = _clients.get(id)) == null)
            throw new UnknownClientKeyException(id);
        return client;
    }

    /**
     * @return network's terminal list
     */
    public Collection<Terminal> getTerminals() {
        return _terminals.values();
    }

    public Terminal getTerminal(String id) throws UnknownTerminalKeyException {
        Terminal t;
        if ((t = _terminals.get(id)) == null)
            throw new UnknownTerminalKeyException(id);
        return t;
    }

    /**
     * @param from  Communication source
     * @param toKey Communication destination ID
     * @param msg   Communication content
     */
    public void sendTextCommunication(Terminal from, String toKey, String msg) throws UnknownTerminalKeyException, DestinationOffException {
        Terminal to = getTerminal(toKey);
        int id = _communications.size() + 1;
        from.makeSms(to, msg);
        Client owner = from.getOwner();
        TextCommunication comm = new TextCommunication(msg, from, to, id);

        to.addReceivedComm(comm);
        from.addMadeComm(comm);
        from.addDebt(comm.getCost());

        owner.resetConsecutiveVideoComm();
        owner.addConsecutiveTextComm();

        owner.checkClientLevelAfterComm();
        _communications.put(comm.getId(), comm);
        from.useTerminal();
        to.useTerminal();
    }

    /**
     * @param from  Communication source
     * @param toKey Communication destination ID
     * @param type  Communication type(Voice/Video)
     */
    public void sendInteractiveCommunication(Terminal from, String toKey, String type) throws UnknownTerminalKeyException,
            DestinationOffException, DestinationSilentException, DestinationBusyException,
            UnsupportedAtDestinationException, UnsupportedAtOriginException {

        Terminal to = getTerminal(toKey);
        InteractiveCommunication comm;
        Client owner = from.getOwner();
        int id = _communications.size() + 1;
        if (type.equals("VOICE")) {

            from.makeVoiceCall(to);
            comm = new VoiceCommunication(from, to, id);
            owner.resetConsecutiveVideoComm();


        } else {

            from.makeVideoCall(to);
            comm = new VideoCommunication(from, to, id);
            owner.addConsecutiveVideoComm();
        }
        owner.resetConsecutiveTextComm();

        to.addReceivedComm(comm);
        from.addMadeComm(comm);

        to.setOngoingComm(comm);
        from.setOngoingComm(comm);
        from.useTerminal();
        to.useTerminal();
        _communications.put(comm.getId(), comm);
    }

    /**
     * @param clientId client ID
     * @param notisOn  true to set notificationsOn / false to set NotificationsOff
     */
    public void setClientNotificationPreference(String clientId, boolean notisOn) throws UnknownClientKeyException,
            NotificationPreferenceAlreadySelectedException {

        getClient(clientId).changeNotificationPreference(notisOn);
    }

    /**
     * @return all Communications
     */
    public Collection<Communication> getAllComms() {
        return _communications.values();
    }

    /**
     * @return all terminal that haven't been used
     */
    public Collection<Terminal> getUnusedTerminals() {
        List<Terminal> unusedTerminals = new ArrayList<>();
        for (Terminal t : _terminals.values())
            if (t.isNew())
                unusedTerminals.add(t);

        return unusedTerminals;
    }

    /**
     * @return sum of all payments made
     */
    public double getGlobalPayments() {
        double sum = 0;
        for (Client c : _clients.values())
            sum += c.getClientPaymentBalance();
        return sum;
    }

    /**
     * @return sum of all debts
     */
    public double getGlobalDebts() {
        double sum = 0;
        for (Client c : _clients.values())
            sum += c.getClientDebtBalance();
        return sum;
    }

    public Collection<Client> getClientsWithDebts() {
        List<Client> clientsWithDebts = new ArrayList<>(_clients.values());
        clientsWithDebts.removeIf(client -> client.getClientDebtBalance() == 0);
        clientsWithDebts.sort(Comparator.comparing(Client::getClientDebtBalance).reversed());
        return clientsWithDebts;

    }
}

