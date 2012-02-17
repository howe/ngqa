package org.nutz.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class Lucenes {

	public static Document doc(Object obj) {
		return null;
	}
	
	public static void main(String[] args) throws Throwable {
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
		Directory directory = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35, analyzer);
		IndexWriter iwriter = new IndexWriter(directory, config);

		Document doc = new Document();
		doc.add(new Field("id", ""+System.currentTimeMillis(), Store.NO, Field.Index.NOT_ANALYZED));
		iwriter.addDocument(doc);
		iwriter.commit();
		
	}
}
