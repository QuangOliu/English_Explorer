package com.ptit.EnglishExplorer.data.entity.auditing;

import com.ptit.EnglishExplorer.data.entity.User;
//import com.ptit.security.CommonUtils;
//import com.ptit.security.SecurityUtils;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.apache.coyote.Constants;

import java.time.LocalDateTime;

public class EntityAuditListener {

	@PrePersist
	public void beforePersit(AuditableEntity auditableEntity) {

		LocalDateTime now = LocalDateTime.now();

		auditableEntity.setCreateDate(now);
		auditableEntity.setModifyDate(now);

//		User user = SecurityUtils.getCurrentUser();
//		auditableEntity.setCreatedBy("admin");
//		if (CommonUtils.isNotNull(user)) {
//
//			auditableEntity.setCreatedBy(user.getUsername());
//			auditableEntity.setModifiedBy(user.getUsername());
//
//		} else {
//			auditableEntity.setCreatedBy("admin");
//		}
	}

	@PreUpdate
	public void beforeMerge(AuditableEntity auditableEntity) {

		auditableEntity.setModifyDate(LocalDateTime.now());

//		User user = SecurityUtils.getCurrentUser();
//
//		if (CommonUtils.isNotNull(user)) {
//			auditableEntity.setModifiedBy(user.getUsername());
//		}
	}
}
