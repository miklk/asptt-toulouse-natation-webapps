package com.asptttoulousenatation.core.shared.document.libelle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LibelleUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -646657560722705051L;
	private Long id;
	private String wholeIntitule;
	private String intitule;
	private boolean hasChild;
	private boolean hasAncestor;
	private List<LibelleUi> children;
	
	public LibelleUi() {
		hasChild = false;
		hasAncestor = false;
	}
	
	public void addChild(LibelleUi ui) {
		if(children == null) {
			children = new ArrayList<>();
		}
		children.add(ui);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}
	

	public String getWholeIntitule() {
		return wholeIntitule;
	}

	public void setWholeIntitule(String pWholeIntitule) {
		wholeIntitule = pWholeIntitule;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String pIntitule) {
		intitule = pIntitule;
	}

	public boolean isHasChild() {
		return hasChild;
	}

	public void setHasChild(boolean pHasChild) {
		hasChild = pHasChild;
	}

	public List<LibelleUi> getChildren() {
		return children;
	}

	public void setChildren(List<LibelleUi> pChildren) {
		children = pChildren;
	}

	public boolean isHasAncestor() {
		return hasAncestor;
	}

	public void setHasAncestor(boolean pHasAncestor) {
		hasAncestor = pHasAncestor;
	}
}