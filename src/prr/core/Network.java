package prr.core;

import java.io.Serializable;
import java.io.IOException;
import java.util.*;

import prr.core.clients.Client;
import prr.core.communications.Communication;
import prr.core.exception.*;
import prr.core.terminals.BasicTerminal;
import prr.core.terminals.FancyTerminal;
import prr.core.terminals.Terminal;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

  private Map<String,Communication> _communications;
  private Map<String,Client> _clients;
  private ArrayList<TariffPlan> tariffPlans;
  private Map<String,Terminal> _terminals;

  public Network(){
    _clients = new HashMap<>();
    tariffPlans = new ArrayList<>();
    _terminals = new HashMap<>();
    _communications = new HashMap<>();
  }
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  
  // FIXME define attributes
  // FIXME define contructor(s)
  // FIXME define methods
  
  /**
   * Read text input file and create corresponding domain entities.
   * 
   * @param filename name of the text input file
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   */
  void importFile(String filename) throws UnrecognizedEntryException, IOException /* FIXME maybe other exceptions */  {
    //FIXME implement method
    Parser p = new Parser(this);
    p.parseFile(filename);
  }

  /**
   * @param terminal
   * @param friend
   */
  public void addFriend(String terminal, String friend) {
  }

  /**
   * @param type
   * @param idTerminal
   * @param idClient
   */
  public Terminal registerTerminal(String type, String idTerminal, String idClient) throws DuplicateTerminalKeyException,
          InvalidTerminalKeyException, UnknownClientKeyException {

      if(idTerminal.length() != 6 || !idTerminal.matches("[0-9]+"))
        throw new InvalidTerminalKeyException(idTerminal);

      if (_terminals.containsKey(idTerminal))
          throw new DuplicateTerminalKeyException(idTerminal);

      if (!_clients.containsKey(idClient))
          throw new UnknownClientKeyException(idClient);
      Client c = _clients.get(idClient);

      Terminal t;
          if (type.equals("FANCY"))
             t = new FancyTerminal(idTerminal, c);
          else
             t = new BasicTerminal(idTerminal, c);

      _terminals.put(idTerminal,t);
      c.addTerminal(t);
      return t;
  }

  /**
   * @param name
   * @param key
   * @param taxNumber
   */
  public Client registerClient(String name, String key, int taxNumber) throws DuplicateClientKeyException {
    Client c =new Client(name,key,taxNumber);
    if (_clients.putIfAbsent(key,c) != null)
        throw new DuplicateClientKeyException(key);
    return c;
  }

  public List<Client> getClients(){
      return List.copyOf(_clients.values());
  }

  public List<Terminal> getTerminals(){
      return List.copyOf(_terminals.values());
  }

  public void sendTextCommunication(Terminal from,String toKey, String msg){

  }

  public void sendInteractiveCommunication(Terminal from,String toKey, String type){

  }
}

