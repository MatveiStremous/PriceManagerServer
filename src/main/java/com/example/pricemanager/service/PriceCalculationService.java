package com.example.pricemanager.service;

import com.example.pricemanager.entity.PriceCalculation;
import com.example.pricemanager.repo.PriceCalculationRepository;

import java.util.List;

public class PriceCalculationService {
    private final PriceCalculationRepository priceCalculationRepository;

    public PriceCalculationService() {
        priceCalculationRepository = new PriceCalculationRepository();
    }

    public double createNewCalculation(PriceCalculation priceCalculation) {
        double result = priceCalculation.getAverageCost() * (priceCalculation.getTaxPerc() / 100 + 1) * (priceCalculation.getIncreasePerc() / 100 + 1);
        priceCalculation.setResult(result);
        priceCalculationRepository.addNewCalculation(priceCalculation);
        return result;
    }

    public void deleteAllUserPriceCalculations(int userId) {
        priceCalculationRepository.deleteAllCalculationsByUserId(userId);
    }

    public List<PriceCalculation> getAllUserPriceCalculations(int userId) {
        return priceCalculationRepository.getCalculationsByUserId(userId);
    }
}
