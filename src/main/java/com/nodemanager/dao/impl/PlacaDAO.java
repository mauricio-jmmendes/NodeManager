package com.nodemanager.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.nodemanager.model.Placa;
import com.nodemanager.util.Status;

public class PlacaDAO extends GenericDAO<Placa, Long> {

  public PlacaDAO(EntityManager entityManager) {
    super(entityManager);
  }

  public List<Placa> findPlacaByUpstreamStatus(Long idCmts, Long qtdUpstream, Status soUpsLivres) {
    return (List<Placa>) getEntityManager()
        .createQuery(
            "SELECT p FROM Placa p, Conector c, Upstream u, Downstream d WHERE p.id = c.placa AND (c.id = u.conectorUp OR c.id = d.conectorDown) AND (u.statusUp = :soUpsLivres OR d.statusDown = :soUpsLivres) group by p.id having count(*) >= :qtdUpstream",
            Placa.class).setParameter("soUpsLivres", soUpsLivres)
        .setParameter("qtdUpstream", qtdUpstream).getResultList();
  }

}
