package com.packtpub.pf.blueprint.controller;

import com.packtpub.pf.blueprint.model.Location;
import com.packtpub.pf.blueprint.persistence.LocationDAO;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Ramkumar Pillai
 * Date: 3/30/14
 * Time: 7:50 PM
 * To change this template use File | Settings | File Templates.
 */

@Named
@RequestScoped
public class LocationController implements Serializable {

    private static final Logger log = Logger.getLogger(LocationController.class.getName());

    @PostConstruct
    public void init() {
        log.info ("Current Lati & longi : $currentLati  &  $currentLong" );
        populateLocationCoordinates();
    }

    @Inject
    private LocationDAO locationDAO;

    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
    }

    private void populateLocationCoordinates(){
        List<Location> locations = locationDAO.resultSet();
        if(locations == null) {
            return;
        }
        centerMap = "";
        locationMap = new DefaultMapModel();
        location = null;

        log.info("Too MAny Map Locations: "+locations.size());

        for(Location loc: locations){
            LatLng ll = new LatLng( loc.getLatitude(), loc.getLongitude());
            locationMap.addOverlay(new Marker(ll, loc.getStreet1(), loc.getFranchiseeNo()));
        }
    }

    @Getter @Setter Location location;
    @Getter @Setter String centerMap;

    @Getter @Setter MapModel locationMap;

    @Getter @Setter Marker marker;

}
