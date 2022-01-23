package com.example.expirydatetracker.domain.useCase

import com.example.expirydatetracker.data.model.Item
import com.example.expirydatetracker.domain.repository.ItemsRepository
import kotlinx.coroutines.flow.Flow

class GetItemsByIdUseCase(private val itemRepository: ItemsRepository) {

    fun execute(id: Int): Flow<Item> = itemRepository.getItemsById(id)
}