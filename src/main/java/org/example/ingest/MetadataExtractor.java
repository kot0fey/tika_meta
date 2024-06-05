package org.example.ingest;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.example.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MetadataExtractor {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static String extract(String path) {

        try (InputStream input = new FileInputStream(path)) {
            AutoDetectParser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();

            parser.parse(input, handler, metadata);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{\n");
            for (String name : metadata.names()) {
                stringBuilder.append(String.format("%s: %s,\n", name, metadata.get(name)));
                logger.info("{}: {}", name, metadata.get(name));
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 2);
            stringBuilder.append("}");
            return stringBuilder.toString();
        } catch (IOException e) {
            logger.error("Error reading the image file.", e);
        } catch (Exception e) {
            logger.error("Error extracting metadata from the image.", e);
        } finally {
            return null;
        }
    }

}
