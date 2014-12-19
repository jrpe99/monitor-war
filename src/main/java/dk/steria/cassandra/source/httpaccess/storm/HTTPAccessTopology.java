package dk.steria.cassandra.source.httpaccess.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;

public class HTTPAccessTopology {
    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("HTTPAccessSource", new HTTPAccessSpout(),10);
        builder.setBolt("HTTPAccessDestination", new HTTPAccessBolt(), 2).shuffleGrouping("HTTPAccessSource");

        Config conf = new Config();
        conf.setNumWorkers(1);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("test", conf, builder.createTopology());
        Utils.sleep(1000000);
        cluster.killTopology("test");
        cluster.shutdown();
    }
}