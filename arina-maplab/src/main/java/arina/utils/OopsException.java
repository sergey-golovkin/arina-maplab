package arina.utils;

import java.util.List;
import java.util.Map;

public class OopsException extends Exception
{
    final List<String> stackTrace;
    final Map<String, String> dump;

    public OopsException(String msg, List<String> stackTrace, Map<String, String> dump, Exception e)
    {
        super(msg, e);

        this.stackTrace = stackTrace;
        this.dump = dump;
    }

    public List<String> stackTrace()
    {
        return stackTrace;
    }

    @Override
    public String toString()
    {
        String msg = this.getMessage();
        StringBuilder sb = new StringBuilder(msg == null ? "" : msg);
        sb.append("\n\nStackTrace:\n");
        stackTrace.forEach(v -> sb.append(v).append("\n"));
        sb.append("\n\nDump:\n");
        dump.forEach((k, v) -> sb.append(k + " value:" + v).append("\n\n"));

        return sb.toString();
    }
}
