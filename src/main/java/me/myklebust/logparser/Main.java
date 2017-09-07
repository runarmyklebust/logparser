package me.myklebust.logparser;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;

import nl.basjes.parse.httpdlog.ApacheHttpdLoglineParser;

public class Main
{
    private final static String LOGFORMAT = "%v:%p %h %l %u %t \"%r\" %>s %O \"%{Referer}i\" \"%{User-Agent}i\" %D";

    public static void main( final String... args )
    {
        if ( args.length != 1 )
        {
            throw new IllegalArgumentException( "Missing parameter file" );
        }

        File file = new File( args[0] );

        final CharSource charSource = getSource( file, args );

        final Set<Entry> entries = getEntries( charSource );

        writeEntries( entries );
    }

    private static void writeEntries( final Set<Entry> entries )
    {
        File slow = new File( "slow.log" );
        slow.delete();

        File all = new File( "output.log" );
        all.delete();

        for ( final Entry entry : entries )
        {
            writeEntry( slow, all, entry );
        }
    }

    private static void writeEntry( final File slow, final File all, final Entry entry )
    {
        try
        {
            final String value = createLogEntryString( entry );

            if ( entry.getSeconds() > 5 )
            {
                Files.append( value + "\n", slow, Charsets.UTF_8 );
            }

            Files.append( value + "\n", all, Charsets.UTF_8 );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    private static String createLogEntryString( final Entry entry )
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( entry.getDate() + "T" + entry.getTime() );
        builder.append( " " );
        builder.append( entry.getSeconds() );
        builder.append( " " );
        builder.append( entry.getUri() );
        builder.append( " " );
        builder.append( entry.getKb() );
        builder.append( "KB" );
        return builder.toString();
    }

    private static CharSource getSource( final File file, final String[] args )
    {
        if ( !file.exists() )
        {
            throw new IllegalArgumentException( "File:  " + args[0] + " does not exist" );
        }

        return Files.asCharSource( file, Charsets.UTF_8 );
    }

    private static Set<Entry> getEntries( final CharSource charSource )
    {
        try
        {
            return charSource.readLines( new LogLineProcessor( new ApacheHttpdLoglineParser<Entry>( Entry.class, LOGFORMAT ) ) );

        }
        catch ( IOException e )
        {
            throw new RuntimeException( "Cannot parse log", e );
        }
    }

}
