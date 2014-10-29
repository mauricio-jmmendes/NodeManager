package com.nodemanager.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import com.nodemanager.managedbean.util.Converter;
import com.nodemanager.managedbean.util.FacesUtils;
import com.nodemanager.model.Cmts;
import com.nodemanager.model.Conector;
import com.nodemanager.model.Downstream;
import com.nodemanager.model.Hub;
import com.nodemanager.model.Pattern;
import com.nodemanager.model.Placa;
import com.nodemanager.model.Slot;
import com.nodemanager.model.Upstream;
import com.nodemanager.service.HubService;
import com.nodemanager.service.PatternService;
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
  private Cmts cmts;
  private List<Cmts> cmtsList;

  private Long slotId;
  private Slot slot;
  private List<Slot> slots;

  private Long placaId;
  private Placa placa;
  private PlacaService placaService;

  private Conector conector;
  private List<Conector> conectors;

  private Converter converter;
  private List<Converter> converterList = new ArrayList<>();

  private PatternService patternService;
  private Pattern pattern;
  private List<Pattern> patterns;


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

    patternService = new PatternService(JPAUtil.getSimpleEntityManager());
    patterns = patternService.getByType("Placa");

    pattern = new Pattern();

  }

  public void onCellEdit(Converter converter) {

    FacesUtils.addInfoMessage("Conector " + converter.getStringNumConector() + " atualizado!");

  }

  public List<Upstream> retornoList() {

    List<Upstream> ups = new ArrayList<>();

    if (null != placa.getConectorList()) {
      for (Conector conector : placa.getConectorList()) {
        if (null != conector.getUpstreamList()) {
          for (Upstream upstream : conector.getUpstreamList()) {
            if (!ups.contains(upstream)) {
              ups.add(upstream);
            }
          }
        }
      }
    }

    return ups;
  }

  public List<Downstream> diretoList() {
    List<Downstream> downs = new ArrayList<>();

    if (null != placa.getConectorList()) {
      for (Conector conector : placa.getConectorList()) {
        if (null != conector.getDownstreamList()) {
          for (Downstream down : conector.getDownstreamList()) {
            if (!downs.contains(down)) {
              downs.add(down);
            }
          }
        }
      }
    }

    return downs;
  }

  public List<Placa> getPlacasFromCmts() {

    List<Placa> placas = new ArrayList<>();

    for (Slot slot : cmts.getSlots()) {
      if (slot.getStatusSlot().equals(Status.OCUPADO)) {
        placas.add(slot.getPlaca());
      }
    }

    return placas;
  }

  public void loadPlaca() {
    for (Placa myPlaca : getPlacasFromCmts()) {
      if (myPlaca.getId() == placaId) {
        placa = myPlaca;
      }
    }
  }

  /**
   * Carrega o cmts pelo Id passado no parametro da requisição.
   */
  public void loadCmtsById() {

    for (Hub hub : hubs) {
      cmtsList = hub.getCmtsList();

      if (null != cmtsList) {
        for (Cmts myCmts : cmtsList) {
          if (myCmts.getId() == cmtsId) {
            cmts = myCmts;
          }
        }
      }
    }
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
        break;
      }
    }
  }

  public void addConectorsAction() {

    Long id = pattern.getId();

    pattern = patternService.getById(id);

    placa.setCodPlaca(pattern.getAlias());

    converter.setStringConectorList(pattern.getDescription());

    converterList.clear();

    List<String> connList = converter.getConectorList();

    for (String conn : connList) {
      Conector conector = new Conector();
      conector.setNumConector(conn);
      conectors.add(conector);

      converter = new Converter();
      converter.setStringNumConector(conn);

      converterList.add(converter);
    }
    // TODO Alterar isso aqui depois, usar uma string
    converter = new Converter();
    pattern = new Pattern();
  }

  public void addConvertersAction() {



  }

  public void deleteConverterAction(ActionEvent actionEvent) {
    converter.setStringNameUpOrDownList("");
    converter.setStringTypeStream("");
  }

  public void save() {

    slot = getSlotFromCmtsById();
    slot.setStatusSlot(Status.OCUPADO);

    placa.setSlot(slot);
    slot.setPlaca(placa);

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
          upstream.setStatusUp(Status.LIVRE);

          upstreams.add(upstream);
        }

        conector.setUpstreamList(upstreams);

      } else { // if not then it is a downstream

        List<Downstream> downstreams = new ArrayList<>();

        for (String element : converter.getUpOrDownList()) {
          Downstream downstream = new Downstream();
          downstream.setConectorDown(conector);
          downstream.setNumDownStream(Integer.parseInt(element));
          downstream.setStatusDown(Status.LIVRE);

          downstreams.add(downstream);
        }

        conector.setDownstreamList(downstreams);

      }

      conector.setPlaca(placa);
      conectors.add(conector);

    }

    placa.setConectorList(conectors);
    placa.setStatusPlaca(Status.FUNCIONANDO);

    try {
      placaService.save(placa);
      FacesUtils.addInfoMessage("Placa cadastrada com sucesso!");
      FacesUtils.getExternalContext().getFlash().setKeepMessages(true);
    } catch (Exception e) {
      FacesUtils.addInfoMessage("Erro ao cadastrar a placa, verifique os dados e tente novamente!");
      FacesUtils.getExternalContext().getFlash().setKeepMessages(true);
    }
  }

  private Slot getSlotFromCmtsById() {
    for (Slot mySlot : slots) {
      if (mySlot.getId().longValue() == slotId.longValue()) {
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
   * @return the placaId
   */
  public Long getPlacaId() {
    return placaId;
  }

  /**
   * @param placaId the placaId to set
   */
  public void setPlacaId(Long placaId) {
    this.placaId = placaId;
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

  /**
   * @return the pattern
   */
  public Pattern getPattern() {
    return pattern;
  }

  /**
   * @param pattern the pattern to set
   */
  public void setPattern(Pattern pattern) {
    this.pattern = pattern;
  }

  /**
   * @return the patterns
   */
  public List<Pattern> getPatterns() {
    return patterns;
  }

  /**
   * @param patterns the patterns to set
   */
  public void setPatterns(List<Pattern> patterns) {
    this.patterns = patterns;
  }

}
