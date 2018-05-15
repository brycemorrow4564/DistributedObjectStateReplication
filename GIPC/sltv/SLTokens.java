package sltv;
import java.io.StreamTokenizer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.*;

public class SLTokens
{
    StreamTokenizer slStreamTokenizer =
       //new StreamTokenizer (InputStreamReader(System.in));
    new StreamTokenizer(System.in);

    public int nextToken ()
       throws IOException
    {
        return (slStreamTokenizer.nextToken());
    }

    public boolean eof()
    {
        return (slStreamTokenizer.ttype == StreamTokenizer.TT_EOF);
    }

}