package com.kmnvxh222.task3;

import java.io.Serializable;
import java.util.Objects;

class Contacts implements Serializable {
    private String id;
    private String name;
    private String typeInfo;
    private String info;


    Contacts(String id, String name, String typeInfo, String info) {
        this.id = id;
        this.name = name;
        this.typeInfo = typeInfo;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTypeInfo() {
        return typeInfo;
    }

    public void setTypeInfo(String typeInfo) {
        this.typeInfo = typeInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Contacts)) {
            return false;
        }
        Contacts contact = (Contacts) o;
        return  Objects.equals(name, contact.name) &&
                Objects.equals(typeInfo, contact.typeInfo) &&
                Objects.equals(info, contact.info);
    }

}
