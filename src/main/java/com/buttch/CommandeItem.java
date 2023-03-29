package com.buttch;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("CommandeItem")
public class CommandeItem {

	private Long commandeitemID;
	private Long commandeID;
	private Long variationID;
	private String code;
	private String nom;
	private String thumbnail;
	private Double prixUnite;
	private Long qte;
	
	
	
	
	
	
	public CommandeItem(Long commandeitemID, Long commandeID, Long variationID, String code, String nom,
			String thumbnail, Double prixUnite, Long qte) {
		super();
		this.commandeitemID = commandeitemID;
		this.commandeID = commandeID;
		this.variationID = variationID;
		this.code = code;
		this.nom = nom;
		this.thumbnail = thumbnail;
		this.prixUnite = prixUnite;
		this.qte = qte;
	}
	
	public CommandeItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Long getCommandeitemID() {
		return commandeitemID;
	}
	public void setCommandeitemID(Long commandeitemID) {
		this.commandeitemID = commandeitemID;
	}
	public Long getCommandeID() {
		return commandeID;
	}
	public void setCommandeID(Long commandeID) {
		this.commandeID = commandeID;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public Double getPrixUnite() {
		return prixUnite;
	}
	public void setPrixUnite(Double prixUnite) {
		this.prixUnite = prixUnite;
	}
	public Long getQte() {
		return qte;
	}
	public void setQte(Long qte) {
		this.qte = qte;
	}
	public Long getVariationID() {
		return variationID;
	}
	public void setVariationID(Long variationID) {
		this.variationID = variationID;
	}
}
