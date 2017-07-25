import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by zzc on 7/24/17.
 */
public class LanguageModel
{
    public static class Map extends Mapper<LongWritable, Text, Text, Text>
    {
        // input: I love big data \t 10
        // output: key = I love big value = data=10

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
        {
            String line = value.toString().trim();
            String[] wordsPlusCount = line.split("\t");
            if(wordsPlusCount.length < 2)
            {
                return;
            }
            String[] words = wordsPlusCount[0].split("\\s+");

        }
    }
    public static class Reduce extends Reducer<Text, Text, DBOutputWritable, NullWritable>
    {

    }
}
