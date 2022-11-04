package prr.core.terminals;


import prr.core.clients.Client;
import prr.core.communications.VideoCommunication;
import prr.core.exception.UnsupportedAtDestinationException;
import prr.core.exception.UnsupportedAtOriginException;

public class BasicTerminal extends Terminal {

    public BasicTerminal(String id, Client c) {
        super(id, c);
    }

    @Override
    public String toString() {
        return super.toString("BASIC");
    }

    public VideoCommunication makeVideoCall(Terminal to) throws UnsupportedAtOriginException {
        throw new UnsupportedAtOriginException(getId());
    }

    protected void acceptVideoCall(Terminal from) throws UnsupportedAtDestinationException {
        throw new UnsupportedAtDestinationException(getId());
    }

}
