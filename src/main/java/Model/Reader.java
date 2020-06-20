package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Reader {

    private File file;
    private Scanner scanner;

    public Scanner getScanner() {
        return scanner;
    }

    /**
     *
     * @return the next line in file, if there is no nextLine - returns null
     */
    public String getNextLine(){

        // Tries to read the file's next line
        if( this.scanner != null && this.scanner.hasNextLine() ){
            return this.scanner.nextLine();
        }
        return null;
    }

    /**
     * Open file with a given path
     * This method allows to use the same Reader
     * @param filePath file's path
     */
    public void openFile(String filePath){

        // Check that path exists
        this.file = new File(filePath);

        if ( !this.file.isFile()) {
            return;
        }
        // Try to create Scanner
        try {
            this.scanner = new Scanner(this.file);

        } catch (FileNotFoundException exception){
            exception.printStackTrace();
        }

        // If for any reason we got here - something went wrong
        return;
    }

    // This method closes the file
    public void closeFile() {
        if( this.scanner != null ){
            this.scanner.close();
            this.scanner = null;
        }
    }

    public static void main(String[] args) {
        Reader reader=new Reader();
        reader.openFile("map.txt");
        reader.getNextLine();
    }
}