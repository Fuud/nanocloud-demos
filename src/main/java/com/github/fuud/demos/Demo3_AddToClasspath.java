package com.github.fuud.demos;

import org.gridkit.nanocloud.Cloud;
import org.gridkit.nanocloud.CloudFactory;
import org.gridkit.vicluster.ViNode;
import org.gridkit.vicluster.ViProps;

import java.lang.management.ManagementFactory;
import java.rmi.Remote;
import java.util.concurrent.Callable;

public class Demo3_AddToClasspath {
    public static void main(String[] args) {

        Cloud cloud = CloudFactory.createCloud();
        ViNode node1 = cloud.node("node1");
        ViProps.at(node1).setLocalType();

        final RemoteNameGetter hostJvmNameGetter = new RemoteNameGetterImpl();

        node1.exec(new Runnable() {
            public void run() {
                System.out.println("I am forked jvm, my name is "+getJvmName()+"; and host name is "+hostJvmNameGetter.getName());
            }
        });

        RemoteNameGetter node1RemoteNameGetter = node1.exec(new Callable<RemoteNameGetter>() {
            public RemoteNameGetter call() {
                return new RemoteNameGetterImpl();
            }
        });

        System.out.println("I am host jvm, my name is "+getJvmName()+"; and node1 jvm name is "+node1RemoteNameGetter.getName());
    }

    public interface RemoteNameGetter extends Remote {
        public String getName();
    }

    public static class RemoteNameGetterImpl implements RemoteNameGetter{

        public String getName() {
            return getJvmName();
        }
    }

    public static String getJvmName(){
        return ManagementFactory.getRuntimeMXBean().getName();
    }
}
