package com.example.common.util;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import com.example.common.config.Constants;
import com.example.common.config.enums.SortOrderEnum;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@SuppressWarnings({ "serial", "unchecked", "rawtypes" })
public class SearchUtil {
     public static Pageable getPageableFromParam(Integer page, Integer size, String sort, SortOrderEnum order) {
    Sort sortRequest;
    if (sort != null) {
      sortRequest = Sort.by(order == SortOrderEnum.asc ? Direction.ASC : Direction.DESC, sort);
    } else {
      sortRequest = Sort.unsorted();
    }
    if (size == null || size > Constants.DEFAULT_PAGE_SIZE_MAX) {
      size = Constants.DEFAULT_PAGE_SIZE_MAX;
    }
    return PageRequest.of(page, size, sortRequest);
  }

  public static <T> Specification<T> like(String fieldName, String value) {
    return new Specification<T>() {
      @Override
      public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (value != null) {
          return cb.like(cb.lower(root.get(fieldName)), value.toLowerCase());
        }
        return cb.conjunction();
      }
    };
  }

  public static <R, F> Specification<R> in(String fieldName, List<F> filterList) {
    return new Specification<R>() {
      @Override
      public Predicate toPredicate(Root<R> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (filterList != null && filterList.size() > 0) {
          In<F> inClause = cb.in(root.get(fieldName));
          filterList.forEach(e -> inClause.value(e));
          return inClause;
        }
        return cb.conjunction();
      }
    };
  }

  public static <T> Specification<T> eq(String fieldName, Object value) {
    return new Specification<T>() {
      @Override
      public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (value != null) {
          return cb.equal(root.get(fieldName), value);
        }
        return cb.conjunction();
      }
    };
  }

  public static <T> Specification<T> gt(String fieldName, Comparable value) {
    return new Specification<T>() {
      @Override
      public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (value != null) {
          cb.greaterThan(root.get(fieldName), value);
        }
        return cb.conjunction();
      }
    };
  }

  public static <T> Specification<T> ge(String fieldName, Comparable value) {
    return new Specification<T>() {
      @Override
      public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (value != null) {
          return cb.greaterThanOrEqualTo(root.<Comparable>get(fieldName), value);
        }
        return cb.conjunction();
      }
    };
  }

  public static <T> Specification<T> lt(String fieldName, Comparable value) {
    return new Specification<T>() {
      @Override
      public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (value != null) {
          return  cb.lessThan(root.<Comparable>get(fieldName), value);
        }
        return cb.conjunction();
      }
    };
  }

  public static <T> Specification<T> le(String fieldName, Comparable value) {
    return new Specification<T>() {
      @Override
      public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (value != null) {
          cb.lessThanOrEqualTo(root.get(fieldName), value);
        }
        return cb.conjunction();
      }
    };
  }
     public static Specification<User> usernameIn(List<String> usernames) {
    return (root, query, criteriaBuilder) -> {
        if (usernames != null && !usernames.isEmpty()) {
            List<Predicate> predicates = new ArrayList<>();
            for (String username : usernames) {
                predicates.add(criteriaBuilder.like(root.get("username"), "%" + username + "%"));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        }
        return criteriaBuilder.conjunction();
    };
}

List<User> usersWithUsernameInList = userRepository.findAll(usernameIn(Arrays.asList("john", "jane", "bob")));
}
