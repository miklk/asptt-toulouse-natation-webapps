package com.asptttoulousenatation.core.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.asptttoulousenatation.core.shared.document.DocumentUi;

@XmlRootElement
public class PageUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7248929381942539859L;
	private String titre;
	private String content;
	private boolean hasSubContent;
	private List<DocumentUi> documents;
	private List<PageUi> sousPages;
	
	public PageUi() {
		hasSubContent = false;
		documents = new ArrayList<DocumentUi>();
		sousPages = new ArrayList<PageUi>();
	}

	public PageUi(String pContent) {
		super();
		content = pContent;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String pTitre) {
		titre = pTitre;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String pContent) {
		content = pContent;
	}

	public boolean isHasSubContent() {
		return hasSubContent;
	}

	public void setHasSubContent(boolean pHasSubContent) {
		hasSubContent = pHasSubContent;
	}

	public List<DocumentUi> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentUi> pDocuments) {
		documents = pDocuments;
	}

	public List<PageUi> getSousPages() {
		return sousPages;
	}

	public void setSousPages(List<PageUi> pSousPages) {
		sousPages = pSousPages;
	}
}