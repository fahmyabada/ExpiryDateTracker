package com.example.expirydatetracker.domain.useCase

import com.example.expirydatetracker.data.model.Item
import com.example.expirydatetracker.domain.repository.ItemsRepository

class GetCheckItemsExpiryUseCase(private val itemRepository: ItemsRepository) {

     fun execute(): List<Item> = itemRepository.getCheckItemsExpiry()
}