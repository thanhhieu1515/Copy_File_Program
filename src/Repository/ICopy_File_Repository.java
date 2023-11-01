
package Repository;

import model.Config;


public interface ICopy_File_Repository {
    void readFileConfig();
    void createFileConfig();
    boolean checkConfig(Config config);
    void copyFolder(Config config);
}
