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
        double result = calculateResult(costCalculation);
        costCalculation.setResult(result);
        costCalculationRepository.addNewCalculation(costCalculation);
        return result;
    }

    public void deleteAllUserCostCalculations(int userId) {
        costCalculationRepository.deleteAllCalculationsByUserId(userId);
    }

    public List<CostCalculation> getAllUserCostCalculations(int userId) {
        List<CostCalculation> costCalculations = costCalculationRepository.getCalculationsByUserId(userId);
        double result = 0;
        for (int i = 0; i < costCalculations.size(); i += 1) {
            result = calculateResult(costCalculations.get(i));
            costCalculations.get(i).setResult(result);
        }
        return costCalculations;
    }

    private double calculateResult(CostCalculation costCalculation) {
        double result = costCalculation.getMaterials() + costCalculation.getDeprecation() +
                costCalculation.getProduction() + costCalculation.getSalary() + costCalculation.getOthers();
        return Math.round(result * 100) / 100.0;
    }
}
