package com.sample;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
//import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.util.Tool;
public class WordCountJob implements Tool{
	private Configuration conf;
	@Override
	public Configuration getConf()
	{
	return conf;
	}
	@Override
	public void setConf(Configuration conf)
	{
		this.conf=conf;
	}
	@Override
	public int run(String []args)throws Exception
	{
		
		Job wordcountjob=new Job(getConf());
		wordcountjob.setJobName("mat word count");
		wordcountjob.setJarByClass(this.getClass());
		wordcountjob.setMapperClass(WordCountMapper.class);
		wordcountjob.setReducerClass(WordCountReducer.class);
		wordcountjob.setCombinerClass(WordCountCombiner.class);
		wordcountjob.setPartitionerClass(WordCountPartitioner.class);
		wordcountjob.setMapOutputKeyClass(Text.class);
		wordcountjob.setMapOutputValueClass(LongWritable.class);
		wordcountjob.setOutputKeyClass(Text.class);
		wordcountjob.setOutputValueClass(LongWritable.class);
		FileInputFormat.setInputPaths(wordcountjob,new Path(args[0]));
		FileOutputFormat.setOutputPath(wordcountjob,new Path(args[1]));
		wordcountjob.setNumReduceTasks(2);
		return wordcountjob.waitForCompletion(true)==true? 0:1;
	}
	public static void main(String []args)throws Exception
	{
		ToolRunner.run(new Configuration(),new WordCountJob(),args);
	}

}
