package prr.app.terminal;

import prr.core.Network;
import prr.core.exception.InvalidCommunicationException;
import prr.core.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Perform payment.
 */
class DoPerformPayment extends TerminalCommand {

    DoPerformPayment(Network context, Terminal terminal) {
        super(Label.PERFORM_PAYMENT, context, terminal);
        addIntegerField("commKey", Message.commKey());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _receiver.payComm(integerField("commKey"));
        } catch (InvalidCommunicationException ice) {
            _display.popup(Message.invalidCommunication());
        }
    }
}
