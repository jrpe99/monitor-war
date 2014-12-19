package dk.steria.cassandra.source.httpaccess.storm;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import dk.steria.cassandra.source.httpaccess.to.HTTPAccessTOFactory;
import dk.steria.cassandra.source.httpaccess.to.HTTPAccessTO;

import java.util.Map;
import java.util.Random;

@SuppressWarnings("serial")
public class HTTPAccessSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private Random random = new Random();

    @SuppressWarnings("rawtypes")
    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void nextTuple() {
        HTTPAccessTO to = HTTPAccessTOFactory.createSimulated();
        collector.emit(new Values(to));
        System.out.println("SPOUT Thread : " + Thread.currentThread().getName() + " emit new HTTP Access");
        int sleepTime = random.nextInt(5000);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void ack(Object id) {
    }

    @Override
    public void fail(Object id) {
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("number"));
    }
}