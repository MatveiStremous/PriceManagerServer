package com.example.pricemanager.service;

import com.example.pricemanager.entity.CostCalculation;
import com.example.pricemanager.repo.CostCalculationRepository;

import java.util.List;

public class CostCalculationService {
    private final CostCalculationRepository costCalculationRepository;

    public CostCalculationService() {
        costCalculationRepository = new CostCalculationRepository();
    }

    public double createNewCalculation(CostCalculation costCalculation) {
        double result = costCalculation.getMaterials() + costCalculation.getDeprecation() + costCalculation.getProduction() + costCalculation.getSalary() + costCalculation.getOthers();
        costCalculation.setResult(result);
        costCalculationRepository.addNewCalculation(costCalculation);
        return result;
    }

    public void deleteAllUserCostCalculations(int userId) {
        costCalculationRepository.deleteAllCalculationsByUserId(userId);
    }

    public List<CostCalculation> getAllUserCostCalculations(int userId) {
        return costCalculationRepository.getCalculationsByUserId(userId);
    }
}
