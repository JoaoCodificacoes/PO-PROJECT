package prr.core.terminals;


import prr.core.clients.Client;

public class BasicTerminal extends Terminal{

    public BasicTerminal(String id, Client c){
        super(id, c);
    }

    @Override
    public String toString() {
        return super.toString("BASIC");
    }

    public void makeVideoCall(Terminal to){

    }

    protected void acceptVideoCall(Terminal from){

    }

}
