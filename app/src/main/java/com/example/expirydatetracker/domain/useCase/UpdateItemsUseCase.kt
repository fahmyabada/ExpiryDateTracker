package com.example.expirydatetracker.domain.useCase

import com.example.expirydatetracker.domain.repository.ItemsRepository

class UpdateItemsUseCase(private val itemRepository: ItemsRepository) {

    suspend fun execute(id: Int, name: String, category: String, date: String) = itemRepository.updateItem(id, name, category, date)
}