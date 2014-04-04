package org.skywing.demo.zookeeper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;

/**
 * Hello world!
 *
 */
public class App 
{
	public static final String SERVER_CONNECT_LISTS = "192.168.56.101:2181,192.168.56.102:2181,192.168.56.103:2181";
	private static final List<ACL> acl = ZooDefs.Ids.OPEN_ACL_UNSAFE;
	private static final String DIR_ZOO = "/zoo";
	private static final String DIR_ZOOKEEPER = "/zoo/keeper_";
	
    public static void main( String[] args ) throws IOException, KeeperException, InterruptedException
    {
    	ZooKeeper zk = new ZooKeeper(SERVER_CONNECT_LISTS, 3000, new ConnectWatcher());
    	
    	TimeUnit.SECONDS.sleep(1);
    	
//    	zk.create(DIR_ZOO, new byte[0], acl, CreateMode.PERSISTENT);
    	zk.create(DIR_ZOOKEEPER, "Hello World".getBytes(Charset.defaultCharset()), acl, CreateMode.EPHEMERAL_SEQUENTIAL);
    	zk.create(DIR_ZOOKEEPER, " ZooKeeper".getBytes(Charset.defaultCharset()), acl, CreateMode.EPHEMERAL_SEQUENTIAL);
    	
    	List<String> children = zk.getChildren(DIR_ZOO, true);
    	for (String child : children)
    	{
    		System.out.println("get child [" + child + "]");
    	}
    }
    
    private static class ConnectWatcher implements Watcher
    {
		@Override
		public void process(WatchedEvent event)
		{
			System.out.println("Event [" + event.getType() + ", " + event.getState() + "] trigger!");
		}
    }
    
}
