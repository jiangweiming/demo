package org.skywing.demo.zookeeper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skywing.demo.zookeeper.group.GroupSupport;

public class GroupTest
{
	private static ZooKeeper zk;
	
	@BeforeClass
	public static void prepare()
	{
		try
		{
			zk = ZookeeperFactory.getZooKeeper("192.168.56.101:2181,192.168.56.102:2181,192.168.56.103:2181");
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
		assertNotNull(zk);
	}
	
	@Test
	public void testGroupCreate() throws KeeperException, InterruptedException
	{
		String path = GroupSupport.createGroup(zk, "test");
		assertTrue(zk.exists(path, false) != null);
		assertEquals(GroupSupport.GROUP_ROOT_NODE + '/' + "test", path);
	}
	
	@Test
	public void testAddToGroup() throws KeeperException, InterruptedException
	{
		String path = GroupSupport.addToGroup(zk, "test", "member_1", "Hello World".getBytes(), false);
		assertEquals(GroupSupport.GROUP_ROOT_NODE + '/' + "test" + '/' + "member_1", path);
		assertTrue(zk.exists(path, false) != null);
		assertEquals("Hello World", new String(zk.getData(path, false, null)));
	}

	@Test
	public void testGroupMembers() throws KeeperException, InterruptedException
	{
		GroupSupport.addToGroup(zk, "test", "member_2", "ZooKeeper".getBytes(), true);
		GroupSupport.addToGroup(zk, "test", "member_3", "Welcome".getBytes(), false);
		
		List<String> children = GroupSupport.groupMembers(zk, "test");
		assertNotNull(children);
		assertEquals(3, children.size());
		assertTrue(children.contains("member_1"));
		assertTrue(children.contains("member_2"));
		assertTrue(children.contains("member_3"));
	}

	@Test
	public void testGroupWatch() throws KeeperException, InterruptedException
	{
		GroupSupport.groupWatch(zk, "test", new TestWatcher());
	}
	
	@Test
	public void testRemoveFromGroup() throws InterruptedException, KeeperException
	{
		GroupSupport.removeFromGroup(zk, "test", "member_1");
		assertTrue(zk.exists(GroupSupport.GROUP_ROOT_NODE + '/' + "test" + '/' + "member_1", false) == null);
	}
	
	@AfterClass
	public static void cleanup() throws InterruptedException, KeeperException
	{
		cleanNodes("/group/test");
		zk = null;
	}

	private static void cleanNodes(String path) throws KeeperException, InterruptedException
	{
		List<String> children = zk.getChildren(path, false);
		if (!CollectionUtils.isEmpty(children))
		{
			for (String child : children)
			{
				String childPath = path + '/' + child; 
				if (zk.getChildren(childPath, false) == null)
				{
					zk.delete(childPath, -1);
				}
				else
				{
					cleanNodes(childPath);
				}
			}
		}
		zk.delete(path, -1);
	}
	
	public static class TestWatcher implements Watcher
	{
		@Override
		public void process(WatchedEvent event)
		{
			 System.out.println(event.getState() + " " + event.getType());
		}
	}
}
