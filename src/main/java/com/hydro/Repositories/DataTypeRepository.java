package com.hydro.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hydro.Entities.DataType;

public interface DataTypeRepository extends JpaRepository<DataType, String>{

}
