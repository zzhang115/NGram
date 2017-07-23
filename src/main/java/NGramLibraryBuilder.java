import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by zzc on 7/22/17.
 */
public class NGramLibraryBuilder
{
    public static class NGramMapper extends Mapper<LongWritable, Text, Text, IntWritable>
    {
        @Override
        protected void setup(Context context) throws IOException, InterruptedException
        {
            super.setup(context);
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
        {
            super.map(key, value, context);
        }
    }
    public static class NGramReducer extends Reducer<Text, IntWritable, Text, IntWritable>
    {
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException
        {
            super.reduce(key, values, context);
        }
    }

}
