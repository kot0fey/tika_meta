package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        if (args.length < 1) {
            logger.error("Please provide the path to the image file.");
            return;
        }

        String imagePath = args[0];
        try (InputStream input = new FileInputStream(imagePath)) {
            AutoDetectParser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();

            parser.parse(input, handler, metadata);

            for (String name : metadata.names()) {
                logger.info("{}: {}", name, metadata.get(name));
            }
        } catch (IOException e) {
            logger.error("Error reading the image file.", e);
        } catch (Exception e) {
            logger.error("Error extracting metadata from the image.", e);
        }    }
}