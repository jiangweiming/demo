package org.skywing.demo.storm.spout;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class WordReader implements IRichSpout
{
	private static final long serialVersionUID = -9061012106380959550L;

	private SpoutOutputCollector collector;
	private FileReader fileReader;
	private boolean completed = false;
	
	@Override
	public void ack(Object msgId)
	{
		System.out.println("ok: " + msgId);
	}

	@Override
	public void activate()
	{
		
	}

	@Override
	public void close()
	{
		try
		{
			this.fileReader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void deactivate()
	{
		
	}

	@Override
	public void fail(Object msgId)
	{
		System.out.println("Fail: " + msgId);
	}

	@Override
	public void nextTuple()
	{
		if (this.completed)
		{
			try
			{
				TimeUnit.SECONDS.sleep(1);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			return;
		}
		String str = null;
		BufferedReader reader = new BufferedReader(this.fileReader);
		try
		{
			while ((str = reader.readLine()) != null)
			{
				this.collector.emit(new Values(str), str);
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException("Error in reading tuple" + e);
		}
		finally
		{
			this.completed = true;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector)
	{
		System.out.println("######### word reader #########");
		try
		{
			this.fileReader = new FileReader(conf.get("wordsFile").toString());
		}
		catch (FileNotFoundException e)
		{
			throw new RuntimeException("Error reading file [" + conf.get("wordsFile") + "]");
		}
		this.collector = collector;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer)
	{
		declarer.declare(new Fields("line"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration()
	{
		return null;
	}

}
