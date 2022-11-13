package com.zk.domain.repository;

import com.zk.domain.core.root.DriverStatus;
import org.springframework.data.repository.CrudRepository;

public interface DriverStatusRepo extends CrudRepository<DriverStatus, Long> {
    DriverStatus findByDriver_DriverId(Long driverId);
}
