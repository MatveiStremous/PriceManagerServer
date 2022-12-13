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
        double result = calculateResult(priceCalculation);
        priceCalculation.setResult(result);
        priceCalculationRepository.addNewCalculation(priceCalculation);
        return result;
    }

    public void deleteAllUserPriceCalculations(int userId) {
        priceCalculationRepository.deleteAllCalculationsByUserId(userId);
    }

    public List<PriceCalculation> getAllUserPriceCalculations(int userId) {
        List<PriceCalculation> priceCalculations = priceCalculationRepository.getCalculationsByUserId(userId);
        double result = 0;
        for (int i = 0; i < priceCalculations.size(); i += 1) {
            result = calculateResult(priceCalculations.get(i));
            priceCalculations.get(i).setResult(result);
        }
        return priceCalculations;
    }

    private double calculateResult(PriceCalculation priceCalculation) {
        double result = priceCalculation.getAverageCost() * (priceCalculation.getTaxPerc() / 100 + 1) *
                (priceCalculation.getIncreasePerc() / 100 + 1);
        return Math.round(result * 100) / 100.0;
    }
}
