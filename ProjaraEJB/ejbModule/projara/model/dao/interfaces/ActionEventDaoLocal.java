package projara.model.dao.interfaces;

import java.util.List;

import projara.model.shop.ActionEvent;

public interface ActionEventDaoLocal extends GenericDaoLocal<ActionEvent, Integer> {

	public List<ActionEvent> findActiveEvents();
}
