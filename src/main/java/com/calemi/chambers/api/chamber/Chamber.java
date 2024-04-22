package com.calemi.chambers.api.chamber;

public class Chamber {

    private String name;
    private ChamberSettings settings;
    private ChamberForm form;

    public Chamber(String name, ChamberSettings settings, ChamberForm form) {
        this.name = name;
        this.settings = settings;
        this.form = form;
    }

    public String getName() {
        return name;
    }

    public ChamberSettings getSettings() {
        return settings;
    }

    public ChamberForm getForm() {
        return form;
    }
}
