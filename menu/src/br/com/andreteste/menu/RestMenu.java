package br.com.andreteste.menu;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.andreteste.dao.DAO;
import br.com.andreteste.modelos.NodeMenu;

@Path("/node/")
public class RestMenu {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<NodeMenu> getMenu() {
		List<NodeMenu> listMenu = new DAO<NodeMenu>(NodeMenu.class).listaRaiz();

		return montaArvore(listMenu);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public int post(NodeMenu menu) throws AddressException, MessagingException {
		int lastId =  new DAO<NodeMenu>(NodeMenu.class).adiciona(menu);
		
		new DAO<NodeMenu>(NodeMenu.class).verificaHasChild();
	
		return	lastId;
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public int put(NodeMenu menu) throws AddressException, MessagingException {
		new DAO<NodeMenu>(NodeMenu.class).atualiza(menu);
		return menu.getId();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{parentId}")
	public List<NodeMenu> getNo(@PathParam("parentId") int parentId) {
		if(parentId==0){
			return null;
		}
		
		List<NodeMenu> menu = new DAO<NodeMenu>(NodeMenu.class).listaArvore(parentId);
		return montaArvore(menu);
	}

	private List<NodeMenu> montaArvore(List<NodeMenu> ListMenu) {

		List<NodeMenu> arvorePronta = new ArrayList<NodeMenu>();

		for (NodeMenu menu : ListMenu) {
			if (menu.getHasChildren()) {
				menu.setChildren(new DAO<NodeMenu>(NodeMenu.class).listaArvore(menu.getId()));
				montaArvore(menu.getChildren());
			}
			arvorePronta.add(menu);
		}

		return arvorePronta;
	}

}
