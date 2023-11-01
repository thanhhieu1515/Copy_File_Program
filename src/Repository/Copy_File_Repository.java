
package Repository;

import common.Algorithm;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import model.Config;

public class Copy_File_Repository implements ICopy_File_Repository {

    private final String pathConfig = "D://Copy_File_Program//src//file_Config//config.properties";
    private final File file = new File(pathConfig);

    Algorithm al = new Algorithm();

    @Override
    public void readFileConfig() {
        Properties properties = new Properties();
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                properties.load(fileReader);
                Config config = new Config(properties.getProperty("COPY_FOLDER"), properties.getProperty("DATA_TYPE"), properties.getProperty("PATH"));
                if (checkConfig(config)) {
                    copyFolder(config);
                } else {
                    System.out.println("System shutdown");
                }
                copyFolder(config);
                fileReader.close();
            } catch (Exception e) {
                System.out.println("File Configure is not found!");
            }
        } else {
            createFileConfig();
            readFileConfig();
        }
    }

    @Override
    public void createFileConfig() {
        Properties properties = new Properties();
        FileOutputStream fileoutputStream = null;
        Config config = createConfig();
        try {
            fileoutputStream = new FileOutputStream(pathConfig);
            properties.setProperty("COPY_FOLDER", config.getCopyFolder());
            properties.setProperty("DATA_TYPE", config.getDataType());
            properties.setProperty("PATH", config.getPath());
            properties.store(fileoutputStream, null);
            System.out.println("Created File");
        } catch (IOException ex) {
            System.out.println("Cannot create Config File");
            System.out.println("System shutdown");
        } finally {
            if (fileoutputStream != null) {
                try {
                    fileoutputStream.close();
                } catch (Exception e) {
                    System.err.println("File cannot create");
                    System.out.println("System shutdown");
                }
            }
        }
    }

    public Config createConfig() {
        String copyFolder = al.getFolder("COPY_FOlDER: ");
        String dataType = al.getString("DATA_TYPE: ");
        String path = al.getFolder("PATH: ");
        return new Config(copyFolder, dataType, path);
    }

    @Override
    public boolean checkConfig(Config config) {
        if (config.getCopyFolder().isEmpty()) {
            System.out.println("Folder Source is not input");
        }
        if (config.getDataType().isEmpty()) {
            System.out.println("Data type is not input");
        }
        if (config.getPath().isEmpty()) {
            System.out.println("Folder Destination is not input");
        }
        return config.getCopyFolder().isEmpty()
                && config.getDataType().isEmpty()
                && config.getPath().isEmpty();
    }

    public boolean checkFolder(File source, File dest) {
        if (!source.exists() || !dest.isDirectory()) {
            System.err.println("Can't find folder Source");
        }
        if (!dest.exists() || !dest.isDirectory()) {
            System.err.println("Can't make folder Destination");
        }
        return source.exists() && source.isDirectory()
                && dest.exists() && dest.isDirectory();
    }

    @Override
    public void copyFolder(Config config) {
        File sourceFolder = new File(config.getCopyFolder());
        File dest = new File(config.getPath());
        if (checkFolder(sourceFolder, dest)) {
            File[] listOfFiles = sourceFolder.listFiles();

            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    copyFile(config.getCopyFolder() + "\\" + listOfFiles[i].getName(), config.getPath());
                    System.out.println("File name: " + listOfFiles[i].getName());
                }
            }
        } else {
            System.err.println("System shutdown");
        }
    }

    public void copyFile(String file, String folder) {
        File f1 = new File(file);
        File f2 = new File(folder);
        if (f1.exists() && f1.isFile() && f2.exists() && f2.isDirectory()) {
            try {
                FileInputStream fis = new FileInputStream(f1);
                FileOutputStream fos = new FileOutputStream(folder + "/" + f1.getName());
                int b;
                while ((b = fis.read()) != -1) {
                    fos.write(b);
                }
                fis.close();
                fos.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
