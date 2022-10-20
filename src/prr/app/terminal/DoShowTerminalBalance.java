package prr.app.terminal;

import prr.core.Network;
import prr.core.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show balance.
 */
class DoShowTerminalBalance extends TerminalCommand {

  DoShowTerminalBalance(Network context, Terminal terminal) {
    super(Label.SHOW_BALANCE, context, terminal);
  }
  
  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    Message.terminalPaymentsAndDebts(_receiver.getId(), (long) _receiver.getBalanceDebt(), (long) _receiver.getBalancePayments());
  }
}
