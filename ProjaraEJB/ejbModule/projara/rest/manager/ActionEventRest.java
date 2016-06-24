package projara.rest.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import projara.model.dao.interfaces.ActionEventDaoLocal;
import projara.model.dao.interfaces.ItemCategoryDaoLocal;
import projara.model.items.ItemCategory;
import projara.model.shop.ActionEvent;
import projara.session.interfaces.ActionManagerLocal;
import projara.util.exception.ActionEventNotExists;
import projara.util.json.view.ActionJson;
import projara.util.json.view.ItemCategoryInfo;

@Path("/actionEvent")
@Stateless
@Local(ActionEventRestLocal.class)
public class ActionEventRest implements ActionEventRestLocal{
	
	private static final String MODIFY = "modify";
	private static final String ADD = "add";
	
	@EJB
	private ActionManagerLocal actionManager;
	
	@EJB
	private ActionEventDaoLocal actionEventDao;
	
	@EJB
	private ItemCategoryDaoLocal itemCategoryDao;
	
	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<ActionJson> getAllActionEvents() {
		List<ActionJson> ret = new ArrayList<>();
		for(ActionEvent a : actionEventDao.findAll()){
			try {
				ret.add(actionManager.transformAction(a));
			} catch (ActionEventNotExists e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	@GET
	@Path("/getItemCat/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<ItemCategoryInfo> getCategoriesForAction(@PathParam("id") int actionId) {
		List<ItemCategoryInfo> ret = new ArrayList<>();
		List<ItemCategory> categories = new ArrayList<>();
		
		ActionEvent ae = actionEventDao.findById(actionId);
		
		if(ae == null){
			categories = itemCategoryDao.findAll();
		}
		else{
			for(ItemCategory ic : itemCategoryDao.findAll()){
				if(ae.getCategories().contains(ic)) continue;
				categories.add(ic);
			}
		}
		for(ItemCategory ic : categories){
			ret.add(new ItemCategoryInfo(ic.getCode(), ic.getName(), ic.getMaxDiscount()));
		}
		return ret;
	}

	@GET
	@Path("/getSubCat/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<ItemCategoryInfo> getItemCat(@PathParam("code") String catCode) {
		List<ItemCategoryInfo> ret = new ArrayList<ItemCategoryInfo>();
		ItemCategory ic = itemCategoryDao.findById(catCode);
		ret.add(new ItemCategoryInfo(ic.getCode(), ic.getName(), ic.getMaxDiscount()));
		for(ItemCategory i : ic.getSubCategories()){
			ret.add(new ItemCategoryInfo(i.getCode(), i.getName(), i.getMaxDiscount()));
		}
		return ret;
	}

	@POST
	@Path("/add/{modify}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public RestMsg addAction(ActionJson action, @PathParam("modify") String modify) {
		ActionEvent ai = new ActionEvent();
		if(modify.equals(MODIFY)){
			ai = actionEventDao.findById(action.getInfo().getId());
		}
		ai.setName(action.getInfo().getName());
		ai.setFrom(action.getInfo().getFrom());
		ai.setUntil(action.getInfo().getUntil());
		ai.setDiscount(action.getInfo().getDicount());
		
		if(modify.equals(ADD)) actionEventDao.persist(ai);
		ai.setCategories(new HashSet<ItemCategory>());
		ai = actionEventDao.merge(ai);
		for(ItemCategoryInfo ici : action.getCategories()){
			ai.addCategories(itemCategoryDao.findById(ici.getCode()));
		}
		ai = actionEventDao.merge(ai);
		return new RestMsg("OK", null);
	}

}
