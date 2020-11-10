package ru.nntu.distributedtesting.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import lombok.SneakyThrows;

import static java.util.stream.Collectors.toList;

public class Utils {

    @SneakyThrows
    public static byte[] zipFiles(String folder) {
        List<String> files = Arrays.stream(new File(folder).list())
                                   .map(file -> folder + "/" + file)
                                   .collect(toList());

        var bos = new ByteArrayOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(bos);
        for (String srcFile : files) {
            File fileToZip = new File(srcFile);
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();
        bos.close();
        return bos.toByteArray();
    }

    @SneakyThrows
    public static List<String> unzipFilesNames(byte[] zip) {
        var fileNames = new ArrayList<String>();
        var zis = new ZipInputStream(new ByteArrayInputStream(zip));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            fileNames.add(zipEntry.getName());
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
        return fileNames;
    }
}
