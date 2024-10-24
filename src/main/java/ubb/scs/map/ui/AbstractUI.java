package ubb.scs.map.ui;

import ubb.scs.map.service.Service;

public abstract class AbstractUI implements UI {
    protected Service service;

    AbstractUI(Service service) {
        this.service = service;
    }

    public abstract void run();
}
