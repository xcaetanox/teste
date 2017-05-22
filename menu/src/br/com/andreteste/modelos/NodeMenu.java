package br.com.andreteste.modelos;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class NodeMenu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	int id;
	String decricao;
	String code;
	String detail;
	int hasChildren;
	@Column(updatable=false)
	int parentId;

	@Transient
	List<NodeMenu> children;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDecricao() {
		return decricao;
	}

	public void setDecricao(String decricao) {
		this.decricao = decricao;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public boolean getHasChildren() {
		if (hasChildren == 0) {
			return false;
		} else {
			return true;
		}
	}

	public void setHasChildren(boolean hasChildren) {
		if(hasChildren){
			this.hasChildren = 1;
		}else{
			this.hasChildren = 0;
		}
		
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<NodeMenu> getChildren() {
		return children;
	}

	public void setChildren(List<NodeMenu> children) {
		this.children = children;
	}

}
