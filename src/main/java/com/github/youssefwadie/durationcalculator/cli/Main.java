package com.github.youssefwadie.durationcalculator.cli;

import com.github.youssefwadie.durationcalculator.core.*;
import com.github.youssefwadie.durationcalculator.core.walker.FileWalker;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public class Main {
    private static final String HEADER = "traverse the file system tree to calculate the total videos' duration";
    public static final String APP_NAME = "videos-duration";

    public static void main(String[] args) throws ParseException, IOException {
        Optional<TraversalDetails> traversalDetails = parseArgs(args);
        if (traversalDetails.isPresent()) {
            FileWalker visitor = new FileWalker(traversalDetails.get(), BasicScannersProvider.videoScanner());
            String out = visitor.visit();
            System.out.println(out);
        }
    }


    private static Optional<TraversalDetails> parseArgs(String... args) throws ParseException {
        final Option filesAssociationsOption = Option.builder()
                .option("t").longOpt("types").argName("file types").desc("file types to consider, default is mp4 and mkv")
                .numberOfArgs(Option.UNLIMITED_VALUES)
                .valueSeparator(' ')
                .build();

        final Option verboseOption = Option.builder()
                .option("v").longOpt("verbose")
                .hasArg(false).required(false)
                .desc("print errors, default mode is quite").build();


        final Option pathOption = Option.builder()
                .option("p").longOpt("path").hasArg()
                .desc("starting path to traverse").build();

        final Option depthOption = Option.builder()
                .option("d").longOpt("depth").hasArg()
                .desc("maximum depth").type(Integer.TYPE).build();

        final Option helpOption = Option.builder().
                option("h").longOpt("help").hasArg(false)
                .desc("display this help and exit").build();


        Options options = new Options()
                .addOption(filesAssociationsOption)
                .addOption(verboseOption)
                .addOption(pathOption)
                .addOption(depthOption)
                .addOption(helpOption);


        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        if (cmd.hasOption(helpOption)) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(Main.APP_NAME, Main.HEADER, options, "", true);
            return Optional.empty();
        }
        String[] filesAssociations = cmd.hasOption(filesAssociationsOption) ?
                cmd.getOptionValues(filesAssociationsOption) : new String[]{"mp4", "mkv"};

        boolean verbose = cmd.hasOption(verboseOption);

        Path startingPath = (cmd.hasOption(pathOption) ?
                Path.of(cmd.getOptionValue(pathOption)) : Path.of(""))
                .toAbsolutePath();

        int depth = cmd.hasOption(depthOption) ?
                Integer.parseInt(cmd.getOptionValue(depthOption)) : Integer.MAX_VALUE;

        return Optional.of(new TraversalDetails(filesAssociations, verbose, startingPath, depth));
    }
}
