package com.cwagnello;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class Room {
    private final String encryptedName;
    private final long sectorId;
    private final String checkSum;
    private Boolean isValid;

    private final static Pattern pattern = Pattern.compile("^([a-z-]+)-(\\d+)\\[([a-z]{5})\\]$");
    private Map<Character, Integer> frequency;

    public Room(final String rawRoomData) {
        Matcher matcher = pattern.matcher(rawRoomData);

        if (matcher.find()) {
            this.encryptedName = matcher.group(1);
            this.sectorId = Long.parseLong(matcher.group(2));
            this.checkSum = matcher.group(3);
            this.frequency = new HashMap<>();

            for (Character c : this.encryptedName.toCharArray()) {
                if (c != '-') {
                    frequency.put(c, 1 + frequency.getOrDefault(c, 0));
                }
            }
        }
        else {
            throw new IllegalArgumentException("Unable to parse room data: " + rawRoomData);
        }
    }

    public boolean isValid() {
        if (this.isValid == null) {
            List<Character> sorted = new ArrayList<>(frequency.keySet());
            sorted.sort((a, b) -> {
                if (this.frequency.get(b) == this.frequency.get(a)) {
                    return a.compareTo(b);
                }
                return this.frequency.get(b).compareTo(this.frequency.get(a));
            });

            String[] sortedArray = new String[5];
            for (int i = 0; i < 5; i++) {
                sortedArray[i] = String.valueOf(sorted.get(i));
            }
            return String.join("", sortedArray).equals(this.checkSum);
        }
        else {
            return this.isValid;
        }
    }
}
