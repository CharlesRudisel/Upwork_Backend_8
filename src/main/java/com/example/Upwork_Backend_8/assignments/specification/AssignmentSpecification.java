package com.example.Upwork_Backend_8.assignments.specification;

import com.example.Upwork_Backend_8.assignments.entity.Assignment;
import com.example.Upwork_Backend_8.assignments.specification.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AssignmentSpecification implements Specification<Assignment> {

    private final List<SearchCriteria>  criteriaList;

    public AssignmentSpecification(){
        this.criteriaList = new ArrayList<>();
    }

    public void addCriteria(SearchCriteria searchCriteria){
        criteriaList.add(searchCriteria);
    }
    @Override
    public Predicate toPredicate(Root<Assignment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        for(SearchCriteria searchCriteria : criteriaList){
            switch (searchCriteria.getOperation()){
                case EQUALITY:
                    predicates.add(criteriaBuilder.equal(root.get(searchCriteria.getKey()) , searchCriteria.getValue()));
                    break;
                case LIKE:
                    predicates.add(criteriaBuilder.like(root.get(searchCriteria.getKey()) , "%" + searchCriteria.getValue() + "%"));
                    break;
                case GREATER_THAN:
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(searchCriteria.getKey()) , searchCriteria.getValue().toString()));
                    break;
                case LESS_THAN:
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString()));
                    break;
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
