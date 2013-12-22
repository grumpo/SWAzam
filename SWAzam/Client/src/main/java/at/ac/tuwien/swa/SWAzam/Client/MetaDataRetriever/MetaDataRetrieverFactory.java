package at.ac.tuwien.swa.SWAzam.Client.MetaDataRetriever;

import java.sql.Connection;

public interface MetaDataRetrieverFactory {
    MetaDataRetriever create(Connection con);
}
