package com.github.fuud.demos;

import org.gridkit.nanocloud.Cloud;
import org.gridkit.nanocloud.CloudFactory;
import org.gridkit.nanocloud.VX;
import org.gridkit.vicluster.ViNode;
import org.gridkit.vicluster.ViProps;

import java.lang.management.ManagementFactory;

public class Demo1_StartJvms {
    public static void main(String[] args) {

        Cloud cloud = CloudFactory.createCloud();
        ViNode node1 = cloud.node("node1");
        ViProps.at(node1).setLocalType();

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
