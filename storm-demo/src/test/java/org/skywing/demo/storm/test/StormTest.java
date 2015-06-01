package org.skywing.demo.storm.test;

import org.junit.Test;
import org.skywing.demo.storm.bolt.WordCounter;
import org.skywing.demo.storm.bolt.WordNormalizer;
import org.skywing.demo.storm.spout.WordReader;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class StormTest
{
	@Test
	public void testTopologyRun() throws InterruptedException
	{
		// Topology definition
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("word-reader", new WordReader());
		builder.setBolt("word-normalizer", new WordNormalizer())
				.shuffleGrouping("word-reader");
		builder.setBolt("word-counter", new WordCounter(), 2).fieldsGrouping(
				"word-normalizer", new Fields("word"));

		// Configuration
		Config conf = new Config();
		conf.put("wordsFile", "E:\\words.txt");
		conf.setDebug(false);

		// Topology run
		conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("started-example", conf,
				builder.createTopology());
		Thread.sleep(1000);
		cluster.shutdown();
	}
}
