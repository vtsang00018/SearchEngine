package com.searchengine.codeu;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.junit.Assert;
import redis.clients.jedis.Protocol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Vincent on 7/27/2016.
 */
public class CommandLine {
    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = { "-search" }, description = "Level of verbosity")
    private String terms;


    public static void main(String[] args) {
        CommandLine jct = new CommandLine();

        // Change search_query variable for new search
        String search_query = "test and more tests";
        String[] argv = {"-search", search_query};
        new JCommander(jct, argv);
        System.out.print(jct.terms);
    }
}
