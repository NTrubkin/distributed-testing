package ru.nntu.distributedtesting.prototype.master;

import io.netty.channel.Channel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.nntu.distributedtesting.prototype.MessageWriter;
import ru.nntu.distributedtesting.prototype.model.MessageType;
import ru.nntu.distributedtesting.prototype.model.Resources;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class ResourcesSender {

    private final Server server;
    private final MessageWriter messageWriter;

    public void send() {
        Resources resources = getResources();

        server.getClients()
              .forEach(client -> send(resources, client));

        System.out.println("Resources was sent to the clients");
    }

    public Resources getResources() {
        var resources = new Resources();

        resources.setBase64MainResources(zipFilesToBase64("C:/Users/trubk/Desktop/dt/input/app-main-res"));
        resources.setBase64TestResources(zipFilesToBase64("C:/Users/trubk/Desktop/dt/input/app-test-res"));

        return resources;
    }

    private String zipFilesToBase64(String source) {
        byte[] mainRes = zipFiles(source);
        byte[] mainBase64Res = Base64.getEncoder().encode(mainRes);
        return new String(mainBase64Res, UTF_8);
    }

    @SneakyThrows
    public byte[] zipFiles(String folder) {
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
    private void send(Resources resources, Channel channel) {
        messageWriter.write(MessageType.RESOURCES, resources, channel);
    }
}
