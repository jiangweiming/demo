package org.skywing.demo.storm.bolt;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

public class WordCounter implements IRichBolt
{
	private static final long serialVersionUID = 1385013616399638124L;
	private Map<String, Integer> counters;
	private OutputCollector collector;
	private String componentName;
	private Integer taskId;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector)
	{
		this.counters = new HashMap<String, Integer>();
		this.collector = collector;
		this.componentName = context.getThisComponentId();
		this.taskId = context.getThisTaskId();
	}

	@Override
	public void execute(Tuple input)
	{
		String word = input.getString(0);
		if (!this.counters.containsKey(word))
		{
			this.counters.put(word, 1);
		}
		else
		{
			this.counters.put(word, this.counters.get(word) + 1);
		}
		this.collector.ack(input);
	}

	@Override
	public void cleanup()
	{
		System.out.println("-- Word Counter [" + this.componentName + "-" + this.taskId + "] --");
		for (Entry<String, Integer> entry : this.counters.entrySet())
		{
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer)
	{
		
	}

	@Override
	public Map<String, Object> getComponentConfiguration()
	{
		return null;
	}

}
