import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

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
        private int threshold;
        @Override
        protected void setup(Context context) throws IOException, InterruptedException
        {
            Configuration configuration = context.getConfiguration();
            threshold = configuration.getInt("threshold", 20);

        }

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
            int count = Integer.parseInt(wordsPlusCount[1]);
            if(count < threshold)
            {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            for(int i = 0; i < words.length - 1; i++)
            {
                stringBuilder.append(words[i]);
                stringBuilder.append(" ");
            }
            String outputKey = stringBuilder.toString().trim();
            String outputValue = words[words.length - 1] + "=" + count;
            context.write(new Text(outputKey), new Text(outputValue));
        }
    }
    public static class Reduce extends Reducer<Text, Text, DBOutputWritable, NullWritable>
    {

    }
}
