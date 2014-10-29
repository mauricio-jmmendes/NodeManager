package com.nodemanager.managedbean;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.nodemanager.managedbean.util.FacesUtils;
import com.nodemanager.model.Cmts;
import com.nodemanager.model.Hub;
import com.nodemanager.model.Pattern;
import com.nodemanager.service.CmtsService;
import com.nodemanager.service.HubService;
import com.nodemanager.service.PatternService;
import com.nodemanager.util.JPAUtil;

@ManagedBean
@RequestScoped
public class CmtsMB {

  private CmtsService cmtsService;

  private HubService hubService;

  private Cmts cmts;

  private List<Hub> hubs;

  private Long hubId;

  private List<Cmts> cmtsList = new ArrayList<Cmts>();

  private Long cmtsId;

  private PatternService patternService;
  private Pattern pattern;
  private List<Pattern> patterns;


  @PostConstruct
  public void init() {
    cmtsService = new CmtsService(JPAUtil.getSimpleEntityManager());
    hubService = new HubService(JPAUtil.getSimpleEntityManager());
   
    patternService = new PatternService(JPAUtil.getSimpleEntityManager());

    cmts = new Cmts();

    hubs = hubService.findAll();

    patterns = patternService.findAll();

    list();
  }

  public void list() {
    cmtsList = cmtsService.findAll();
  }

  public void prepareToAdd() {
    this.cmts = new Cmts();
    this.hubs = hubService.findAll();
  }

  /**
   * Carrega o cmts pelo Id passado no parametro da requisição.
   */
  public void loadCmtsById() {

    for (Cmts myCmts : cmtsList) {
      if (myCmts.getId() == cmtsId) {
        cmts = myCmts;
      }
    }
  }

  public List<String> getSlotListFromString() {

    String slots = pattern.getDescription();

    StringTokenizer stringTokenizer = new StringTokenizer(slots, ", ");

    List<String> slotList = new ArrayList<>(slots.length());

    while (stringTokenizer.hasMoreElements()) { // 0 1 2 ...
      slotList.add(stringTokenizer.nextToken());
    }

    return slotList;
  }

  public void save() {

    for (Pattern myPattern : patterns) {
      if (cmts.getModelo().equals(myPattern.getAlias())) {
        pattern = myPattern;
      }
    }

    Hub hub = hubService.getById(hubId);
    cmts.setHub(hub);

    if (null != hub.getCmtsList()) {
      hub.getCmtsList().add(cmts);
    } else {
      List<Cmts> list = new ArrayList<>();
      list.add(cmts);
      hub.setCmtsList(list);
    }

    try {
      cmtsService.save(cmts, getSlotListFromString());
      FacesUtils.addInfoMessage("CMTS cadastrado com sucesso!");
      FacesUtils.getExternalContext().getFlash().setKeepMessages(true);
      list();
      this.cmts = new Cmts();
    } catch (Exception e) {
      FacesUtils.addInfoMessage("Não foi possível cadastrar o CMTS! \n" + e.getMessage());
    }
  }

  public void delete() {
    try {
      cmtsService.delete(cmts);
      FacesUtils.addInfoMessage("CMTS removido com sucesso!");
      list();
    } catch (Exception e) {
      FacesUtils.addInfoMessage("Não foi possível remover o CMTS! \n" + e.getMessage());
    }
  }

  public void prepareToUpdate(Cmts cmts) {
    this.cmts = cmtsService.getById(cmts.getId());
  }

  public void update() {

    cmtsService.update(cmts);
    FacesUtils.addInfoMessage("CMTS atualizado com sucesso!");

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

  public Cmts getCmts() {
    return cmts;
  }

  public void setCmts(Cmts cmts) {
    this.cmts = cmts;
  }

  public List<Cmts> getCmtsList() {
    return cmtsList;
  }

  public void setCmtsList(List<Cmts> cmtsList) {
    this.cmtsList = cmtsList;
  }

  /**
   * @return the hub
   */
  public List<Hub> getHubs() {
    return hubs;
  }

  /**
   * @param hub the hub to set
   */
  public void setHubs(List<Hub> hubs) {
    this.hubs = hubs;
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
