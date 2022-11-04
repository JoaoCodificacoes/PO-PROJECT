package prr.app.terminals;

import prr.core.Network;
import prr.core.exception.DuplicateTerminalKeyException;
import prr.core.exception.InvalidTerminalKeyException;
import prr.core.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

    DoRegisterTerminal(Network receiver) {
        super(Label.REGISTER_TERMINAL, receiver);
        addStringField("id", Message.terminalKey());
        addOptionField("type", Message.terminalType(), "BASIC", "FANCY");
        addStringField("client", Message.clientKey());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _receiver.registerTerminal(optionField("type"), stringField("id"), stringField("client"));

        } catch (DuplicateTerminalKeyException dtke) {
            throw new prr.app.exception.DuplicateTerminalKeyException(dtke.getKey());
        } catch (InvalidTerminalKeyException itke) {
            throw new prr.app.exception.InvalidTerminalKeyException(itke.getKey());
        } catch (UnknownClientKeyException ucke) {
            throw new prr.app.exception.UnknownClientKeyException(ucke.getKey());
        }
    }
}
