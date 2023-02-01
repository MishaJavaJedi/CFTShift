package com.shulga.data.db

import com.shulga.domain.models.searchHistory.SearchHistoryModel

internal fun SearchHistoryEntity.toModel() = SearchHistoryModel(id = id, number = number)
internal fun SearchHistoryModel.toEntity() = SearchHistoryEntity(id = id, number = number)