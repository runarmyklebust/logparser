package me.myklebust.logparser;

import nl.basjes.parse.core.Field;

public class Entry
{
    private String uri;

    private int kb;

    private int ms;

    private int seconds;

    private String time;

    private String date;

    private String queryString;

    private String host;

    private String path;

    @Field("HTTP.URI:request.firstline.original.uri")
    public void requestUri( final String value )
    {
        uri = value;
    }

    @Field("HTTP.HOST:request.firstline.original.uri.host")
    public void host( final String value )
    {
        host = value;
    }

    @Field("TIME.TIME:request.receive.time.last.time_utc")
    public void time( final String time )
    {
        this.time = time;
    }

    @Field("BYTES:response.bytes")
    public void bytes( final String value )
    {
        kb = Integer.parseInt( value ) / 1024;
    }

    @Field("HTTP.PATH:request.firstline.original.uri.path")
    public void path( final String value )
    {
        path = value;
    }


    @Field("MICROSECONDS:response.server.processing.time.original")
    public void setTimeUsed( final String ms )
    {
        this.ms = Integer.parseInt( ms );

        this.seconds = ( this.ms / 1_000_000 );
    }


    @Field("TIME.DATE:request.receive.time.last.date_utc")
    public void setDate( final String val )
    {
        this.date = val;
    }

    @Field("HTTP.QUERYSTRING:request.firstline.uri.query")
    public void queryString( final String queryString )
    {
        this.queryString = queryString;
    }

    public String getUri()
    {
        return uri;
    }

    public Integer getKb()
    {
        return kb;
    }

    public int getMs()
    {
        return ms;
    }

    public int getSeconds()
    {
        return seconds;
    }


    public String getTime()
    {
        return time;
    }

    public String getDate()
    {
        return date;
    }

    public String getHost()
    {
        return host;
    }

    public String getPath()
    {
        return path;
    }
}

