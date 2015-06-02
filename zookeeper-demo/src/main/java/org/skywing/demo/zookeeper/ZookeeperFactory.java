package org.skywing.demo.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperFactory
{
	public static ZooKeeper getZooKeeper(String servers) throws IOException
	{
		CountDownLatch latch = new CountDownLatch(1);
		return getZooKeeper(servers, 3000, new DefaultInstanceWatcher(latch), latch);
	}
	
	private static ZooKeeper getZooKeeper(String servers, int timeout, Watcher watcher, CountDownLatch latch) throws IOException
	{
		ZooKeeper zk = new ZooKeeper(servers, timeout, watcher);
		try
		{
			latch.await();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return zk;
	}
	
	public static class DefaultInstanceWatcher implements Watcher
	{
		private CountDownLatch latch;
		
		public DefaultInstanceWatcher(CountDownLatch latch)
		{
			this.latch = latch;
		}

		@Override
		public void process(WatchedEvent event)
		{
			if (event.getState() == KeeperState.SyncConnected)
			{
				latch.countDown();
			}
		}
	}
}
