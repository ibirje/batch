package com.buttch;

public class CommandeConst {

	
	public static final String READER_NAME = "commandeItemReader";
	public static final String WRITER_NAME_CSV = "writerNameCSV";
	public static final String WRITER_NAME_DB = "writerNameDB";	
	public static final String WRITER_NAME_XML = "writerNameXML";
	public static final String STEP1_NAME = "commandeItemStep1";
	public static final String STEP2_NAME = "commandeItemStep2";
	public static final String JOB_NAME = "commandeItemJob";
	
	public static final String[] COLUMN_NAMES = new String[] { "commandeID", "commandeitemID", "nom", "prixUnite", "qte", "thumbnail" };

	public static final String COMMANDE_ITEMS_XML_FILE = "D:/spring_output/found_commandeItems.xml";
	public static final String COMMANDE_ITEMS_CSV_FILE = "D:/spring_output/found_commandeItems.csv";


	/*
	 * SQL QUERIES
	 */

	public static final String READER_QUERY = """
			SELECT commandeitemID, commandeID, variationID, code, nom, thumbnail, prixUnite, qte 
			FROM CommandeItem;
			""";
	public static final String WRITER_QUERY = """
			INSERT INTO CommandeItemExtra(commandeitemID, commandeID, variationID, code, nom, thumbnail, prixUnite, qte)
			VALUES(:commandeitemID, :commandeID, :variationID, :code, :nom, :thumbnail, :prixUnite, :qte)
			""";

}
