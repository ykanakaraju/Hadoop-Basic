package com.bigdataminds.facebookdataset.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.bigdataminds.facebookdataset.util.FacebookDatasetUtil;

public class FacebookDatasetAgeMapper extends Mapper<LongWritable, Text, NullWritable, Text> {
	
	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {
		super.setup(context);
	}

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		String[] split = line.split("\\t");
		if (split.length >= FacebookDatasetUtil.YEAR_COLUMN_INDEX + 1) {
			String yearString = split[FacebookDatasetUtil.YEAR_COLUMN_INDEX];
			if (yearString != null) {
				Long year = FacebookDatasetUtil.getLong(yearString);
				if (year != null && year >= FacebookDatasetUtil.START_YEAR && year <= FacebookDatasetUtil.END_YEAR) {
					context.write(NullWritable.get(), value);
				}
			}
			
		}
	}
	
	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {
		super.cleanup(context);
	}

}
