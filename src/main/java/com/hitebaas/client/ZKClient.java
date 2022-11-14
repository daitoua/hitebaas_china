package com.hitebaas.client;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.hitebaas.data.ZKClientInfo;

public class ZKClient {

	
	private LeaderLatch leader;

    private CuratorFramework client;
    
    public ZKClient (LeaderLatch leader,CuratorFramework client){
        this.client = client;
        this.leader = leader;
    }
    
    /**
     * 启动客户端
     * @throws Exception
     */
    public void startZKClient() throws Exception {
        client.start();
        leader.start();
    }

    /**
     * 关闭客户端
     * @throws Exception
     */
    public void closeZKClient() throws Exception {
        leader.close();
        client.close();
    }

    /**
     * 判断是否变为领导者
     * @return
     */
    public boolean hasLeadership(){
        return leader.hasLeadership() && ZKClientInfo.isLeader;
    }


	public LeaderLatch getLeader() {
		return leader;
	}

	public void setLeader(LeaderLatch leader) {
		this.leader = leader;
	}

	public CuratorFramework getClient() {
		return client;
	}

	public void setClient(CuratorFramework client) {
		this.client = client;
	}
    
    
	
}
