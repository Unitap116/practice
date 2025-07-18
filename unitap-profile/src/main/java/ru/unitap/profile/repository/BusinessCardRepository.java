package ru.unitap.profile.repository;

import ru.unitap.profile.entity.BusinessCardEntity;

public interface BusinessCardRepository {

  BusinessCardEntity save(BusinessCardEntity businessCard);
}
