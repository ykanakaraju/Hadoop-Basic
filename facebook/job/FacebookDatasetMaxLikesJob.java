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

import com.bigdataminds.facebookdataset.mapper.FacebookDatasetMaxLikesMapper;

public class FacebookDatasetMaxLikesJob implements Tool {

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

		Job maxLikesJob = new Job(getConf());
		maxLikesJob.setJobName("FacebookAgeAggregationJob");
		maxLikesJob.setJarByClass(this.getClass());
		maxLikesJob.setMapperClass(FacebookDatasetMaxLikesMapper.class);
		// carJob.setReducerClass(FacebookDatasetMaxLikesReducer.class);
		// carJob.setCombinerClass(FacebookDatasetMaxLikesCombiner.class);
		maxLikesJob.setNumReduceTasks(0);
		maxLikesJob.setMapOutputKeyClass(NullWritable.class);
		maxLikesJob.setMapOutputValueClass(Text.class);
		maxLikesJob.setOutputKeyClass(NullWritable.class);
		maxLikesJob.setOutputValueClass(Text.class);
		maxLikesJob.setNumReduceTasks(1);

		if (args == null || args.length < 2) {
			throw new RuntimeException("Invalid usage. Pass the input file path and output file path in the same order.");
		}
		FileInputFormat.setInputPaths(maxLikesJob, new Path(args[0]));
		FileOutputFormat.setOutputPath(maxLikesJob, new Path(args[1]));
		maxLikesJob.setNumReduceTasks(1);
		return maxLikesJob.waitForCompletion(true) == true ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		ToolRunner.run(new Configuration(), new FacebookDatasetMaxLikesJob(), args);
	}

}
