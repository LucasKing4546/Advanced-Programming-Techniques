package action;

import domain.Identifiable;
import repository.IRepository;

public class ActionAdd<ID, T extends Identifiable<ID>> implements IAction{
    private IRepository<ID, T> repo;
    private T addedElem;

    public ActionAdd(IRepository<ID, T> repo, T addedElem) {
        this.repo = repo;
        this.addedElem = addedElem;
    }

    @Override
    public void executeUndo() {
        repo.removeElement(addedElem.getId());
    }

    @Override
    public void executeRedo() {
        repo.addElement(addedElem.getId(), addedElem);
    }
}
