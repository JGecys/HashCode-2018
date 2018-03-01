package edu.ktu.skills;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static final String INPUT_FILE = "";
    public static final String OUTPUT_FILE = "";

    void run() throws IOException {
        read(INPUT_FILE);

        write(OUTPUT_FILE);
    }

    void read(String file) throws IOException {
        FileInputStream stream = new FileInputStream(file);
        Scanner scanner = new Scanner(stream);
        

        scanner.close();
    }

    void write(String file) throws IOException {
        FileWriter writer = new FileWriter(file);

        writer.close();
    }

    public static void main(String[] args) throws IOException {
        // write your code here
        new Main().run();
    }


}
