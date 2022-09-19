package com.zk.dirt.core;

import com.zk.dirt.intef.iListable;

public enum eDirtEntityRelation implements iListable {
    None,
    OneToOne,
    OneToMany,
    ManyToOne,
    ManyToMany;


    @Override
    public String getText() {
        return this.toString();
    }

    public static void main(String[] args) {
        System.out.println(OneToMany);
    }
}
