package com.nodemanager.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.nodemanager.managedbean.util.Converter;
import com.nodemanager.managedbean.util.FacesUtils;
import com.nodemanager.model.Cmts;
import com.nodemanager.model.Conector;
import com.nodemanager.model.Downstream;
import com.nodemanager.model.Hub;
import com.nodemanager.model.Placa;
import com.nodemanager.model.Slot;
import com.nodemanager.model.Upstream;
import com.nodemanager.service.HubService;
import com.nodemanager.service.PlacaService;
import com.nodemanager.util.JPAUtil;
import com.nodemanager.util.Status;

@ManagedBean
@ViewScoped
public class PlacaMB {

  private Long hubId;
  private List<Hub> hubs;

  private HubService hubService;

  private Long cmtsId;
  private List<Cmts> cmtsList;

  private Long slotId;
  private Slot slot;
  private List<Slot> slots;

  private Placa placa;
  private PlacaService placaService;

  private Conector conector;
  private List<Conector> conectors;

  private Converter converter;
  private List<Converter> converterList = new ArrayList<>();


  @PostConstruct
  public void init() {
    converter = new Converter();
    converterList = new ArrayList<>();

    placa = new Placa();
    placaService = new PlacaService(JPAUtil.getSimpleEntityManager());

    conector = new Conector();
    conectors = new ArrayList<>();

    hubService = new HubService(JPAUtil.getSimpleEntityManager());
    hubs = hubService.findAll();

  }

  public void handleHubListBoxChange() {
    Hub hub = hubService.getById(hubId);
    cmtsList = hub.getCmtsList();
  }

  public void handleCmtsListBoxChange() {
    slots = new ArrayList<>();

    for (Cmts myCmts : cmtsList) {
      if (myCmts.getId() == cmtsId) {
        slots = myCmts.getSlots();
      }
    }
  }

  public void addConectorsAction() {
    for (String conn : converter.getConectorList()) {
      Conector conector = new Conector();
      conector.setNumConector(conn);
      conectors.add(conector);
    }
  }

  public void addConvertersAction() {
    converterList.add(converter);
    converter = new Converter();

  }

  public void save() {

    slot = getSlotFromCmtsById();
    slot.setStatusSlot(Status.OCUPADO);

    placa.setSlot(slot);

    List<Conector> conectors = new ArrayList<>();

    for (Converter converter : converterList) {
      conector = new Conector();

      conector.setNumConector(converter.getStringNumConector());
      conector.setStatusConector(Status.OCUPADO);

      if (converter.getStringTypeStream().equals("UPSTREAM")) {

        List<Upstream> upstreams = new ArrayList<>();

        for (String element : converter.getUpOrDownList()) { // 0, 1, 2, 3, ...
          Upstream upstream = new Upstream();
          upstream.setConectorUp(conector);
          upstream.setNumUpStream(Integer.parseInt(element));
          upstream.setStatusUp(Status.OCUPADO);

          upstreams.add(upstream);
        }

        conector.setUpstreamList(upstreams);

      } else { // if not then it is a downstream

        List<Downstream> downstreams = new ArrayList<>();

        for (String element : converter.getUpOrDownList()) {
          Downstream downstream = new Downstream();
          downstream.setConectorDown(conector);
          downstream.setNumDownStream(Integer.parseInt(element));
          downstream.setStatusDown(Status.OCUPADO);

          downstreams.add(downstream);
        }

        conector.setDownstreamList(downstreams);

      }

      conector.setPlaca(placa);
      conectors.add(conector);

    }

    placa.setConectorList(conectors);

    placaService.save(placa);
    FacesUtils.addInfoMessage("Placa cadastrada com sucesso!");

  }

  private Slot getSlotFromCmtsById() {
    for (Slot mySlot : slots) {
      if (mySlot.getId() == slotId) {
        return mySlot;
      }
    }
    return null;
  }

  public void prepareToEdit(Placa placa) {
    this.placa = placa;

  }

  public void delete() {
    placaService.delete(placa);
    FacesUtils.addInfoMessage("Placa removida com sucesso!");
  }

  public void addUpOrDown() {
    converterList.add(converter);
    converter = new Converter();
  }

  /**
   * @return the slotId
   */
  public Long getSlotId() {
    return slotId;
  }

  /**
   * @param slotId the slotId to set
   */
  public void setSlotId(Long slotId) {
    this.slotId = slotId;
  }

  /**
   * @return the slot
   */
  public Slot getSlot() {
    return slot;
  }

  /**
   * @param slot the slot to set
   */
  public void setSlot(Slot slot) {
    this.slot = slot;
  }

  /**
   * @return the slots
   */
  public List<Slot> getSlots() {
    return slots;
  }

  /**
   * @param slots the slots to set
   */
  public void setSlots(List<Slot> slots) {
    this.slots = slots;
  }

  /**
   * @return the placa
   */
  public Placa getPlaca() {
    return placa;
  }

  /**
   * @param placa the placa to set
   */
  public void setPlaca(Placa placa) {
    this.placa = placa;
  }

  /**
   * @return the converter
   */
  public Converter getConverter() {
    return converter;
  }

  /**
   * @param converter the converter to set
   */
  public void setConverter(Converter converter) {
    this.converter = converter;
  }

  /**
   * @return the hubId
   */
  public Long getHubId() {
    return hubId;
  }

  /**
   * @param hubId the hubId to set
   */
  public void setHubId(Long hubId) {
    this.hubId = hubId;
  }

  /**
   * @return the hubs
   */
  public List<Hub> getHubs() {
    return hubs;
  }

  /**
   * @param hubs the hubs to set
   */
  public void setHubs(List<Hub> hubs) {
    this.hubs = hubs;
  }

  /**
   * @return the cmtsId
   */
  public Long getCmtsId() {
    return cmtsId;
  }

  /**
   * @param cmtsId the cmtsId to set
   */
  public void setCmtsId(Long cmtsId) {
    this.cmtsId = cmtsId;
  }

  /**
   * @return the cmtsList
   */
  public List<Cmts> getCmtsList() {
    return cmtsList;
  }

  /**
   * @param cmtsList the cmtsList to set
   */
  public void setCmtsList(List<Cmts> cmtsList) {
    this.cmtsList = cmtsList;
  }

  /**
   * @return the conector
   */
  public Conector getConector() {
    return conector;
  }

  /**
   * @param conector the conector to set
   */
  public void setConector(Conector conector) {
    this.conector = conector;
  }

  /**
   * @return the conectors
   */
  public List<Conector> getConectors() {
    return conectors;
  }

  /**
   * @param conectors the conectors to set
   */
  public void setConectors(List<Conector> conectors) {
    this.conectors = conectors;
  }

  /**
   * @return the converterList
   */
  public List<Converter> getConverterList() {
    return converterList;
  }

  /**
   * @param converterList the converterList to set
   */
  public void setConverterList(List<Converter> converterList) {
    this.converterList = converterList;
  }

}
