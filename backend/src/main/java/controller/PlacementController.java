package controller;

import model.Placement;
import service.PlacementService;

import java.util.List;

public class PlacementController {

    private PlacementService placementService;

    public PlacementController() {
        placementService = new PlacementService();
    }

    // GET ALL PLACEMENTS
    public List<Placement> getAllPlacements() {
        return placementService.getAllPlacements();
    }

    // GET PLACEMENT BY ID
    public Placement getPlacementById(int placementId) {
        return placementService.getPlacementById(placementId);
    }

    // ADD PLACEMENT
    public boolean addPlacement(Placement placement) {
        return placementService.addPlacement(placement);
    }

    // UPDATE PLACEMENT
    public boolean updatePlacement(Placement placement) {
        return placementService.updatePlacement(placement);
    }

    // DELETE PLACEMENT
    public boolean deletePlacement(int placementId) {
        return placementService.deletePlacement(placementId);
    }
}