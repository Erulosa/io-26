package mainJava;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveLoadData {
static void saveData(DataBase dataBase) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("database"));
        oos.writeObject(dataBase);
        oos.flush();
        oos.close();
    }
static DataBase loadData() {
        DataBase db = null;
try {
            ObjectInputStream oin = new ObjectInputStream(new FileInputStream("database"));
            db = (DataBase) oin.readObject();
        } catch (Exception ignored) { }
return db;
    }
static boolean isDataBaseExists() {
return new File("database").exists();
    }
}
