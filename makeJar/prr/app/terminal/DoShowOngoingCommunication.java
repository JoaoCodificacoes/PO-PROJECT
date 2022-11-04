package prr.app.terminal;

import prr.core.Network;
import prr.core.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Command for showing the ongoing communication.
 */
class DoShowOngoingCommunication extends TerminalCommand {

    DoShowOngoingCommunication(Network context, Terminal terminal) {
        super(Label.SHOW_ONGOING_COMMUNICATION, context, terminal);
    }

    @Override
    protected final void execute() throws CommandException {
        if (_receiver.getOngoingCommunication() != null)
            _display.popup(_receiver.getOngoingCommunication().toString());
        else
            _display.popup(Message.noOngoingCommunication());
    }
}

