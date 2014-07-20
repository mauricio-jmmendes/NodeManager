package com.nodemanager.test;

import java.util.ArrayList;
import java.util.List;

import com.nodemanager.dao.utils.SimpleEntityManager;
import com.nodemanager.model.Cmts;
import com.nodemanager.model.Hub;
import com.nodemanager.model.Slot;
import com.nodemanager.service.CmtsService;
import com.nodemanager.service.HubService;
import com.nodemanager.util.Status;

public class TestDAOLayer {
	public static void main(String[] args) {
		String persistenceUnitName = "NodeManagerPU";

		SimpleEntityManager simpleEntityManager = new SimpleEntityManager(
				persistenceUnitName);

		/**
		 * THE SERVICE LAYER ENCAPSULATES EVERY BEGIN/COMMIT/ROLLBACK
		 */
		CmtsService cmtsService = new CmtsService(simpleEntityManager);
		HubService hubService = new HubService(simpleEntityManager);

		Hub hub = hubService.getByCodHub("HUB_B");

		Cmts cmts = new Cmts();
		cmts.setHub(hub);
		cmts.setIp("201.11.12.1");
		cmts.setMarca("Motorolla");
		cmts.setModelo("Motorolla");
		cmts.setNome("CPSPRI92");

		List<Slot> slots = new ArrayList<>();

		for (int i = 1; i < 11; i++) {
			Slot slot = new Slot();
			slot.setCmts(cmts);
			slot.setCodSlot(i + "/0");
			slot.setStatusSlot(Status.LIVRE);

			slots.add(slot);

		}

		cmts.setSlots(slots);

		cmtsService.save(cmts);

		for (Cmts c : cmtsService.findAll()) {
			System.out.println(c.getIp() + " - " + c.getMarca() + " - "
					+ c.getModelo());
		}

		/**
		 * ALWAYS NEED TO BE CALLED!
		 */
		simpleEntityManager.close();
	}
}