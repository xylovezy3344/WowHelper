package com.xiaoyu.wowhelpers.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity()
public class Mount {
    @Id(autoincrement = true)
    private long id;
    private String version;
    private String name;
    private String faction;
    private boolean fly;
    private String category;
    private String source;
    @Generated(hash = 350598160)
    public Mount(long id, String version, String name, String faction, boolean fly,
            String category, String source) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.faction = faction;
        this.fly = fly;
        this.category = category;
        this.source = source;
    }
    @Generated(hash = 2014362176)
    public Mount() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getVersion() {
        return this.version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFaction() {
        return this.faction;
    }
    public void setFaction(String faction) {
        this.faction = faction;
    }
    public boolean getFly() {
        return this.fly;
    }
    public void setFly(boolean fly) {
        this.fly = fly;
    }
    public String getCategory() {
        return this.category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getSource() {
        return this.source;
    }
    public void setSource(String source) {
        this.source = source;
    }


}
