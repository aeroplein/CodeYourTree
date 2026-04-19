package com.codeyourtree.backend.repository;

import com.codeyourtree.backend.model.TreeData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


//JpaRepository<TreeData, Long>: <Hangi tablo için çalışacak, o tablonun ID tipi ne.>
@Repository
public interface TreeDataRepository extends JpaRepository<TreeData, Long> {

}
