package com.example.security.repo;

import com.example.security.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreRepo extends JpaRepository<Store,String> {
    Optional<Store> findById(String id);
    @Query("SELECT c FROM Store c WHERE c.staffIds LIKE %:staffIds% AND c.isDelete = :isDelete")
    List<Store> findByStaffIdsLikeAndIsDelete(@Param("staffIds") String staffIds ,@Param("isDelete") Integer isDelete);
    List<Store> findAllByOwnerIdAndIsDelete(String ownerId,Integer isdelete);
    @Query("SELECT e FROM Store e WHERE e.expireDate <= :value")
    List<Store> findByExpireDateThanOrEqual(@Param("value") String value);
    @Query("SELECT s FROM Store s WHERE s.ownerId = :Store OR s.name LIKE %:staffIds%")
    List<Store> findByDescriptionOrName(@Param("Store") String Store, @Param("staffIds") String staffIds);
    @Query("SELECT s FROM Store s WHERE (s.ownerId = :owmerId OR s.staffIds LIKE %:staffIds%) AND s.isDelete = :isDelete")
    List<Store> findByOwnerIdOrStaffIdsAndIsDelete(@Param("owmerId") String owmerId, @Param("staffIds") String staffIds, @Param("isDelete") Integer isDelete);
//    List<Store> findAllById(List<String> ids);
}


