package services;

import action.IAction;
import java.util.Stack;

public class CommandPattern {
    private Stack<IAction> undoStack = new Stack<>();
    private Stack<IAction> redoStack = new Stack<>();

    public void addAction(IAction action) {
        undoStack.push(action);
        redoStack.clear();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            IAction action = undoStack.pop();
            action.executeUndo();
            redoStack.push(action);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            IAction action = redoStack.pop();
            action.executeRedo();
            undoStack.push(action);
        }
    }
}