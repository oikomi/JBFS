package org.miaohong.jbfs.pitchfork.meta;

/**
 * Created by miaohong on 16/2/24.
 */
public class StoreMeta {
    private String state;
    private String admin;
    private String api;
    private String id;
    private String rack;
    private int status;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public StoreMeta(String state, String admin, String api, String id, String rack, int status) {
        this.state = state;
        this.admin = admin;
        this.api = api;
        this.id = id;
        this.rack = rack;
        this.status = status;
    }
}
