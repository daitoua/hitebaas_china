package com.hitebaas.util;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.Participant;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hitebaas.client.ZKClient;
import com.hitebaas.client.ZKClientListener;
import com.hitebaas.entity.HiteBassConstant;



@Component
public class ZookeeperUtils implements Watcher{
	private static final Logger logger = Logger.getLogger(ZookeeperUtils.class);
	public static final int SESSION_TIME_OUT = 300000;//session超时时间。
	public static final String ENCODING_SET = "UTF-8";
	
	@Autowired
	private ZooKeeper zookeeper;
	
	//public static final String configPath = "/config/aaa";
	
	 private CountDownLatch countDownLatch = new CountDownLatch(1);
	
	/**连接zookeeper
	    * @param host
	    * @throws Exception
	    */
	   public void connectZookeeper(String host) throws Exception{
	      zookeeper = new ZooKeeper(host, SESSION_TIME_OUT, this);
	      
	      countDownLatch.countDown();
	      System.out.println("zookeeper connection success");
	      
	   }
	   public void disconnect() {
		   try {
			zookeeper.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	
	/**
	 * 创建路径
	 * @param data
	 * @throws KeeperException
	 * @throws InterruptedException
	 * @throws UnsupportedEncodingException 
	 */
	public void createPath(String path, String value) throws KeeperException, InterruptedException, UnsupportedEncodingException {
		byte[] data = value.getBytes(ENCODING_SET);
		String result = zookeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		logger.info("创建了路径" + path + ":" + result );
	}
	/**
	 * 创建临时路径
	 * @param data
	 * @throws KeeperException
	 * @throws InterruptedException
	 * @throws UnsupportedEncodingException 
	 */
	public void createEPath(String path, String value) throws KeeperException, InterruptedException, UnsupportedEncodingException {
		byte[] data = value.getBytes(ENCODING_SET);
		String result = zookeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		logger.info("创建了路径" + path + ":" + result );
	}
	/**
	 * 创建有序路径
	 * @param data
	 * @throws KeeperException
	 * @throws InterruptedException
	 * @throws UnsupportedEncodingException 
	 */
	public void createSPath(String path, String value) throws KeeperException, InterruptedException, UnsupportedEncodingException {
		byte[] data = value.getBytes(ENCODING_SET);
		String result = zookeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
		logger.info("创建了路径" + path + ":" + result );
	}
	/**
	 * 创建临时有序路径
	 * @param data
	 * @throws KeeperException
	 * @throws InterruptedException
	 * @throws UnsupportedEncodingException 
	 */
	public void createESPath(String path, String value) throws KeeperException, InterruptedException, UnsupportedEncodingException {
		byte[] data = value.getBytes(ENCODING_SET);
		String result = zookeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		logger.info("创建了路径" + path + ":" + result );
	}
	/**
	 * 取值
	 * @param path
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 * @throws UnsupportedEncodingException 
	 */
	public String getPath(String path) throws KeeperException, InterruptedException, UnsupportedEncodingException {
		byte[] data = zookeeper.getData(path, this, null);
		return new String(data, ENCODING_SET);
	}
	
	/**
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	public void setPath(String path, String value) throws UnsupportedEncodingException, KeeperException, InterruptedException {
		Stat stat = zookeeper.setData(path, value.getBytes(ENCODING_SET), -1);
		
	}
	/**
	 * 监听该节点
	 * @param path
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void addWatch(String path) throws KeeperException, InterruptedException {
		zookeeper.exists(path, this);//监听该路径节点
	}
	/**
	 * 监听子节点
	 * @param path
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void addChildrenWatch(String path) throws KeeperException, InterruptedException {
		zookeeper.getChildren(path, this);//监听该路径节点
		zookeeper.getChildren(path, true);
	}
	
	
	@Override
	public void process(WatchedEvent event) {
		try {
			if (event.getState() == KeeperState.SyncConnected) {
			    System.err.println("eventType:" + event.getType());
			    System.err.println("eventType int:" + event.getType().getIntValue());
			    if(event.getType()==Event.EventType.None){
			    	
			    }else if(event.getType()==Event.EventType.NodeCreated){
			        System.out.println("listen:节点创建");
			    }else if(event.getType() == Event.EventType.NodeDataChanged){
			    	logger.info(event.getPath() + ",节点修改,重新获取" + event.getPath() + ":" + getPath(event.getPath()));
			    }else if(event.getType() ==Event.EventType.NodeChildrenChanged){
			        System.out.println("listen:子节点修改");
			        List<String> cps = zookeeper.getChildren(event.getPath(), true);
			        for(String cp : cps) {
			        	logger.info(event.getPath() + ",子节点修改,重新获取" + event.getPath() + "/" + cp+ ":" + getPath(event.getPath() + "/"+cp));
			        }
			    }
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 查询节点是否存在
	 * @param value
	 * @return
	 */
	public boolean IsExist(String value){
        try {
            String root = "/";
            List<String> list= iterChildNodeList(root, zookeeper);
            for(String str : list) {
            	if(str.equals(value)) {
            		return true;
            	}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		
        return false;
    }
	
	 /**
     * 迭代获取父节点的子节点
     * @param parentNodeName
     * @param zooKeeper
     * @return
     */
    public  ArrayList<String> iterChildNodeList(String parentNodeName, ZooKeeper zooKeeper){
    	ArrayList<String> childNodeList= new ArrayList<String>();
        if(parentNodeName != null && !parentNodeName.equals("")){
            try {
               childNodeList = (ArrayList<String>)zooKeeper.getChildren(parentNodeName, null);
                if(childNodeList.size() > 0){
                   // System.out.println("父结点:" + parentNodeName);
                    for(String childNode : childNodeList){
                        String childNodePath = "";
                        if(!parentNodeName.equals("/")){
                            childNodePath = parentNodeName + "/" + childNode;
                        }else {
                            childNodePath = parentNodeName +  childNode;
                        }
                       // System.out.println(parentNodeName + "的子节点：" + childNodePath);
                        iterChildNodeList(childNodePath, zooKeeper);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return childNodeList;
    }
    
    public  String getNodeData(String path){
        try {
            String data = new String(zookeeper.getData(path, null, new Stat()));
	        return data;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        	try {
				zookeeper.close();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        return null;
    }
    public  String getNodeData2(String path){
        try {
            String data = new String(zookeeper.getData(path, null, new Stat()));
	        return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public  void setStr(String path,String str){
        try{
            byte[] data = str.getBytes();
           
            String beforeData = new String(zookeeper.getData(path, null, new Stat()));
            Stat stat = zookeeper.setData(path, data, -1);
            // 修改之后的节点数据
            String afterData = new String(zookeeper.getData(path, null, new Stat()));
            System.out.println("更新之前数据为:" + beforeData);
            System.out.println("更新之后数据为:" + afterData);
            
        }catch (Exception e){
            System.out.println(e);
        }
    }
    
	public ZooKeeper getZookeeper() {
		return zookeeper;
	}
	public void setZookeeper(ZooKeeper zookeeper) {
		this.zookeeper = zookeeper;
	}
	
	public String getLeader() {
		String leader="";
		CuratorFramework client =
                CuratorFrameworkFactory.builder()
                        .connectString(HiteBassConstant.ZOOKEEPER_IP)
                        .retryPolicy(new ExponentialBackoffRetry(5000, 3))
                        .connectionTimeoutMs(5000)
                        .build();
		
		LeaderLatch leaderLatch = new LeaderLatch(client, HiteBassConstant.LEADERLATCH, "client2", LeaderLatch.CloseMode.NOTIFY_LEADER);
        ZKClientListener zkClientListener = new ZKClientListener();
        leaderLatch.addListener(zkClientListener);
        

        ZKClient zkClient = new ZKClient(leaderLatch,client);
        try {
			zkClient.startZKClient();
			leader=leaderLatch.getLeader().getId();
			return leader;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				zkClient.closeZKClient();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
        
		return "";
		
	}
	public String getLeader2() {
		String leader="";
		CuratorFramework client =
                CuratorFrameworkFactory.builder()
                        .connectString(HiteBassConstant.ZOOKEEPER_IP)
                        .retryPolicy(new ExponentialBackoffRetry(5000, 3))
                        .connectionTimeoutMs(5000)
                        .build();
		
		LeaderLatch leaderLatch = new LeaderLatch(client, HiteBassConstant.LEADERLATCH, "client2", LeaderLatch.CloseMode.NOTIFY_LEADER);
        ZKClientListener zkClientListener = new ZKClientListener();
        leaderLatch.addListener(zkClientListener);
        

        ZKClient zkClient = new ZKClient(leaderLatch,client);
        try {
			zkClient.startZKClient();
			leader=leaderLatch.getLeader().getId();
			return leader;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				zkClient.closeZKClient();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
        
		return "";
		
	}
	public List<String> getNotLeader() {
		List<String> strs=new ArrayList<String>();
		CuratorFramework client =
                CuratorFrameworkFactory.builder()
                        .connectString(HiteBassConstant.ZOOKEEPER_IP)
                        .retryPolicy(new ExponentialBackoffRetry(2000, 3))
                        .connectionTimeoutMs(2000)
                        .build();
		
		LeaderLatch leaderLatch = new LeaderLatch(client, HiteBassConstant.LEADERLATCH, "client2", LeaderLatch.CloseMode.NOTIFY_LEADER);
        ZKClientListener zkClientListener = new ZKClientListener();
        leaderLatch.addListener(zkClientListener);
        

        ZKClient zkClient = new ZKClient(leaderLatch,client);
        try {
			zkClient.startZKClient();
			String ip=leaderLatch.getLeader().getId();
			String mip=leaderLatch.getId();
			Collection<Participant> participants = leaderLatch.getParticipants();
			System.out.println(participants.size());
		    for (Participant participant : participants) {
		    	//System.out.println(participant.getId());
		        if (ip.equals(participant.getId())||mip.equals(participant.getId())) {
		        	
		            continue;
		           
		        }
		        strs.add(participant.getId());
		    }
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				zkClient.closeZKClient();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
        
		return strs;
		
	}
	
}
