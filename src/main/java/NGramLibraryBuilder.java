import org.apache.hadoop.conf.Configuration;
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

        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
        {
            //input a sentence
            //I love bigdata n = 3
            /* I love -> 1
            love bigdata -> 1
            I love big -> 1
            */
            Configuration conf = context.getConfiguration();
            int numGram = conf.getInt("numGram", 5);// 5 is default value, if get value is null
            String line = value.toString().trim().toLowerCase().replaceAll("[^a-z]", " ");
            String[] words = line.split("\\s+");
            if(words.length < 2)
            {
                return;
            }
            StringBuilder stringBuilder;
            for(int i = 0; i < words.length; i++)
            {
                stringBuilder = new StringBuilder();
                stringBuilder.append(words[i]);

            }
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
