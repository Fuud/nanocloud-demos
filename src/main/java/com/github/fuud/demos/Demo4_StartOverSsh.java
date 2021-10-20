package com.github.fuud.demos;

import org.gridkit.nanocloud.Cloud;
import org.gridkit.nanocloud.CloudFactory;
import org.gridkit.nanocloud.RemoteNode;
import org.gridkit.vicluster.ViNode;
import org.gridkit.vicluster.ViProps;

import java.lang.management.ManagementFactory;

public class Demo4_StartOverSsh {

    public static void main(String[] args) {

        Cloud cloud = CloudFactory.createCloud();
        ViNode node1 = cloud.node("node1");
        ViProps.at(node1).setRemoteType();
        node1.x(RemoteNode.REMOTE).useSimpleRemoting();
        node1.x(RemoteNode.REMOTE).setHostsConfigFile("?na"); //turn off host config file
        node1.x(RemoteNode.REMOTE).setRemoteAccount("root");
//        node1.x(RemoteNode.REMOTE).setSshPrivateKey("path to private key"); // for key auth
        node1.x(RemoteNode.REMOTE).setPassword("secret"); // for password auth

        node1.x(RemoteNode.REMOTE).setRemoteHost("<host>"); // for non-default port use host:port

        System.out.println("I am host jvm, my name is "+getJvmName());

        node1.exec(new Runnable() {
            public void run() {
                System.out.println("I am forked jvm, my name is "+getJvmName());
            }
        });
    }

    public static String getJvmName(){
        return ManagementFactory.getRuntimeMXBean().getName();
    }
}
