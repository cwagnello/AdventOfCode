package com.cwagnello;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

class Presents implements Comparable<Presents> {
    private Set<Long> group;

    public Presents() {
        this.group = new HashSet<>();
    }

    public Presents(Collection<Long> presents) {
        this();
        this.group.addAll(presents);
    }

    @Override
    public int compareTo(Presents other) {
        return this.group.size() - other.group.size();
    }

    public Set<Long> getPresents() {
        return this.group;
    }

    public void add(Long present) {
        this.group.add(present);
    }

    public void addAll(Collection<Long> presents) {
        this.group.addAll(presents);
    }
}