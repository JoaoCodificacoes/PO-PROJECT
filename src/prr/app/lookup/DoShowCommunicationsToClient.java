package prr.app.lookup;

import prr.core.Network;
import prr.core.communications.Communication;
import prr.core.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Show communications to a client.
 */
class DoShowCommunicationsToClient extends Command<Network> {

    DoShowCommunicationsToClient(Network receiver) {
        super(Label.SHOW_COMMUNICATIONS_TO_CLIENT, receiver);
        addStringField("clientKey", Message.clientKey());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            for (Communication c : _receiver.getClient(stringField("clientKey")).getAllReceivedCommunications())
                _display.addLine(c.toString());
            _display.display();

        } catch (UnknownClientKeyException ucke) {
            throw new prr.app.exception.UnknownClientKeyException(ucke.getKey());
        }
    }
}
