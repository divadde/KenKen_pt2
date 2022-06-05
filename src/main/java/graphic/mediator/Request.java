package graphic.mediator;

import graphic.command.Command;

public class Request {
    public enum Tipo{
        NEWGAME, READY, SAVEGAME, LOADGAME, SHOWSOLUTION, NEXTSOLUTION, PREVIOUSSOLUTION, SHOWSUGGESTINGS, HELP
    }

    private Tipo type;
    private Command command;

    public Request(Tipo type, Command command){
        this.type=type;
        this.command=command;
    }

    public Tipo getTipo() {
        return type;
    }
    public Command getCommand(){
        return command;
    }
}
