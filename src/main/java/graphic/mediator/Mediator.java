package graphic.mediator;

import java.util.LinkedList;
import java.util.List;


//MEDIATOR
public class Mediator {
    List<Subject> subjects = new LinkedList<>();

    public void addSubject(Subject s){
        subjects.add(s);
    }

    public void notify(Request r){
        for(Subject subject: subjects)
            subject.handleRequest(r);
    }
}
