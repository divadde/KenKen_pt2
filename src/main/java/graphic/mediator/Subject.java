package graphic.mediator;

public interface Subject {

    void handleRequest(Request request);

    //metodo per forzare i subject ad avere il proprio mediator
    void setMediator(Mediator mediator);

}
