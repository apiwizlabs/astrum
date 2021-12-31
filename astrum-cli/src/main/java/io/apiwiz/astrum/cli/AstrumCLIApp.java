package io.apiwiz.astrum.cli;

import io.apiwiz.astrum.cli.cmd.LinterCommand;
import picocli.CommandLine;

public class AstrumCLIApp {


    public static void main(String[] args) {
        int exitCode = new CommandLine(new LinterCommand()).execute(args);
    }

}
