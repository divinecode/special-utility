package com.divinecode.specialutility;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileUtils {

    public static void writeByteArrayToFile(@NotNull File file, @NotNull byte[] content) throws IOException {
        if (!file.exists()) file.createNewFile();

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content);
        }

    }

    public static void unzip(@NotNull final File inputJar, @NotNull final File outputDir) {
        final String location = outputDir.getAbsolutePath();
        checkDir(location, "");

        try  {
            FileInputStream fin = new FileInputStream(inputJar);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze;

            while ((ze = zin.getNextEntry()) != null) {
                System.out.println("Unzipping " + ze.getName());

                if(ze.isDirectory()) {
                    checkDir(location, ze.getName());
                } else {

                    FileOutputStream fos = new FileOutputStream(location + File.separatorChar + ze.getName());
                    BufferedOutputStream bos = new BufferedOutputStream(fos);

                    byte[] buffer = new byte[1024];
                    int read;

                    while ((read = zin.read(buffer)) != -1) {
                        bos.write(buffer, 0, read);
                    }

                    bos.close();
                    zin.closeEntry();
                    fos.close();
                }
            }
            zin.close();
        } catch(Exception e) {
            System.out.println("Unzipping failed...");
            e.printStackTrace();
        }
    }

    public static void checkDir(@NotNull final String location, @NotNull final String dir) {
        File file = new File(location + File.separatorChar + dir);
        if(!file.isDirectory()) file.mkdirs();
    }

    public static void packToJar(@NotNull final File contentDir, @NotNull final File jarFile) {
        Path sourceDir = contentDir.toPath();

        try {
            ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(jarFile));
            Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
                    try {
                        Path targetFile = sourceDir.relativize(file);
                        outputStream.putNextEntry(new ZipEntry(targetFile.toString().replace('\\', '/')));
                        byte[] bytes = Files.readAllBytes(file);
                        outputStream.write(bytes, 0, bytes.length);
                        outputStream.closeEntry();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
