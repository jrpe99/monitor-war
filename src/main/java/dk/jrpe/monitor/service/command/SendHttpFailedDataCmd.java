package dk.jrpe.monitor.service.command;

import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import dk.jrpe.monitor.db.httpaccess.to.JsonHTTPAccessTO;
import dk.jrpe.monitor.db.datasource.DataSource;
import dk.jrpe.monitor.db.datasource.DataSourceFactory;
import dk.jrpe.monitor.json.JSONMapper;

/**
 *
 * @author Jörgen Persson
 */
public class SendHttpFailedDataCmd extends Command {
    private final DataSource dataSource = DataSourceFactory.getDefault();

    @Override public void execute(CommandHandler cmdHandler) {
        JsonHTTPAccessTO to = JSONMapper.toJsonHTTPAccessTO(cmdHandler.getJson());
        if(to != null) this.dataSource.updateHttpFailed(new HTTPAccessTO(to));
    }
}
