import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import java.io.IOException;

/**
 * Created by zzc on 7/22/17.
 */
public class Driver
{
    public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException
    {
        //inputDir
        //outputDir
        //NGram
        //threshold
        //topK
        String inputDir = args[0];
        String outputDir = args[1];
        String nGrams= args[2];
        String threshold = args[3];
        String topK = args[4];

        // job1
        Configuration configuration1 = new Configuration();
        configuration1.set("textinputformat.record.delimiter", ".");
        configuration1.set("nGram", nGrams);

        Job job1 = Job.getInstance();
        job1.setJobName("NGram");
        job1.setJarByClass(Driver.class);

        job1.setMapperClass(NGramLibraryBuilder.NGramMapper.class);
        job1.setReducerClass(NGramLibraryBuilder.NGramReducer.class);

        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(IntWritable.class);

        job1.setInputFormatClass(TextInputFormat.class);
        job1.setOutputFormatClass(org.apache.hadoop.mapreduce.lib.output.TextOutputFormat.class);

        TextInputFormat.setInputPaths(job1, new Path(inputDir));
        TextOutputFormat.setOutputPath(job1, new Path(outputDir));
        job1.waitForCompletion(true);

        //connect two jobs, last output is the next input
        // job2
        Configuration configuration2 = new Configuration();
        configuration2.set("threshold", threshold);
        configuration2.set("n", topK);

        DBConfiguration.configureDB(configuration2, "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/test", "root", "238604");
        Job job2 = Job.getInstance(configuration2);
        job2.setJobName("Model");
        job2.setJarByClass(Driver.class);

//        job2.addArchiveToClassPath(new Path("path_to_ur_connector"));
        job2.setMapperClass(LanguageModel.Map.class);
        job2.setReducerClass(LanguageModel.Reduce.class);

        job2.setMapOutputKeyClass(Text.class);
        job2.setMapOutputValueClass(Text.class);
        job2.setOutputKeyClass(DBOutputWritable.class);
        job2.setOutputValueClass(NullWritable.class);

        job2.setInputFormatClass(TextInputFormat.class);
        job2.setOutputFormatClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job2, "output", new String[] {"starting_phrase", "following_word", "count"});

        TextInputFormat.setInputPaths(job2, args[1]);
        TextOutputFormat.setOutputPath(job1, new Path(outputDir));
        job2.waitForCompletion(true);
    }
}
