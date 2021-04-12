package com.bigdataminds.facebookdataset.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.bigdataminds.facebookdataset.util.FacebookDatasetUtil;

public class FacebookDatasetMaxLikesMapper extends Mapper<LongWritable, Text, NullWritable, Text> {
	
	private long maxLikes = 0;
	
	private String maxLikesText;

	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		super.setup(context);
	}

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		String[] split = line.split("\\t");
		if (split.length >= FacebookDatasetUtil.LIKES_COLUMN_INDEX + 1) {
			String likesString = split[FacebookDatasetUtil.LIKES_COLUMN_INDEX];
			if (likesString != null) {
				Long numOfLikes = FacebookDatasetUtil.getLong(likesString);
				if (numOfLikes != null && numOfLikes > maxLikes) {
					maxLikes = numOfLikes;
					maxLikesText = line;
				}
			}
			
		}
	}
	
	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {
		super.cleanup(context);
		System.out.println(maxLikesText);
		context.write(NullWritable.get(), new Text(maxLikesText));
	}

}
