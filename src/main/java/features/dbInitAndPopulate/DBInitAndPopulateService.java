package features.dbInitAndPopulate;

import features.settings.Settings;
import org.flywaydb.core.Flyway;

public class DBInitAndPopulateService {
    public static void main(String[] args) {
        String db_url = new Settings().getPref(Settings.DB_URL_KEY);

        Flyway flyway = Flyway.configure().dataSource(db_url, null, null).load();
        flyway.migrate();
    }
}
