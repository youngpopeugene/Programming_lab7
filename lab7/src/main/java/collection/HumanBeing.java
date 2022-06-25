package main.java.collection;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class for humans
 */
public class HumanBeing extends Object implements Comparable<HumanBeing>, Serializable {
    private long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final Coordinates coordinates; //Поле не может быть null
    private final LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private final boolean realHero;
    private final boolean hasToothpick;
    private final Float impactSpeed; //Поле может быть null
    private final long minutesOfWaiting;
    private final WeaponType weaponType; //Поле может быть null
    private final Mood mood; //Поле не может быть null
    private final Car car; //Поле не может быть null

    public HumanBeing(String name, Coordinates coordinates, boolean realHero,
                      boolean hasToothpick, Float impactSpeed, long minutesOfWaiting, WeaponType weaponType, Mood mood, Car car) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.minutesOfWaiting = minutesOfWaiting;
        this.weaponType = weaponType;
        this.mood = mood;
        this.car = car;
    }

    public HumanBeing(Long id, String name, Coordinates coordinates, LocalDateTime creationDate, boolean realHero,
                      boolean hasToothpick, Float impactSpeed, long minutesOfWaiting, WeaponType weaponType, Mood mood, Car car) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.minutesOfWaiting = minutesOfWaiting;
        this.weaponType = weaponType;
        this.mood = mood;
        this.car = car;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isRealHero() {
        return realHero;
    }

    public Float getImpactSpeed() {
        return impactSpeed;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public Mood getMood() {
        return mood;
    }

    public Car getCar() {
        return car;
    }

    public boolean isHasToothpick() {
        return hasToothpick;
    }

    public long getMinutesOfWaiting() {
        return minutesOfWaiting;
    }

    @Override
    public int compareTo(HumanBeing o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HumanBeing that = (HumanBeing) o;
        return realHero == that.realHero && hasToothpick == that.hasToothpick
                && minutesOfWaiting == that.minutesOfWaiting && Objects.equals(id, that.id)
                && Objects.equals(name, that.name) && Objects.equals(coordinates, that.coordinates)
                && Objects.equals(creationDate, that.creationDate)
                && Objects.equals(impactSpeed, that.impactSpeed) && weaponType == that.weaponType && mood == that.mood
                && Objects.equals(car, that.car);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, realHero, hasToothpick, impactSpeed, minutesOfWaiting, weaponType, mood, car);
    }

    @Override
    public String toString() {
        return "HumanBeing { " + "id = " + id +
                ", name = " + name +
                ", coordinate X = " + coordinates.getX() +
                ", coordinate Y = " + coordinates.getY() +
                ", creationDate = " + creationDate +
                ", realHero = " + realHero +
                ", hasToothpick = " + hasToothpick +
                ", impactSpeed = " + impactSpeed +
                ", minutesOfWaiting = " + minutesOfWaiting +
                ", weaponType = " + weaponType.getStringWeaponType() +
                ", mood = " + mood.getMood() +
                ", car.cool = " + car.getCool()
                + " }";
    }
}
