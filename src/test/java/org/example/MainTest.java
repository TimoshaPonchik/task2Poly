package org.example;

import org.kohsuke.args4j.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

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
        new MainTest().args(args);
    }

    void args(String[] args) throws CmdLineException, IOException {
        CmdLineParser parser = new CmdLineParser(this);
        parser.parseArgument(args);
        new Main().algorithm(arguments, register, unique, change, num, ofile);
        tester();
    }

    void tester() throws IOException {
        //argument: -i -c -s 1 -o C:\output.txt C:\input.txt
        String fileOut = "C:\\test.txt";

        //input
        FileWriter writer1 = new FileWriter(arguments.get(0), false);
        List<String> dict1 = new ArrayList<String>();
        dict1.add("Abv");
        dict1.add("aBV");
        dict1.add("ABv");
        dict1.add("bca");
        for (String line : dict1) {
            writer1.write(line);
            writer1.write("\n");
            writer1.flush();
        }

        //output
        FileWriter writer2 = new FileWriter(fileOut, false);
        List<String> dict2 = new ArrayList<String>();
        dict2.add("3bv");
        dict2.add("ca");
        for (String line : dict2) {
            writer2.write(line);
            writer2.write("\n");
            writer2.flush();
        }

        String line1 = "";
        String line2 = "";
        File file1 = new File(fileOut);
        File file2 = new File(ofile);
        FileReader fr1 = new FileReader(file1);
        FileReader fr2 = new FileReader(file2);
        BufferedReader reader1 = new BufferedReader(fr1);
        BufferedReader reader2 = new BufferedReader(fr2);
        while (line1 != null && line2 != null) {
            line1 = reader1.readLine();
            line2 = reader2.readLine();
            System.out.println(line1);
            System.out.println(line2);
            assertEquals(line1, line2);
        }
    }
}