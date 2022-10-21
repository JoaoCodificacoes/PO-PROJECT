package prr.app.client;

import prr.core.Network;
import prr.core.clients.Client;
import prr.core.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show the payments and debts of a client.
 */
class DoShowClientPaymentsAndDebts extends Command<Network> {

    DoShowClientPaymentsAndDebts(Network receiver) {
        super(Label.SHOW_CLIENT_BALANCE, receiver);
        addStringField("clientID", Message.key());
    }

    @Override
    protected final void execute() throws CommandException {
        String clientId = stringField("clientID");
        try {
            Client client = _receiver.getClient(clientId);
            _display.popup(Message.clientPaymentsAndDebts(clientId,
                    Math.round(client.getClientPaymentBalance()),
                    Math.round(client.getClientDebtBalance())));
        } catch (UnknownClientKeyException ucke) {
            throw new prr.app.exception.UnknownClientKeyException(ucke.getKey());


        }

    }
}
