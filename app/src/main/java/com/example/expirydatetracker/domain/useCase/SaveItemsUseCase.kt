package com.example.expirydatetracker.domain.useCase

import com.example.expirydatetracker.data.model.Item
import com.example.expirydatetracker.domain.repository.ItemsRepository

class SaveItemsUseCase(private val itemRepository: ItemsRepository) {

    suspend fun execute(item: Item) = itemRepository.saveItem(item)
}