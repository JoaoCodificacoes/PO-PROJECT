package prr.app.lookup;

import prr.core.Network;
import prr.core.clients.Client;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.*;


/**
 * Show clients with negative balance.
 */
class DoShowClientsWithDebts extends Command<Network> {

  DoShowClientsWithDebts(Network receiver) {
    super(Label.SHOW_CLIENTS_WITH_DEBTS, receiver);
  }

  @Override
  protected final void execute() throws CommandException {
   List <Client> sortedByDebt = new ArrayList<>(_receiver.getClients());
   sortedByDebt.sort(Comparator.comparing(Client::getClientDebtBalance,Comparator.reverseOrder()));
    for (Client client:sortedByDebt)
      _display.addLine(client.toString());
    _display.display();
  }
}
