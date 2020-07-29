package com.dlx.chapter14.blockingQueue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueTest {
    private static final int FILE_QUEUE_SIZE = 100;
    private static final int SEARCH_THREADS = 10;
    private static final File DUMMY = new File("");
    private static BlockingQueue<File> queue = new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)){
            System.out.print("Enter base directory (e.g. /opt/jdk.1.8.9/src)");
            String directory = in.nextLine();
            System.out.print("Enter keyword (e.g. volatile)");
            String keyword = in.nextLine();

            Runnable enumrator=()->{
                try{
                    enumerate(new File(directory));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            new Thread(enumrator).start();

            for(int i=1;i<=SEARCH_THREADS;i++){
                Runnable searcher=()->{
                  try{
                      boolean done=false;
                      while (!done) {
                          File file = queue.take();
                          if (file == DUMMY) {
                              queue.put(file);
                              done = true;
                          }
                          else search(file,keyword);
                      }
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  } catch (FileNotFoundException e) {
                      e.printStackTrace();
                  }
                };
                new Thread(searcher).start();
            }
        }
    }

    public static void enumerate(File directory) throws InterruptedException {
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                enumerate(file);
            }
            else
                queue.put(file);
        }
    }

    public static void search(File file, String keyword) throws FileNotFoundException {
        try (Scanner in = new Scanner(file, "UTF-8")) {
            int lineNumber=0;
            while (in.hasNextLine()) {
                lineNumber++;
                String line = in.nextLine();
                if (line.contains(keyword)) {
                    System.out.printf("文件名：%s ，列数:%d 内容:%s%n ",file.getPath(),lineNumber,line);
                }
            }
        }
    }
}
