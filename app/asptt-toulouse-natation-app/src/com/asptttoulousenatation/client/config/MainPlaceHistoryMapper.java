package com.asptttoulousenatation.client.config;

import com.asptttoulousenatation.client.MainPlace;
import com.asptttoulousenatation.client.subscription.SubscriptionPlace;
import com.asptttoulousenatation.client.userspace.UserSpacePlace;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({MainPlace.Tokenizer.class, UserSpacePlace.Tokenizer.class, SubscriptionPlace.Tokenizer.class})
public interface MainPlaceHistoryMapper extends PlaceHistoryMapper {

}
