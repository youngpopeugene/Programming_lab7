package main.java.commands;

import main.java.util.Console;
import main.java.util.Text;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Execute_script {

    private final Console console;
    static final List<String> pathList = new ArrayList<>();

    public Execute_script(Console console) {
        this.console = console;
    }

    public static void deleteLastPath() {
        pathList.remove(pathList.size() - 1);
    }

    public static List<String> getPathList() {
        return pathList;
    }

    public void execute(String filePath) {
        try {
            if (!pathList.contains(filePath)) {
                File file = new File(filePath);
                Path path = Paths.get(filePath);
                console.setScanner(new Scanner(path));
                console.setReadFromFileStatus(true);
                pathList.add(filePath);
                System.out.println(Text.getGreenText("\nThe script is being executed!"));
            } else {
                System.out.println(Text.getRedText("\nThis script is already running!"));
            }
        } catch (Exception e) {
            System.out.println(Text.getRedText("\nWrong file path!"));
        }
    }
}
