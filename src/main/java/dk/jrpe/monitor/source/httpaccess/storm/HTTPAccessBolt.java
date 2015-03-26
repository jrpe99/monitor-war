package dk.jrpe.monitor.source.httpaccess.storm;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import dk.jrpe.monitor.db.cassandra.dao.httpaccess.HTTPAccessDAO;
import dk.jrpe.monitor.db.datasource.DataSource;
import dk.jrpe.monitor.db.datasource.DataSourceFactory;
import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class HTTPAccessBolt extends BaseRichBolt {
    private OutputCollector collector;
    private HTTPAccessDAO httpAccessDAO = null;
    private DataSource dataSource = DataSourceFactory.getDefault();
    
    @SuppressWarnings("rawtypes")
    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        httpAccessDAO = new HTTPAccessDAO(this.dataSource);
    }

    @Override
    public void execute(Tuple tuple) {
        List<Object> values = tuple.getValues();
        values.stream().map((value) -> (HTTPAccessTO)value).forEach((to) -> {
            System.out.println("BOLT Thread : " + Thread.currentThread().getName() + " process HTTP Access IP: " + to.getIpAddress());
            httpAccessDAO.saveAndUpdate(to);
        });
        collector.ack(tuple);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("number"));
    }

    @Override
    public void cleanup() {
        try {
            this.dataSource.close();
        } catch (Exception ex) {
            Logger.getLogger(HTTPAccessBolt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}