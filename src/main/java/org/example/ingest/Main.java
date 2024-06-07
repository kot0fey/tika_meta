package org.example.ingest;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.ingest.IngestDocument;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String [] args){
        Map<String, Object> source = new HashMap<>();
        source.put("title", "First Document");
        source.put("description", "This is the description of the first document.");
        source.put("timestamp", "2024-06-06T10:00:00Z");
        source.put("base64", "dGVzdAo=");
        IngestDocument ingestDocument = new IngestDocument("my_index", "5",0L, null, null, source);
        String field = "base64";
        String targetField = "decoded_string";
        try (InputStream stream = new ByteArrayInputStream(
                ingestDocument.getFieldValue(field, String.class).getBytes(StandardCharsets.UTF_8)
        )) {
            Metadata metadata = new Metadata();
            AutoDetectParser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler();
            ParseContext context = new ParseContext();
            parser.parse(stream, handler, metadata, context);
            for (String name : metadata.names()) {
//                ingestDocument.setFieldValue(targetField + "." + name, metadata.get(name));
                ingestDocument.setFieldValue(name, metadata.get(name));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TikaException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } finally {
//            return ingestDocument;
        }
    }
}
