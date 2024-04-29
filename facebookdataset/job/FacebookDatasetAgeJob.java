package com.bigdataminds.facebookdataset.job;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
//import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.bigdataminds.facebookdataset.mapper.FacebookDatasetAgeMapper;

public class FacebookDatasetAgeJob implements Tool {

	private Configuration conf = new Configuration();

	@Override
	public Configuration getConf() {
		return conf;
	}

	@Override
	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	@Override
	public int run(String[] args) throws Exception {

		Job carJob = new Job(getConf());
		carJob.setJobName("FacebookAgeAggregationJob");
		carJob.setJarByClass(this.getClass());
		carJob.setMapperClass(FacebookDatasetAgeMapper.class);
		// carJob.setReducerClass(CarDataSetReducer.class);
		// carJob.setCombinerClass(CarDataSetCombiner.class);
		// carJob.setMapOutputKeyClass(NullWritable.class);
		// carJob.setMapOutputValueClass(Text.class);
		carJob.setOutputKeyClass(NullWritable.class);
		carJob.setOutputValueClass(Text.class);
		carJob.setNumReduceTasks(0);

		if (args == null || args.length < 2) {
			throw new RuntimeException("Invalid usage. Pass the input file path and output file path in the same order.");
		}
		FileInputFormat.setInputPaths(carJob, new Path(args[0]));
		FileOutputFormat.setOutputPath(carJob, new Path(args[1]));
		carJob.setNumReduceTasks(1);
		return carJob.waitForCompletion(true) == true ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		ToolRunner.run(new Configuration(), new FacebookDatasetAgeJob(), args);
	}

}
