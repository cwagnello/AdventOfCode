package com.cwagnello;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class Player {
    private int hitPoints;
    private Weapon weapon;
    private Set<Armor> armorPieces;
    private Set<Ring> rings;

    public Player() {
        this.armorPieces = new HashSet<>();
        this.rings = new HashSet<>();
    }

    public Player(int hitPoints, Weapon weapon, Set<Armor> armorPieces, Set<Ring> rings) {
        this.hitPoints = hitPoints;
        this.weapon = weapon;
        this.armorPieces = armorPieces == null ? new HashSet<>() : armorPieces;
        this.rings = rings == null ? new HashSet<>() : rings;
    }
    public void configure(int bitmask, List<Item> items) {

        for (int i = 0; i < items.size(); i++) {
            if (((bitmask >> i) & 1) == 1) {
                switch(items.get(i)) {
                    case Weapon w -> this.setWeapon(w);
                    case Armor a -> this.add(a);
                    case Ring r -> this.add(r);
                    default -> throw new IllegalStateException("Item type not found: " + items.get(i));
                }
            }
        }
    }

    public boolean winsFight(Player boss) {
        int bossRemainingHitpoints = boss.getHitPoints();
        int playerRemainingHitPoints = this.getHitPoints();

        while (playerRemainingHitPoints > 0 && bossRemainingHitpoints > 0) {
            int damageToBoss = this.getWeapon().getDamage() - boss.armorPieces.stream().mapToInt(Armor::getArmor).sum() + this.rings.stream().mapToInt(Ring::getDamage).sum();
            bossRemainingHitpoints -= Math.max(1, damageToBoss);
            if (bossRemainingHitpoints <= 0) {
                break;
            }
            int damageToPlayer = boss.getWeapon().getDamage() - this.armorPieces.stream().mapToInt(Armor::getArmor).sum() - this.rings.stream().mapToInt(Ring::getArmor).sum();
            playerRemainingHitPoints -= Math.max(1, damageToPlayer);
        }
        return playerRemainingHitPoints > 0;
    }

    public int calculateEquipmentCost() {
        int cost = 0;
        cost += this.weapon.getCost();
        cost += this.armorPieces.stream().mapToInt(Armor::getCost).sum();
        cost += this.rings.stream().mapToInt(Ring::getCost).sum();
        return cost;
    }

    public void add(Armor armor) {
        this.armorPieces.add(armor);
    }
    public void add(Ring ring) {
        this.rings.add(ring);
    }
}
