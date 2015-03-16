package dk.jrpe.monitor.source.httpaccess.storm;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import dk.jrpe.monitor.db.cassandra.CassandraConnectionHandler;
import dk.jrpe.monitor.db.cassandra.HTTPAccessDAO;
import dk.jrpe.monitor.source.httpaccess.to.HTTPAccessTO;
import java.util.List;

@SuppressWarnings("serial")
public class HTTPAccessBolt extends BaseRichBolt {
    private OutputCollector collector;
    private CassandraConnectionHandler conn = null;
    private HTTPAccessDAO httpAccessDAO = null;
    
    @SuppressWarnings("rawtypes")
    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        conn = new CassandraConnectionHandler();
        conn.connect();
        httpAccessDAO = new HTTPAccessDAO(conn);
    }

    @Override
    public void execute(Tuple tuple) {
        List<Object> values = tuple.getValues();
        values.stream().map((value) -> (HTTPAccessTO)value).forEach((to) -> {
            System.out.println("BOLT Thread : " + Thread.currentThread().getName() + " process HTTP Access IP: " + to.getIpAddress());
            httpAccessDAO.create(to);
        });
        collector.ack(tuple);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("number"));
    }

    @Override
    public void cleanup() {
        conn.close();
    }
}