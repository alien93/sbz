package projara.model.dao.interfaces;

import java.util.List;

import projara.model.shop.ActionEvent;

public interface ActionEventDaoLocal extends GenericDaoLocal<ActionEvent, Short> {

	public List<ActionEvent> findActiveEvents();
}
