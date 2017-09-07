package me.myklebust.logparser;

import java.util.Comparator;

public class LogEntryComperator
    implements Comparator<Entry>
{

    @Override
    public int compare( final Entry o1, final Entry o2 )
    {
        return o1.getMs() - o2.getMs();
    }
}
