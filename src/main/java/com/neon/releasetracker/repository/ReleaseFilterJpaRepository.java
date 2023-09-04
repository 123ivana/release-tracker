package com.neon.releasetracker.repository;

import com.neon.releasetracker.model.ReleaseFilterRequest;
import com.neon.releasetracker.model.Release;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReleaseFilterJpaRepository implements ReleaseFilterRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Release> findAllByFilter(ReleaseFilterRequest filterReleaseRequestDto) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Release> criteriaQuery = criteriaBuilder.createQuery(Release.class);
        Root<Release> releaseRoot = criteriaQuery.from(Release.class);
        criteriaQuery.select(releaseRoot);
        Predicate whereExpression = createWhereClause(criteriaBuilder, releaseRoot, filterReleaseRequestDto, criteriaQuery);
        return entityManager.createQuery(criteriaQuery.where(whereExpression)).getResultList();
    }

    private Predicate createWhereClause(CriteriaBuilder criteriaBuilder, Root<Release> root,
                                        ReleaseFilterRequest request, CriteriaQuery<Release> criteriaQuery) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.conjunction());
        if (request.name() != null && !request.name().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("name"), request.name()));
        }
        if (request.description() != null && !request.description().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("description"), request.description()));
        }
        if (request.status() != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), request.status()));
        }
        if (request.lastUpdateAt() != null) {
            predicates.add(criteriaBuilder.equal(root.get("lastUpdateAt"), request.lastUpdateAt()));
        }
        if (request.lastUpdateAt() != null) {
            predicates.add(criteriaBuilder.equal(root.get("lastUpdateAt"), request.lastUpdateAt()));
        }
        if (request.releaseDate() != null) {
            predicates.add(criteriaBuilder.equal(root.get("releaseDate"), request.releaseDate()));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
