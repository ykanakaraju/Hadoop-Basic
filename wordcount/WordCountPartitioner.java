package com.sample;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class WordCountPartitioner extends Partitioner<Text, LongWritable> {

	  public int getPartition(Text key, LongWritable value, int numPartitions) {

	  return (key.toString().substring(0, 1).hashCode()) % numPartitions;

	  }

	}