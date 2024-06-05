package org.example.ingest;

import org.elasticsearch.ingest.Processor;
import org.elasticsearch.plugins.IngestPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.ingest.Processor.Factory;

import java.util.HashMap;
import java.util.Map;

public class IngestMetaExtractPlugin extends Plugin implements IngestPlugin {
    @Override
    public Map<String, Processor.Factory> getProcessors(Processor.Parameters parameters) {
        Map<String, Factory> processors = new HashMap<>();
        processors.put(ExtractMetadataProcessor.TYPE, new ExtractMetadataProcessor.Factory());
        return processors;
    }
}
