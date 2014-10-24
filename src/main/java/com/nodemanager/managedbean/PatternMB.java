package com.nodemanager.managedbean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.nodemanager.managedbean.util.FacesUtils;
import com.nodemanager.model.Pattern;
import com.nodemanager.service.PatternService;
import com.nodemanager.util.JPAUtil;

@ManagedBean
@RequestScoped
public class PatternMB {

  private PatternService patternService;
  private Pattern pattern;

  @PostConstruct
  public void init() {
    patternService = new PatternService(JPAUtil.getSimpleEntityManager());

    pattern = new Pattern();

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

  public void save() {

    try {
      patternService.save(pattern);

      FacesUtils.addInfoMessage("Padrão cadastrado com sucesso!");
      FacesUtils.getExternalContext().getFlash().setKeepMessages(true);

    } catch (Exception e) {
      FacesUtils.addInfoMessage("Não foi possível cadastrar o Padrão! \n" + e.getMessage());
    }

  }
}
