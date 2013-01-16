package com.asptttoulousenatation.core.client.ui;

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
	private boolean updatedMorning;
	private boolean updatedMidday;
	private boolean updatedNight;
	private boolean updatedBodybuilding;
	private boolean updatedComment;

	public SwimmerStatWidget() {
		updatedMorning = false;
		updatedMidday = false;
		updatedNight = false;
		updatedBodybuilding = false;
		updatedComment = false;
	}

	public SwimmerStatWidget(SwimmerStatUi pSwimmerStat) {
		this();
		swimmerStat = pSwimmerStat;
		morning = new TextBox();
		midday = new TextBox();
		night = new TextBox();
		bodybuilding = new TextBox();
		comment = new TextBox();
		setValues();
	}

	private void setValues() {
		if (swimmerStat.getMorning() != null) {
			morning.setValue(Integer.toString(swimmerStat.getMorning()
					.getDistance()));
		} else {
			morning.setValue("0");
		}
		if (swimmerStat.getMidday() != null) {
			midday.setValue(Integer.toString(swimmerStat.getMidday()
					.getDistance()));
		} else {
			midday.setValue("0");
		}
		if (swimmerStat.getNight() != null) {
			night.setValue(Integer.toString(swimmerStat.getNight()
					.getDistance()));
		} else {
			night.setValue("0");
		}
		if (swimmerStat.getBodybuilding() != null) {
			bodybuilding.setValue(Integer.toString(swimmerStat
					.getBodybuilding().getDistance()));
		} else {
			bodybuilding.setValue("0");
		}
		if (swimmerStat.getComment() != null
				&& !swimmerStat.getComment().isEmpty()) {
			comment.setValue(swimmerStat.getComment());
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

	public boolean isUpdatedMorning() {
		return updatedMorning;
	}

	public void setUpdatedMorning(boolean pUpdatedMorning) {
		updatedMorning = pUpdatedMorning;
	}

	public boolean isUpdatedMidday() {
		return updatedMidday;
	}

	public void setUpdatedMidday(boolean pUpdatedMidday) {
		updatedMidday = pUpdatedMidday;
	}

	public boolean isUpdatedNight() {
		return updatedNight;
	}

	public void setUpdatedNight(boolean pUpdatedNight) {
		updatedNight = pUpdatedNight;
	}

	public boolean isUpdatedBodybuilding() {
		return updatedBodybuilding;
	}

	public void setUpdatedBodybuilding(boolean pUpdatedBodybuilding) {
		updatedBodybuilding = pUpdatedBodybuilding;
	}

	public boolean isUpdatedComment() {
		return updatedComment;
	}

	public void setUpdatedComment(boolean pUpdatedComment) {
		updatedComment = pUpdatedComment;
	}
}