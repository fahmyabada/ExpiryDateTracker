package com.example.expirydatetracker.domain.useCase

import com.example.expirydatetracker.domain.repository.ItemsRepository

class UpdateStatusExpiryUseCase(private val itemRepository: ItemsRepository) {

     fun execute(id: Int) = itemRepository.updateStatusExpiry(id)
}