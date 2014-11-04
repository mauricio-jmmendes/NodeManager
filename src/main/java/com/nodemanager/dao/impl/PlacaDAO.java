package com.nodemanager.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.nodemanager.model.Placa;
import com.nodemanager.util.Status;

public class PlacaDAO extends GenericDAO<Placa, Long> {

  public PlacaDAO(EntityManager entityManager) {
    super(entityManager);
  }

  public List<Placa> findPlacaByUpstreamStatus(List<Long> cmtsIds, Long qtdUpstream,
      Status statusUpstream) {

    List<Placa> list = new ArrayList<>();
    StringBuilder query = new StringBuilder();

    query.append("SELECT p FROM Cmts c, Slot s, Placa p, Conector co, Upstream u WHERE");

    if (!cmtsIds.isEmpty()) {
      query.append(" c.id in :cmtsIds AND");
    }

    query.append(" c.id = s.cmts AND s.id = p.slot AND p.id = co.placa AND co.id = u.conectorUp");
    query.append(" AND u.statusUp = :statusUpstream group by p.id having count(*) >= :qtdUpstream");

    if (!cmtsIds.isEmpty()) {
      list =
          (List<Placa>) getEntityManager().createQuery(query.toString(), Placa.class)
              .setParameter("statusUpstream", statusUpstream).setParameter("cmtsIds", cmtsIds)
              .setParameter("qtdUpstream", qtdUpstream).getResultList();
    } else {
      list =
          (List<Placa>) getEntityManager().createQuery(query.toString(), Placa.class)
              .setParameter("statusUpstream", statusUpstream)
              .setParameter("qtdUpstream", qtdUpstream).getResultList();
    }

    return list;
  }

  public List<Placa> findPlacaByDownstreamStatus(Long qtdDownstream, Status statusDownstream) {

    List<Placa> list = new ArrayList<>();
    StringBuilder query = new StringBuilder();

    query.append("SELECT p FROM Cmts c, Slot s, Placa p, Conector co, Downstream d WHERE c.id = s.cmts");
    query.append(" AND s.id = p.slot AND p.id = co.placa");
    query.append(" AND co.id = d.conectorDown AND d.statusDown = :statusDownstream");
    query.append(" group by p.id having count(*) >= :qtdDownstream");

    list =
        (List<Placa>) getEntityManager().createQuery(query.toString(), Placa.class)
            .setParameter("statusDownstream", statusDownstream)
            .setParameter("qtdDownstream", qtdDownstream).getResultList();

    return list;
  }



}
