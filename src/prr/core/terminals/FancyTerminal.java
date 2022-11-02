package prr.core.terminals;


import prr.core.clients.Client;
import prr.core.communications.Communication;
import prr.core.communications.VideoCommunication;
import prr.core.exception.DestinationBusyException;
import prr.core.exception.DestinationOffException;
import prr.core.exception.DestinationSilentException;
import prr.core.exception.UnsupportedAtDestinationException;

public class FancyTerminal extends Terminal {
    public FancyTerminal(String id, Client c) {
        super(id, c);
    }

    @Override
    public String toString() {
        return super.toString("FANCY");
    }

    public VideoCommunication makeVideoCall(Terminal to) throws DestinationSilentException,
            DestinationOffException, DestinationBusyException, UnsupportedAtDestinationException {

        to.acceptVideoCall(this);
        VideoCommunication comm = new VideoCommunication(this, to);
        addMadeComm(comm);
        to.addReceivedComm(comm);
        addDebt(comm.getCost());
        to.addDebt(comm.getCost());
        getTerminalMode().toBusy();
        to.getTerminalMode().toBusy();
        return comm;
    }

    protected void acceptVideoCall(Terminal from) throws DestinationBusyException, DestinationOffException,
            DestinationSilentException {
        getTerminalMode().getCall();
    }
}
