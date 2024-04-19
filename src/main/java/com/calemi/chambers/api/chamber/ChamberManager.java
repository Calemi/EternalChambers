package com.calemi.chambers.api.chamber;

public class ChamberManager {

    public final boolean debug = true;

    public static ChamberManager instance;

    public ChamberManager() {
        instance = this;
    }
}
