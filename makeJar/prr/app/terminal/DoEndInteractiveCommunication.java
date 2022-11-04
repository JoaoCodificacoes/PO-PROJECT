package prr.app.terminal;

import prr.core.Network;
import prr.core.communications.InteractiveCommunication;
import prr.core.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for ending communication.
 */
class DoEndInteractiveCommunication extends TerminalCommand {

    DoEndInteractiveCommunication(Network context, Terminal terminal) {
        super(Label.END_INTERACTIVE_COMMUNICATION, context, terminal, Terminal::canEndCurrentCommunication);
        addIntegerField("duration", Message.duration());
    }

    @Override
    protected final void execute() throws CommandException {
        InteractiveCommunication c = _receiver.getOngoingCommunication();
        _receiver.endOngoingCommunication(integerField("duration"));
        _display.popup(Message.communicationCost(Math.round(c.getCost())));
    }
}
