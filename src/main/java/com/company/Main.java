package com.company;

import com.company.businessLogic.Logics;
import com.company.dataBase.DBConnect;
import com.company.dataBase.ReadNotificationThread;
import com.company.dataBase.ReadTables;
import com.company.readFile.WriteFile;
import com.company.readFile.ReadFile;

import java.nio.file.Path;
import java.time.LocalTime;
import java.util.Timer;

public class Main {

    public static void treadReadNotification() {
        ReadNotificationThread t = new ReadNotificationThread();
        t.start();
        try {
            Thread.sleep(150);
            t.interrupt();
            Thread.sleep(150);
        } catch (InterruptedException e) {
            System.out.println("Thread has been interrupted");
        }
    }

    public static void main(String[] args) {
        DBConnect dbConnect = new DBConnect();
        dbConnect.createPool();

        WriteFile.writeInfo(String.valueOf(LocalTime.now()) + '\n');
        Path path = Path.of(args[0]);

        ReadFile readFile = new ReadFile(path);
        readFile.readFile();

        WriteFile.writeInfo(String.valueOf(LocalTime.now()) + '\n');
        WriteFile.writeInfo("Logics start\n");
        treadReadNotification();

        Timer timer = new Timer();
        timer.schedule(new Logics(), 100, 1000);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.cancel();

        WriteFile.writeInfo("Logics прекращена\n");
        treadReadNotification();

        ReadTables readTables = new ReadTables();
        readTables.readAllTables();
    }
}
