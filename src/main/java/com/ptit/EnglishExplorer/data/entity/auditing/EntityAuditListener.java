package com.ptit.EnglishExplorer.data.entity.auditing;

import com.ptit.EnglishExplorer.auditing.ApplicationAuditAware;
import com.ptit.EnglishExplorer.data.entity.User;
//import com.ptit.security.CommonUtils;
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

		User user = ApplicationAuditAware.getCurrentUser();
		if (user != null) {
			auditableEntity.setCreatedBy(user.getUsername());
			auditableEntity.setModifiedBy(user.getUsername());
		} else {
			auditableEntity.setCreatedBy("admin");
			auditableEntity.setModifiedBy("admin");
		}
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
