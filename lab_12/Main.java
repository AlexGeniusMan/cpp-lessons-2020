package ru.alexgeniusman.java2020.lab_12;

import java.io.*;
import java.nio.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    private final ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
    private final Map<String, String> data = new HashMap<>();
    private final Charset charset = StandardCharsets.UTF_8;

    public void add(String word, String text) throws Exception {
        if (data.get(word) != null)
            throw new Exception("Word already exists");
        data.put(word, text);
    }

    public void delete(String word) {
        data.remove(word);
    }

    public void save(String fileName) throws IOException {
        if (data.size() == 0)
            throw new IOException("Nothing to write");
        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(fileName, false))) {
            writeInt(os, data.size());
            byte[] word;
            byte[] text;
            for (Map.Entry<String, String> entry : data.entrySet()) {
                word = entry.getKey().getBytes(charset);
                writeInt(os, word.length);
                text = entry.getValue().getBytes(charset);
                writeInt(os, text.length);
                os.write(word);
                os.write(text);
            }
        }
    }

    private void writeInt(OutputStream out, int num) throws IOException {
        ByteBuffer dbuf = ByteBuffer.allocate(4);
        dbuf.order(byteOrder).putInt(num);
        out.write(dbuf.array());
    }

    private int readInt(InputStream in) throws IOException {
        byte[] out = new byte[4];
        int i = in.read(out);
        if (i != 4)
            throw new IOException("Error reading file");
        return ByteBuffer.wrap(out).order(byteOrder).getInt();
    }

    public void load(String fileName) throws IOException {
        try (InputStream is = new BufferedInputStream(new FileInputStream(fileName))) {
            int n = readInt(is);
            int s1;
            int s2;
            byte[] word;
            byte[] text;
            for (int i = 0; i < n; i++) {
                word = new byte[readInt(is)];
                text = new byte[readInt(is)];
                s1 = is.read(word);
                s2 = is.read(text);
                if (s1 != word.length || s2 != text.length)
                    throw new IOException("Error reading file");
                data.put(new String(word, charset), new String(text, charset));
            }
        }
    }

    public String find(String word) {
        String out = data.get(word);
        return Objects.requireNonNullElse(out, "Not found");
    }


    public static void main(String[] args) {
        String file = "test.txt";
        Main obj = new Main();
        try {
            obj.add("Abc", "AbcAbc");
            obj.add("Bca", "BcaBca");
            obj.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Main fileClass121 = new Main();
        try {
            fileClass121.load(file);
            String test1 = "abc";
            String test2 = "Abc";
            System.out.printf("%s - %s\n", test1, fileClass121.find(test1));
            System.out.printf("%s - %s\n", test2, fileClass121.find(test2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}