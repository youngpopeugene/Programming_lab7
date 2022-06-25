package main.java.collection;

import java.io.Serializable;

/**
 * Enum for human's weapon
 */
public enum WeaponType implements Serializable {
    HAMMER("HAMMER"),
    AXE("AXE"),
    PISTOL("PISTOL"),
    SHOTGUN("SHOTGUN"),
    BAT("BAT");

    private final String weaponType;

    WeaponType(String weaponType) {
        this.weaponType = weaponType;
    }

    public String getStringWeaponType() {
        return weaponType;
    }

    @Override
    public String toString() {
        return "WeaponType{" +
                "weaponType='" + weaponType + '\'' +
                '}';
    }
}
