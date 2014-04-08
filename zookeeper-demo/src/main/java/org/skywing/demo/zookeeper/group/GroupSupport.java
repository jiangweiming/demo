package org.skywing.demo.zookeeper.group;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;

/**
 * Zookeeper Group Manager
 * @author jwm
 * @since 1.6
 * @version 1.0
 */
public class GroupSupport
{
	public static final String GROUP_ROOT_NODE = "/group";
	private static final List<ACL> acl = ZooDefs.Ids.OPEN_ACL_UNSAFE;
	
	public static String createGroup(ZooKeeper zookeeper, String group) throws KeeperException, InterruptedException
	{
		Validate.notNull(zookeeper);
		Validate.notBlank(group);
		if (zookeeper.exists(GROUP_ROOT_NODE, false) == null)
		{
			zookeeper.create(GROUP_ROOT_NODE, new byte[0], acl, CreateMode.PERSISTENT);
		}
		String path = GROUP_ROOT_NODE + '/' + group;
		if (zookeeper.exists(path, false) != null)
		{
			return path;
		}
		return zookeeper.create(path, new byte[0], acl, CreateMode.PERSISTENT);
	}
	
	public static String addToGroup(ZooKeeper zookeeper, String group, String member, byte[] data, boolean isTemp) throws KeeperException, InterruptedException
	{
		Validate.notNull(zookeeper);
		Validate.notBlank(group);
		Validate.notBlank(member);
		String path = GROUP_ROOT_NODE + '/' + group + '/' + member;
		if (zookeeper.exists(path, false) != null)
		{
			return path;
		}
		CreateMode mode = isTemp ? CreateMode.EPHEMERAL : CreateMode.PERSISTENT;
		return zookeeper.create(path, data, acl, mode);
	}
	
	public static void removeFromGroup(ZooKeeper zookeeper, String group, String member) throws InterruptedException, KeeperException
	{
		Validate.notNull(zookeeper);
		Validate.notBlank(group);
		Validate.notBlank(member);
		String path = GROUP_ROOT_NODE + '/' + group + '/' + member;
		if (zookeeper.exists(path, false) == null)
		{
			return;
		}
		zookeeper.delete(path, -1);
	}
	
	public static List<String> groupMembers(ZooKeeper zookeeper, String group) throws KeeperException, InterruptedException
	{
		Validate.notBlank(group);
		String path = GROUP_ROOT_NODE + '/' + group;
		if (zookeeper.exists(path, false) == null)
		{
			return null;
		}
		return zookeeper.getChildren(path, false);
	}
	
	public static void groupWatch(ZooKeeper zookeeper, String group, Watcher callback) throws KeeperException, InterruptedException
	{
		Validate.notBlank(group);
		Validate.notNull(callback);
		String path = GROUP_ROOT_NODE + '/' + group;
		zookeeper.getChildren(path, callback);
	}
}
