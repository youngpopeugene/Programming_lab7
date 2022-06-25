package main.java.util;

import main.java.collection.*;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private final Console console;
    private Session session;

    public Validator(Console aConsole) {
        console = aConsole;
    }

    public void setSession(Session aSession) {
        this.session = aSession;
    }

    public Command splitString(String inputString) {
        String command;
        String arg;
        String line = inputString;
        Pattern commandName = Pattern.compile("^\\w+\\s+");
        Pattern argName = Pattern.compile("^.+");

        Matcher matcher = commandName.matcher(line);
        if (matcher.find()) command = matcher.group();
        else return null;

        line = line.substring(command.length());
        matcher = argName.matcher(line);
        arg = matcher.find() ? matcher.group() : "";

        return new Command(command.trim(), arg.trim(), session.getUsername());
    }

    public HumanBeing validateHuman() {
        if (console.isReadFromFileStatus()) {
            return validateHumanFromFile();
        }
        System.out.println(Text.getBlueText("Name:"));
        String name = console.nextLine();
        while (name.trim().equals("")) {
            System.out.println(Text.getRedText("Input error, please try again!"));
            name = console.nextLine();
        }

        System.out.println(Text.getBlueText("Coordinate x:"));
        Long x = (Long) read(Long::valueOf);
        while (x > 695) {
            System.out.println(Text.getRedText("Max value of coordinate X : 695!"));
            x = (Long) read(Long::valueOf);
        }

        System.out.println(Text.getBlueText("Coordinate y:"));
        Long y = (Long) read(Long::valueOf);

        System.out.println(Text.getBlueText("realHero (true or false):"));
        boolean realHero = false;
        String strRealHero = (String) read(String::valueOf);
        while (!strRealHero.equals("true") && !strRealHero.equals("false")) {
            System.out.println(Text.getRedText("Input error, please try again!"));
            strRealHero = (String) read(String::valueOf);
        }
        if (strRealHero.equals("true")) realHero = true;

        System.out.println(Text.getBlueText("HasToothpick (true or false):"));
        boolean hasToothpick = false;
        String strHasToothpick = (String) read(String::valueOf);
        while (!strHasToothpick.equals("true") && !strHasToothpick.equals("false")) {
            System.out.println(Text.getRedText("Input error, please try again!"));
            strHasToothpick = (String) read(String::valueOf);
        }
        if (strHasToothpick.equals("true")) hasToothpick = true;

        System.out.println(Text.getBlueText("ImpactSpeed:"));
        Float impactSpeed = (Float) read(Float::valueOf);

        System.out.println(Text.getBlueText("MinutesOfWaiting:"));
        Long minutesOfWaiting = (Long) read(Long::valueOf);

        System.out.println(Text.getBlueText("WeaponType (HAMMER, AXE, PISTOL, SHOTGUN or BAT):"));
        WeaponType weaponType = (WeaponType) read(WeaponType::valueOf);

        System.out.println(Text.getBlueText("Mood (SADNESS, APATHY, CALM, RAGE or FRENZY):"));
        Mood mood = (Mood) read(Mood::valueOf);

        System.out.println(Text.getBlueText("Car.cool (true or false):"));
        boolean cool = false;
        String strCool = (String) read(String::valueOf);
        while (!strCool.equals("true") && !strCool.equals("false")) {
            System.out.println(Text.getRedText("Input error, please try again!"));
            strCool = (String) read(String::valueOf);
        }
        if (strCool.equals("true")) cool = true;

        return new HumanBeing(name, new Coordinates(x, y), realHero, hasToothpick, impactSpeed,
                minutesOfWaiting, weaponType, mood, new Car(cool));
    }

    public HumanBeing validateHumanFromFile() {
        try {
            String name = console.nextLine();
            if (name.trim().equals("")) {
                throw new Exception();
            }

            Long x = (Long) read(Long::valueOf);
            if (x > 695) {
                throw new Exception();
            }

            Long y = (Long) read(Long::valueOf);

            boolean realHero = false;
            String strRealHero = (String) read(String::valueOf);
            if (!strRealHero.equals("true") && !strRealHero.equals("false")) {
                throw new Exception();
            }
            if (strRealHero.equals("true")) realHero = true;

            boolean hasToothpick = false;
            String strHasToothpick = (String) read(String::valueOf);
            while (!strHasToothpick.equals("true") && !strHasToothpick.equals("false")) {
                throw new Exception();
            }
            if (strHasToothpick.equals("true")) hasToothpick = true;

            Float impactSpeed = (Float) read(Float::valueOf);

            Long minutesOfWaiting = (Long) read(Long::valueOf);

            WeaponType weaponType = (WeaponType) read(WeaponType::valueOf);

            Mood mood = (Mood) read(Mood::valueOf);

            boolean cool = false;
            String strCool = (String) read(String::valueOf);
            if (!strCool.equals("true") && !strCool.equals("false")) {
                throw new Exception();
            }
            if (strCool.equals("true")) cool = true;

            return new HumanBeing(name, new Coordinates(x, y), realHero, hasToothpick, impactSpeed,
                    minutesOfWaiting, weaponType, mood, new Car(cool));
        } catch (Exception e) {
            return null;
        }
    }

    public boolean validateCommand(Command command) {
        String name = command.getCommandName();
        String argument = command.getArgName();
        if (name.equals("add") && argument.equals("")) return true;
        else if (name.equals("add_if_min") && argument.equals("")) return true;
        else if (name.equals("clear") && argument.equals("")) return true;
        else if (name.equals("help") && argument.equals("")) return true;
        else if (name.equals("info") && argument.equals("")) return true;
        else if (name.equals("max_by_real_hero") && argument.equals("")) return true;
//        else if (name.equals("save") && argument.equals("")) return true;
        else if (name.equals("show") && argument.equals("")) return true;
        else if (name.equals("remove_greater") && argument.equals("")) return true;
        else if (name.equals("remove_lower") && argument.equals("")) return true;
        else if (name.equals("execute_script")) return true;
        else if (name.equals("exit") && argument.equals("")) return true;
        else if (name.equals("count_greater_than_weapon_type")) {
            try {
                WeaponType.valueOf(argument);
            } catch (Exception e) {
                return false;
            }
            return true;
        } else if (name.equals("remove_by_id") || name.equals("update")) {
            try {
                Long.valueOf(argument);
                if (Long.parseLong(argument) < 0) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
            return true;
        } else if (name.equals("filter_by_impact_speed")) {
            try {
                Float.valueOf(argument);
            } catch (Exception e) {
                return false;
            }
            return true;
        } else return false;
    }

    private Object read(Function<String, Object> check) {
        while (true) {
            String str = console.nextLine();
            if (!console.isReadFromFileStatus()) {
                try {
                    return check.apply(str);
                } catch (Exception e) {
                    System.out.println(Text.getRedText("Input error, please try again!"));
                }
            } else {
                return check.apply(str);
            }
        }
    }
}
