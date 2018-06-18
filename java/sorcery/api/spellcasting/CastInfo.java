package sorcery.api.spellcasting;

import java.util.ArrayList;

import sorcery.api.element.ElementStack;

public class CastInfo {
	public ElementStack[] elements = new ElementStack[] {};
	public int mojoCost;
	
	/** 0 = Right; 1 = Left */
	public final int castingHand;
	
	public CastInfo(ElementStack[] elements, int mojoCost) {
		this.elements = elements;
		this.mojoCost = mojoCost;
		this.castingHand = 0;
	}
	
	public CastInfo(ElementStack[] elements, int mojoCost, int castingHand) {
		this.elements = elements;
		this.mojoCost = mojoCost;
		this.castingHand = castingHand;
	}
}
