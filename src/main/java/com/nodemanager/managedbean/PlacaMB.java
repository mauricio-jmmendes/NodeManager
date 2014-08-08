package com.nodemanager.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import com.nodemanager.managedbean.util.Converter;
import com.nodemanager.managedbean.util.FacesUtils;
import com.nodemanager.model.Conector;
import com.nodemanager.model.Placa;
import com.nodemanager.model.Slot;
import com.nodemanager.model.Upstream;
import com.nodemanager.service.PlacaService;
import com.nodemanager.util.JPAUtil;

@ManagedBean
public class PlacaMB {

  private Placa placa;
  private PlacaService placaService;

  private Slot slot;

  private Conector conector;
  private Converter converter;

  private List<Converter> converterList = new ArrayList<>();


  @PostConstruct
  public void init() {
    setConverter(new Converter());
    converterList = new ArrayList<>();

    placa = new Placa();
    placaService = new PlacaService(JPAUtil.getSimpleEntityManager());

  }

  public void save() {

    slot = new Slot();


    placa.setSlot(slot);


    for (Converter converter : converterList) {
      conector = new Conector();
      conector.setNumConector(converter.getPrimeiroParametro());

      if (converter.getSegundoParametro().equals("UPSTREAM")) {

        List<Upstream> upstreams = new ArrayList<>();

        for (int i = 0; i < converter.getTerceiroParametro().size(); i++) {
          Upstream upstream = new Upstream();
          upstream.setConectorUp(conector);
          upstream.setNumUpStream(Integer.parseInt(converter.getTerceiroParametro().get(i)));

          upstreams.add(upstream);
        }

        conector.setUpstreamList(upstreams);
      }

    }


    placaService.save(placa);
    FacesUtils.addInfoMessage("Placa cadastrada com sucesso!");
    placa = new Placa();

  }

  public void prepareToEdit(Placa placa) {
    this.placa = placa;

  }

  public void delete() {
    placaService.delete(placa);
    FacesUtils.addInfoMessage("Placa removida com sucesso!");
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
}
