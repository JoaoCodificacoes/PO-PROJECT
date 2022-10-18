package prr.core.terminals;


import prr.core.clients.Client;
import prr.core.communications.Communication;

import java.io.Serializable;
import java.util.*;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract terminal.
 */
public abstract class Terminal implements Serializable /* FIXME maybe add more interfaces */{
  private final Client _owner;
  private final String _id;
  private ArrayList<Double> _debts;
  private ArrayList<Double> _payments;
  private TerminalMode _mode;
  private Map<String,Terminal> _friends;
  private Map<String,Client> _toNotify;
  private ArrayList<Communication> _madeCommunications;
  private ArrayList<Communication> _receivedCommunications;


  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;


  public Terminal(String id,Client c){
    _id = id;
    _owner = c;
    _mode = TerminalMode.ON;
    _friends = new HashMap<>();
    _toNotify = new HashMap<>();
    _debts =new ArrayList<>();
    _payments = new ArrayList<>();
  }


  
  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive communication) and
   *          it was the originator of this communication.
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

  public boolean setOnIdle(){
    _mode = TerminalMode.ON;
    return true;
  }

  public void endOngoingCommunication(int size){


  }

  public void makeVoiceCall(Terminal to){
  }

  protected void acceptVoiceCall(Terminal from){
  }

  public void makeSMS(Terminal to, String message){
  }

  protected void acceptSMS(Terminal from){
  }

  protected abstract  void makeVideoCall(Terminal to);
  protected abstract void acceptVideoCall(Terminal to);

  public String getId() {
    return _id;
  }

  public Client getOwner() {
    return _owner;
  }

  public Collection<Terminal> getFriends() {
    return  _friends.values();
  }
  public TerminalMode getTerminalStatus(){
    return _mode;
  }

  public Collection<Double> getDebts(){
    return (Collection<Double>) Collections.unmodifiableList(_debts);
  }

  public Collection<Double> getPayments(){
    return (Collection<Double>) Collections.unmodifiableList(_payments);
  }

  public double getBalanceDebt(){
    double sum = 0;
    for (double i : _debts)
      sum += i;

    return sum;
  }

  public double getBalancePaid(){
    double sum = 0;
    for (double i : _payments)
      sum += i;

    return sum;
  }

  public List<Communication> getMadeCommunications() {
    return Collections.unmodifiableList(_madeCommunications);
  }
  public List<Communication> getReceivedCommunications() {
    return Collections.unmodifiableList(_receivedCommunications);
  }
}
