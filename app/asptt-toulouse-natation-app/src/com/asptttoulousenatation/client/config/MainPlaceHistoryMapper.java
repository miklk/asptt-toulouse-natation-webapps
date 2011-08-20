package com.asptttoulousenatation.client.config;

import com.asptttoulousenatation.client.userspace.menu.MenuPlace;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({MenuPlace.Tokenizer.class})
public interface MainPlaceHistoryMapper extends PlaceHistoryMapper {

}
