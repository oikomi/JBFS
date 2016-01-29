package org.miaohong.jbfs.store.zk;

/**
 * Created by miaohong on 16/1/28.
 */
public class ZkStoreData {
    private String admin;
    private String api;
    private String id;
    private String rack;
    private int status;

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

    public ZkStoreData(String admin, String api, String id, String rack, int status) {
        this.admin = admin;
        this.api = api;
        this.id = id;
        this.rack = rack;
        this.status = status;
    }
}


