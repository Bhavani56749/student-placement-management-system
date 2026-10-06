package service;

import dao.PlacementDAO;
import model.Placement;

import java.util.List;

public class PlacementService {

    private PlacementDAO placementDAO;

    public PlacementService() {
        placementDAO = new PlacementDAO();
    }

    public List<Placement> getAllPlacements() {
        return placementDAO.getAllPlacements();
    }

    public Placement getPlacementById(int placementId) {
        return placementDAO.getPlacementById(placementId);
    }

    public boolean addPlacement(Placement placement) {
        return placementDAO.addPlacement(placement);
    }

    public boolean updatePlacement(Placement placement) {
        return placementDAO.updatePlacement(placement);
    }

    public boolean deletePlacement(int placementId) {
        return placementDAO.deletePlacement(placementId);
    }
}