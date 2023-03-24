package org.example;

import java.io.*;


import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.FileReader;
import java.util.*;

public class Main {
    @Option(name = "-i", usage = "String comparisons should be case-insensitive")
    private boolean register;
    @Option(name = "-u", usage = "Only unique strings", forbids = {"-c"})
    private boolean unique;
    @Option(name = "-c", usage = "Printing the number of lines that have been replaced", forbids = {"-u"})
    private boolean change;
    @Option(name = "-s", usage = "Ignore first (default: 0) symbols of each string", metaVar = "num")
    private int num;
    @Option(name = "-o", usage = "Output file (default: console)", metaVar = "ofile")
    private String ofile;

    @Argument
    private List<String> arguments = new ArrayList<String>();

    public static void main(String[] args) throws CmdLineException, IOException {
        System.out.println(Arrays.toString(args));
        if (!Arrays.toString(args).matches("\\[(-[iuc], ){0,3}(-s, [0-9]+, )?(-o, [^-]+)?")) {
            throw new IllegalArgumentException("Некорректный ввод");
        }
        new Main().parser(args);
    }

    void parser(String[] args) throws CmdLineException, IOException {
        CmdLineParser parser = new CmdLineParser(this);
        parser.parseArgument(args);
        algorithm(arguments, register, unique, change, num, ofile);
    }

    void algorithm(List<String> arguments, boolean register, boolean unique, boolean change, int num, String ofile) throws IOException {
        int colivo = 0;
        String temp = "";
        String line = "";
        File file = new File(arguments.get(0));
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        Scanner abc = new Scanner(System.in);
        List<String> lines = new ArrayList<>();
        while (!Objects.equals(line, "`") && line != null) {
            if (arguments.isEmpty()) {
                if (register) {
                    line = abc.nextLine().toLowerCase();
                } else {
                    line = abc.nextLine();
                }
            } else {
                if (register) {
                    try {
                        line = reader.readLine().toLowerCase();
                    } catch (Exception e) {
                        line = reader.readLine();
                    }
                } else {
                    line = reader.readLine();
                }
            }
            if (num > 0) {
                line = removefirstChar(line, num);
            }
            if (temp.equals(line) || colivo == 0) {
                colivo++;
            } else {
                if (colivo > 1 && !unique) {
                    if (change) {
                        lines.add(colivo + temp);
                    } else {
                        lines.add(temp);
                    }
                } else if (colivo == 1) {
                    lines.add(temp);
                }
                colivo = 1;
            }
            temp = line;
        }
        inputer(lines, ofile);
    }

    private String removefirstChar(String str, Integer num) {
        if (str == null || str.length() == 0 || str.equals("`")) {
            return str;
        }
        if (str.length() > num) {
            return str.substring(num);
        } else {
            return "";
        }
    }

    private void inputer(List<String> list, String ofile) throws IOException {
        if (ofile == null) {
            for (String line : list) {
                System.out.println(line);
            }
        } else {
            FileWriter writer = new FileWriter(ofile, false);
            for (String line : list) {
                writer.write(line);
                writer.write("\n");
                writer.flush();
            }
        }
    }
}