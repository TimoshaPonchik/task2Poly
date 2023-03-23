package org.example;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    @Option(name = "-i", usage = "String comparisons should be case-insensitive")
    private boolean register;
    @Option(name = "-u", usage = "Only unique strings")
    private boolean unique;
    @Option(name = "-c", usage = "Printing the number of lines that have been replaced")
    private boolean change;
    @Option(name = "-s", usage = "Ignore first (default: 0) symbols of each string", metaVar = "num")
    private int num;
    @Option(name = "-o", usage = "Output file (default: console)", metaVar = "ofile")
    private String ofile;

    @Argument
    private List<String> arguments = new ArrayList<String>();
    private Boolean checker = true;

    public static void main(String[] args) throws CmdLineException {
        new Main().parser(args);
    }

    private void parser(String[] args) throws CmdLineException {
        CmdLineParser parser = new CmdLineParser(this);
        parser.parseArgument(args);

        try {
            FileReader a = new FileReader(arguments.get(arguments.size() - 1));
        } catch (Exception e) {
            checker = false;
        }
        algorithm(arguments);
    }

    public void algorithm(List<String> list) {
        Scanner abc = new Scanner(System.in);
        int colivo = 0;
        String temp = "";
        boolean a = true;
        List<String> lines = new ArrayList<>();
        String line = "";
        while (!Objects.equals(line, "`")) {
            line = abc.nextLine();
            if (a || temp.equals(line)) {
                a = false;
                colivo++;
                temp = line;
            } else {
                if (colivo > 1) {
                    lines.add(colivo + temp);
                } else {
                    lines.add(temp);
                }
                temp = line;
                colivo = 1;
            }
        }
        System.out.println(lines);
    }

    public void doMethod(Scanner line) {

    }
}