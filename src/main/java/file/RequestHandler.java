package file;

public abstract class RequestHandler {
    protected RequestHandler nextSolver;

    public void setNextSolver(RequestHandler nextSolver) {
        this.nextSolver = nextSolver;
    }
    public abstract void solveRequest(LoadRequest request);
}
