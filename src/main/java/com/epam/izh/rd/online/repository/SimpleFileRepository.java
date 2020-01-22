package com.epam.izh.rd.online.repository;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class SimpleFileRepository implements FileRepository {

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        File dir = new File("src\\main\\resources\\"+ path);
        long numb = 0;
        if(dir.isDirectory()){
            for(File file : dir.listFiles()){
                if(file.isDirectory()){
                    numb+= countFilesInDirectory(path+"/"+file.getName());
                }
                else {
                     numb++;
                }
            }
        }
        else {
            System.out.println("Not Directory");
        }
        return numb;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        File dir = new File("src\\main\\resources\\"+ path);
        long numb = 0;
        if(dir.isDirectory()){
            numb++;
            for(File file : dir.listFiles()){
                if(file.isDirectory()){
                    numb+= countFilesInDirectory(path+"\\"+file.getName());
                }
            }
        }
        else {
            System.out.println("Not Directory");
        }
        return numb;
    }


    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) throws IOException {
        File folder = new File(from);
        File[] listOfFiles = folder.listFiles();
        Path destDir = Paths.get(to);
        if (listOfFiles != null)
            for (File file : listOfFiles)
                Files.copy(file.toPath(), destDir.resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name)  {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(path);
        File file = new File(resource.getPath());

        try {
            FileWriter fileWriter = new FileWriter(file.getPath()+"/"+name);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        File file = new File("src/main/resources/"+fileName);
        String result="";
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = null;

            while ((line=bufferedReader.readLine())!=null){
                result+=line;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
