package org.example.ingest;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.elasticsearch.ingest.AbstractProcessor;
import org.elasticsearch.ingest.IngestDocument;
import org.elasticsearch.ingest.Processor;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.elasticsearch.ingest.ConfigurationUtils.readStringProperty;


public class ExtractMetadataProcessor extends AbstractProcessor {
    public static final String TYPE = "metadata_extractor";

    private final String field;
    private final String targetField;

    public ExtractMetadataProcessor(String tag, String description, String field, String targetField) {
        super(tag, description);
        this.field = field;
        this.targetField = targetField;
    }

    @Override
    public IngestDocument execute(IngestDocument ingestDocument) {
        try (InputStream stream = ingestDocument.getFieldValue(field, InputStream.class)) {
            Metadata metadata = new Metadata();
            AutoDetectParser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler();
            ParseContext context = new ParseContext();
            parser.parse(stream, handler, metadata, context);

            ingestDocument.setFieldValue(targetField, metadata);
            for (String name : metadata.names()) {
                ingestDocument.setFieldValue(targetField + "." + name, metadata.get(name));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TikaException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } finally {
            return ingestDocument;
        }
    }


    @Override
    public String getType() {
        return TYPE;
    }


    public static final class Factory implements Processor.Factory {

        @Override
        public Processor create(Map<String, Processor.Factory> factories, String tag, String description, Map<String, Object> config) throws Exception {
            String field = readStringProperty(TYPE, tag, config, "field", "base64");
            String targetField = readStringProperty(TYPE, tag, config, "target_field", "metadata");
            return new ExtractMetadataProcessor(tag, description, field, targetField);
        }
    }

}
