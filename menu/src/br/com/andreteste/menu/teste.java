package br.com.andreteste.menu;

import java.util.List;

import br.com.andreteste.dao.DAO;
import br.com.andreteste.modelos.NodeMenu;

public class teste {

	public static void main(String[] args) {
		
		List<NodeMenu> menus = new DAO<NodeMenu>(NodeMenu.class).listaTodos();
		System.out.println(menus.toString());
		
		for (NodeMenu menu : menus) {
		    System.out.println(menu.getChildren().size());
		}

	}

}
