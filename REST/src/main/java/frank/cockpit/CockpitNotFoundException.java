package frank.cockpit;

@SuppressWarnings("serial")
public class CockpitNotFoundException extends Exception {

    public CockpitNotFoundException() {
    }

    public CockpitNotFoundException(String arg0) {
        super(arg0);
    }

    public CockpitNotFoundException(Throwable arg0) {
        super(arg0);
    }

    public CockpitNotFoundException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public CockpitNotFoundException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

}
