package main.java.util;

import main.java.commands.Execute_script;

import java.util.List;
import java.util.Scanner;

public class Console {
    private Scanner scanner;
    private boolean readFromFileStatus;

    public Console() {
        scanner = new Scanner(System.in);
        readFromFileStatus = false;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void setReadFromFileStatus(boolean b) {
        readFromFileStatus = b;
    }

    public boolean isReadFromFileStatus() {
        return readFromFileStatus;
    }

    public String nextLine() {
        if (readFromFileStatus) {
            if (!scanner.hasNextLine()) {
                List<String> pathList = Execute_script.getPathList();
                Execute_script.deleteLastPath();
                if (pathList.size() > 0) scanner = new Scanner(pathList.get(pathList.size() - 1));
                else {
                    setReadFromFileStatus(false);
                    scanner = new Scanner(System.in);
                    System.out.println(Text.getBlueText("Enter the command:"));
                }

            }
        }
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }
}
