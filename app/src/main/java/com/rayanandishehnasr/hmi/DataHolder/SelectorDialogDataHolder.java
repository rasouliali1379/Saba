package com.rayanandishehnasr.hmi.DataHolder;

public class SelectorDialogDataHolder {
    String label;
    int id;

    public SelectorDialogDataHolder(String label, int id) {
        this.label = label;
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
