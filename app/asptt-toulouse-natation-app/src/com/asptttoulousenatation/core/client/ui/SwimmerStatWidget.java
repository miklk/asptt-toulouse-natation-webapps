package com.asptttoulousenatation.core.client.ui;

import java.util.List;

import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatDataUi;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatUi;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.TextBox;

public class SwimmerStatWidget implements IsSerializable {

	private SwimmerStatUi swimmerStat;
	private TextBox morning;
	private TextBox midday;
	private TextBox night;
	private TextBox bodybuilding;
	private TextBox comment;

	public SwimmerStatWidget() {

	}

	public SwimmerStatWidget(SwimmerStatUi pSwimmerStat) {
		swimmerStat = pSwimmerStat;
		morning = new TextBox();
		midday = new TextBox();
		night = new TextBox();
		bodybuilding = new TextBox();
		comment = new TextBox();
		setValues();
	}

	private void setValues() {
		List<SwimmerStatDataUi> lData = swimmerStat.getData();
		if (lData != null) {
			if (lData.size() >= 1) {
				morning.setValue(Integer.toString(lData.get(0).getDistance()));
				comment.setValue(lData.get(0).getComment());
			} else {
				morning.setValue("0");
			}
			if (lData.size() >= 2) {
				midday.setValue(Integer.toString(lData.get(1).getDistance()));
			} else {
				midday.setValue("0");
			}
			if (lData.size() >= 3) {
				night.setValue(Integer.toString(lData.get(2).getDistance()));
			} else {
				night.setValue("0");
			}
			if (lData.size() >= 4) {
				bodybuilding.setValue(Integer.toString(lData.get(3)
						.getDistance()));
			} else {
				bodybuilding.setValue("0");
			}
		}
	}

	public SwimmerStatUi getSwimmerStat() {
		return swimmerStat;
	}

	public void setSwimmerStat(SwimmerStatUi pSwimmerStat) {
		swimmerStat = pSwimmerStat;
	}

	public TextBox getMorning() {
		return morning;
	}

	public void setMorning(TextBox pMorning) {
		morning = pMorning;
	}

	public TextBox getMidday() {
		return midday;
	}

	public void setMidday(TextBox pMidday) {
		midday = pMidday;
	}

	public TextBox getNight() {
		return night;
	}

	public void setNight(TextBox pNight) {
		night = pNight;
	}

	public TextBox getBodybuilding() {
		return bodybuilding;
	}

	public void setBodybuilding(TextBox pBodybuilding) {
		bodybuilding = pBodybuilding;
	}

	public TextBox getComment() {
		return comment;
	}

	public void setComment(TextBox pComment) {
		comment = pComment;
	}
}