import java.io.File;
import java.text.DecimalFormat;

public class Prog {
    //Field of string(contains path to temp folder)
    private static final String pathToTemp = System.getenv("TEMP");
    private static double sizeOfClearedFiles = 0;

    public static void main(String[] args) {        //Main method

        File tempFolder = new File(pathToTemp);     //Create file

        cleanTemp(tempFolder);                   //run method to clean temp

        printInfo();                            //print info
    }


    private static void cleanTemp(File folder) {
        File[] files = folder.listFiles();                //array of files in current folder
        if (files != null) {                              //check that folder is not clear, to avoid deleting fulled folder
            for (File file : files) {                     // For-each loop to iterate all files in array
                clearFolder(file);                        // Method to clear folder
            }
        }
        deleteFolder(folder);                               //method to delete folder
    }


    private static void clearFolder(File file) {
        if (file.isDirectory()) {                     //checking directory
            cleanTemp(file);                        //recursive reusing of method
        } else {
            getUsageSpace(file);                    //method to get info about size of file and write it in variable
            boolean deleted = file.delete();            //deleting file and get info about status of deleting
            if (deleted) {
                System.out.println("File: " + file.getName() + " deleted");
            } else {
                System.out.println("Cant delete file " + file.getName());
            }
        }
    }


    private static void deleteFolder(File folder) {             //deleting folders
        if (!folder.getAbsolutePath().equals(pathToTemp)) {     //check the folder is not temp folder
            boolean deleteFolder = folder.delete();             //deleting operation
            if (deleteFolder) {
                System.out.println("Folder: " + folder.getAbsolutePath() + " deleted");
            } else {
                System.out.println("Can`t delete folder: " + folder.getAbsolutePath());
            }
        }
    }


    private static void getUsageSpace(File file) {      //method to get info about size of file and write it in variable
        long fileSizeBytes = file.length();
        double fileSizeMB = fileSizeBytes / (1024.0 * 1024.0);
        sizeOfClearedFiles += fileSizeMB;
    }


    private static void printInfo() {                               //print info
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String formattedFileSizeMB = decimalFormat.format(sizeOfClearedFiles);
        System.out.println("\n\tDONE!");
        System.out.println("\n\n\tCleared space: " + formattedFileSizeMB + " MB\n");
    }

}
