package org.skywing.demo.zookeeper.curator;

import java.io.Serializable;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

public class TestObject implements Serializable
{
    private static final long serialVersionUID = -4898902932399200106L;

    private String error;
    private int count;
    private StackTraceElement[] traces;

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public StackTraceElement[] getTraces()
    {
        return traces;
    }

    public void setTraces(StackTraceElement[] traces)
    {
        this.traces = traces;
    }

    public TestObject(String error, int count, StackTraceElement[] traces)
    {
        this.error = error;
        this.count = count;
        this.traces = traces;
    }
    
    public static void main(String[] args)
    {
        StackTraceElement[] traces = {new StackTraceElement("ErrorClass", "ErrorMethod", "ErrorJava", 1), new StackTraceElement("ErrorClass", "ErrorMethod", "ErrorJava", 3)};
        TestObject obj = new TestObject("It's a eroor msg.", 2, traces);
        
        final String address = "192.168.56.101:2181,192.168.56.102:2181,192.168.56.103:2181";
//        final String address = "127.0.0.1:5000,127.0.0.1:5001,127.0.0.1:5002";
        CuratorFramework client = CuratorFrameworkFactory.newClient(address, new ExponentialBackoffRetry(1000, 3));
        client.start();
        try
        {
//            String response = client.create().forPath("/synflow/test2", null);
//            System.out.println("zk response: " + response);
            
            System.out.println(client.create().withProtection().forPath("/synflow/test2"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            client.close();
        }
    }
}
