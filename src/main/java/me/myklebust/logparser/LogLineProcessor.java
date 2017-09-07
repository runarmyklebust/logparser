package me.myklebust.logparser;

import java.io.IOException;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.google.common.io.LineProcessor;

import nl.basjes.parse.core.exceptions.DissectionFailure;
import nl.basjes.parse.core.exceptions.InvalidDissectorException;
import nl.basjes.parse.core.exceptions.MissingDissectorsException;
import nl.basjes.parse.httpdlog.ApacheHttpdLoglineParser;

public class LogLineProcessor
    implements LineProcessor<Set<Entry>>
{
    private final ApacheHttpdLoglineParser<Entry> parser;

    private final Set<Entry> entries;

    ObjectMapper mapper = new ObjectMapper();

    public LogLineProcessor( final ApacheHttpdLoglineParser<Entry> parser )
    {
        this.parser = parser;

        this.entries = Sets.newTreeSet( new LogEntryComperator() );
    }


    @Override
    public boolean processLine( final String line )
        throws IOException
    {
        JsonEntry jsonEntry = mapper.readValue( line, JsonEntry.class );

        try
        {
            entries.add( this.parser.parse( jsonEntry.getLog() ) );
        }
        catch ( DissectionFailure dissectionFailure )
        {
            //dissectionFailure.printStackTrace();
        }
        catch ( InvalidDissectorException e )
        {
            // e.printStackTrace();
        }
        catch ( MissingDissectorsException e )
        {
            //e.printStackTrace();
        }

        return true;
    }

    @Override
    public Set<Entry> getResult()
    {
        return this.entries;
    }
}
