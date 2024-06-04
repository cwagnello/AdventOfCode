package com.cwagnello;

import java.util.Map;
import java.util.Set;

public record AuntSue(Integer index, Map<String, Integer> properties) {
    public void put(String key, Integer value) {
        this.properties.put(key, value);
    }

    public int get(String key) {
        return this.properties.get(key);
    }

    public Set<String> keys() {
        return this.properties.keySet();
    }
}
