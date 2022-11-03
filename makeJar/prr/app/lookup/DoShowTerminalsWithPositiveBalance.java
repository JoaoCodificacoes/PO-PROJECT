package prr.app.lookup;

import prr.core.Network;
import prr.core.terminals.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show terminals with positive balance.
 */
class DoShowTerminalsWithPositiveBalance extends Command<Network> {

    DoShowTerminalsWithPositiveBalance(Network receiver) {
        super(Label.SHOW_TERMINALS_WITH_POSITIVE_BALANCE, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        for (Terminal terminal : _receiver.getTerminals())
            if (terminal.getBalance() > 0)
                _display.addLine(terminal);
        _display.display();

    }
}
