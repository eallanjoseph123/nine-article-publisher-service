package com.nine.service.model.entity;

import org.springframework.util.CollectionUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Collection;

@MappedSuperclass
public abstract class AbstractPersistent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	protected <T extends  AbstractPersistent> boolean isCollectionContainsEntity(Collection<T> collection, AbstractPersistent entity){
		if(CollectionUtils.isEmpty(collection) || entity == null){
			return false;
		}
		for(AbstractPersistent existingEntity : collection){

			if(existingEntity == entity){
				return true;
			}

			if(existingEntity.getId() != null && entity.getId() != null && existingEntity.getId().equals(entity.getId())){
				return true;
			}
		}

		return false;
	}
}
