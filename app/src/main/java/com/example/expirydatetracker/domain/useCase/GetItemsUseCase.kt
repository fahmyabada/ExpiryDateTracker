package com.example.expirydatetracker.domain.useCase

import com.example.expirydatetracker.data.model.Item
import com.example.expirydatetracker.domain.repository.ItemsRepository
import kotlinx.coroutines.flow.Flow

class GetItemsUseCase(private val itemRepository: ItemsRepository) {

     fun execute(): Flow<List<Item>> = itemRepository.getItem()
}