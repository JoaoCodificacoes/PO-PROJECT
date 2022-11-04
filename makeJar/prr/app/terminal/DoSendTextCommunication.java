package prr.app.terminal;

import prr.core.Network;
import prr.core.exception.DestinationOffException;
import prr.core.exception.UnknownTerminalKeyException;
import prr.core.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {

    DoSendTextCommunication(Network context, Terminal terminal) {
        super(Label.SEND_TEXT_COMMUNICATION, context, terminal, Terminal::canStartCommunication);
        addStringField("toID", Message.terminalKey());
        addStringField("msg", Message.textMessage());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _network.sendTextCommunication(_receiver, stringField("toID"), stringField("msg"));

        } catch (DestinationOffException doe) {
            _display.popup(Message.destinationIsOff(doe.getKey()));

        } catch (UnknownTerminalKeyException utke) {
            throw new prr.app.exception.UnknownTerminalKeyException(utke.getKey());
        }
    }
} 
