package com.taodigitalsolutions.retail.repository;


public interface ApprovalQueueRepository extends JpaRepository<ApprovalQueue, Long> {
    List<ApprovalQueue> findAllByOrderByRequestDateAsc();
}
