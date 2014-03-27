package org.skywing.demo.storm;

import java.util.Map;

import backtype.storm.Config;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class WordNormalizer implements IRichBolt
{
	private static final long serialVersionUID = 6403910644793860780L;
	private Config conf;
	private OutputCollector collector;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector)
	{
		this.conf = (Config) stormConf;
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input)
	{
		String sentence = input.getString(0);
		String[] words = sentence.split(" ");
		for (String word : words)
		{
			word = word.trim();
			if (!word.isEmpty())
			{
				word = word.toLowerCase();
				this.collector.emit(input, new Values(word));
			}
		}
		this.collector.ack(input);
	}

	@Override
	public void cleanup()
	{
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer)
	{
		declarer.declare(new Fields("word"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration()
	{
		return this.conf;
	}

}
