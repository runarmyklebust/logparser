package me.myklebust.logparser;

public class JsonEntry
{
    private String log;

    private String stream;

    private String time;

    public JsonEntry()
    {

    }

    public String getLog()
    {
        return log;
    }

    public void setLog( final String log )
    {
        this.log = log;
    }

    public String getStream()
    {
        return stream;
    }

    public void setStream( final String stream )
    {
        this.stream = stream;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime( final String time )
    {
        this.time = time;
    }
}
