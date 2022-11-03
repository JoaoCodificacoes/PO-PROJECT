package prr.app.lookup;

import prr.core.Network;
import prr.core.clients.Client;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show clients without debts.
 */
class DoShowClientsWithoutDebts extends Command<Network> {

    DoShowClientsWithoutDebts(Network receiver) {
        super(Label.SHOW_CLIENTS_WITHOUT_DEBTS, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        for (Client client : _receiver.getClients())
            if (client.getClientDebtBalance() == 0)
                _display.addLine(client.toString());
        _display.display();
    }
}
